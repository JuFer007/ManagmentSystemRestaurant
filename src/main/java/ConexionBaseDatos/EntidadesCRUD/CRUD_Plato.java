package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesGestion.Plato;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Plato {
    //Agregar un nuevo plato
    public boolean agregarPlato(Plato plato) {
        String sql = "INSERT INTO Plato (nombrePlato, precioPlato, disponibilidad) VALUES (?, ?, ?)"; // No se guarda rutaImagen
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, plato.getNombrePlato());
            ps.setDouble(2, plato.getPrecioPlato());
            ps.setString(3, plato.getDisponibilidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Listar todos los platos
    public List<String> listarPlatos() {
        List<String> listaPlatos = new ArrayList<>();
        String sql = "SELECT nombrePlato FROM Plato";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String nombrePlato = rs.getString("nombrePlato");
                listaPlatos.add(nombrePlato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPlatos;
    }

    //Actualizar un plato
    public boolean actualizarPlato(Plato plato) {
        String sql = "UPDATE Plato SET nombrePlato = ?, precioPlato = ?, disponibilidad = ? WHERE idPlato = ?"; // No se actualiza rutaImagen
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, plato.getNombrePlato());
            ps.setDouble(2, plato.getPrecioPlato());
            ps.setString(3, plato.getDisponibilidad());
            ps.setInt(4, plato.getIdPlato());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Obtener datos de plato
    public Plato obtenerPlatoPorNombre(String nombrePlato) {
        String sql = "SELECT idPlato, nombrePlato, precioPlato, disponibilidad FROM Plato WHERE nombrePlato = ?"; // No se obtiene rutaImagen
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombrePlato);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Plato plato = new Plato();
                    String nombre = rs.getString("nombrePlato");
                    String rutaImagen = nombre.replaceAll("\\s+", "") + ".jpg";

                    plato.setIdPlato(rs.getInt("idPlato"));
                    plato.setNombrePlato(nombre);
                    plato.setPrecioPlato(rs.getDouble("precioPlato"));
                    plato.setDisponibilidad(rs.getString("disponibilidad"));
                    plato.setRutaImagen(rutaImagen); // Se asigna la ruta generada
                    return plato;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
