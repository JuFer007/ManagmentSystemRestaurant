package Forms;
import Clases.ClasesEmpleados.Administrador;
import Clases.ClasesEmpleados.Empleado;
import Clases.ClasesEmpleados.Mesero;
import ConexionBaseDatos.EntidadesCRUD.CRUD_Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.layout.GridPane;

public class fmrEmpleado {

    @FXML private TextField cajaBusqueda;

    @FXML private TableView<Object[]> tablaEmpleados;
    @FXML private TableColumn<Object[], String> columnDNI;
    @FXML private TableColumn<Object[], String> columnNombre;
    @FXML private TableColumn<Object[], String> columnApellidos;
    @FXML private TableColumn<Object[], Double> columnSalario;
    @FXML private TableColumn<Object[], String> columnCargo;
    @FXML private TableColumn<Object[], String> columnCodigo;
    @FXML private TableColumn<Object[], String> columnEstado;
    @FXML private TableColumn<Object[], String> columnHorasTrabajo;
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaNombre;
    @FXML private TextField cajaApellidoPat;
    @FXML private TextField cajaApellidoMat;
    @FXML private TextField cajaSalarioE;
    @FXML private TextField cajaNumeroTelef;
    @FXML private TextField cajaHorasTrabajo;
    @FXML private Button btnCambiarEstado;
    @FXML private Button btnModificarDatos;
    @FXML private Button btnAgregar;
    @FXML private ComboBox<String> comboBoxCargo;
    @FXML private ComboBox<String> comboBoxEstado;

    private CRUD_Empleado crudEmpleado = new CRUD_Empleado();
    private ObservableList<Object[]> listaEmpleados;

    @FXML
    public void initialize() {
        configurarColumnas();
        configurarTabla();
        cargarEmpleados();
        configurarSeleccionTabla();
        configurarComboBoxes();
    }

