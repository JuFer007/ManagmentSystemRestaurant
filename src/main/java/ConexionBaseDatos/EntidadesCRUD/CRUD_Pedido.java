package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesGestion.Pedido;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Pedido {
    //Crear un nuevo pedido y devolver el ID generado
    public int crearPedido(Pedido pedido) {
        String sql = "INSERT INTO Pedido (idMesa, idMesero, idCliente, fecha, estadoPedido, codigoPedido, totalPedido) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idPedidoGenerado = -1;
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pedido.getIdMesa());
            ps.setInt(2, pedido.getIdMesero());
            ps.setInt(3, pedido.getIdCliente());
            ps.setDate(4, pedido.getFecha());
            ps.setString(5, pedido.getEstadoPedido());
            ps.setString(6, pedido.getCodigoPedido());
            ps.setDouble(7, pedido.getTotalPedido());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPedidoGenerado = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPedidoGenerado;
    }

    //Listar todos los pedidos
    public List<Object[]> listarPedidos() {
        List<Object[]> listaPedidos = new ArrayList<>();
        String sql = "SELECT pedido.idPedido, pedido.codigoPedido, cliente.nombreCliente, " +
                "mesa.numeroMesa, pedido.estadoPedido, pedido.totalPedido " +
                "FROM pedido " +
                "INNER JOIN cliente ON pedido.idCliente = cliente.idCliente " +
                "INNER JOIN mesa ON mesa.idMesa = pedido.idMesa " +
                "ORDER BY pedido.idPedido";

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("idPedido");
                fila[1] = rs.getString("codigoPedido");
                fila[2] = rs.getString("nombreCliente");
                fila[3] = rs.getInt("numeroMesa");
                fila[4] = rs.getString("estadoPedido");
                fila[5] = rs.getDouble("totalPedido");

                listaPedidos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPedidos;
    }
}
