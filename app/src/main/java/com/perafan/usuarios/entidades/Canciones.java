package com.perafan.usuarios.entidades;

import android.net.Uri;

import java.io.Serializable;

public class Canciones implements Serializable
{
    private int idLC;
    private String titulo;
    private String artista;
    private String album;
    private String genero;
    private float  precio;
    private String img;

    public Canciones() {
    }


    public Canciones(int idLC, String titulo, String artista, String album, String genero, float precio,String img) {
        this.idLC = idLC;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.genero = genero;
        this.precio = precio;
        this.img = img;
    }


    public int getIdLC() {
        return idLC;
    }

    public void setIdLC(int idLC) {
        this.idLC = idLC;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getImg() {   return img;     }

    public void setImg(String img) {  this.img = img;     }

}
