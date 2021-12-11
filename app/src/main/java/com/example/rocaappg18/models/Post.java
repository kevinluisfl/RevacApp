package com.example.rocaappg18.models;

public class Post {
    private String id;
    private String nombre;
    private String documento;
    private String imagen1;
    private String correo;
    private String telefono;
    private String vacuna;
    private String IdUser;

    public Post() {
    }

    public Post(String id, String nombre, String documento, String imagen1, String correo, String telefono, String vacuna, String idUser) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.imagen1 = imagen1;
        this.correo = correo;
        this.telefono = telefono;
        this.vacuna = vacuna;
        IdUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }
}
