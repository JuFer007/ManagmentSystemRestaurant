// Variables globales para paginación
let todosLosPedidos = [];
let pedidosPaginaActual = 1;
const pedidosPorPagina = 10;
let filtroEstadoActual = "Todos";

document.addEventListener("DOMContentLoaded", () => {
});

function cargarPedidos() {
    const tablaBody = document.querySelector("#tablaPedidosBody");
    
    try {
        fetch("http://localhost:8080/pedidos")
            .then(response => {
                if (!response.ok) throw new Error("Error al obtener los pedidos");
                return response.json();
            })
            .then(pedidos => {
                // Ordenar por prioridad de estado y luego por fecha
                pedidos.sort((a, b) => {
                    // Prioridad: "En proceso" primero, luego "Completado"
                    const prioridadEstado = {
                        "En proceso": 1,
                        "En Proceso": 1,
                        "Completado": 2
                    };
                    
                    const prioridadA = prioridadEstado[a.estadoPedido] || 3;
                    const prioridadB = prioridadEstado[b.estadoPedido] || 3;
                    
                    if (prioridadA !== prioridadB) {
                        return prioridadA - prioridadB;
                    }
                    
                    // Si tienen el mismo estado, ordenar por fecha ascendentemente
                    return new Date(a.fecha) - new Date(b.fecha);
                });
                
                todosLosPedidos = pedidos;
                pedidosPaginaActual = 1;
                aplicarFiltroYRenderizar();
                actualizarPaginacionPedidos();
            })
            .catch(error => {
                console.error("Error:", error);
                if (tablaBody) {
                    tablaBody.innerHTML = `<tr><td colspan='6' class='text-center text-danger'>Error al cargar pedidos</td></tr>`;
                }
                mostrarToast('Error al cargar los pedidos', "danger");
            });
    } catch (error) {
        console.error("Error:", error);
        mostrarToast('Error al cargar los pedidos', "danger");
    }
}

// ========== APLICAR FILTRO Y RENDERIZAR ==========
function aplicarFiltroYRenderizar() {
    let pedidosFiltrados = [...todosLosPedidos];
    
    if (filtroEstadoActual !== "Todos") {
        pedidosFiltrados = pedidosFiltrados.filter(p => p.estadoPedido === filtroEstadoActual);
    }
    
    renderizarPedidos(pedidosFiltrados);
}

// ========== RENDERIZAR PEDIDOS ==========
function renderizarPedidos(pedidos) {
    const tablaBody = document.querySelector("#tablaPedidosBody");
    if (!tablaBody) return;
    
    tablaBody.innerHTML = "";
    
    if (pedidos.length === 0) {
        tablaBody.innerHTML = "<tr><td colspan='6' class='text-center'>No se encontraron pedidos</td></tr>";
        actualizarInfoPedidos(0, 0);
        return;
    }
    
    // Calcular paginación
    const inicio = (pedidosPaginaActual - 1) * pedidosPorPagina;
    const fin = inicio + pedidosPorPagina;
    const pedidosPaginados = pedidos.slice(inicio, fin);
    
    pedidosPaginados.forEach(pedido => {
        const estadoBadge = pedido.estadoPedido?.toLowerCase().includes('proceso') ? 'badge-warning' : 'badge-success';
        const row = `
            <tr>
                <td>${pedido.codigoPedido}</td>
                <td>${pedido.nombreCliente} ${pedido.apellidosCliente}</td>
                <td>${pedido.fecha}</td>
                <td>S/. ${pedido.totalPedido?.toFixed(2) || "0.00"}</td>
                <td><span class="badge ${estadoBadge}">${pedido.estadoPedido}</span></td>
                <td style="text-align: center;">
                    <button class="btn-action btn-view" onclick="verDetallePedido(${pedido.idPedido})" title="Ver Detalle">
                        <i class="ri-eye-line"></i> Ver Detalle
                    </button>
                </td>
            </tr>
        `;
        tablaBody.insertAdjacentHTML("beforeend", row);
    });
    
    const totalFiltrados = pedidos.length;
    actualizarInfoPedidos(pedidosPaginados.length, totalFiltrados);
}

// ========== ACTUALIZAR INFORMACIÓN DE PAGINACIÓN ==========
function actualizarInfoPedidos(mostrados, total) {
    const infoElement = document.getElementById("infoPedidos");
    if (infoElement) {
        const inicio = (pedidosPaginaActual - 1) * pedidosPorPagina + 1;
        const fin = Math.min(inicio + mostrados - 1, total);
        infoElement.textContent = `Mostrando ${inicio} a ${fin} de ${total} pedidos`;
    }
}

// ========== CREAR PAGINACIÓN ==========
function actualizarPaginacionPedidos() {
    const paginacionNav = document.getElementById("paginacionPedidosNav");
    if (!paginacionNav) return;
    
    let pedidosFiltrados = [...todosLosPedidos];
    if (filtroEstadoActual !== "Todos") {
        pedidosFiltrados = pedidosFiltrados.filter(p => p.estadoPedido === filtroEstadoActual);
    }
    
    const totalPaginas = Math.ceil(pedidosFiltrados.length / pedidosPorPagina);
    
    if (totalPaginas <= 1) {
        paginacionNav.innerHTML = "";
        return;
    }
    
    let html = "";
    
    // Botón anterior
    html += `
        <li class="page-item ${pedidosPaginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaPedidos(${pedidosPaginaActual - 1}); return false;">Anterior</a>
        </li>
    `;
    
    // Números de página
    for (let i = 1; i <= totalPaginas; i++) {
        if (i === 1 || i === totalPaginas || (i >= pedidosPaginaActual - 1 && i <= pedidosPaginaActual + 1)) {
            html += `
                <li class="page-item ${i === pedidosPaginaActual ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPaginaPedidos(${i}); return false;">${i}</a>
                </li>
            `;
        } else if (i === pedidosPaginaActual - 2 || i === pedidosPaginaActual + 2) {
            html += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
        }
    }
    
    // Botón siguiente
    html += `
        <li class="page-item ${pedidosPaginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaPedidos(${pedidosPaginaActual + 1}); return false;">Siguiente</a>
        </li>
    `;
    
    paginacionNav.innerHTML = html;
}

// ========== CAMBIAR PÁGINA ==========
function cambiarPaginaPedidos(pagina) {
    let pedidosFiltrados = [...todosLosPedidos];
    if (filtroEstadoActual !== "Todos") {
        pedidosFiltrados = pedidosFiltrados.filter(p => p.estadoPedido === filtroEstadoActual);
    }
    
    const totalPaginas = Math.ceil(pedidosFiltrados.length / pedidosPorPagina);
    if (pagina < 1 || pagina > totalPaginas) return;
    
    pedidosPaginaActual = pagina;
    aplicarFiltroYRenderizar();
    actualizarPaginacionPedidos();
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

          const btnCambiar = document.getElementById("btnCambiarEstado");
          btnCambiar.onclick = () => cambiarEstado(idPedido);
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
        cargarPedidos();
        mostrarToast("Pedido completado y pago generado.", "success");
    } catch (err) {
        console.error("Error al cambiar estado:", err);
        mostrarToast(err.message, "danger");
    }
}

function filtrarPorEstado(estado) {
    filtroEstadoActual = estado;
    pedidosPaginaActual = 1;
    aplicarFiltroYRenderizar();
    actualizarPaginacionPedidos();
}
