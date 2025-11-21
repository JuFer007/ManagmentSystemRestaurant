// Variables globales para paginación
let todosLosEmpleados = [];
let empleadosPaginaActual = 1;
const empleadosPorPagina = 10;

document.addEventListener('DOMContentLoaded', function () {
});

//CARGAR EMPLEADOS DESDE LA BD
async function cargarEmpleados() {
    const tablaBody = document.querySelector("#tablaEmpleadosBody");
    
    try {
        const response = await fetch('/system/empleados/listar');
        if (!response.ok) throw new Error("Error al obtener los empleados");
        
        let empleados = await response.json();
        
        // Ordenar por prioridad de estado: Activo primero, luego Inactivo
        empleados.sort((a, b) => {
            const prioridadEstado = {
                "Activo": 1,
                "Inactivo": 2
            };
            
            const prioridadA = prioridadEstado[a.estadoEmpleado] || 3;
            const prioridadB = prioridadEstado[b.estadoEmpleado] || 3;
            
            if (prioridadA !== prioridadB) {
                return prioridadA - prioridadB;
            }
            
            // Si tienen el mismo estado, ordenar alfabéticamente por nombre
            return a.nombreEmpleado.localeCompare(b.nombreEmpleado);
        });
        
        todosLosEmpleados = empleados;
        empleadosPaginaActual = 1;
        renderizarEmpleados(todosLosEmpleados);
        actualizarPaginacionEmpleados();
    } catch (error) {
        console.error("Error:", error);
        if (tablaBody) {
            tablaBody.innerHTML = `<tr><td colspan='7' class='text-center text-danger'>Error al cargar empleados</td></tr>`;
        }
        mostrarToast('Error al cargar los empleados', "danger");
    }
}

// ========== RENDERIZAR EMPLEADOS ==========
function renderizarEmpleados(empleados) {
    const tablaBody = document.querySelector("#tablaEmpleadosBody");
    if (!tablaBody) return;
    
    tablaBody.innerHTML = "";
    
    if (empleados.length === 0) {
        tablaBody.innerHTML = "<tr><td colspan='7' class='text-center'>No se encontraron empleados</td></tr>";
        actualizarInfoEmpleados(0, 0);
        return;
    }
    
    // Calcular paginación
    const inicio = (empleadosPaginaActual - 1) * empleadosPorPagina;
    const fin = inicio + empleadosPorPagina;
    const empleadosPaginados = empleados.slice(inicio, fin);
    
    empleadosPaginados.forEach(empleado => {
        const estadoBadge = empleado.estadoEmpleado === 'Activo' ? 'bg-success' : 'bg-danger';
        const row = `
            <tr>
                <td>${empleado.dniEmpleado}</td>
                <td>${empleado.nombreEmpleado}</td>
                <td>${empleado.apellidoPaternoEmpleado} ${empleado.apellidoMaternoEmpleado}</td>
                <td>${empleado.cargoEmpleado}</td>
                <td>S/. ${empleado.salarioEmpleado.toFixed(2)}</td>
                <td><span class="badge ${estadoBadge}">${empleado.estadoEmpleado}</span></td>
                <td style="text-align: center;">
                    <button class="btn-action btn-edit" onclick="editarEmpleado('${empleado.idEmpleado}')" title="Editar Empleado">
                        <i class="ri-edit-2-line"></i> Editar
                    </button>
                    <button class="btn-action btn-status" onclick="cambiarEstadoEmpleado('${empleado.idEmpleado}')" title="Cambiar Estado">
                        <i class="ri-refresh-line"></i> Estado
                    </button>
                </td>
            </tr>
        `;
        tablaBody.insertAdjacentHTML("beforeend", row);
    });
    
    actualizarInfoEmpleados(empleadosPaginados.length, empleados.length);
}

// ========== ACTUALIZAR INFORMACIÓN DE PAGINACIÓN ==========
function actualizarInfoEmpleados(mostrados, total) {
    const infoElement = document.getElementById("infoEmpleados");
    if (infoElement) {
        const inicio = (empleadosPaginaActual - 1) * empleadosPorPagina + 1;
        const fin = Math.min(inicio + mostrados - 1, total);
        infoElement.textContent = `Mostrando ${inicio} a ${fin} de ${total} empleados`;
    }
}

// ========== CREAR PAGINACIÓN ==========
function actualizarPaginacionEmpleados() {
    const paginacionNav = document.getElementById("paginacionEmpleadosNav");
    if (!paginacionNav) return;
    
    const totalPaginas = Math.ceil(todosLosEmpleados.length / empleadosPorPagina);
    
    if (totalPaginas <= 1) {
        paginacionNav.innerHTML = "";
        return;
    }
    
    let html = "";
    
    // Botón anterior
    html += `
        <li class="page-item ${empleadosPaginaActual === 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaEmpleados(${empleadosPaginaActual - 1}); return false;">Anterior</a>
        </li>
    `;
    
    // Números de página
    for (let i = 1; i <= totalPaginas; i++) {
        if (i === 1 || i === totalPaginas || (i >= empleadosPaginaActual - 1 && i <= empleadosPaginaActual + 1)) {
            html += `
                <li class="page-item ${i === empleadosPaginaActual ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPaginaEmpleados(${i}); return false;">${i}</a>
                </li>
            `;
        } else if (i === empleadosPaginaActual - 2 || i === empleadosPaginaActual + 2) {
            html += `<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`;
        }
    }
    
    // Botón siguiente
    html += `
        <li class="page-item ${empleadosPaginaActual === totalPaginas ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="cambiarPaginaEmpleados(${empleadosPaginaActual + 1}); return false;">Siguiente</a>
        </li>
    `;
    
    paginacionNav.innerHTML = html;
}

// ========== CAMBIAR PÁGINA ==========
function cambiarPaginaEmpleados(pagina) {
    const totalPaginas = Math.ceil(todosLosEmpleados.length / empleadosPorPagina);
    if (pagina < 1 || pagina > totalPaginas) return;
    
    empleadosPaginaActual = pagina;
    renderizarEmpleados(todosLosEmpleados);
    actualizarPaginacionEmpleados();
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
            cargarEmpleados();
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
