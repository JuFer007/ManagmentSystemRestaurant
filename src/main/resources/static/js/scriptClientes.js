//Cargar clientes desde el backend
function cargarClientes() {
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
                            <button class="btn btn-warning btn-sm" onclick="editarCliente('${c.dniCliente}')">
                                <i class="ri-edit-2-line"></i>
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarCliente('${c.dniCliente}')">
                                <i class="ri-delete-bin-line"></i>
                            </button>
                        </td>
                    </tr>`;
            });
        })
        .catch(err => console.error("Error al listar clientes:", err));
}

//Abrir y cerrar formulario modal
function abrirFormulario() {
    document.getElementById("modalCliente").style.display = "block";
    document.getElementById("formCliente").reset();
    document.getElementById("tituloModal").textContent = "Nuevo Cliente";
}

function cerrarFormulario() {
    document.getElementById("modalCliente").style.display = "none";
}

//Guardar o actualizar cliente
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formCliente");
    if (form) {
        form.addEventListener("submit", e => {
            e.preventDefault();

            const cliente = {
                dniCliente: document.getElementById("dniCliente").value.trim(),
                nombreCliente: document.getElementById("nombreCliente").value.trim(),
                apellidosCliente: document.getElementById("apellidosCliente").value.trim()
            };

            fetch("/system/clientes/guardar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cliente)
            })
                .then(res => {
                    if (!res.ok) throw new Error("Error al guardar");
                    cerrarFormulario();
                    cargarClientes();
                })
                .catch(err => console.error("Error:", err));
        });
    }
});

//Editar cliente
function editarCliente(dni) {
    fetch(`/system/clientes/buscar/${dni}`)
        .then(res => res.json())
        .then(c => {
            document.getElementById("dniCliente").value = c.dniCliente;
            document.getElementById("nombreCliente").value = c.nombreCliente;
            document.getElementById("apellidosCliente").value = c.apellidosCliente;
            document.getElementById("tituloModal").textContent = "Editar Cliente";
            document.getElementById("modalCliente").style.display = "block";
        })
        .catch(err => console.error("Error al buscar cliente:", err));
}

//Filtro de tabla
function filterTable(value, tableId) {
    const rows = document.querySelectorAll(`#${tableId} tbody tr`);
    rows.forEach(row => {
        row.style.display = row.textContent.toLowerCase().includes(value.toLowerCase())
            ? ""
            : "none";
    });
}
