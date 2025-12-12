document.addEventListener("DOMContentLoaded", async () => {
    const usuarioGuardado = localStorage.getItem("usuario");

    if (!usuarioGuardado) {
        console.warn("No se encontró usuario en localStorage");
        // Mostrar mensaje o redirigir al login
        window.location.href = '/login';
        return;
    }

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

        // OBJETO CON TODOS LOS MÓDULOS
        const navItems = {
            dashboard: document.getElementById("nav-dashboard"),
            empleados: document.getElementById("nav-empleados"),
            clientes: document.getElementById("nav-clientes"),
            verPedidos: document.getElementById("nav-verPedidos"),
            nuevoPedido: document.getElementById("nav-nuevoPedido"),
            platos: document.getElementById("nav-platos"),
            pagos: document.getElementById("nav-pagos")
        };

        // PRIMERO OCULTAR TODOS
        Object.values(navItems).forEach(item => {
            if (item) {
                item.style.display = "none";
            }
        });

        // MOSTRAR SEGÚN CARGO
        switch (cargo.toLowerCase()) {
            case "administrador":
                // Administrador ve TODO
                Object.values(navItems).forEach(item => {
                    if (item) item.style.display = "flex";
                });
                break;

            case "mesero":
                // Mesero: ver pedidos, nuevo pedido, platos, pagos
                if (navItems.verPedidos) navItems.verPedidos.style.display = "flex";
                if (navItems.nuevoPedido) navItems.nuevoPedido.style.display = "flex";
                if (navItems.platos) navItems.platos.style.display = "flex";
                if (navItems.pagos) navItems.pagos.style.display = "flex";
                break;

            case "cocinero":
                // Cocinero: ver pedidos y platos
                if (navItems.verPedidos) navItems.verPedidos.style.display = "flex";
                if (navItems.platos) navItems.platos.style.display = "flex";
                break;

            default:
                if (navItems.dashboard) navItems.dashboard.style.display = "flex";
        }

    } catch (error) {
        console.error("Error al obtener el usuario:", error);
        alert("Error al cargar los datos del usuario. Por favor, inicia sesión nuevamente.");
        localStorage.clear();
        window.location.href = '/login';
    }
});

function logout() {
    localStorage.clear();
    window.location.href = '/logout';
}
