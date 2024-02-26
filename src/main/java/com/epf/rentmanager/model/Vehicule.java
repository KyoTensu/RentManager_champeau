package com.epf.rentmanager.model;

public class Vehicule {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public int getId(){
        return this.id;
    }

    public String getConstructeur(){
        return this.constructeur;
    }

    public String getModele(){
        return this.modele;
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

    public void setModele(String modele){
        this.modele = modele;
    }

    public void setNb_places(int nb_places){
        this.nb_places = nb_places;
    }
}
