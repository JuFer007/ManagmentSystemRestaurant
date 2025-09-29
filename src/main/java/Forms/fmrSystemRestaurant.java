package Forms;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class fmrSystemRestaurant extends Application {

    @FXML private Button btnCerrarSesion;
    @FXML private Button btnCerrarVentana;
    @FXML private Menu menuGestionarEmpleados;
    @FXML private MenuItem menuGestionEmpelados;
    @FXML private MenuItem menuGestionClientes;
    @FXML private Menu menuPedidos;
    @FXML private MenuItem menuVerPedidos;
    @FXML private MenuItem menuAgregarPedido;
    @FXML private Menu menuGestionPlatos;
    @FXML private MenuItem menuVerPlatos;
    @FXML private Menu menuPagos;
    @FXML private MenuItem menuVerPagos;
    @FXML private Menu menuReportes;
    @FXML private MenuItem menuReporteGeneral;
    @FXML private AnchorPane contenedorPantallas;


    @FXML
    private void initialize() {
        menuGestionEmpelados.setOnAction(e -> cargarPantalla("/Formularios/GestionPersonas/Empleados.fxml"));
        menuVerPedidos.setOnAction(e -> cargarPantalla("/Formularios/GestionPedidos/Pedidos.fxml"));
        menuAgregarPedido.setOnAction(e -> cargarPantalla("/Formularios/GestionPedidos/NuevoPedido.fxml"));
        menuVerPlatos.setOnAction(e -> cargarPantalla("/Formularios/Platos/Plato.fxml"));
        menuVerPagos.setOnAction(e -> cargarPantalla("/Formularios/Pago/Pago.fxml"));
        menuReporteGeneral.setOnAction(e -> cargarPantalla("/Formularios/Reporte/Reporte.fxml"));
        menuGestionClientes.setOnAction(e -> cargarPantalla("/Formularios/GestionPersonas/Clientes.fxml"));

        btnCerrarVentana.setOnAction(e -> cerrarVentana());
    }

    //cargar pantalla
    private void cargarPantalla(String fxml) {
        try {
            AnchorPane pantalla = FXMLLoader.load(getClass().getResource(fxml));
            contenedorPantallas.getChildren().clear();
            contenedorPantallas.getChildren().add(pantalla);

            AnchorPane.setTopAnchor(pantalla, 0.0);
            AnchorPane.setBottomAnchor(pantalla, 0.0);
            AnchorPane.setLeftAnchor(pantalla, 0.0);
            AnchorPane.setRightAnchor(pantalla, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cerrarVentana() {
        System.exit(0);
    }


    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Formularios/Principales/SystemRestaurant.fxml"));
        Scene scene = new Scene(root);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
            primaryStage.setOpacity(0.8);
        });

        root.setOnMouseReleased(event -> primaryStage.setOpacity(1));

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
