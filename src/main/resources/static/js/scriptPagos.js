// Variables globales para paginación
let todosLosPagos = [];
let pagosPaginaActual = 1;
const pagosPorPagina = 10;

function cargarPagos() {
    const tablaBody = document.querySelector("#tablaPagosBody");
    
    try {
        fetch("/system/pagos/listar")
            .then(response => {
                if (!response.ok) throw new Error("Error al obtener los pagos");
                return response.json();
            })
            .then(pagos => {
                // Ordenar por prioridad de estado y luego por fecha
                pagos.sort((a, b) => {
                    // Prioridad: Pendiente primero, luego Pagado, luego Anulado
                    const prioridadEstado = {
                        "Pendiente": 1,
                        "Pagado": 2,
                        "Anulado": 3
                    };
                    
                    const prioridadA = prioridadEstado[a.estadoPago] || 4;
                    const prioridadB = prioridadEstado[b.estadoPago] || 4;
                    
                    if (prioridadA !== prioridadB) {
                        return prioridadA - prioridadB;
                    }
                    
                    // Si tienen el mismo estado, ordenar por fecha ascendentemente
                    return new Date(a.fechaPago) - new Date(b.fechaPago);
                });
                
                todosLosPagos = pagos;
                pagosPaginaActual = 1;
                renderizarPagos(todosLosPagos);
                actualizarPaginacionPagos();
            })
            .catch(error => {
                console.error("Error:", error);
                if (tablaBody) {
                    tablaBody.innerHTML = `<tr><td colspan='6' class='text-center text-danger'>Error al cargar pagos</td></tr>`;
                }
                mostrarToast('Error al cargar los pagos', "danger");
            });
    } catch (error) {
        console.error("Error:", error);
        mostrarToast('Error al cargar los pagos', "danger");
    }
}

// ========== RENDERIZAR PAGOS ==========
function renderizarPagos(pagos) {
    const tablaBody = document.querySelector("#tablaPagosBody");
    if (!tablaBody) return;
    
    tablaBody.innerHTML = "";
    
    if (pagos.length === 0) {
        tablaBody.innerHTML = "<tr><td colspan='6' class='text-center'>No se encontraron pagos</td></tr>";
        actualizarInfoPagos(0, 0);
        return;
    }
    
    // Calcular paginación
    const inicio = (pagosPaginaActual - 1) * pagosPorPagina;
    const fin = inicio + pagosPorPagina;
    const pagosPaginados = pagos.slice(inicio, fin);
    
    pagosPaginados.forEach(pago => {
        let badgeClass = 'badge-secondary';
        if (pago.estadoPago === 'Pagado') badgeClass = 'badge-success';
        else if (pago.estadoPago === 'Pendiente') badgeClass = 'badge-warning';
        else if (pago.estadoPago === 'Anulado') badgeClass = 'badge-danger';
        
        const disabled = pago.estadoPago !== "Pagado" ? "disabled" : "";
        const disabledClass = disabled ? "disabled" : "";
        
        let btnEditar = "";
        if (pago.estadoPago !== "Pagado") {
            btnEditar = `
                <button class="btn-action btn-edit" onclick="abrirModalCambiarEstado(${pago.idPago})" title="Editar Pago">
                    <i class="ri-pencil-line"></i> Editar
                </button>`;
        }
        
        const row = `
            <tr>
                <td class="text-center">${pago.codigoPedido}</td>
                <td class="text-center">${pago.fechaPago}</td>
                <td>${pago.metodoPago}</td>
                <td>S/. ${pago.montoPago.toFixed(2)}</td>
                <td><span class="badge ${badgeClass}">${pago.estadoPago}</span></td>
                <td style="text-align: center;">
                    <button class="btn-action btn-primary-icon ${disabledClass}" title="Ver Ticket"
                        onclick="visualizarTicket(${pago.idPago})" ${disabled}>
                        <i class="ri-file-text-line"></i> Ticket
                    </button>
                    ${btnEditar}
                </td>
            </tr>
        `;
        tablaBody.insertAdjacentHTML("beforeend", row);
    });
    
    actualizarInfoPagos(pagosPaginados.length, pagos.length);
}

// ========== ACTUALIZAR INFORMACIÓN DE PAGINACIÓN ==========
function actualizarInfoPagos(mostrados, total) {
    const infoElement = document.getElementById("infoPagos");
    if (infoElement) {
        const inicio = (pagosPaginaActual - 1) * pagosPorPagina + 1;
        const fin = Math.min(inicio + mostrados - 1, total);
        infoElement.textContent = `Mostrando ${inicio} a ${fin} de ${total} pagos`;
    }
}

// ========== CREAR PAGINACIÓN ==========
function actualizarPaginacionPagos() {
    const paginacionNav = document.getElementById("paginacionPagosNav");
    if (!paginacionNav) return;
    
    const totalPaginas = Math.ceil(todosLosPagos.length / pagosPorPagina);
    
    if (totalPaginas <= 1) {
        paginacionNav.innerHTML = "";
        return;
    }
    
    let html = "";
    
    // Botón anterior
    html += `
        <li class="page-item ${pagosPaginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaPagos(${pagosPaginaActual - 1}); return false;">Anterior</a>
        </li>
    `;
    
    // Números de página
    for (let i = 1; i <= totalPaginas; i++) {
        if (i === 1 || i === totalPaginas || (i >= pagosPaginaActual - 1 && i <= pagosPaginaActual + 1)) {
            html += `
                <li class="page-item ${i === pagosPaginaActual ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPaginaPagos(${i}); return false;">${i}</a>
                </li>
            `;
        } else if (i === pagosPaginaActual - 2 || i === pagosPaginaActual + 2) {
            html += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
        }
    }
    
    // Botón siguiente
    html += `
        <li class="page-item ${pagosPaginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaPagos(${pagosPaginaActual + 1}); return false;">Siguiente</a>
        </li>
    `;
    
    paginacionNav.innerHTML = html;
}

