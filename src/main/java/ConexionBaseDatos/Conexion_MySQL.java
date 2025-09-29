package ConexionBaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_MySQL {
    private static final String cadena_Conexion = "jdbc:mysql://localhost:3306/Restaurante?useSSL=false&serverTimezone=UTC";
    private static final String usuario = "root";
    private static final String contraseña = "123456";

    private static Conexion_MySQL instancia = null;

    private Conexion_MySQL() {
    }

    public static Conexion_MySQL getInstancia() {
        if (instancia == null) {
            instancia = new Conexion_MySQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            return DriverManager.getConnection(cadena_Conexion, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
