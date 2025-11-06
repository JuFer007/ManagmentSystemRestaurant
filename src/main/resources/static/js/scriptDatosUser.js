document.addEventListener("DOMContentLoaded", async () => {
    const usuarioGuardado = localStorage.getItem("usuario");

    if (usuarioGuardado) {
        try {
            const response = await fetch(`/buscar/${usuarioGuardado}`);
            if (!response.ok) throw new Error("Usuario no encontrado");

            const usuario = await response.json();
            console.log("Datos del usuario:", usuario);

            const cargo = usuario.idEmpleado.cargoEmpleado;
            const nombreEmpleado = usuario.idEmpleado.nombreEmpleado;
            const apellidoPaterno = usuario.idEmpleado.apellidoPaternoEmpleado;
            const nombreUsuario = `${nombreEmpleado} ${apellidoPaterno}`;

            document.querySelector(".fw-semibold").textContent = nombreUsuario;

            const avatar = document.querySelector(".user-avatar img");
            let imagenURL = "";
            switch (cargo.toLowerCase()) {
                case "administrador":
                    imagenURL = "imagenes/admin.png";
                    break;
                case "mesero":
                    imagenURL = "imagenes/mesero.png";
                    break;
                case "cocinero":
                    imagenURL = "imagenes/cocinero.png";
                    break;
                default:
                    imagenURL = "https://www.iconpacks.net/icons/2/free-user-icon-3296-thumb.png";
            }

            avatar.src = imagenURL;

            const navItems = {
                dashboard: document.getElementById("nav-dashboard"),
                empleados: document.getElementById("nav-empleados"),
                clientes: document.getElementById("nav-clientes"),
                verPedidos: document.getElementById("nav-verPedidos"),
                nuevoPedido: document.getElementById("nav-nuevoPedido"),
                platos: document.getElementById("nav-platos"),
                delivery: document.getElementById("nav-delivery"),
                pagos: document.getElementById("nav-pagos")
            };

            Object.values(navItems).forEach(item => {
                item.style.display = "none";
                item.classList.remove("disabled-item");
            });

            switch (cargo.toLowerCase()) {
                case "administrador":
                    Object.values(navItems).forEach(item => item.style.display = "flex");
                    break;

                case "mesero":
                    navItems.verPedidos.style.display = "flex";
                    navItems.nuevoPedido.style.display = "flex";
                    navItems.delivery.style.display = "flex";
                    navItems.platos.style.display = "flex";
                    break;

                case "cocinero":
                    navItems.verPedidos.style.display = "flex";
                    navItems.platos.style.display = "flex";
                    break;
                default:
                    navItems.dashboard.style.display = "flex";
            }

        } catch (error) {
            console.error("Error al obtener el usuario:", error);
        }
    } else {
        console.warn("No se encontró usuario en localStorage");
    }
});

function logout() {
    window.location.href = '/logout';
    localStorage.clear();
}
