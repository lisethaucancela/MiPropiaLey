/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ley.controlador;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ley.entidades.cLey;
import ley.modelo.mLey;
import recursos.Util;

/**
 *
 * @author Liseth
 */
@ManagedBean
@RequestScoped
public class votarControlador {

    private cLey selLey;
    private ArrayList<cLey> lstLey;

    public votarControlador() {
        this.lstLey = new ArrayList<>();
        this.selLey = new cLey();
    }

    public void cargarLeyes() {
        try {
            lstLey = mLey.obtenerLey();
        } catch (Exception e) {
            System.err.println("e" + e.getMessage());
        }
    }

    @PostConstruct
    public void reinit() {
        cargarLeyes();
    }

    public void votar() {
        try {
            System.err.println(selLey.getTitulo());
            if (mLey.votarLey(selLey)) {
                Util.addSuccessMessage("Gracias", "Tu voto ha sido registrado");
                cargarLeyes();
            }
            selLey = null;
            selLey = new cLey();
        } catch (Exception e) {
            System.err.println("e" + e.getMessage());
            Util.addErrorMessage("Vuelva a intentarlo");
        }
    }

    public ArrayList<cLey> getLstLey() {
        return lstLey;
    }

    public void setLstLey(ArrayList<cLey> lstLey) {
        this.lstLey = lstLey;
    }

    public cLey getSelLey() {
        return selLey;
    }

    public void setSelLey(cLey selLey) {
        this.selLey = selLey;
    }

}
