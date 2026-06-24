package com.natacion.dao;

import com.natacion.model.Participante;
import com.natacion.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

    private final Connection con = Conexion.getInstancia().getConexion();

    public boolean insertar(Participante p) {
        String sql = "INSERT INTO participantes "
                   + "(cedula, nombre, apellido, edad, correo, estado_civil, jornada, categoria, observaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setInt   (4, p.getEdad());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getEstadoCivil());
            ps.setString(7, p.getJornada());
            ps.setString(8, p.getCategoria());
            ps.setString(9, p.getObservaciones());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }


    public List<Participante> listar() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM participantes ORDER BY id";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }


    public boolean actualizar(Participante p) {
        String sql = "UPDATE participantes SET "
                   + "cedula=?, nombre=?, apellido=?, edad=?, correo=?, "
                   + "estado_civil=?, jornada=?, categoria=?, observaciones=? "
                   + "WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,  p.getCedula());
            ps.setString(2,  p.getNombre());
            ps.setString(3,  p.getApellido());
            ps.setInt   (4,  p.getEdad());
            ps.setString(5,  p.getCorreo());
            ps.setString(6,  p.getEstadoCivil());
            ps.setString(7,  p.getJornada());
            ps.setString(8,  p.getCategoria());
            ps.setString(9,  p.getObservaciones());
            ps.setInt   (10, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM participantes WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }


    public boolean correoExiste(String correo, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM participantes WHERE correo=? AND id<>?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setInt   (2, idExcluir);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
            return false;
        }
    }

    // ── Mapear ResultSet → Participante ───────────────────────────────
    private Participante mapear(ResultSet rs) throws SQLException {
        return new Participante(
            rs.getInt   ("id"),
            rs.getString("cedula"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getInt   ("edad"),
            rs.getString("correo"),
            rs.getString("estado_civil"),
            rs.getString("jornada"),
            rs.getString("categoria"),
            rs.getString("observaciones")
        );
    }
}
