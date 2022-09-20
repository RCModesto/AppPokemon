package com.rctem.pokemon.model;

import java.io.Serializable;

public class Pokemon implements Serializable {
    private int number;
    private String name;
    private String url;

    public String getUrl() {
        String subUrl = "pokemon-form/" + this.getNumber();
        return subUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public int getNumber() {
        String[] slashedUrl = this.url.split("/");
        this.number = Integer.parseInt(slashedUrl[slashedUrl.length - 1]);
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
