/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Mecanico extends Persona {
    private int idMecanico;
    private String especialidad;

    public Mecanico(String m102, String juan_Pérez, String frenos_y_Suspensión) {
        super();
    }

    public Mecanico(int idMecanico, String identificador, String nombre, String especialidad) {
        super(identificador, nombre);
        this.idMecanico = idMecanico;
        this.especialidad = especialidad;
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String mostrarInformacion() {
        return "Mecanico [ID: " + idMecanico + ", Nombre: " + getNombre() + ", Especialidad: " + especialidad + "]";
    }
}
