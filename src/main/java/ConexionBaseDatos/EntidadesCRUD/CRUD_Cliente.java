package ConexionBaseDatos.EntidadesCRUD;

import Clases.ClasesEmpleados.Cliente;
import ConexionBaseDatos.Conexion_MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Cliente {
    //Crear cliente
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (dniCliente, nombreCliente, apellidosCliente) VALUES (?, ?, ?)";
        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getDniCliente());
            ps.setString(2, cliente.getNombreCliente());
            ps.setString(3, cliente.getApellidosCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Listar todos los clientes
    public List<Object[]> listarClientes() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT dniCliente, nombreCliente, apellidosCliente FROM Cliente";

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getString("dniCliente");
                fila[1] = rs.getString("nombreCliente");
                fila[2] = rs.getString("apellidosCliente");
                lista.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //Actualizar cliente por DNI
    public boolean actualizarNombreYApellidos(String dniCliente, String nuevoNombre, String nuevosApellidos) {
        String sql = "UPDATE Cliente SET nombreCliente = ?, apellidosCliente = ? WHERE dniCliente = ?";

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevosApellidos);
            ps.setString(3, dniCliente);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
