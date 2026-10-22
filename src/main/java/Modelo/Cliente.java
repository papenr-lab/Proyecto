/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Cliente extends Persona {
    private int idCliente;
    private String telefono;
    private String correo;

    public Cliente(String c001, String maría_López) {
        super();
    }

    public Cliente(int idCliente, String identificador, String nombre, String telefono, String correo) {
        super(identificador, nombre);
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String mostrarInformacion() {
        return "Cliente [ID: " + getIdentificador() + ", Nombre: " + getNombre() + "]";
    }
}
