package Clases;
import ConexionBaseDatos.Conexion_POSTGRESQL;
import java.sql.Connection;

public class AppRestaurant {
    public static void main(String[] args) {
        Conexion_POSTGRESQL conexionPostgreSQL = Conexion_POSTGRESQL.getInstancia();

        // Obtener la conexión
        Connection conexion = conexionPostgreSQL.getConexion();

        if (conexion != null) {
            System.out.println("Conexión exitosa a la base de datos.");
        } else {
            System.out.println("No se pudo conectar a la base de datos.");
        }
    }
}
