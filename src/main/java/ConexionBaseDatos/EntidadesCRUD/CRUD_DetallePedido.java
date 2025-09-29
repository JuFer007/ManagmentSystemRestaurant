package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesGestion.DetallePedido;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUD_DetallePedido {
    //Agregar un detalle a un pedido
    public boolean agregarDetallePedido(DetallePedido detalle) {
        String sql = "INSERT INTO DetallePedido (idPedido, idPlato, cantidad, subTotal) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getIdPedido());
            ps.setInt(2, detalle.getIdPlato());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Obtener todos los detalles de un pedido específico
    public List<Object[]> listarDetallesPorPedido(int idPedido) {
        List<Object[]> detalles = new ArrayList<>();
        String sql = "SELECT plato.idPlato, plato.nombrePlato, detallePedido.cantidad, detallePedido.subTotal " +
                "FROM plato " +
                "INNER JOIN detallePedido ON plato.idPlato = detallePedido.idPlato " +
                "WHERE detallePedido.idPedido = ? " +
                "ORDER BY detallePedido.idDetallePedido";

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[4];
                    fila[0] = rs.getInt("idPlato");
                    fila[1] = rs.getString("nombrePlato");
                    fila[2] = rs.getInt("cantidad");
                    fila[3] = rs.getDouble("subTotal");
                    detalles.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
