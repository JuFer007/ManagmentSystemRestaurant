document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById('page-nuevo-pedido')) {
        listarPlatosParaPedido();
        cargarClientesEnSelect();
        cargarMesasEnSelect();
    }
});

let resumenPedido = [];

// ======================
// 📦 CARGAR PLATOS
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

// ======================
// 👤 CARGAR CLIENTES
// ======================
async function cargarClientesEnSelect() {
    const select = document.getElementById("selectClientePedido");
    if (!select) return;
    try {
        const response = await fetch("/system/clientes/listar");
        const clientes = await response.json();
        clientes.forEach(cliente => {
            select.innerHTML += `<option value="${cliente.idCliente}">
                ${cliente.nombreCliente} ${cliente.apellidosCliente}
            </option>`;
        });
    } catch (error) {
        console.error("Error al cargar clientes:", error);
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
// FINALIZAR PEDIDO
// ======================
async function finalizarPedido() {
    const nombreNuevoCliente = document.getElementById("inputNuevoCliente").value.trim();
    const idClienteExistente = document.getElementById("selectClientePedido").value;
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

    let idClienteFinal;

    try {
        if (nombreNuevoCliente) {
            const nuevoCliente = await crearNuevoCliente(nombreNuevoCliente);
            idClienteFinal = nuevoCliente.idCliente;
        } else if (idClienteExistente) {
            idClienteFinal = parseInt(idClienteExistente);
        } else {
            mostrarToast("Seleccione o ingrese un cliente.", "warning");
            return;
        }

        await crearPedido(idClienteFinal, totalPedido, parseInt(idMesaSeleccionada));

    } catch (error) {
        console.error('Error en el proceso de finalizar pedido:', error);
        mostrarToast(error.message || "Ocurrió un error inesperado.", "error");
    }
}

// ======================
// CREAR CLIENTE NUEVO
// ======================
async function crearNuevoCliente(nombreCompleto) {
    const partesNombre = nombreCompleto.split(' ');
    const nombre = partesNombre.shift() || '';
    const apellidos = partesNombre.join(' ') || '';

    const clienteData = {
        dniCliente: `TEMP-${Date.now()}`,
        nombreCliente: nombre,
        apellidosCliente: apellidos
    };

    const response = await fetch('/system/clientes/guardar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clienteData)
    });

    if (!response.ok) throw new Error('Error al crear el nuevo cliente.');
    return await response.json();
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
    document.getElementById("selectClientePedido").selectedIndex = 0;
    document.getElementById("numeroMesa").selectedIndex = 0;
    mostrarToast("Pedido limpiado.", "info");
}
