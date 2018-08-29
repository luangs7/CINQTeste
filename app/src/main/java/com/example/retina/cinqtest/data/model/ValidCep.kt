package com.example.retina.cinqtest.data.model

import com.google.gson.annotations.SerializedName

class ValidCep {

    @SerializedName("uf")
    var uf: String? = null

    @SerializedName("complemento")
    var complemento: String? = null

    @SerializedName("logradouro")
    var logradouro: String? = null

    @SerializedName("bairro")
    var bairro: String? = null

    @SerializedName("localidade")
    var localidade: String? = null

    @SerializedName("ibge")
    var ibge: String? = null

    @SerializedName("unidade")
    var unidade: String? = null

    @SerializedName("gia")
    var gia: String? = null

    @SerializedName("cep")
    var cep: String? = null

    override fun toString(): String {
        return "ValidCep{" +
                "uf = '" + uf + '\'' +
                ",complemento = '" + complemento + '\'' +
                ",logradouro = '" + logradouro + '\'' +
                ",bairro = '" + bairro + '\'' +
                ",localidade = '" + localidade + '\'' +
                ",ibge = '" + ibge + '\'' +
                ",unidade = '" + unidade + '\'' +
                ",gia = '" + gia + '\'' +
                ",cep = '" + cep + '\'' +
                "}"
    }
}