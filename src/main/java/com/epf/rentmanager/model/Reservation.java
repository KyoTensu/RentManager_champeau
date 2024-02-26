package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int client_id;
    private int vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    public int getId(){
        return this.id;
    }

    public int getClient_id(){
        return this.client_id;
    }

    public int getVehicle_id(){
        return this.vehicle_id;
    }

    public LocalDate getDebut(){
        return this.debut;
    }

    public LocalDate getFin(){
        return this.fin;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setClient_id(int client_id){
        this.client_id = client_id;
    }

    public void setVehicle_id(int vehicle_id){
        this.vehicle_id = vehicle_id;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
}
