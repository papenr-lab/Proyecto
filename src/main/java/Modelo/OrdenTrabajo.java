/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
import java.util.Date;

public class OrdenTrabajo {
    private int idOrden;
    private String codigoOrden;
    private Vehiculo vehiculo;
    private Mecanico mecanico;
    private String descripcionProblema;
    private String estado; 
    private Date fechaCreacion;
    private double totalReparacion;

    private ArrayList<DetalleServicio> listaServicio;
    private ArrayList<DetalleRepuesto> listaRepuesto;

    public OrdenTrabajo() {
        this.listaServicio = new ArrayList<>();
        this.listaRepuesto = new ArrayList<>();
        this.estado = "PENDIENTE";
    }

    public void agregarServicio(DetalleServicio detalle) {
        this.listaServicio.add(detalle);
        recalcularTotal();
    }

    public void agregarRepuesto(DetalleRepuesto detalle) {
        this.listaRepuesto.add(detalle);
        recalcularTotal();
    }

    public void recalcularTotal() {
        double total = 0;
        for (DetalleServicio ds : listaServicio) {
            total += ds.getCosto();
        }
        for (DetalleRepuesto dr : listaRepuesto) {
            total += dr.getSubtotal();
        }
        this.totalReparacion = total;
    }

    public String getEstado() {
        return this.estado;
    }

    public double getTotalReparacion() {
        return this.totalReparacion;
    }
}
