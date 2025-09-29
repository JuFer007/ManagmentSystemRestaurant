package ConexionBaseDatos.EntidadesCRUD;
import Clases.ClasesEmpleados.Administrador;
import Clases.ClasesEmpleados.Empleado;
import Clases.ClasesEmpleados.Mesero;
import ConexionBaseDatos.Conexion_MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Empleado {
    //Listar empleados
    public List<Object[]> listarEmpleados() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT dniEmpleado, nombreEmpleado, apellidoPaternoEmpleado, apellidoMaternoEmpleado, " +
                "salarioEmpleado, cargoEmpleado, codigoEmpleado, estadoEmpleado, horasTrabajo FROM Empleado";

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getString("dniEmpleado");
                fila[1] = rs.getString("nombreEmpleado");
                fila[2] = rs.getString("apellidoPaternoEmpleado");
                fila[3] = rs.getString("apellidoMaternoEmpleado");
                fila[4] = rs.getDouble("salarioEmpleado");
                fila[5] = rs.getString("cargoEmpleado");
                fila[6] = rs.getString("codigoEmpleado");
                fila[7] = rs.getString("estadoEmpleado");
                fila[8] = rs.getString("horasTrabajo");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    //Deshabilitar empleado
    public boolean despedirEmpleadoPorDni(String dniEmpleado) {
        String sql = "UPDATE Empleado SET estadoEmpleado = 'Despedido' WHERE dniEmpleado = ?";
        boolean actualizado = false;

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dniEmpleado);
            int filas = ps.executeUpdate();
            actualizado = (filas > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualizado;
    }

    //Agregar empleado
    public boolean agregarEmpleado(Empleado empleado, String cargo, Administrador administrador, Mesero mesero) {
        String sqlEmpleado = "INSERT INTO Empleado (dniEmpleado, codigoEmpleado, nombreEmpleado, apellidoPaternoEmpleado, " +
                "apellidoMaternoEmpleado, estadoEmpleado, horasTrabajo, salarioEmpleado, cargoEmpleado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        boolean registrado = false;

        try (Connection con = Conexion_MySQL.getInstancia().getConexion();
             PreparedStatement psEmpleado = con.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS)) {

            psEmpleado.setString(1, empleado.getDniEmpleado());
            psEmpleado.setString(2, empleado.getCodigoEmpleado());
            psEmpleado.setString(3, empleado.getNombreEmpleado());
            psEmpleado.setString(4, empleado.getApellidoPaternoEmpleado());
            psEmpleado.setString(5, empleado.getApellidoMaternoEmpleado());
            psEmpleado.setString(6, empleado.getEstadoEmpleado());
            psEmpleado.setInt(7, empleado.getHorasTrabajo());
            psEmpleado.setDouble(8, empleado.getSalarioEmpleado());
            psEmpleado.setString(9, cargo);

            int filas = psEmpleado.executeUpdate();

            if (filas > 0) {
                ResultSet rs = psEmpleado.getGeneratedKeys();
                int idEmpleado = 0;
                if (rs.next()) {
                    idEmpleado = rs.getInt(1);
                }

                if (cargo.equalsIgnoreCase("administrador")) {
                    String sqlAdmin = "INSERT INTO Administrador (idEmpleado, telefono, correoElectronico) VALUES (?, ?, ?)";
                    try (PreparedStatement psAdmin = con.prepareStatement(sqlAdmin)) {
                        psAdmin.setInt(1, idEmpleado);
                        psAdmin.setString(2, administrador.getTelefono());
                        psAdmin.setString(3, administrador.getCorreoElectronico());
                        psAdmin.executeUpdate();
                    }
                } else if (cargo.equalsIgnoreCase("mesero")) {
                    String sqlMesero = "INSERT INTO Mesero (idEmpleado, turnoTrabajo, codigoMesero) VALUES (?, ?, ?)";
                    try (PreparedStatement psMesero = con.prepareStatement(sqlMesero)) {
                        psMesero.setInt(1, idEmpleado);
                        psMesero.setString(2, mesero.getTurnoTrabajo());
                        psMesero.setString(3, mesero.getCodigoMesero());
                        psMesero.executeUpdate();
                    }
                }
                registrado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrado;
    }
}
