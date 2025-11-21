async function visualizarTicket(idPedido) {
    try {
        mostrarToast("Generando ticket...", "info");
        
        const response = await fetch(`http://localhost:8080/api/tickets/pedido/${idPedido}`);
        if (!response.ok) throw new Error('Error al generar el ticket');

        const blobData = await response.arrayBuffer();
        const blob = new Blob([blobData], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);

        const nuevaPestaña = window.open(url, '_blank');

        if (!nuevaPestaña) {
            alert('El navegador bloqueó la apertura de la ventana. Por favor, permite pop-ups.');
        }
    } catch (error) {
        console.error('Error al visualizar ticket:', error);
        mostrarError(error.message);
        ocultarCargando();
    }
}

function mostrarCargando(mensaje) {
    const loader = document.getElementById('loader');
    if (loader) {
        loader.textContent = mensaje;
        loader.style.display = 'block';
    } else {
        console.log(mensaje);
    }
}

function ocultarCargando() {
    const loader = document.getElementById('loader');
    if (loader) loader.style.display = 'none';
}

function mostrarError(mensaje) {
    alert(mensaje);
}
