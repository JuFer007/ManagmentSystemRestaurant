package Forms;
import Clases.ClasesGestion.Plato;
import Clases.ClasesGestion.gestorImagenPlato;
import ConexionBaseDatos.EntidadesCRUD.CRUD_Plato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class fmrPlato {
    //lista de platos
    @FXML private ListView<String> listPlatos;

    //imagen del plato
    @FXML private ImageView imgPlato;

    //cajas para nuevos datos
    @FXML private TextField nombreNuevoPlato, nuevoPrecioPlato, precioPlato, disponibilidadPlato;

    //label
    @FXML private Label nombrePlato;

    //botones
    @FXML private Button btnSubirImg, btnAgregarPlato, btnEditar;

    @FXML private AnchorPane AnchorPane;

    private File fileSeleccionado;
    private CRUD_Plato crudPlato = new CRUD_Plato();
    private ObservableList<String> listaObservablePlatos;

    @FXML
    public void initialize() {
        AnchorPane.setTopAnchor(imgPlato, 0.0);
        AnchorPane.setBottomAnchor(imgPlato, 0.0);
        AnchorPane.setLeftAnchor(imgPlato, 0.0);
        AnchorPane.setRightAnchor(imgPlato, 0.0);

        imgPlato.setPreserveRatio(false);

        Rectangle clip = new Rectangle();
        clip.setWidth(imgPlato.getFitWidth());
        clip.setHeight(imgPlato.getFitHeight());
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imgPlato.setClip(clip);

        cargarListaPlatos();
        configurarSeleccionPlatos();
        if (!listaObservablePlatos.isEmpty()) {
            listPlatos.getSelectionModel().select(0);
        }

        btnSubirImg.setOnAction(e -> seleccionarImagen());
        btnAgregarPlato.setOnAction(e -> agregarPlato());
        btnEditar.setOnAction(e -> editarPlato());
    }

    //Cargar lista de platos
    private void cargarListaPlatos() {
        listaObservablePlatos = FXCollections.observableArrayList(crudPlato.listarPlatos());
        listPlatos.setItems(listaObservablePlatos);
    }

    //para seleccioanr un plato
    private void configurarSeleccionPlatos() {
        listPlatos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Plato plato = crudPlato.obtenerPlatoPorNombre(newVal);
                if (plato != null) {
                    precioPlato.setEditable(false);
                    disponibilidadPlato.setEditable(false);

                    nombrePlato.setText(plato.getNombrePlato());
                    precioPlato.setText(String.valueOf(plato.getPrecioPlato()));
                    disponibilidadPlato.setText(String.valueOf(plato.getDisponibilidad()));
                    gestorImagenPlato.mostrarImagen(plato, imgPlato);
                    if (plato.getRutaImagen() != null) {
                        fileSeleccionado = new File("imagenesPlatos/" + plato.getRutaImagen()); // Para edición posterior
                    } else {
                        fileSeleccionado = null;
                    }
                }
            }
        });
    }

    //seleccionar imagen de un plato
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Plato");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) btnSubirImg.getScene().getWindow();
        fileSeleccionado = fileChooser.showOpenDialog(stage);

        if (fileSeleccionado != null) {
            Image image = new Image(fileSeleccionado.toURI().toString());
            imgPlato.setImage(image);
        }
    }

    //Agregar un nuevo plato
    private void agregarPlato() {
        if (nombreNuevoPlato.getText().isEmpty() || nuevoPrecioPlato.getText().isEmpty() || fileSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos deben estar llenos y seleccionar una imagen");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(nuevoPrecioPlato.getText());
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "El precio debe ser un número positivo");
            return;
        }

        String nombre = nombreNuevoPlato.getText();
        String nombreArchivo = nombre.replaceAll("\\s+", "") + ".jpg";

        Plato plato = new Plato();
        plato.setNombrePlato(nombre);
        plato.setPrecioPlato(precio);
        plato.setDisponibilidad("Disponible");
        plato.setRutaImagen(nombreArchivo);

        try {
            gestorImagenPlato.cambiarImagen(nombre, fileSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar la imagen");
            return;
        }

        boolean agregado = crudPlato.agregarPlato(plato);
        if (agregado) {
            cargarListaPlatos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Plato agregado correctamente");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al agregar plato");
        }
    }

    //Editar plato seleccionado
    private void editarPlato() {
        String nombreSeleccionado = listPlatos.getSelectionModel().getSelectedItem();
        if (nombreSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un plato primero");
            return;
        }

        Plato plato = crudPlato.obtenerPlatoPorNombre(nombreSeleccionado);
        if (plato == null) return;

        if (nombreNuevoPlato.getText().isEmpty() || nuevoPrecioPlato.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Todos los campos deben estar llenos");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(nuevoPrecioPlato.getText());
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "El precio debe ser un número positivo");
            return;
        }

        String nuevoNombre = nombreNuevoPlato.getText();
        plato.setNombrePlato(nuevoNombre);
        plato.setPrecioPlato(precio);

        if (fileSeleccionado != null) {
            String nombreArchivo = nuevoNombre.replaceAll("\\s+", "") + ".jpg";
            try { // Guardar la nueva imagen física
                gestorImagenPlato.cambiarImagen(nuevoNombre, fileSeleccionado);
                plato.setRutaImagen(nombreArchivo);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar la imagen");
                return;
            }
        }

        boolean actualizado = crudPlato.actualizarPlato(plato);
        if (actualizado) {
            cargarListaPlatos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Plato actualizado correctamente");
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar plato");
        }
    }

    //mostrar alertas
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
