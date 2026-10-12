/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class DetalleRepuesto {
    private int idDetalle;
    private Repuesto repuesto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    
    public DetalleRepuesto(Repuesto repuesto, int cantidad) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.subtotal = repuesto.getPrecio() * cantidad;
    }
    
    public Repuesto getRepuesto() {
        return repuesto;
    }
    
    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.repuesto.getPrecio() * cantidad;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
}