    //configurar las columnas
    private void configurarColumnas() {
        columnDNI.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[0]));
        columnNombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[1]));
        columnApellidos.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[2] + " " + cellData.getValue()[3]));
        columnSalario.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((Double) cellData.getValue()[4]));
        columnCargo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[5]));
        columnCodigo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[6]));
        columnEstado.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[7]));
        columnHorasTrabajo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue()[8]));
    }

    //cargar los empleados
    private void cargarEmpleados() {
        listaEmpleados = FXCollections.observableArrayList(crudEmpleado.listarEmpleados());
        tablaEmpleados.setItems(listaEmpleados);
    }

    //seleccion de tabla
    private void configurarSeleccionTabla() {
        tablaEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostrarEmpleadoEnCampos(newSel);
            }
        });
    }

    //mostrar los datos de un empleado al seleccionar
    private void mostrarEmpleadoEnCampos(Object[] fila) {
        cajaDNI.setText((String) fila[0]);
        cajaNombre.setText((String) fila[1]);
        cajaApellidoPat.setText((String) fila[2]);
        cajaApellidoMat.setText((String) fila[3]);
        cajaSalarioE.setText(String.valueOf(fila[4]));
        comboBoxCargo.setValue((String) fila[5]);
        cajaHorasTrabajo.setText((String) fila[8]);
    }

    //comboBox configurados
    private void configurarComboBoxes() {
        comboBoxCargo.setItems(FXCollections.observableArrayList("Administrador", "Mesero", "Gerente", "Cajero"));
        comboBoxEstado.setItems(FXCollections.observableArrayList("Contratado", "Despedido"));
    }

    //limpiar campos
    private void limpiarCampos() {
        cajaDNI.clear();
        cajaNombre.clear();
        cajaApellidoPat.clear();
        cajaApellidoMat.clear();
        cajaSalarioE.clear();
        cajaNumeroTelef.clear();
        cajaHorasTrabajo.clear();
        comboBoxCargo.setValue(null);
        comboBoxEstado.setValue(null);
    }

    //configurar tabla
    private void configurarTabla() {
        tablaEmpleados.setEditable(false);
        tablaEmpleados.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    //agregar un empleado
    @FXML
    private void agregarEmpleado() {
        if (cajaDNI.getText().isEmpty() || cajaNombre.getText().isEmpty() ||
                cajaApellidoPat.getText().isEmpty() || cajaApellidoMat.getText().isEmpty() ||
                cajaSalarioE.getText().isEmpty() || cajaHorasTrabajo.getText().isEmpty() ||
                comboBoxCargo.getValue() == null || comboBoxEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos deben estar llenos");
            return;
        }

        if (!cajaDNI.getText().matches("\\d{8}")) {
            mostrarAlerta(Alert.AlertType.WARNING, "El DNI debe tener 8 dígitos numéricos");
            return;
        }

        if (!cajaNombre.getText().matches("[a-zA-Z\\s]+") ||
                !cajaApellidoPat.getText().matches("[a-zA-Z\\s]+") ||
                !cajaApellidoMat.getText().matches("[a-zA-Z\\s]+")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre y apellidos solo pueden contener letras y espacios");
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(cajaSalarioE.getText());
            if (salario < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Salario debe ser un número positivo");
            return;
        }

        int horas;
        try {
            horas = Integer.parseInt(cajaHorasTrabajo.getText());
            if (horas < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Horas de trabajo deben ser un número entero positivo");
            return;
        }

        String dni = cajaDNI.getText();
        String nombre = cajaNombre.getText();
        String apellidoPat = cajaApellidoPat.getText();
        String apellidoMat = cajaApellidoMat.getText();
        double salarioEmpleado = salario;
        int horasTrabajo = horas;
        String cargo = comboBoxCargo.getValue();
        String estado = comboBoxEstado.getValue();

        Empleado empleado = new Empleado();
        empleado.setDniEmpleado(dni);
        empleado.setNombreEmpleado(nombre);
        empleado.setApellidoPaternoEmpleado(apellidoPat);
        empleado.setApellidoMaternoEmpleado(apellidoMat);
        empleado.setSalarioEmpleado(salarioEmpleado);
        empleado.setHorasTrabajo(horasTrabajo);
        empleado.setCargoEmpleado(cargo);
        empleado.setEstadoEmpleado(estado);

        if (cargo.equalsIgnoreCase("Administrador")) {
            Dialog<Administrador> dialog = new Dialog<>();
            dialog.setTitle("Datos Administrador");
            dialog.setHeaderText("Ingrese teléfono y correo del Administrador");
            ButtonType agregarBtn = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(agregarBtn, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField telefono = new TextField();
            telefono.setPromptText("Teléfono");
            TextField correo = new TextField();
            correo.setPromptText("Correo");

            grid.add(new Label("Teléfono:"), 0, 0);
            grid.add(telefono, 1, 0);
            grid.add(new Label("Correo:"), 0, 1);
            grid.add(correo, 1, 1);

            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == agregarBtn) {
                    Administrador admin = new Administrador();
                    admin.setTelefono(telefono.getText());
                    admin.setCorreoElectronico(correo.getText());
                    return admin;
                }
                return null;
            });

            Administrador admin = dialog.showAndWait().orElse(null);
            if (admin != null) {
                boolean agregado = crudEmpleado.agregarEmpleado(empleado, cargo, admin, null);
                if (agregado) {
                    cargarEmpleados();
                    limpiarCampos();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Administrador agregado correctamente");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error al agregar Administrador");
                }
            }

        } else if (cargo.equalsIgnoreCase("Mesero")) {
            Dialog<Mesero> dialog = new Dialog<>();
            dialog.setTitle("Datos Mesero");
            dialog.setHeaderText("Ingrese turno y código del Mesero");
            ButtonType agregarBtn = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(agregarBtn, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            ComboBox<String> comboTurno = new ComboBox<>();
            comboTurno.setItems(FXCollections.observableArrayList("Mañana", "Tarde", "Noche"));
            comboTurno.setPromptText("Seleccione turno");

            TextField codigoMesero = new TextField();
            codigoMesero.setPromptText("Código Mesero (opcional)");

            grid.add(new Label("Turno:"), 0, 0);
            grid.add(comboTurno, 1, 0);
            grid.add(new Label("Código Mesero:"), 0, 1);
            grid.add(codigoMesero, 1, 1);

            dialog.getDialogPane().setContent(grid);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == agregarBtn) {
                    Mesero mesero = new Mesero();
                    mesero.setTurnoTrabajo(comboTurno.getValue());
                    mesero.setCodigoMesero(codigoMesero.getText().isEmpty() ? mesero.generarCodigo() : codigoMesero.getText());
                    return mesero;
                }
                return null;
            });

            Mesero mesero = dialog.showAndWait().orElse(null);
            if (mesero != null) {
                boolean agregado = crudEmpleado.agregarEmpleado(empleado, cargo, null, mesero);
                if (agregado) {
                    cargarEmpleados();
                    limpiarCampos();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Mesero agregado correctamente");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error al agregar Mesero");
                }
            }
        } else {
            boolean agregado = crudEmpleado.agregarEmpleado(empleado, cargo, null, null);
            if (agregado) {
                cargarEmpleados();
                limpiarCampos();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado agregado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al agregar empleado");
            }
        }
    }

    //para cambiar estado
    @FXML
    private void cambiarEstado() {
        Object[] seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String dni = (String) seleccionado[0];
            String estadoActual = (String) seleccionado[7];
            String nuevoEstado = estadoActual.equals("Activo") ? "Inactivo" : "Activo";

            // Llama al CRUD para actualizar estado
            boolean actualizado = crudEmpleado.despedirEmpleadoPorDni(dni);
            if (actualizado) {
                cargarEmpleados();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Estado cambiado correctamente");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al cambiar estado");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un empleado");
        }
    }

    //alertas
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
