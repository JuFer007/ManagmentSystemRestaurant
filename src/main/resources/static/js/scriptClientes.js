// Variables globales para paginación
let todosLosClientes = [];
let clientesPaginaActual = 1;
const clientesPorPagina = 10;

// ========== CARGAR CLIENTES ==========
async function cargarClientes() {
    const tablaBody = document.querySelector("#tablaClientesBody");
    
    try {
        const response = await fetch('/system/clientes/listar');
        if (!response.ok) throw new Error("Error al obtener los clientes");
        
        todosLosClientes = await response.json();
        clientesPaginaActual = 1;
        renderizarClientes(todosLosClientes);
        actualizarPaginacionClientes();
    } catch (error) {
        console.error("Error:", error);
        tablaBody.innerHTML = `<tr><td colspan='4' class='text-center text-danger'>Error al cargar clientes</td></tr>`;
        mostrarToast('Error al cargar los clientes', "danger");
    }
}

// ========== RENDERIZAR CLIENTES ==========
function renderizarClientes(clientes) {
    const tablaBody = document.querySelector("#tablaClientesBody");
    tablaBody.innerHTML = "";
    
    if (clientes.length === 0) {
        tablaBody.innerHTML = "<tr><td colspan='4' class='text-center'>No se encontraron clientes</td></tr>";
        actualizarInfoClientes(0, 0);
        return;
    }
    
    // Calcular paginación
    const inicio = (clientesPaginaActual - 1) * clientesPorPagina;
    const fin = inicio + clientesPorPagina;
    const clientesPaginados = clientes.slice(inicio, fin);
    
    clientesPaginados.forEach(cliente => {
        const row = `
            <tr>
                <td>${cliente.dniCliente}</td>
                <td>${cliente.nombreCliente}</td>
                <td>${cliente.apellidosCliente}</td>
                <td style="text-align: center;">
                    <button class="btn-action btn-edit" onclick="editarCliente(${cliente.idCliente})" title="Editar Cliente">
                        <i class="ri-edit-2-line"></i> Editar
                    </button>
                </td>
            </tr>
        `;
        tablaBody.insertAdjacentHTML("beforeend", row);
    });
    
    actualizarInfoClientes(clientesPaginados.length, clientes.length);
}

// ========== ACTUALIZAR INFORMACIÓN DE PAGINACIÓN ==========
function actualizarInfoClientes(mostrados, total) {
    const infoElement = document.getElementById("infoClientes");
    if (infoElement) {
        const inicio = (clientesPaginaActual - 1) * clientesPorPagina + 1;
        const fin = Math.min(inicio + mostrados - 1, total);
        infoElement.textContent = `Mostrando ${inicio} a ${fin} de ${total} clientes`;
    }
}

// ========== CREAR PAGINACIÓN ==========
function actualizarPaginacionClientes() {
    const paginacionNav = document.getElementById("paginacionClientesNav");
    if (!paginacionNav) return;
    
    const totalPaginas = Math.ceil(todosLosClientes.length / clientesPorPagina);
    
    if (totalPaginas <= 1) {
        paginacionNav.innerHTML = "";
        return;
    }
    
    let html = "";
    
    // Botón anterior
    html += `
        <li class="page-item ${clientesPaginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaClientes(${clientesPaginaActual - 1}); return false;">Anterior</a>
        </li>
    `;
    
    // Números de página
    for (let i = 1; i <= totalPaginas; i++) {
        if (i === 1 || i === totalPaginas || (i >= clientesPaginaActual - 1 && i <= clientesPaginaActual + 1)) {
            html += `
                <li class="page-item ${i === clientesPaginaActual ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPaginaClientes(${i}); return false;">${i}</a>
                </li>
            `;
        } else if (i === clientesPaginaActual - 2 || i === clientesPaginaActual + 2) {
            html += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
        }
    }
    
    // Botón siguiente
    html += `
        <li class="page-item ${clientesPaginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaClientes(${clientesPaginaActual + 1}); return false;">Siguiente</a>
        </li>
    `;
    
    paginacionNav.innerHTML = html;
}

// ========== CAMBIAR PÁGINA ==========
function cambiarPaginaClientes(pagina) {
    const totalPaginas = Math.ceil(todosLosClientes.length / clientesPorPagina);
    if (pagina < 1 || pagina > totalPaginas) return;
    
    clientesPaginaActual = pagina;
    renderizarClientes(todosLosClientes);
    actualizarPaginacionClientes();
}

