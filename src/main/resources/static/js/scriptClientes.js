let tablaClientesDT;

// ========== CARGAR CLIENTES ==========
function cargarClientes() {
    if (tablaClientesDT) {
        tablaClientesDT.ajax.reload();
        return;
    }

    tablaClientesDT = new DataTable('#tablaClientes', {
        ajax: {
            url: '/system/clientes/listar',
            dataSrc: ''
        },
        columns: [
            { data: 'dniCliente', className: 'text-start'},
            { data: 'nombreCliente', className: 'text-start'},
            { data: 'apellidosCliente', className: 'text-start'},
            {
                data: 'idCliente',
                render: (data) => `<button class="btn btn-warning btn-sm" onclick="editarCliente(${data})"><i class="ri-edit-2-line"></i> Editar</button>`,
                orderable: false,
                searchable: false,
                className: 'text-center',
            }
        ],
        language: { url: 'https://cdn.datatables.net/plug-ins/2.0.8/i18n/es-ES.json' }
    });
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
                tablaClientesDT.ajax.reload();
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
