package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationCli {
    private ReservationService reservationService;

    public static ReservationCli instance;

    private ReservationCli(){ this.reservationService = ReservationService.getInstance();}

    public static ReservationCli getInstance(){
        if (instance == null) {
            instance = new ReservationCli();
        }

        return instance;
    }

    public void create(){
        try {
            int resaClientId = IOUtils.readInt("Entrez l'id du client:");
            int resaVehicleId = IOUtils.readInt("Entrez l'id du véhicule:");
            LocalDate resaDebut = IOUtils.readDate("Entrez la date de début de réservation:", true);
            LocalDate resaFin = IOUtils.readDate("Entrez la date de fin de réservation:", true);

            Reservation resaToCreate = new Reservation();
            resaToCreate.setClient_id(resaClientId);
            resaToCreate.setVehicle_id(resaVehicleId);
            resaToCreate.setDebut(resaDebut);
            resaToCreate.setFin(resaFin);

            reservationService.create(resaToCreate);
        }catch(ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void displayAllResa(){
        try {
            List<Reservation> resaList = reservationService.findAll();
            for(Reservation resa : resaList){
                IOUtils.print("Client_id: "+resa.getClient_id()+"; vehicle_id: "+resa.getVehicle_id()+"; Date de début: "+resa.getDebut()+"; Date de fin: "+resa.getFin());
            }
        }catch(ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void displayResaClient(){
        try{
            int searchId = IOUtils.readInt("Entrez l'id du client à filtrer");

            List<Reservation> resaList = reservationService.findByClientId(searchId);
            for(Reservation resa : resaList){
                IOUtils.print("vehicle_id: "+resa.getVehicle_id()+"; Date de début: "+resa.getDebut()+"; Date de fin: "+resa.getFin());
            }
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void displayResaVehicle(){
        try{
            int searchId = IOUtils.readInt("Entrez l'id du véhicule à filtrer");

            List<Reservation> resaList = reservationService.findByVehicleId(searchId);
            for(Reservation resa : resaList){
                IOUtils.print("client_id: "+resa.getClient_id()+"; Date de début: "+resa.getDebut()+"; Date de fin: "+resa.getFin());
            }
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void deleteResa(){
        try{
            int searchId = IOUtils.readInt("Entrez l'id de la réservation à supprimer: ");
            reservationService.delete(reservationService.findAll().get(searchId-1));
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }
}
