document.addEventListener("DOMContentLoaded", () => {
    // Solo ejecutar si estamos en la página de nuevo pedido
    if (document.getElementById('page-nuevo-pedido')) {
        listarPlatosParaPedido();
        cargarClientesEnSelect();
    }
});

// Un arreglo para mantener el estado del pedido actual
let resumenPedido = [];

// Carga los platos disponibles en la lista
async function listarPlatosParaPedido() {
    const contenedor = document.getElementById("contenedorListaPlatos");
    if (!contenedor) return;
    contenedor.innerHTML = "";

    try {
        const response = await fetch("/system/platos/listar");
        if (!response.ok) throw new Error("Error al cargar los platos");
        const platos = await response.json();

        platos.filter(plato => plato.disponibilidad === 'Disponible').forEach(plato => {
            const item = document.createElement("div");
            item.className = "list-group-item d-flex justify-content-between align-items-center flex-wrap plato-item";
            item.innerHTML = `
                <div>
                    <h6 class="mb-1 nombre-plato">${plato.nombrePlato}</h6>
                    <small>S/. ${plato.precioPlato.toFixed(2)}</small>
                </div>
                <div class="d-flex align-items-center mt-2 mt-md-0">
                    <input type="number" class="form-control form-control-sm" value="1" min="1" style="width: 60px; text-align: center;">
                    <button class="btn btn-primary btn-sm ms-2" onclick='agregarAPedido(${JSON.stringify(plato)}, this)'>
                        <i class="ri-add-line"></i> Agregar
                    </button>
                </div>`;
            contenedor.appendChild(item);
        });
    } catch (error) {
        console.error("Error al listar platos para pedido:", error);
        contenedor.innerHTML = `<p class="text-danger">No se pudieron cargar los platos.</p>`;
    }
}

// Carga los clientes en el <select>
async function cargarClientesEnSelect() {
    const select = document.getElementById("selectClientePedido");
    if (!select) return;
    try {
        const response = await fetch("/system/clientes/listar");
        const clientes = await response.json();
        clientes.forEach(cliente => {
            select.innerHTML += `<option value="${cliente.idCliente}">${cliente.nombreCliente} ${cliente.apellidosCliente}</option>`;
        });
    } catch (error) {
        console.error("Error al cargar clientes:", error);
    }
}

// Agrega un plato al resumen del pedido
function agregarAPedido(plato, boton) {
    const cantidadInput = boton.previousElementSibling;
    const cantidad = parseInt(cantidadInput.value);

    if (isNaN(cantidad) || cantidad <= 0) {
        mostrarToast("La cantidad debe ser un número positivo.", "warning");
        return;
    }

    const platoExistente = resumenPedido.find(item => item.idPlato === plato.idPlato);

    if (platoExistente) {
        platoExistente.cantidad += cantidad;
    } else {
        resumenPedido.push({ ...plato, cantidad });
    }

    actualizarResumenTabla();
    cantidadInput.value = 1; // Resetear cantidad
    mostrarToast(`${plato.nombrePlato} agregado al pedido.`, "success");
}

// Actualiza la tabla del resumen y el total
function actualizarResumenTabla() {
    const tbody = document.querySelector("#tablaResumenPedido tbody");
    const totalEl = document.getElementById("totalPedido");
    tbody.innerHTML = "";
    let totalGeneral = 0;

    resumenPedido.forEach((item, index) => {
        const subtotal = item.precioPlato * item.cantidad;
        totalGeneral += subtotal;
        tbody.innerHTML += `
            <tr>
                <td>${item.nombrePlato}</td>
                <td>${item.cantidad}</td>
                <td>S/. ${subtotal.toFixed(2)}</td>
                <td>
                    <button class="btn btn-danger btn-sm" onclick="eliminarDelPedido(${index})">
                        <i class="ri-delete-bin-line"></i>
                    </button>
                </td>
            </tr>`;
    });

    totalEl.textContent = `S/. ${totalGeneral.toFixed(2)}`;
}

