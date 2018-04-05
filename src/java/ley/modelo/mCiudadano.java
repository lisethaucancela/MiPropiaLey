/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ley.modelo;
import java.sql.SQLException;
import java.util.ArrayList;
import ley.accesodatos.AccesoDatos;
import ley.accesodatos.ConjuntoResultado;
import ley.accesodatos.Parametro;
import ley.entidades.cCiudadano;

/**
 *
 * @author Liseth
 */
public class mCiudadano {

    public static int ingresarCiudadano(cCiudadano ciudadano) throws Exception {
        int respuesta = 0;
        try {
            ArrayList<Parametro> lstP = new ArrayList<>();
            String sql = "SELECT ley.fn_insert_ciudadano(\n"
                    + "   ?,?,?,?,?);";
            lstP.add(new Parametro(1, ciudadano.getCedula()));
            lstP.add(new Parametro(2, ciudadano.getNombres()));
            lstP.add(new Parametro(3, ciudadano.getApellidos()));
            lstP.add(new Parametro(4, ciudadano.getMail()));
            lstP.add(new Parametro(5, ciudadano.getTelefono()));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstP);
            while (rs.next()) {
                respuesta = rs.getInt(0);
            }
            rs = null;
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        return respuesta;
    }
}
