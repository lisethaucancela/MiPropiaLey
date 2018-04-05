/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ley.controlador;

import facebook4j.FacebookException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import ley.entidades.cCiudadano;
import ley.entidades.cLey;
import ley.modelo.mCiudadano;
import ley.modelo.mLey;
import model.Publish;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import recursos.Util;
import recursos.Validaciones;

/**
 *
 * @author Liseth
 */
@ManagedBean
@ViewScoped
public class leyControlador {

    private cLey objLey = new cLey();
    private cCiudadano objCiudadano = new cCiudadano();

    private boolean skip;
    private cLey selLey;
    private ArrayList<cLey> lstLey;

    public leyControlador() {
        this.lstLey = new ArrayList<>();
        this.selLey = new cLey();
        this.objLey = new cLey();
        this.objCiudadano = new cCiudadano();
    }

    public String url() {
        return "/crear-ley.xhtml#publicar";
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

    public void publicar() {
        try {
            Publish.publicar(objLey);
            RequestContext.getCurrentInstance().execute("share()");
        } catch (FacebookException | ParseException | FileNotFoundException ex) {
            System.out.println("Error en el controlador, al publicar!");
        }
    }

    public void insertarLey() {
        try {
            //RequestContext.getCurrentInstance().execute("share()");
            if (objCiudadano.getCedula() != null && objCiudadano.getApellidos() != null
                    && objCiudadano.getNombres() != null && objCiudadano.getTelefono() != null
                    && objCiudadano.getMail() != null && objLey.getSituacion() != null
                    && objLey.getSolucion() != null && objLey.getTitulo() != null) {
                int cod = mCiudadano.ingresarCiudadano(objCiudadano);
                if (cod > 0) {
                    objLey.setCod_ciudadano(cod);
                    if (mLey.ingresarLey(objLey)) {

                        //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                        //ec.redirect("index.xhtml#publicar");
                        Util.addSuccessMessage("Succesful", "Datos Ingresados Correctamente");
                        cargarLeyes();
                        //RequestContext.getCurrentInstance().reset("frmLey");
                        //RequestContext.getCurrentInstance().execute("PF('modalIngreso').show()");
                        //RequestContext.getCurrentInstance().execute("$('#modalIngreso').modal('show')");
                        //publicar();    
                        //RequestContext.getCurrentInstance().execute("limpiarForm()"); 
                        // RequestContext.getCurrentInstance().reset("frmLey:wizard");
                        objCiudadano = null;
                        objLey = null;
                        objCiudadano = new cCiudadano();
                        objLey = new cLey();

                    } else {
                        Util.addErrorMessage("Datos no Ingresados");
                    }
                } else {
                    Util.addErrorMessage("Datos NO Ingresados");
                }
            } else {
                Util.addErrorMessage("Por favor ingrese los datos correctamente");
            }

        } catch (Exception e) {
            System.err.println("e" + e.getMessage());
            Util.addErrorMessage("Vuelva a intentarlo");
        }
    }

    public void limpiar() {
        objCiudadano.setApellidos("");
        objCiudadano.setNombres("");
        objCiudadano.setCedula("");
        objCiudadano.setMail("");
        objCiudadano.setTelefono("");
        objLey.setSituacion("");
        objLey.setSolucion("");
        objLey.setTitulo("");
    }

    public void k() {
        // publicar();
        RequestContext.getCurrentInstance().execute("share()");

    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;	//reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public cLey getObjLey() {
        return objLey;
    }

    public void setObjLey(cLey objLey) {
        this.objLey = objLey;
    }

    public cCiudadano getObjCiudadano() {
        return objCiudadano;
    }

    public void setObjCiudadano(cCiudadano objCiudadano) {
        this.objCiudadano = objCiudadano;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public cLey getSelLey() {
        return selLey;
    }

    public void setSelLey(cLey selLey) {
        this.selLey = selLey;
    }

    public ArrayList<cLey> getLstLey() {
        return lstLey;
    }

    public void setLstLey(ArrayList<cLey> lstLey) {
        this.lstLey = lstLey;
    }

}