// Elimina un item del pedido
function eliminarDelPedido(index) {
    resumenPedido.splice(index, 1);
    actualizarResumenTabla();
}


// Finaliza y guarda el pedido en la base de datos
async function finalizarPedido() {
    const nombreNuevoCliente = document.getElementById("inputNuevoCliente").value.trim();
    const idClienteExistente = document.getElementById("selectClientePedido").value;
    const totalTexto = document.getElementById("totalPedido").textContent;
    const totalPedido = parseFloat(totalTexto.replace('S/. ', ''));

    if (resumenPedido.length === 0) {
        mostrarToast("El pedido está vacío. Agregue al menos un plato.", "warning");
        return;
    }

    let idClienteFinal;

    try {
        // Prioridad 1: Crear un nuevo cliente si se ingresó un nombre
        if (nombreNuevoCliente) {
            const nuevoCliente = await crearNuevoCliente(nombreNuevoCliente);
            if (!nuevoCliente || !nuevoCliente.idCliente) {
                throw new Error("No se pudo crear el nuevo cliente.");
            }
            idClienteFinal = nuevoCliente.idCliente;
        } 
        // Prioridad 2: Usar el cliente existente si está seleccionado
        else if (idClienteExistente) {
            idClienteFinal = parseInt(idClienteExistente);
        } 
        // Si no hay cliente, mostrar error
        else {
            mostrarToast("Por favor, ingrese un nuevo cliente o seleccione uno existente.", "warning");
            return;
        }

        // Una vez que tenemos el ID del cliente, procedemos a crear el pedido
        await crearPedido(idClienteFinal, totalPedido);

    } catch (error) {
        console.error('Error en el proceso de finalizar pedido:', error);
        mostrarToast(error.message || "Ocurrió un error inesperado.", "error");
    }
}

// Función auxiliar para crear un nuevo cliente
async function crearNuevoCliente(nombreCompleto) {
    // Dividimos el nombre para tener nombre y apellido (es una aproximación simple)
    const partesNombre = nombreCompleto.split(' ');
    const nombre = partesNombre.shift() || ''; // El primer elemento es el nombre
    const apellidos = partesNombre.join(' ') || ''; // El resto son los apellidos

    const clienteData = {
        // Generamos un DNI temporal único para evitar conflictos en la BD.
        // El backend podría mejorarlo.
        dniCliente: `TEMP-${Date.now()}`, 
        nombreCliente: nombre,
        apellidosCliente: apellidos
    };

    const response = await fetch('/system/clientes/guardar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clienteData)
    });

    if (!response.ok) {
        throw new Error('Error al crear el nuevo cliente.');
    }
    return await response.json(); // Asumimos que el backend devuelve el cliente creado con su ID
}

// Función auxiliar para crear el pedido
async function crearPedido(idCliente, totalPedido) {
    // Preparamos los datos para enviar
    const pedidoData = {
        idCliente: idCliente,
        idMesero: 1, // Temporal: Deberías tener una forma de seleccionar el mesero
        idMesa: 1,   // Temporal: Deberías tener una forma de seleccionar la mesa
        totalPedido: totalPedido,
        detalles: resumenPedido.map(item => ({
            idPlato: item.idPlato,
            cantidad: item.cantidad
        }))
    };

    const response = await fetch('/pedidos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pedidoData),
    });

    if (!response.ok) {
        const errorData = await response.text();
        console.error("Error del servidor al crear pedido:", errorData);
        throw new Error('Error al guardar el pedido. Código: ' + response.status);
    }

    mostrarToast("Pedido creado exitosamente.", "success");
    limpiarPedido(); // Limpiamos la interfaz para un nuevo pedido
}

function limpiarPedido() {
    resumenPedido = [];
    actualizarResumenTabla();
    document.getElementById("inputNuevoCliente").value = "";
    document.getElementById("selectClientePedido").selectedIndex = 0;
    mostrarToast("Pedido limpiado.", "info");
}