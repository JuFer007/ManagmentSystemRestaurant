async function visualizarTicket(idPedido) {
    try {
        mostrarCargando('Cargando ticket...');

        const response = await fetch(`http://localhost:8080/api/tickets/pedido/${idPedido}`);
        if (!response.ok) throw new Error('Error al generar el ticket');

        const blobData = await response.arrayBuffer();
        const blob = new Blob([blobData], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);

        const nuevaPestaña = window.open(url, '_blank');

        const a = document.createElement('a');
        a.href = url;
        a.download = `ticket_pedido_${idPedido}.pdf`;
        document.body.appendChild(a);

        nuevaPestaña.onload = () => {
            nuevaPestaña.document.title = `ticket_pedido_${idPedido}.pdf`;
        };

        ocultarCargando();

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
