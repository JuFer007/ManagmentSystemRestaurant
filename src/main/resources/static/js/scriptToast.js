function mostrarToast(mensaje, tipo = "success") {
    const container = document.getElementById("toastContainer");
    if (!container) return;

    let icono = "";
    switch (tipo) {
        case "success":
            icono = '<i class="ri-checkbox-circle-line me-2"></i>';
            break;
        case "danger":
            icono = '<i class="ri-error-warning-line me-2"></i>';
            break;
        case "warning":
            icono = '<i class="ri-alert-line me-2"></i>';
            break;
        case "info":
            icono = '<i class="ri-information-line me-2"></i>';
            break;
        default:
            icono = '<i class="ri-notification-2-line me-2"></i>';
    }

    //Crear el toast dinámico
    const toast = document.createElement("div");
    toast.className = `toast align-items-center text-bg-${tipo} border-0 shadow`;
    toast.setAttribute("role", "alert");
    toast.setAttribute("aria-live", "assertive");
    toast.setAttribute("aria-atomic", "true");

    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body d-flex align-items-center">
                ${icono}
                <span>${mensaje}</span>
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto"
                    data-bs-dismiss="toast" aria-label="Cerrar"></button>
        </div>
    `;

    container.appendChild(toast);

    //Mostrar y eliminar automáticamente
    const bsToast = new bootstrap.Toast(toast, { delay: 3000 });
    bsToast.show();

    toast.addEventListener("hidden.bs.toast", () => toast.remove());
}
