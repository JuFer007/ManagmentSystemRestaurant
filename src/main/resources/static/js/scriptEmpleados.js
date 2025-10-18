//CARGAR EMPLEADOS DESDE LA BD
function cargarEmpleados() {
    fetch('/system/empleados/listar')
        .then(res => res.json())
        .then(empleados => {
            const tbody = document.querySelector('#tablaEmpleados tbody');
            tbody.innerHTML = '';
            empleados.forEach(e => {
                tbody.innerHTML += `
                    <tr>
                        <td>${e.dniEmpleado}</td>
                        <td>${e.nombreEmpleado}</td>
                        <td>${e.apellidoPaternoEmpleado} ${e.apellidoMaternoEmpleado}</td>
                        <td>${e.cargoEmpleado}</td>
                        <td>${e.salarioEmpleado}</td>
                        <td>${e.estadoEmpleado}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="editarEmpleado('${e.idEmpleado}')"><i class="ri-edit-2-line"></i> Editar</button>
                        </td>
                    </tr>`;
            });
        })
        .catch(err => console.error('Error al listar empleados:', err));

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
                cargarEmpleados();
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