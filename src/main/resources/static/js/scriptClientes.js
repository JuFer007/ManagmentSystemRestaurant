// ========== CARGAR CLIENTES ==========
async function cargarClientes() {
    const page = document.getElementById('page-clientes');
    if (page.style.display === 'none') {
        return;
    }
    fetch("/system/clientes/listar")
        .then(res => res.json())
        .then(clientes => {
            const tbody = document.querySelector("#tablaClientes tbody");
            tbody.innerHTML = "";
            clientes.forEach(c => {
                tbody.innerHTML += `
                    <tr>
                        <td>${c.dniCliente}</td>
                        <td>${c.nombreCliente}</td>
                        <td>${c.apellidosCliente}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="editarCliente(${c.idCliente})">
                                <i class="ri-edit-2-line"></i> Editar
                            </button>
                        </td>
                    </tr>`;
            });
        })
        .catch(err => console.error("Error al listar clientes:", err));
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

            const method = idCliente ? "POST" : "POST";

            fetch(url, {
                method: method,
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

/**
 * @param {string} str
 * @returns {string}
 */
function removeAccents(str) {
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// ========== FILTRO DE TABLA ==========
function filterTable(value, tableId) {
    const normalizedValue = removeAccents(value.toLowerCase());
    const rows = document.querySelectorAll(`#${tableId} tbody tr`);
    rows.forEach(row => {
        const rowText = removeAccents(row.textContent.toLowerCase());
        row.style.display = rowText.includes(normalizedValue) ? "" : "none";
    });
}
