package com.epf.rentmanager.model;

public class Vehicule {
    private int id;
    private String constructeur;
    private int nb_places;
    private String model;

    public int getId(){
        return this.id;
    }

    public String getConstructeur(){
        return this.constructeur;
    }

    public String getModel(){
        return this.model;
    }

    public int getNb_places(){
        return this.nb_places;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setConstructeur(String constructeur){
        this.constructeur = constructeur;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setNb_places(int nb_places){
        this.nb_places = nb_places;
    }
}