// ========== CAMBIAR PÁGINA ==========
function cambiarPaginaPagos(pagina) {
    const totalPaginas = Math.ceil(todosLosPagos.length / pagosPorPagina);
    if (pagina < 1 || pagina > totalPaginas) return;
    
    pagosPaginaActual = pagina;
    renderizarPagos(todosLosPagos);
    actualizarPaginacionPagos();
}

async function abrirModalCambiarEstado(idPago) {
    try {
        const response = await fetch(`/system/pagos/buscar/${idPago}`);
        if (!response.ok) throw new Error('No se pudo obtener los datos del pago.');
        const pago = await response.json();

        document.getElementById('idPagoEditar').value = pago.idPago;
        document.getElementById('codigoPedidoPago').value = pago.codigoPedido;
        document.getElementById('fechaPagoEditar').value = pago.fechaPago;
        document.getElementById('montoPagoEditar').value = pago.montoPago.toFixed(2);
        document.getElementById('metodoPagoEditar').value = pago.metodoPago;
        document.getElementById('estadoPagoEditar').value = pago.estadoPago;

        const pedidoResp = await fetch(`/pedidos/buscar-por-codigo/${pago.codigoPedido}`);
        if (!pedidoResp.ok) throw new Error("No se pudo obtener el pedido.");

        const pedido = await pedidoResp.json();

        if (pedido.idCliente && pedido.idCliente.dniCliente && pedido.idCliente.dniCliente.trim() !== "") {
            document.getElementById("containerDni").style.display = "none";
        } else {
            document.getElementById("containerDni").style.display = "block";
        }

        const modal = new bootstrap.Modal(document.getElementById('modalEditarPago'));
        modal.show();
        document.getElementById('btnActualizarPago').onclick = () => actualizarPago();
    } catch (error) {
        console.error('Error al abrir modal de edición:', error);
        mostrarToast(error.message, 'danger');
    }
}

async function actualizarPago() {
    const idPago = document.getElementById('idPagoEditar').value;
    const metodoPago = document.getElementById('metodoPagoEditar').value;
    const estadoPago = document.getElementById('estadoPagoEditar').value;
    const nuevoDni = document.getElementById('dniClientePago').value.trim();
    const codigoPedido = document.getElementById('codigoPedidoPago').value.trim();

    try {
        const pedidoResp = await fetch(`/pedidos/buscar-por-codigo/${codigoPedido}`);

        if (!pedidoResp.ok) {
            throw new Error(`No se encontró el pedido con el código: ${codigoPedido}`);
        }

        const pedido = await pedidoResp.json();

        if (nuevoDni !== "") {
            const dniResp = await fetch(`/system/clientes/actualizar-dni`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    idPedido: pedido.idPedido,
                    nuevoDni: nuevoDni
                })
            });

            if (!dniResp.ok) {
                throw new Error("Error al actualizar el DNI.");
            }
        }

        const url = `/system/pagos/actualizarDatos/${idPago}?metodoPago=${encodeURIComponent(metodoPago)}&estadoPago=${encodeURIComponent(estadoPago)}`;
        const pagoResp = await fetch(url, { method: 'PUT' });

        if (!pagoResp.ok) {
            throw new Error("Error al actualizar el pago.");
        }

        const modalInstance = bootstrap.Modal.getInstance(document.getElementById('modalEditarPago'));
        if (modalInstance) modalInstance.hide();

        cargarPagos();
        mostrarToast('Pago actualizado correctamente.', 'success');

    } catch (error) {
        console.error("Error:", error);
        mostrarToast(error.message, 'danger');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const modalEl = document.getElementById('modalEditarPago');
    if (modalEl) {
        modalEl.addEventListener('hidden.bs.modal', () => {
            const btn = document.getElementById('btnActualizarPago');
            if (btn) {
                btn.onclick = null;
            }
        });
    }
});

async function imprimirTicketDesdeCodigo(codigoPedido) {
    try {
        const resp = await fetch(`/pedidos/buscar-por-codigo/${codigoPedido}`);

        if (!resp.ok) {
            throw new Error(`No se encontró el pedido con código ${codigoPedido}`);
        }

        const pedido = await resp.json();
        const idPedido = pedido.idPedido;

        imprimirTicketPedido(idPedido);

    } catch (error) {
        console.error("Error:", error);
        mostrarError(error.message);
    }
}

const style = document.createElement('style');
style.innerHTML = `
    .badge {
        display: inline-block;
        padding: .35em .65em;
        font-size: .75em;
        font-weight: 700;
        line-height: 1;
        color: #fff;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: .25rem;
    }
    .badge-success { background-color: #28a745; }
    .badge-warning { background-color: #ffc107; color: #000; }
    .badge-danger { background-color: #dc3545; }
    .badge-secondary { background-color: #6c757d; }
`;
document.head.appendChild(style);
