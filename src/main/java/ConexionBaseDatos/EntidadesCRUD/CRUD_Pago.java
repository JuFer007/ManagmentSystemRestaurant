package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesFInanzas.Pago;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUD_Pago {
    //Registrar un nuevo pago
    public boolean registrarPago(Pago pago) {
        String sql = "INSERT INTO Pago (idPedido, montoPago, metodoPago, fechaPago) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pago.getIdPedido());
            ps.setDouble(2, pago.getMontoPago());
            ps.setString(3, pago.getMetodoPago());
            ps.setDate(4, pago.getFechaPago());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
