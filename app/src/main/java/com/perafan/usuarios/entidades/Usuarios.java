package com.perafan.usuarios.entidades;

import java.util.HashMap;

public class Usuarios {

    private int idU;
    private String nombre;
    private String apellidos;
    private String email;
    private String clave;
    private int telefono;
    private int acepta;
    public HashMap<Integer,Integer> cart;


    public Usuarios() {

    }
    public Usuarios(int idU, String nombre, String apellidos, String email, String clave, int telefono, int acepta) {
        this.idU = idU;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.acepta = acepta;
        this.cart = cart;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) { this.idU = idU; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTelefono() {return telefono;   }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getAcepta() {
        return acepta;
    }

    public void setAcepta(int acepta) {
        this.acepta = acepta;
    }

}
