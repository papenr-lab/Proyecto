/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tallermecanico;

import Modelo.Cliente;
import Modelo.Mecanico;
import Modelo.Vehiculo;
import Modelo.Repuesto;
import Modelo.Servicio;
import Modelo.OrdenTrabajo;
import Modelo.DetalleServicio;
import Modelo.DetalleRepuesto;

public class TallerMecanico {

    public static void main(String[] args) {
        System.out.println("=====Probando el sistema del Taller El Ultimo Arrranque=====\n");
        
        Cliente cliente = new Cliente("C001", "María López");
        Mecanico mecanico = new Mecanico("M102", "Juan Pérez", "Frenos y Suspensión");

        System.out.println(cliente.mostrarInformacion());
        System.out.println(mecanico.mostrarInformacion());
        System.out.println();

        Servicio cambioAceite = new Servicio(1, "Cambio de aceite", 100.0);
        Repuesto filtroAceite = new Repuesto(101, "Filtro de Aceite", "Filtro sintético", 75.0, 20);

        OrdenTrabajo orden = new OrdenTrabajo();

        DetalleServicio detServicio = new DetalleServicio(cambioAceite);
        DetalleRepuesto detRepuesto = new DetalleRepuesto(filtroAceite, 1);
        
        orden.agregarServicio(detServicio);
        orden.agregarRepuesto(detRepuesto);

        System.out.println("Estado de la orden: " + orden.getEstado());
        System.out.println("Total calculado para la reparacion: Q" + orden.getTotalReparacion());
    }
}
