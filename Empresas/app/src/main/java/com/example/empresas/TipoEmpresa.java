package com.example.empresas;

import com.google.gson.annotations.SerializedName;

public class TipoEmpresa {

    private int id;

    @SerializedName("enterprise_type_name")
    private String tipoEmpresaString;

    public TipoEmpresa(int id, String tipoEmpresaString) {
        this.id = id;
        this.tipoEmpresaString = tipoEmpresaString;
    }

    public int getId() {
        return id;
    }

    public String getTipoEmpresaString() {
        return tipoEmpresaString;
    }
}