// ========== ABRIR MODAL NUEVO CLIENTE ==========
function abrirFormulario() {
    document.getElementById("formCliente").reset();
    const dniInput = document.getElementById("dniCliente");
    dniInput.disabled = false;
    document.getElementById("idCliente").value = "";
    
    const modalTitle = document.querySelector("#modalCliente .modal-title");
    modalTitle.innerHTML = '<i class="ri-user-add-line me-2"></i>Nuevo Cliente';
    
    const btnGuardar = document.getElementById("btnGuardarCliente");
    btnGuardar.innerHTML = '<i class="ri-save-line me-1"></i>Agregar';
    
    const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("modalCliente"));
    modal.show();
}

// ========== CERRAR MODAL ==========
function cerrarFormulario() {
    const modalElement = document.getElementById("modalCliente");
    const modal = bootstrap.Modal.getInstance(modalElement);
    if (modal) {
        modal.hide();
    }
}

// ========== GUARDAR / ACTUALIZAR CLIENTE ==========
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formCliente");

    if (form) {
        form.addEventListener("submit", e => {
            e.preventDefault();

            const idCliente = document.getElementById("idCliente").value;
            const dniCliente = document.getElementById("dniCliente").value.trim();
            const nombreCliente = document.getElementById("nombreCliente").value.trim();
            const apellidosCliente = document.getElementById("apellidosCliente").value.trim();

            if (!idCliente && !/^\d{8}$/.test(dniCliente)) {
                mostrarToast("El DNI debe contener exactamente 8 dígitos numéricos.", "warning");
                return;
            }

            if (!/^[a-zA-Z\s]+$/.test(nombreCliente)) {
                mostrarToast("El nombre solo debe contener letras y espacios.", "warning");
                return;
            }

            if (apellidosCliente.split(' ').filter(word => word.length > 0).length < 2) {
                mostrarToast("Debe ingresar al menos dos apellidos.", "warning");
                return;
            }

            const cliente = {
                idCliente: idCliente ? parseInt(idCliente) : null,
                dniCliente: dniCliente,
                nombreCliente: nombreCliente,
                apellidosCliente: apellidosCliente
            };

            const url = idCliente
                ? "/system/clientes/actualizar"
                : "/system/clientes/guardar";

            fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cliente)
            })
            .then(res => {
                if (!res.ok) {
                    return res.text().then(text => { throw new Error(text || "Error en el servidor") });
                }
                return res.json();
            })
            .then(data => {
                const mensaje = idCliente ? "Cliente actualizado con éxito." : "Cliente agregado exitosamente.";
                mostrarToast(mensaje, "success");
                cerrarFormulario();
                cargarClientes();
            })
            .catch(err => {
                console.error("Error al guardar cliente:", err);
                const mensajeError = err.message.includes("could not execute statement") ? "El DNI ya está registrado." : "No se pudo guardar el cliente.";
                mostrarToast(mensajeError, "danger");
            });
        });
    }
});

// ========== EDITAR CLIENTE ==========
function editarCliente(id) {
    fetch(`/system/clientes/buscar/${id}`)
        .then(async res => {
            if (!res.ok) {
                throw new Error(`Error del servidor: ${res.status}`);
            }
            const text = await res.text();
            if (!text) {
                throw new Error('El cliente no existe o ha sido eliminado.');
            }
            return JSON.parse(text);
        })
        .then(c => {
            document.getElementById("idCliente").value = c.idCliente;
            document.getElementById("dniCliente").value = c.dniCliente;
            document.getElementById("nombreCliente").value = c.nombreCliente;
            document.getElementById("apellidosCliente").value = c.apellidosCliente;

            const dniInput = document.getElementById("dniCliente");
            dniInput.disabled = true;

            document.querySelector("#modalCliente .modal-title").innerHTML = '<i class="ri-edit-2-line me-2"></i>Editar Cliente';
            document.getElementById("btnGuardarCliente").innerHTML = '<i class="ri-save-line me-1"></i>Guardar Cambios';

            const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("modalCliente"));
            modal.show();
        })
        .catch(err => {
            console.error("Error al cargar datos para editar:", err);
            mostrarToast(err.message || "Error al cargar los datos del cliente.", "danger");
        });
}
