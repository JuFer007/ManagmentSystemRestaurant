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


// TODO: Implementar la lógica para finalizar y limpiar el pedido
function finalizarPedido() {
    // Aquí irá la lógica para enviar el pedido al backend
    mostrarToast("Función 'Finalizar Pedido' no implementada.", "info");
}

function limpiarPedido() {
    resumenPedido = [];
    actualizarResumenTabla();
    mostrarToast("Pedido limpiado.", "info");
}