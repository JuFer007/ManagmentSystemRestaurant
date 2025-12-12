document.addEventListener("DOMContentLoaded", () => {

    // --- Estadísticas Generales ---
    fetch("/dashboard/stats")
        .then(res => res.json())
        .then(data => {
            document.getElementById("cantidadEmpleados").textContent = data.cantidadEmpleados || 0;
            document.getElementById("numeroClientes").textContent = data.numeroClientes || 0;
            document.getElementById("cantidadPedidos").textContent = data.cantidadPedidos || 0;
            const simbolo = "S/. ";
            document.getElementById("ingresosTotales").textContent = simbolo + ((data.ingresosTotales || 0).toFixed(2));
        })
        .catch(err => console.error("Error al cargar estadísticas:", err));

    // --- Ingresos por Mes ---
    fetch("/dashboard/ingresos-mes")
        .then(res => res.json())
        .then(data => {
            if (!Array.isArray(data)) throw new Error("Datos inválidos de ingresos por mes");
            const labels = data.map(d => `Mes ${d.mes}`);
            const valores = data.map(d => d.ingreso);

            const ctx = document.getElementById("ingresosMes").getContext("2d");
            new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "Ingresos S/.",
                        data: valores,
                        borderColor: "#d8651e",
                        backgroundColor: "#faaa69",
                        tension: 0.3,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    plugins: { legend: { display: true } },
                    scales: { y: { beginAtZero: true } }
                }
            });
        })
        .catch(err => console.error("Error al cargar ingresos por mes:", err));

    // --- Platos Más Vendidos ---
    fetch("/dashboard/platos-mas-vendidos")
        .then(res => res.json())
        .then(data => {
            if (!Array.isArray(data)) throw new Error("Datos inválidos de platos más vendidos");
            const labels = data.map(d => d.plato);
            const valores = data.map(d => d.cantidad);

            const ctx = document.getElementById("platosVendidos").getContext("2d");
            new Chart(ctx, {
                type: "bar",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "Cantidad Vendida",
                        data: valores,
                        backgroundColor: [
                            "rgba(217, 101, 29, 0.7)",
                            "rgba(255, 140, 50, 0.7)",
                            "rgba(230, 120, 40, 0.7)",
                            "rgba(255, 160, 80, 0.7)",
                            "rgba(200, 90, 20, 0.7)"
                        ],
                        borderColor: [
                            "rgba(217, 101, 29, 1)",
                            "rgba(255, 140, 50, 1)",
                            "rgba(230, 120, 40, 1)",
                            "rgba(255, 160, 80, 1)",
                            "rgba(200, 90, 20, 1)"
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: { legend: { display: false } },
                    scales: { y: { beginAtZero: true } }
                }
            });
        })
        .catch(err => console.error("Error al cargar platos más vendidos:", err));
});
