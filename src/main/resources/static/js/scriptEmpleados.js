let tablaEmpleadosDT;

document.addEventListener('DOMContentLoaded', function () {
});

//CARGAR EMPLEADOS DESDE LA BD
function cargarEmpleados() {
    if (tablaEmpleadosDT) {
        tablaEmpleadosDT.ajax.reload();
        return;
    }
    tablaEmpleadosDT = new DataTable('#tablaEmpleados', {
        ajax: {
            url: '/system/empleados/listar',
            dataSrc: ''
        },
        columns: [
            { data: 'dniEmpleado', className: 'text-start' },
            { data: 'nombreEmpleado', className: 'text-start' },
            {
                data: null,
                render: (data, type, row) => `${row.apellidoPaternoEmpleado} ${row.apellidoMaternoEmpleado}`,
                className: 'text-start'
            },
            { data: 'cargoEmpleado', className: 'text-start' },
            { data: 'salarioEmpleado', className: 'text-start' },
            {
                data: 'estadoEmpleado',
                className: 'text-start',
                render: (data) => `<span class="badge ${data === 'Activo' ? 'bg-success' : 'bg-danger'}">${data}</span>`
            },
            {
                data: 'idEmpleado',
                render: (data, type, row) => `
                    <button class="btn btn-warning btn-sm" onclick="editarEmpleado('${data}')"><i class="ri-edit-2-line"></i> Editar</button>
                    <button class="btn btn-secondary btn-sm" onclick="cambiarEstadoEmpleado('${data}')"><i class="ri-refresh-line"></i> Estado</button>`
                ,
                orderable: false,
                searchable: false,
                className: 'text-center',
                width: '180px'
            }
        ],
        language: { url: 'https://cdn.datatables.net/plug-ins/2.0.8/i18n/es-ES.json' }
    });

}

// ABRIR FORMULARIO NUEVO/EDITAR EMPLEADO
function abrirFormularioEmpleado(idEmpleado = null) {
    const form = document.getElementById("formEmpleado");
    form.reset();
    document.getElementById("idEmpleado").value = "";
    const dniInput = document.getElementById("dniEmpleado");
    dniInput.disabled = false;

    const modalTitle = document.querySelector("#modalEmpleado .modal-title");
    const btnGuardar = document.getElementById("btnGuardarEmpleado");
    const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("modalEmpleado"));

    if (idEmpleado) {
        modalTitle.innerHTML = '<i class="ri-edit-2-line me-2"></i>Editar Empleado';
        btnGuardar.innerHTML = '<i class="ri-save-line me-1"></i>Guardar Cambios';

        fetch(`/system/empleados/buscar/${idEmpleado}`)
            .then(res => {
                if (!res.ok) throw new Error(`Error ${res.status}`);
                return res.json();
            })
            .then(e => {
                document.getElementById("idEmpleado").value = e.idEmpleado;
                document.getElementById("dniEmpleado").value = e.dniEmpleado;
                document.getElementById("nombreEmpleado").value = e.nombreEmpleado;
                document.getElementById("apellidoPaternoEmpleado").value = e.apellidoPaternoEmpleado;
                document.getElementById("apellidoMaternoEmpleado").value = e.apellidoMaternoEmpleado;
                document.getElementById("cargoEmpleado").value = e.cargoEmpleado;
                document.getElementById("salarioEmpleado").value = e.salarioEmpleado;
                document.getElementById("estadoEmpleado").value = e.estadoEmpleado;
                dniInput.disabled = true;
                modal.show();
            })
            .catch(err => {
                console.error("Error al cargar datos para editar:", err);
                mostrarToast("Error al cargar los datos del empleado.", "danger");
            });
    } else {
        modalTitle.innerHTML = '<i class="ri-user-add-line me-2"></i>Nuevo Empleado';
        btnGuardar.innerHTML = '<i class="ri-save-line me-1"></i>Agregar';
        modal.show();
    }
}

// CERRAR MODAL
function cerrarFormularioEmpleado() {
    const modalElement = document.getElementById("modalEmpleado");
    const modal = bootstrap.Modal.getInstance(modalElement);
    if (modal) modal.hide();
}

// GUARDAR / ACTUALIZAR EMPLEADO
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('formEmpleado');
    if (form) {
        form.addEventListener("submit", e => {
            e.preventDefault();

            const idEmpleado = document.getElementById('idEmpleado').value;
            const dniEmpleado = document.getElementById('dniEmpleado').value.trim();
            const nombreEmpleado = document.getElementById('nombreEmpleado').value.trim();
            const apellidoPaternoEmpleado = document.getElementById('apellidoPaternoEmpleado').value.trim();
            const apellidoMaternoEmpleado = document.getElementById('apellidoMaternoEmpleado').value.trim();
            const cargoEmpleado = document.getElementById('cargoEmpleado').value;
            const salarioEmpleado = document.getElementById('salarioEmpleado').value;
            const estadoEmpleado = document.getElementById('estadoEmpleado').value;

            if (!idEmpleado && !/^\d{8}$/.test(dniEmpleado)) {
                return mostrarToast("El DNI debe contener 8 dígitos numéricos.", "warning");
            }

            if (!/^[a-zA-Z\s]+$/.test(nombreEmpleado) ||
                !/^[a-zA-Z\s]+$/.test(apellidoPaternoEmpleado) ||
                !/^[a-zA-Z\s]+$/.test(apellidoMaternoEmpleado)) {
                return mostrarToast("Los nombres y apellidos solo deben contener letras.", "warning");
            }

            const empleado = {
                idEmpleado,
                dniEmpleado,
                nombreEmpleado,
                apellidoPaternoEmpleado,
                apellidoMaternoEmpleado,
                cargoEmpleado,
                salarioEmpleado,
                estadoEmpleado
            };

            const url = idEmpleado ? "/system/empleados/actualizar" : "/system/empleados/guardar";

            fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(empleado)
            })
            .then(res => {
                if (!res.ok) throw new Error("Error en el servidor");
                return res.json();
            })
            .then(() => {
                mostrarToast(idEmpleado ? "Empleado actualizado con éxito." : "Empleado agregado exitosamente.", "success");
                cerrarFormularioEmpleado();
                tablaEmpleadosDT.ajax.reload(); // Recargamos la tabla con DataTables
            })
            .catch(err => {
                console.error("Error al guardar empleado:", err);
                mostrarToast("No se pudo guardar el empleado.", "danger");
            });
        });
    }
});

// EDITAR EMPLEADO
function editarEmpleado(id) {
    abrirFormularioEmpleado(id);
}

//Cambiar estado
async function cambiarEstadoEmpleado(idEmpleado) {
    try {
        const responseEmpleado = await fetch(`/system/empleados/buscar/${idEmpleado}`);
        if (!responseEmpleado.ok) throw new Error("No se encontró el empleado");

        const empleado = await responseEmpleado.json();
        const nuevoEstadoEmpleado = empleado.estadoEmpleado === "Activo" ? "Inactivo" : "Activo";

        const response = await fetch(`/system/empleados/cambiarEstado/${idEmpleado}?estadoEmpleado=${encodeURIComponent(nuevoEstadoEmpleado)}`, {
            method: "PUT"
        });

        if (response.ok) {
            tablaEmpleadosDT.ajax.reload();
            mostrarToast(`Estado cambiado.`, "info");

        } else {
            const msg = await response.text();
            mostrarToast(msg || "No se pudo cambiar el estado.", "danger");
        }
    } catch (error) {
        console.error("Error al cambiar estado:", error);
        mostrarToast("Error al intentar cambiar el estado del empleado.", "danger");
    }
}