package ConexionBaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_POSTGRESQL {
    private static final String CADENA_CONEXION = "jdbc:postgresql://localhost:5432/Restaurante";
    private static final String USUARIO = "postgres";
    private static final String CONTRASEÑA = "Fjunior07!";

    private static Conexion_POSTGRESQL instancia = null;

    private Conexion_POSTGRESQL() {
    }

    public static Conexion_POSTGRESQL getInstancia() {
        if (instancia == null) {
            instancia = new Conexion_POSTGRESQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            return DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
