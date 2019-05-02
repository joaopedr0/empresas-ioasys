package com.example.empresas;

import com.google.gson.annotations.SerializedName;

public class Empresa {

    private int id;

    @SerializedName("enterprise_name")
    private String name;

    @SerializedName("email_enterprise")
    private String email;

    private String photo;

    private String description;

    private String city;

    private String country;

    private int value;

    private int share_price;

    private TipoEmpresa tipoEmpresa;

    public Empresa(int id, String name, String email, String photo, String description, String city, String country, int value, int share_price, TipoEmpresa tipoEmpresa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.description = description;
        this.city = city;
        this.country = country;
        this.value = value;
        this.share_price = share_price;
        this.tipoEmpresa = tipoEmpresa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }
}
