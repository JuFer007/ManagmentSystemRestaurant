document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById('page-nuevo-pedido')) {
        listarPlatosParaPedido();
        cargarMesasEnSelect();
        inicializarBuscadorClientes();
    }
});

let resumenPedido = [];
let timeoutBusqueda;
let clienteSeleccionadoManualmente = false;

// ======================
// INICIALIZAR BUSCADOR DE CLIENTES
// ======================
function inicializarBuscadorClientes() {
    const inputCliente = document.getElementById('inputNuevoCliente');
    const listaClientes = document.getElementById('listaClientes');
    const idClienteSeleccionado = document.getElementById('idClienteSeleccionado');

    inputCliente.addEventListener('input', function() {
        const termino = this.value.trim();

        // Resetear cuando el usuario escribe de nuevo
        idClienteSeleccionado.value = '';
        clienteSeleccionadoManualmente = false;

        clearTimeout(timeoutBusqueda);

        if (termino.length < 2) {
            listaClientes.style.display = 'none';
            return;
        }

        timeoutBusqueda = setTimeout(async () => {
            try {
                const response = await fetch(`/system/clientes/buscar?termino=${encodeURIComponent(termino)}`);
                const clientes = await response.json();

                listaClientes.innerHTML = '';

                if (clientes.length > 0) {
                    clientes.forEach(cliente => {
                        const li = document.createElement('li');
                        li.className = 'list-group-item list-group-item-action';
                        li.style.cursor = 'pointer';
                        li.innerHTML = `
                            <strong>${cliente.nombreCliente} ${cliente.apellidosCliente}</strong>
                            <small class="text-muted d-block">DNI: ${cliente.dniCliente || 'N/A'}</small>
                        `;
                        li.onclick = () => seleccionarCliente(cliente, inputCliente, idClienteSeleccionado, listaClientes);
                        listaClientes.appendChild(li);
                    });

                    listaClientes.style.display = 'block';
                } else {
                    listaClientes.style.display = 'none';
                }

            } catch (error) {
                console.error('Error al buscar clientes:', error);
            }
        }, 300);
    });

    // Ocultar lista al hacer clic fuera
    document.addEventListener('click', function(e) {
        if (!inputCliente.contains(e.target) && !listaClientes.contains(e.target)) {
            listaClientes.style.display = 'none';
        }
    });
}

// ======================
// SELECCIONAR CLIENTE DE LA LISTA
// ======================
function seleccionarCliente(cliente, inputCliente, idClienteSeleccionado, listaClientes) {
    inputCliente.value = `${cliente.nombreCliente} ${cliente.apellidosCliente}`;
    idClienteSeleccionado.value = cliente.idCliente;
    clienteSeleccionadoManualmente = true;
    listaClientes.style.display = 'none';
    console.log('Cliente existente seleccionado, ID:', cliente.idCliente);
}

// ======================
// CARGAR PLATOS
// ======================
async function listarPlatosParaPedido() {
    const contenedor = document.getElementById("contenedorListaPlatos");
    if (!contenedor) return;
    contenedor.innerHTML = "";

    try {
        const response = await fetch("/system/platos/listar");
        if (!response.ok) throw new Error("Error al cargar los platos");
        const platos = await response.json();

        platos
            .filter(plato => plato.disponibilidad === 'Disponible')
            .forEach(plato => {
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

// ===============
// CARGAR MESAS
// ===============
async function cargarMesasEnSelect() {
    const selectMesa = document.getElementById("numeroMesa");
    if (!selectMesa) return;

    try {
        const response = await fetch("/mesa/numMesas");
        if (!response.ok) throw new Error("Error al cargar las mesas");

        const mesas = await response.json();

        selectMesa.innerHTML = `<option value="">Seleccione una mesa</option>`;

        mesas.forEach(mesa => {
            const option = document.createElement("option");
            option.value = mesa.idMesa;
            option.textContent = `Mesa ${mesa.numeroMesa}`;
            selectMesa.appendChild(option);
        });

    } catch (error) {
        console.error("Error al cargar mesas:", error);
        selectMesa.innerHTML = `<option value="">Error al cargar mesas</option>`;
    }
}

// ======================
// AGREGAR PLATO AL PEDIDO
// ======================
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
    cantidadInput.value = 1;
    mostrarToast(`${plato.nombrePlato} agregado al pedido.`, "success");
}

// ======================
// ACTUALIZAR TABLA
// ======================
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

// ======================
// ELIMINAR PLATO
// ======================
function eliminarDelPedido(index) {
    resumenPedido.splice(index, 1);
    actualizarResumenTabla();
}

// ======================
// 🔄 FINALIZAR PEDIDO (ACTUALIZADO)
// ======================
async function finalizarPedido() {
    const textoCliente = document.getElementById("inputNuevoCliente").value.trim();
    const idClienteSeleccionado = document.getElementById("idClienteSeleccionado").value;
    const idMesaSeleccionada = document.getElementById("numeroMesa").value;
    const totalTexto = document.getElementById("totalPedido").textContent;
    const totalPedido = parseFloat(totalTexto.replace('S/. ', ''));

    if (!idMesaSeleccionada) {
        mostrarToast("Seleccione una mesa antes de continuar.", "warning");
        return;
    }

    if (resumenPedido.length === 0) {
        mostrarToast("El pedido está vacío. Agregue al menos un plato.", "warning");
        return;
    }

    if (!textoCliente) {
        mostrarToast("Ingrese un nombre de cliente.", "warning");
        return;
    }

    let idClienteFinal = idClienteSeleccionado;

    try {
        // Si NO seleccionó ninguno de la lista, crear nuevo cliente
        if (!clienteSeleccionadoManualmente || !idClienteFinal) {
            console.log('🆕 No seleccionó cliente existente, creando nuevo...');

            const response = await fetch('/system/clientes/crear-desde-texto', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ texto: textoCliente })
            });

            if (!response.ok) throw new Error('Error al crear el cliente');

            const nuevoCliente = await response.json();
            idClienteFinal = nuevoCliente.idCliente;
            console.log('✅ Nuevo cliente creado con ID:', idClienteFinal);
        } else {
            console.log('✅ Usando cliente existente con ID:', idClienteFinal);
        }

        // Crear el pedido
        await crearPedido(idClienteFinal, totalPedido, parseInt(idMesaSeleccionada));

    } catch (error) {
        console.error('❌ Error en el proceso de finalizar pedido:', error);
        mostrarToast(error.message || "Ocurrió un error inesperado.", "error");
    }
}

// ==============
// CREAR PEDIDO
// ==============
async function crearPedido(idCliente, totalPedido, idMesa) {
    const pedidoData = {
        idCliente: idCliente,
        idEmpleado: 1,
        idMesa: idMesa,
        totalPedido: totalPedido,
        detalles: resumenPedido.map(item => ({
            idPlato: item.idPlato,
            cantidad: item.cantidad
        }))
    };

    console.log("📤 Pedido que se enviará:", pedidoData);

    const response = await fetch('/pedidos/nuevoPedido', {
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
    limpiarPedido();
}

// ================
// LIMPIAR PEDIDO
// ================
function limpiarPedido() {
    resumenPedido = [];
    actualizarResumenTabla();
    document.getElementById("inputNuevoCliente").value = "";
    document.getElementById("idClienteSeleccionado").value = "";
    document.getElementById("numeroMesa").selectedIndex = 0;
    clienteSeleccionadoManualmente = false;
    mostrarToast("Pedido limpiado.", "info");
}
