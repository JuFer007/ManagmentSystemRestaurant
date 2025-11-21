let tablaPedidosDT;

document.addEventListener("DOMContentLoaded", () => {
});

function cargarPedidos() {
    if (tablaPedidosDT) {
        tablaPedidosDT.ajax.reload();
        return;
    }

    tablaPedidosDT = new DataTable('#tablaPedidos', {
        ajax: {
            url: "http://localhost:8080/pedidos",
            dataSrc: ''
        },
        columns: [
            { data: 'codigoPedido' },
            { data: null, render: (data, type, row) => `${row.nombreCliente} ${row.apellidosCliente}` },
            { data: 'fecha', className: 'text-start' },
            {
                data: 'totalPedido',
                render: (data) => `S/. ${data?.toFixed(2) || "0.00"}`
            },
            {
                data: 'estadoPedido',
                className:'text-start',
                render: (data) => `<span class="badge ${data.toLowerCase().includes('proceso') ? 'bg-warning' : 'bg-success'}">${data}</span>`
            },
            {
                data: 'idPedido',
                render: (data) => `<button class="btn btn-sm btn-danger" onclick="verDetallePedido(${data})"><i class="ri-eye-line"></i> Ver Detalle</button>`,
                orderable: false 
            }
        ],
        order: [[4, 'asc'], [2, 'desc']], 
        language: { url: 'https://cdn.datatables.net/plug-ins/2.0.8/i18n/es-ES.json' }
    });
}

function verDetallePedido(idPedido) {
    fetch(`http://localhost:8080/pedidos/${idPedido}`)
        .then(response => {
            if (!response.ok) throw new Error("Error obteniendo pedido");
            return response.json();
        })
        .then(pedido => {
          document.getElementById("numeroPedidoModal").innerText = pedido.codigoPedido;
          document.getElementById("clienteDetalle").innerText = `${pedido.nombreCliente} ${pedido.apellidosCliente}`;
          document.getElementById("fechaDetalle").innerText = pedido.fecha;

          const empleadoNombre = pedido.nombreEmpleado ?
                `${pedido.nombreEmpleado} ${pedido.apellidoPaternoEmpleado || ''} ${pedido.apellidoMaternoEmpleado || ''}`.trim() :
                "No asignado";
          document.getElementById("empleadoDetalle").innerText = empleadoNombre;
          const estado = document.getElementById("estadoDetalle");
          estado.innerText = pedido.estadoPedido;
          estado.className = "badge " + (pedido.estadoPedido === "Completado" ? "bg-success" :
          pedido.estadoPedido === "En proceso" ? "bg-warning" : "bg-secondary");

          const btnCambiar = document.getElementById("btnCambiarEstado");          btnCambiar.onclick = () => cambiarEstado(idPedido); // Asignar el ID al botón
          btnCambiar.style.display = (pedido.estadoPedido === "Completado") ? "none" : "inline-block";

          return fetch(`http://localhost:8080/detallesPedido/pedido/${idPedido}`);
        })
        .then(response => {
            if (!response.ok) throw new Error("Error obteniendo detalles");
            return response.json();
        })
        .then(detalles => {
            mostrarDetallePlatos(detalles);
            new bootstrap.Modal(document.getElementById("modalDetallePedido")).show();
        })
        .catch(error => {
            console.error("Error al abrir el modal:", error);
        });
}

function mostrarDetallePlatos(detalles) {
    const tbody = document.getElementById("listaPlatosDetalle");
    tbody.innerHTML = "";
    let total = 0;

    detalles.forEach(detalle => {
        const plato = detalle.idPlato?.nombrePlato || "—";
        const precio = detalle.idPlato?.precioPlato || 0;
        const cantidad = detalle.cantidad || 0;
        const subtotal = detalle.subTotal || (precio * cantidad);

        total += subtotal;

        tbody.innerHTML += `
            <tr>
                <td>${plato}</td>
                <td class="text-center">${cantidad}</td>
                <td class="text-end">S/. ${precio.toFixed(2)}</td>
                <td class="text-end">S/. ${subtotal.toFixed(2)}</td>
            </tr>
        `;
    });

    document.getElementById("totalDetalle").innerText = "S/. " + total.toFixed(2);
}



//Cambiar estado
async function cambiarEstado(idPedido) {
    const estadoActual = document.getElementById("estadoDetalle").innerText.trim();
    if (estadoActual === "Completado") {
        mostrarToast("Este pedido ya está completado.", "info");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/pedidos/${idPedido}/estado?nuevoEstado=Completado`, {
            method: "PUT"
        });

        if (!response.ok) {
            throw new Error("Error al actualizar el estado del pedido.");
        }

        document.getElementById("estadoDetalle").innerText = "Completado";
        document.getElementById("estadoDetalle").className = "badge bg-success";
        document.getElementById("btnCambiarEstado").style.display = "none";
        const modal = bootstrap.Modal.getInstance(document.getElementById("modalDetallePedido"));
        if (modal) modal.hide();
        tablaPedidosDT.ajax.reload();
        mostrarToast("Pedido completado y pago generado.", "success");
    } catch (err) {
        console.error("Error al cambiar estado:", err);
        mostrarToast(err.message, "danger");
    }
}

function filtrarPorEstado(estado) {
    if (tablaPedidosDT) {
        // Columna de estado es la 4 (índice 4)
        const filtro = (estado === 'Todos') ? '' : `^${estado}$`;
        tablaPedidosDT.column(4).search(filtro, true, false).draw();
    }
}
