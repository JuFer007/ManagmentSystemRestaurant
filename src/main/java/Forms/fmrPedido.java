package Forms;
import Clases.ClasesGestion.Pedido;
import Clases.ClasesGestion.DetallePedido;
import ConexionBaseDatos.EntidadesCRUD.CRUD_Pedido;
import ConexionBaseDatos.EntidadesCRUD.CRUD_DetallePedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.util.List;

public class fmrPedido {

    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, String> columnNumeroPedido;
    @FXML private TableColumn<Pedido, String> columnCliente;
    @FXML private TableColumn<Pedido, String> columnNumeroMesa;
    @FXML private TableColumn<Pedido, String> columnEstadoPedido;
    @FXML private TableColumn<Pedido, Double> columnTotalPedido;
    @FXML private TableView<DetallePedido> tablaDetalle;
    @FXML private TableColumn<DetallePedido, Integer> columnNumeroDetalle;
    @FXML private TableColumn<DetallePedido, String> columnPlato;
    @FXML private TableColumn<DetallePedido, Integer> columnCantidad;
    @FXML private TableColumn<DetallePedido, Double> columnSubTotal;
    @FXML private TextField txtTotal;
    @FXML private Button btnNuevoPedido;
    @FXML private Button btnCambiarEstado;
    @FXML private Button btnCancelarPedido;

    private CRUD_Pedido crudPedido = new CRUD_Pedido();
    private CRUD_DetallePedido crudDetalle = new CRUD_DetallePedido();
    private ObservableList<Pedido> listaPedidos = FXCollections.observableArrayList();
    private ObservableList<DetallePedido> listaDetalle = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColumnasPedidos();
        configurarColumnasDetalle();
        cargarPedidos();

        // Selección de pedido
        tablaPedidos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDetallePedido(newSel.getIdPedido());
            }
        });

        btnNuevoPedido.setOnAction(e -> nuevoPedido());
        btnCambiarEstado.setOnAction(e -> cambiarEstado());
        btnCancelarPedido.setOnAction(e -> cancelarPedido());
    }

    private void configurarColumnasPedidos() {
        columnNumeroPedido.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCodigoPedido()));
        columnCliente.setCellValueFactory(data -> new ReadOnlyStringWrapper("Cliente #" + data.getValue().getIdCliente())); // Ajusta si quieres nombre real
        columnNumeroMesa.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getIdMesa())));
        columnEstadoPedido.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEstadoPedido()));
        columnTotalPedido.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getTotalPedido()));

        tablaPedidos.setItems(listaPedidos);
        tablaPedidos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurarColumnasDetalle() {
        columnNumeroDetalle.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getIdDetallePedido()));
        columnPlato.setCellValueFactory(data -> new ReadOnlyStringWrapper("Plato #" + data.getValue().getIdPlato())); // Ajusta si quieres nombre del plato
        columnCantidad.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCantidad()));
        columnSubTotal.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSubTotal()));

        tablaDetalle.setItems(listaDetalle);
        tablaDetalle.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void cargarPedidos() {
        listaPedidos.clear();
        List<Pedido> pedidos = crudPedido.listarPedidos();
        listaPedidos.addAll(pedidos);
    }

    private void cargarDetallePedido(int idPedido) {
        listaDetalle.clear();
        List<DetallePedido> detalles = crudDetalle.listarDetallesPorPedido(idPedido);
        listaDetalle.addAll(detalles);

        double total = detalles.stream().mapToDouble(DetallePedido::getSubTotal).sum();
        txtTotal.setText(String.valueOf(total));
    }

    private void nuevoPedido() {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Función de Nuevo Pedido no implementada aún.");
    }

    private void cambiarEstado() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {
            String estadoActual = pedidoSeleccionado.getEstadoPedido();
            pedidoSeleccionado.setEstadoPedido(estadoActual.equals("Pendiente") ? "Entregado" : "Pendiente");
            tablaPedidos.refresh();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido.");
        }
    }

    private void cancelarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Función de cancelar pedido no implementada aún.");
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
