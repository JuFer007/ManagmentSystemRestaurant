let tablaPagosDT;

function cargarPagos() {
    if (tablaPagosDT) {
        tablaPagosDT.ajax.reload();
        return;
    }
    tablaPagosDT = new DataTable('#tablaPagos', {
        ajax: {
            url: "/system/pagos/listar",
            dataSrc: ""
        },
        columns: [
            { "data": "codigoPedido", className: 'text-center'},
            { "data": "fechaPago", className: 'text-center' },
            { "data": "metodoPago", className: 'text-start'},
            {
                "data": "montoPago",
                "render": function (data) {
                    return `S/. ${data.toFixed(2)}`;
                }, className: 'text-start'
            },
            {
                "data": "estadoPago",
                "render": function (data) {
                    let badgeClass = 'badge-secondary';
                    if (data === 'Pagado') {
                        badgeClass = 'badge-success';
                    } else if (data === 'Pendiente') {
                        badgeClass = 'badge-warning';
                    } else if (data === 'Anulado') {
                        badgeClass = 'badge-danger';
                    }
                    return `<span class="badge ${badgeClass}">${data}</span>`;
                }
            },
            {
                "data": null,
                render: (data, type, row) => {
                    let btnDescargar = `
                        <button class="btn btn-info btn-sm btn-ver" title="Descargar">
                            <i class="ri-download-line"></i> Descargar
                        </button>`;

                    // Ocultar el botón Editar si el estado es Pagado
                    let btnEditar = "";
                    if (row.estadoPago !== "Pagado") {
                        btnEditar = `
                            <button class="btn btn-warning btn-sm" onclick="abrirModalCambiarEstado(${row.idPago})">
                                <i class="ri-pencil-line"></i> Editar
                            </button>`;
                    }

                    return btnDescargar + btnEditar;
                },
                orderable: false,
                searchable: false
            }

        ],
        language: { url: 'https://cdn.datatables.net/plug-ins/2.0.8/i18n/es-ES.json' }
    });
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

        tablaPagosDT.ajax.reload();
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

// Estilos para los badges de estado (puedes agregarlos a tu CSS principal)
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
