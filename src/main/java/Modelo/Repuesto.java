/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Repuesto {
    private int idRepuesto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;
    
    public Repuesto(int idRepuesto, String nombre, String descripcion, double precio, int cantidad) {
        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidad;
    }
    
    public double getPrecio() {
        return this.precio;
    }
    
    public boolean validarStock(int cantidadRequerida) {
        return this.cantidadDisponible >= cantidadRequerida && cantidadRequerida > 0;
    }
    
    public int getIdRepuesto() {
        return idRepuesto; 
    }
    
    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public String getDescripcion() {
    return descripcion;
}

    public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}
}
