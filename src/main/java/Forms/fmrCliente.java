package Forms;
import Clases.ClasesEmpleados.Cliente;
import ConexionBaseDatos.EntidadesCRUD.CRUD_Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyStringWrapper;

public class fmrCliente {

    //Caja de búsqueda
    @FXML private TextField cajaBusqueda;

    //Tabla de clientes
    @FXML private TableView<Object[]> tablaClientes;
    @FXML private TableColumn<Object[], String> columnDNI;
    @FXML private TableColumn<Object[], String> columnNombre;
    @FXML private TableColumn<Object[], String> columnApellido;

    //Campos para ingresar datos
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaNombre;
    @FXML private TextField cajaApellidos;

    //Botones
    @FXML private Button btnModificarDatos;
    @FXML private Button btnAgregarCliente;

    private CRUD_Cliente crudCliente = new CRUD_Cliente();
    private ObservableList<Object[]> listaClientes;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarClientes();
        configurarSeleccionTabla();

        btnAgregarCliente.setOnAction(e -> agregarCliente());
        btnModificarDatos.setOnAction(e -> modificarCliente());
    }

    //Configurar columnas de la tabla
    private void configurarColumnas() {
        columnDNI.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[0]));
        columnNombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[1]));
        columnApellido.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[2]));
        tablaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Evita scroll horizontal
    }

    //Cargar clientes desde la BD
    private void cargarClientes() {
        listaClientes = FXCollections.observableArrayList(crudCliente.listarClientes());
        tablaClientes.setItems(listaClientes);
    }

    //Configurar selección de tabla
    private void configurarSeleccionTabla() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cajaDNI.setText((String) newSel[0]);
                cajaNombre.setText((String) newSel[1]);
                cajaApellidos.setText((String) newSel[2]);
                cajaDNI.setEditable(false); // DNI no se puede modificar
            }
        });
    }

    //Agregar nuevo cliente
    private void agregarCliente() {
        String dni = cajaDNI.getText();
        String nombre = cajaNombre.getText();
        String apellidos = cajaApellidos.getText();

        if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos deben estar llenos");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setDniCliente(dni);
        cliente.setNombreCliente(nombre);
        cliente.setApellidosCliente(apellidos);

        if (crudCliente.agregarCliente(cliente)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente agregado correctamente");
            limpiarCampos();
            cargarClientes();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al agregar cliente");
        }
    }

    //Modificar cliente seleccionado
    private void modificarCliente() {
        String dni = cajaDNI.getText();
        String nombre = cajaNombre.getText();
        String apellidos = cajaApellidos.getText();

        if (dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos deben estar llenos");
            return;
        }

        if (crudCliente.actualizarNombreYApellidos(dni, nombre, apellidos)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente actualizado correctamente");
            limpiarCampos();
            cargarClientes();
            cajaDNI.setEditable(true);
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar cliente");
        }
    }

    //Limpiar campos
    private void limpiarCampos() {
        cajaDNI.clear();
        cajaNombre.clear();
        cajaApellidos.clear();
        cajaDNI.setEditable(true);
    }

    //Mostrar alertas
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
