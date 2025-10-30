// =============================
// NAVEGACIÓN ENTRE SECCIONES
// =============================

// Mostrar u ocultar módulos del sidebar
function toggleSubmenu(element) {
    const submenu = element.nextElementSibling;
    const arrow = element.querySelector('.arrow');

    document.querySelectorAll('.submenu').forEach(s => {
        if (s !== submenu) s.classList.remove('open');
    });

    document.querySelectorAll('.nav-group-title').forEach(g => {
        if (g !== element) {
            g.classList.remove('open');
            g.querySelector('.arrow').style.transform = 'rotate(0deg)';
        }
    });

    submenu.classList.toggle('open');
    element.classList.toggle('open');
    arrow.style.transform = submenu.classList.contains('open') ? 'rotate(90deg)' : 'rotate(0deg)';
}

// Cambiar entre páginas del sistema
function loadPage(page, element) {
    if (page) sessionStorage.setItem('currentPage', page);

    document.querySelectorAll(".page-content").forEach(sec => sec.style.display = "none");
    document.querySelectorAll(".nav-item.active").forEach(i => i.classList.remove("active"));

    if (element) element.classList.add("active");

    const section = document.getElementById(`page-${page}`);
    if (section) section.style.display = "block";

    const titles = {
        dashboard: ["Dashboard Principal", "Gestión integral del restaurante"],
        empleados: ["Gestión de Empleados", "Administra el personal del restaurante"],
        clientes: ["Gestión de Clientes", "Administra la base de clientes"],
        pedidos: ["Gestión de Pedidos", "Visualiza y administra los pedidos"],
        "nuevo-pedido": ["Nuevo Pedido", "Registra un nuevo pedido"],
        platos: ["Gestión de Menú", "Administra los platos del restaurante"],
        pagos: ["Gestión de Pagos", "Administra los pagos y comprobantes"]
    };

    if (titles[page]) {
        document.getElementById("pageTitle").textContent = titles[page][0];
        document.getElementById("pageDesc").textContent = titles[page][1];
    }

    if (page === "clientes" && typeof cargarClientes === "function") cargarClientes();
    if (page === "empleados" && typeof cargarEmpleados === "function") cargarEmpleados();
    if (page === "pedidos" && typeof cargarPedidos === "function") cargarPedidos();
}

// =============================
// CARGA INICIAL DE LA PÁGINA
// =============================
document.addEventListener("DOMContentLoaded", () => {
    const savedPage = sessionStorage.getItem('currentPage') || 'dashboard';
    const navItem = document.querySelector(`.nav-item[onclick*="loadPage('${savedPage}'"]`);

    if (navItem) {
        const parentSubmenu = navItem.closest('.submenu');
        if (parentSubmenu && !parentSubmenu.classList.contains('open')) {
            const groupTitle = parentSubmenu.previousElementSibling;
            if (groupTitle) {
                groupTitle.click();
            }
        }
        loadPage(savedPage, navItem);
    } else {
        loadPage('dashboard', document.querySelector('.nav-item[onclick*="loadPage(\'dashboard\'"]'));
    }
});
