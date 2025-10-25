document.addEventListener("DOMContentLoaded", cargarPedidos);

//Cargar todos los pedidos en la tabla
function cargarPedidos() {
    fetch("http://localhost:8080/pedidos")
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener los pedidos");
            return response.json();
        })
        .then(pedidos => {
            pedidos.sort((a, b) => {
                const prioridadEstado = {
                    "En Proceso": 1,
                    "En proceso": 1,
                    "Completado": 2
                };

                const estadoA = prioridadEstado[a.estadoPedido] || 3;
                const estadoB = prioridadEstado[b.estadoPedido] || 3;

                if (estadoA !== estadoB) return estadoA - estadoB;

                return new Date(b.fecha) - new Date(a.fecha);
            });

            const tbody = document.querySelector("#tablaPedidos tbody");
            tbody.innerHTML = "";

            pedidos.forEach(pedido => {
                tbody.innerHTML += `
                    <tr>
                        <td>${pedido.codigoPedido}</td>
                        <td>${pedido.nombreCliente} ${pedido.apellidosCliente}</td>
                        <td>${pedido.fecha}</td>
                        <td>S/. ${pedido.totalPedido?.toFixed(2) || "0.00"}</td>
                        <td>
                            <span class="badge ${pedido.estadoPedido.toLowerCase() === 'en proceso' ? 'bg-warning' : 'bg-success'}">
                                ${pedido.estadoPedido}
                            </span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-danger" onclick="verDetallePedido(${pedido.idPedido})">
                                <i class="ri-eye-line"></i> Ver Detalle
                            </button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(error => console.error("Error al cargar pedidos:", error));
}

function verDetallePedido(idPedido) {
    document.getElementById("numeroPedidoModal").innerText = idPedido;

    fetch(`http://localhost:8080/pedidos/${idPedido}`)
        .then(response => {
            if (!response.ok) throw new Error("Error obteniendo pedido");
            return response.json();
        })
        .then(pedido => {
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

          const btnCambiar = document.getElementById("btnCambiarEstado");
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
function cambiarEstado() {
    const idPedido = document.getElementById("numeroPedidoModal").innerText;
    const estadoActual = document.getElementById("estadoDetalle").innerText;

    if (estadoActual === "Completado") {
        console.log("Este pedido ya está completado.");
        return;
    }

    fetch(`http://localhost:8080/pedidos/${idPedido}/estado?nuevoEstado=Completado`, {
        method: "PUT"
    })

    .then(resp => {
        if (!resp.ok) throw new Error("Error al actualizar estado");
        return resp.text();
    })
    .then(() => {
        document.getElementById("estadoDetalle").innerText = "Completado";
        document.getElementById("estadoDetalle").className = "badge bg-success";
        document.getElementById("btnCambiarEstado").style.display = "none";
        const modal = bootstrap.Modal.getInstance(document.getElementById("modalDetallePedido"));
        modal.hide();
        cargarPedidos();
        mostrarToast("Estado de pedido cambiado a completado","success");
    })
    .catch(err => console.error("Error:", err));
}

function filtrarPedidos(texto) {
    const filas = document.querySelectorAll("#tablaPedidos tbody tr");
    texto = texto.toLowerCase();

    filas.forEach(fila => {
        const codigo = fila.children[0].innerText.toLowerCase();
        const cliente = fila.children[1].innerText.toLowerCase();
        fila.style.display = (codigo.includes(texto) || cliente.includes(texto)) ? "" : "none";
    });
}

let ultimoFiltro = "todos";

function filtrarPorEstado(estado) {
    ultimoFiltro = estado.toLowerCase();
    const filas = document.querySelectorAll("#tablaPedidos tbody tr");
    filas.forEach(fila => {
        const estadoFila = fila.querySelector(".badge")
            ? fila.querySelector(".badge").innerText.toLowerCase()
            : fila.children[4].innerText.toLowerCase();
        if (ultimoFiltro === "todos" || ultimoFiltro === "") {
            fila.style.display = "";
        } else {
            fila.style.display = estadoFila === ultimoFiltro ? "" : "none";
        }
    });
}
