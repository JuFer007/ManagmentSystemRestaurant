package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesGestion.Mesa;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Mesa {
    //Agregar una nueva mesa
    public boolean agregarMesa(Mesa mesa) {
        String sql = "INSERT INTO Mesa (numeroMesa, capacidad, estadoMesa) VALUES (?, ?, ?)";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumeroMesa());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstadoMesa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Listar todas las mesas
    public List<Mesa> listarMesas() {
        List<Mesa> listaMesas = new ArrayList<>();
        String sql = "SELECT idMesa, idMesero, numeroMesa, capacidad, estadoMesa FROM Mesa";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setIdMesero(rs.getInt("idMesero"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setCapacidad(rs.getInt("capacidad"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                listaMesas.add(mesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    //Actualizar el estado de una mesa
    public boolean actualizarEstadoMesa(int idMesa, String nuevoEstado) {
        String sql = "UPDATE Mesa SET estadoMesa = ? WHERE idMesa = ?";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMesa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
