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
            { "data": "idPago", className: 'text-center', width: '7%'},
            { "data": "fechaPago", className: 'text-center', width: '20%' },
            { "data": "metodoPago", className: 'text-start', width: '15%'},
            {
                "data": "montoPago",
                "render": function (data) {
                    return `S/. ${data.toFixed(2)}`;
                }, className: 'text-center', width: '15%'
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
                render: (data, type, row) => `
                    <button class="btn btn-info btn-sm btn-ver" title="Ver Detalles">
                        <i class="ri-eye-line"></i>
                    </button>
                    <button class="btn btn-warning btn-sm" onclick="abrirModalCambiarEstado(${row.idPago})">
                        <i class="ri-pencil-line"></i> Editar
                    </button>`,
                orderable: false,
                searchable: false
            }
        ],
        language: { url: 'https://cdn.datatables.net/plug-ins/2.0.8/i18n/es-ES.json' }
    });
}

function abrirModalCambiarEstado(id) {
    Swal.fire({
        title: 'Cambiar Estado del Pago',
        text: 'Seleccione el nuevo estado para el pago.',
        input: 'select',
        inputOptions: {
            'Pagado': 'Pagado',
            'Pendiente': 'Pendiente',
            'Anulado': 'Anulado'
        },
        showCancelButton: true,
        confirmButtonText: 'Actualizar',
        cancelButtonText: 'Cancelar',
        showLoaderOnConfirm: true,
        preConfirm: (nuevoEstado) => {
            if (!nuevoEstado) {
                Swal.showValidationMessage('Debe seleccionar un estado');
                return false;
            }
            return fetch(`/system/pagos/cambiarEstado/${id}?estadoPago=${nuevoEstado}`, {
                method: 'PUT'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.statusText);
                }
                return response.text();
            })
            .catch(error => {
                Swal.showValidationMessage(`La solicitud falló: ${error}`);
            });
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: '¡Éxito!',
                text: result.value,
                icon: 'success',
                timer: 2000
            });
            tablaPagosDT.ajax.reload(); // Recargar la tabla para mostrar el cambio
        }
    });
}

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
