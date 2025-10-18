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
                            <button class="btn btn-warning btn-sm" onclick="editarEmpleado('${e.dniEmpleado}')"><i class="ri-edit-2-line"></i></button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarEmpleado('${e.dniEmpleado}')"><i class="ri-delete-bin-line"></i></button>
                        </td>
                    </tr>`;
            });
        })
        .catch(err => console.error('Error al listar empleados:', err));

}
