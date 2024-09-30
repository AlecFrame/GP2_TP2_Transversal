
package Persistencia;

import Modelo.Materia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionData {
    private Connection con = Conexion.cargaConexion();
    
    public List<Materia> obtenerMateriasCursadas(int id) throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT materia.idMateria,materia.nombre,materia.anio,materia.estado FROM inscripcion JOIN materia on (materia.idMateria=inscripcion.idMateria) WHERE inscripcion.idAlumno=?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        
        ResultSet r = ps.executeQuery();
        
        while (r.next()) {
            lista.add(new Materia(r.getInt("idMateria"),r.getString("nombre"),r.getInt("anio"),r.getBoolean("estado")));
        }
        
        return lista;
    }
    
}
