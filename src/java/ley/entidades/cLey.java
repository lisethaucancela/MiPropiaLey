/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ley.entidades;

/**
 *
 * @author Liseth
 */
public class cLey {
    private int codigo;
    private String situacion;
    private String titulo;
    private String solucion;
    private int cod_ciudadano;
    private int votos;

    public cLey() {
    }

    public cLey(int codigo, String situacion, String titulo, String solucion, int votos) {
        this.codigo = codigo;
        this.situacion = situacion;
        this.titulo = titulo;
        this.solucion = solucion;
        this.votos = votos;
    }

    public cLey(int codigo, String situacion, String titulo, String solucion) {
        this.codigo = codigo;
        this.situacion = situacion;
        this.titulo = titulo;
        this.solucion = solucion;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public int getCod_ciudadano() {
        return cod_ciudadano;
    }

    public void setCod_ciudadano(int cod_ciudadano) {
        this.cod_ciudadano = cod_ciudadano;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
    
    
    
}
