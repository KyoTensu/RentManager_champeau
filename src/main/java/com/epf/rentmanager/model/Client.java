package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public int getId(){
        return this.id;
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public String getEmail(){
        return this.email;
    }

    public LocalDate getNaissance(){
        return this.naissance;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setNaissance(LocalDate naissance){
        this.naissance = naissance;
    }
}
