/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Servicio {
    private int idServicio;
    private String codigo;
    private String descripcion;
    private double costoManoObra;
    
    public Servicio(int idServicio, String descripcion, double costoManoObra) {
        this.idServicio = idServicio;
        this.descripcion = descripcion;
        this.costoManoObra = costoManoObra;
    }

    public int getIdServicio() {
        return idServicio;
    }
    
    public void setIdServicio(int idServicio){
        this.idServicio = idServicio;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public double getCostoManoObra() {
        return costoManoObra;
    } 
    
    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }
}
