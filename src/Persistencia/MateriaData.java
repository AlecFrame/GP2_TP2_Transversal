
package Persistencia;

import Modelo.Materia;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class MateriaData {
    private Connection con;
    
    public MateriaData(Connection con) {
        this.con = con;
    }
    
    public void guardarMateria(Materia m) {
        try {
            String sql = "insert into materia(idMateria, nombre, anio, estado)"
                    + " values (?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, m.getIdMateria());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getAnio());
            ps.setBoolean(4, m.isEstado());
            
            int filas = ps.executeUpdate();
            if (filas>0) {
                System.out.println("Materia Registrada con exito");
            }
        }catch(SQLException e) {
            System.err.println("Datos de materia son incompatibles");
        }
    }
    
    public Materia buscarMateria(int codigo) {
        Materia materia = null;
        try{
            String sql = "select idMateria,nombre,anio,estado from materia where idMateria = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            ResultSet resultado = ps.executeQuery();
            while(resultado.next()) {
                materia = new Materia(resultado.getInt("idMateria"),resultado.getString("nombre"),
                    resultado.getInt("anio"),resultado.getBoolean("estado"));
            }
        }catch(SQLException e) {
            System.err.println("Materia no encontrada "+e);
        }
        return materia;
    }
    
    public void eliminarMateria(int codigo) {
        try{
            String sql = "delete from materia where idMateria = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            int filas = ps.executeUpdate();
            if (filas>0) {
                System.out.println("Se ha eliminado la materia correctamente");
            }else
                System.err.println("No se ha encontrado la materia");
        }catch(SQLException e) {
            System.err.println("Materia no encontrada "+e);
        }
    }
    
    public void actualizarMateria(Materia m,String cambiar) {
        try {
            int filas=0;
            if (cambiar.contains("nombre")) {
                if (m.getNombre()!=null) {
                    String sql = "update materia set nombre=? where idMateria=?";

                    PreparedStatement st = con.prepareStatement(sql);
                    st.setString(1, m.getNombre());
                    st.setInt(2, m.getIdMateria());

                    filas = st.executeUpdate();
                }else
                    System.err.println("No se actualizo el nombre de la amteria ("+m.getIdMateria()+") porque es nulo");
            }
            if (cambiar.contains("anio")) {
                if (m.getAnio()!=0) {
                    String sql = "update materia set anio=? where idMateria=?";

                    PreparedStatement st = con.prepareStatement(sql);
                    st.setInt(1, m.getAnio());
                    st.setInt(2, m.getIdMateria());

                    filas = st.executeUpdate();
                }else
                    System.err.println("No se actualizo el dni de la materia ("+m.getIdMateria()+") porque es 0");
            }
            if (cambiar.contains("estado")) {
                String sql = "update materia set estado=? where idMateria=?";
                
                PreparedStatement st = con.prepareStatement(sql);
                st.setBoolean(1, m.isEstado());
                st.setInt(2, m.getIdMateria());
                
                filas = st.executeUpdate();
            }
            if (filas>0) {
                System.out.println("Materia ("+m.getIdMateria()+") actualizado con exito");
            }else
                System.err.println("No se encontra el codigo de la Materia");
        }catch(SQLException e) {
            System.err.println("Datos de materia incompatibles: "+e);
        }
    }
    
    public void cambiarEstado(int dni) throws SQLException {
        int filas=0;
        String sql = "update alumno set estado=? where dni=?";
                
        PreparedStatement st = con.prepareStatement(sql);
        st.setBoolean(1, false);
        st.setInt(2, dni);
                
        filas = st.executeUpdate();
        if (filas>0) {
            System.out.println("Estado de Alumno actualizado con exito");
        }else
            System.err.println("No se encontra el dni del alumno");
    }
    
    public void motrarTablaMateria() throws SQLException {
        Statement statement = con.createStatement();
        ResultSet resultado = statement.executeQuery("SELECT * FROM materia");
        
        while(resultado.next()) {
            String estado = (resultado.getBoolean("estado"))? "activo":"inactivo";
            System.out.println(resultado.getString("idMateria")+", "+resultado.getString("nombre")+", "+resultado.getInt("anio")+", "+estado);
        }
    }
}
