function abrirFormularioPlato() {
    document.getElementById("formPlato").reset();
    document.getElementById("tituloModalPlato").textContent = "Agregar Nuevo Plato";
    document.getElementById("btnGuardarPlato").textContent = "Guardar";
    document.getElementById("previsualizacion").style.display = "none";
    document.getElementById("idPlato").value = "";
    const modal = new bootstrap.Modal(document.getElementById("modalPlato"));
    modal.show();
}

//Guardar o actualizar plato
async function guardarPlato() {
    const form = document.getElementById("formPlato");
    const formData = new FormData(form);
    const idPlato = document.getElementById("idPlato").value;
    let url = "/system/platos/guardar";
    let method = "POST";

    if (idPlato) {
        url = `/system/platos/actualizar/${idPlato}`;
        method = "PUT";
    }

    try {
        const response = await fetch(url, { method, body: formData });
        console.log("Estado de respuesta:", response.status);

        if (response.ok) {
            mostrarToast(`Plato ${idPlato ? "actualizado" : "guardado"} correctamente`, "success");

            const modalInstance = bootstrap.Modal.getInstance(document.getElementById("modalPlato"));
            if (modalInstance) modalInstance.hide();

            form.reset();
            document.getElementById("previsualizacion").style.display = "none";
            await listarPlatos();
        } else {
            const errorText = await response.text();
            console.log(`Error al guardar el plato: ${errorText}`, "danger");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

//Listar platos
async function listarPlatos() {
    const contenedor = document.getElementById("contenedorTarjetasPlatos");
    contenedor.innerHTML = "";
    try {
        const response = await fetch("/system/platos/listar");
        if (!response.ok) throw new Error("Error al cargar los platos");

        const platos = await response.json();

        platos.forEach(plato => {
            const tarjeta = document.createElement("div");
            tarjeta.className = "col";
            tarjeta.innerHTML = `
                <div class="card h-90 shadow-sm">
                    <img src="/${plato.imagenURL}"
                         class="card-img-top"
                         alt="${plato.nombrePlato}"
                         style="height: 220px; object-fit: cover; border-top-left-radius: 16px; border-top-right-radius: 16px;">
                    <div class="card-body d-flex flex-column" style="padding: 14px;">
                        <h5 class="card-title mb-1" style="font-size: 0.95rem; font-weight: bold !important;">${plato.nombrePlato}</h5>
                        <p class="card-text mb-2" style="font-weight: 500; font-size: 0.9rem;">S/. ${plato.precioPlato.toFixed(2)}</p>
                        <div class="text-end mb-2" style="text-align: left !important;">
                            <span class="badge ${plato.disponibilidad === 'Disponible' ? 'bg-success' : 'bg-danger'}"
                                  style="font-size: 0.75rem; padding: 4px 10px; margin-bottom: 10px !important;">
                                ${plato.disponibilidad}
                            </span>
                        </div>
                        <div class="d-flex justify-content-end gap-2" style="display: flex !important; justify-content: flex-start !important; gap: 6px !important; margin-top: -10px !important;">
                            <button class="btn btn-sm btn-warning" onclick="editarPlato(${plato.idPlato})" style="font-size: 0.75rem; padding: 4px 8px;">
                                <i class="ri-edit-line" style="font-size: 0.85rem;"></i> Editar
                            </button>
                            <button class="btn btn-sm btn-secondary" onclick="cambiarEstadoPlato(${plato.idPlato})" style="font-size: 0.75rem; padding: 4px 8px;">
                                <i class="ri-refresh-line" style="font-size: 0.85rem;"></i> Disponibilidad
                            </button>
                        </div>
                    </div>
                </div>
            `;
            contenedor.appendChild(tarjeta);
        });
    } catch (error) {
        console.error("Error al listar platos:", error);
    }
}

//Previsualizar imagen
function previsualizarImagen(input) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("imagenPreview").src = e.target.result;
            document.getElementById("previsualizacion").style.display = "block";
        };
        reader.readAsDataURL(file);
    }
}

//Editar plato
async function editarPlato(idPlato) {
    try {
        const response = await fetch(`/system/platos/${idPlato}`);
        if (!response.ok) throw new Error("No se pudo obtener el plato");

        const plato = await response.json();

        document.getElementById("idPlato").value = plato.idPlato;
        document.getElementById("nombrePlato").value = plato.nombrePlato;
        document.getElementById("precioPlato").value = plato.precioPlato;
        document.getElementById("disponibilidad").value = plato.disponibilidad;

        if (plato.imagenURL && plato.imagenURL.trim() !== "") {
            document.getElementById("imagenPreview").src = `/${plato.imagenURL}`;
        } else {
            document.getElementById("imagenPreview").src = "/imagenesPlatos/default.jpg";
        }
        document.getElementById("previsualizacion").style.display = "block";

        document.getElementById("tituloModalPlato").textContent = "Editar Plato";
        document.getElementById("btnGuardarPlato").textContent = "Actualizar";

        const modal = new bootstrap.Modal(document.getElementById("modalPlato"));
        modal.show();
    } catch (error) {
        console.error("Error al cargar plato:", error);
    }
}

//Cambiar estado
async function cambiarEstadoPlato(idPlato) {
    try {
        const responsePlato = await fetch(`/system/platos/${idPlato}`);
        if (!responsePlato.ok) throw new Error("No se encontró el plato");

        const plato = await responsePlato.json();
        const nuevaDisponibilidad = plato.disponibilidad === "Disponible" ? "No disponible" : "Disponible";

        const response = await fetch(`/system/platos/cambiarEstado/${idPlato}?disponibilidad=${encodeURIComponent(nuevaDisponibilidad)}`, {
            method: "PUT"
        });

        if (response.ok) {
            await listarPlatos();
            mostrarToast(`Disponibilidad cambiada.`, "info");

        } else {
            const msg = await response.text();
        }
    } catch (error) {
        console.error("Error al cambiar estado:", error);
    }
}

//Buscador
function filterTarjetas(searchText) {
    const contenedor = document.getElementById("contenedorTarjetasPlatos");
    const tarjetas = contenedor.getElementsByClassName("col");
    const texto = searchText.toLowerCase();

    for (let tarjeta of tarjetas) {
        const nombre = tarjeta.querySelector(".card-title").textContent.toLowerCase();
        tarjeta.style.display = nombre.includes(texto) ? "" : "none";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    listarPlatos();
});
