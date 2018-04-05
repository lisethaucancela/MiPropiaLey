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
import ley.entidades.cLey;

/**
 *
 * @author Liseth
 */
public class mLey {

    public static boolean ingresarLey(cLey ley) throws Exception {
        boolean respuesta = false;
        try {
            ArrayList<Parametro> lstP = new ArrayList<>();
            String sql = "SELECT ley.fn_insert_ley(\n"
                    + "   ?, ?, ?, ?);";
            lstP.add(new Parametro(1, ley.getSituacion()));
            lstP.add(new Parametro(2, ley.getTitulo()));
            lstP.add(new Parametro(3, ley.getSolucion()));
            lstP.add(new Parametro(4, ley.getCod_ciudadano()));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstP);
            while (rs.next()) {
                if (rs.getBoolean(0) == true) {
                    respuesta = true;
                }
            }
            rs = null;
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        return respuesta;
    }

    public static ArrayList<cLey> obtenerLey() throws Exception {
        ArrayList<cLey> lstLey = new ArrayList<cLey>();
        try {
            ArrayList<Parametro> lstP = new ArrayList<>();
            String sql = "SELECT * from ley.fn_select_ley();";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql);
            lstLey = llenarLey(rs);
            rs = null;
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        return lstLey;
    }

    public static ArrayList<cLey> llenarLey(ConjuntoResultado rs) throws Exception {
        ArrayList<cLey> lstLey = new ArrayList<cLey>();
        cLey objLey = null;
        try {
            while (rs.next()) {
                objLey = new cLey(rs.getInt(0),
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
                lstLey.add(objLey);
            }
            objLey = null;
        } catch (Exception e) {
            lstLey.clear();
            System.err.println("e" + e.getMessage());
            throw e;
        }
        return lstLey;
    }

    public static boolean votarLey(cLey ley) throws Exception {
        boolean respuesta = false;
        try {
            ArrayList<Parametro> lstP = new ArrayList<>();
            String sql = "SELECT ley.fn_votar_ley(?);";
            lstP.add(new Parametro(1, ley.getCodigo()));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstP);
            while (rs.next()) {
                if (rs.getBoolean(0) == true) {
                    respuesta = true;
                }
            }
            rs = null;
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
        return respuesta;
    }
}
