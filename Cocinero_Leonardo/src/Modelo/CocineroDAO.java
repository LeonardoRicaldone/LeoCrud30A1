/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Leonardo
 */
import static Modelo.ClaseConexion.getConexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CocineroDAO {

    // Método para insertar un nuevo cocinero
    public boolean insertarCocinero(Cocinero cocinero) {
        String sql = "INSERT INTO tbCocinero (Nombre_Cocinero, Edad_Cocinero, Peso_Cocinero, Correo_Cocinero) VALUES (?, ?, ?, ?)";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cocinero.getNombreCocinero());
            ps.setInt(2, cocinero.getEdadCocinero());
            ps.setDouble(3, cocinero.getPesoCocinero());
            ps.setString(4, cocinero.getCorreoCocinero());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un cocinero
    public boolean actualizarCocinero(Cocinero cocinero) {
        String sql = "UPDATE tbCocinero SET Nombre_Cocinero=?, Edad_Cocinero=?, Peso_Cocinero=?, Correo_Cocinero=? WHERE ID_Cocinero=?";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cocinero.getNombreCocinero());
            ps.setInt(2, cocinero.getEdadCocinero());
            ps.setDouble(3, cocinero.getPesoCocinero());
            ps.setString(4, cocinero.getCorreoCocinero());
            ps.setInt(5, cocinero.getIdCocinero());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un cocinero
    public boolean eliminarCocinero(int idCocinero) {
        String sql = "DELETE FROM tbCocinero WHERE ID_Cocinero=?";
        try (Connection con = getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCocinero);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos los cocineros
    public List<Cocinero> listarCocineros() {
        List<Cocinero> cocineros = new ArrayList<>();
        String sql = "SELECT * FROM tbCocinero";
        try (Connection con = getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cocinero cocinero = new Cocinero(
                    rs.getInt("ID_Cocinero"),
                    rs.getString("Nombre_Cocinero"),
                    rs.getInt("Edad_Cocinero"),
                    rs.getDouble("Peso_Cocinero"),
                    rs.getString("Correo_Cocinero")
                );
                cocineros.add(cocinero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cocineros;
    }
}

