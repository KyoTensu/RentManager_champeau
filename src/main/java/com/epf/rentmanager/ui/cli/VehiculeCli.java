package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.util.List;

public class VehiculeCli {
    private VehicleService vehicleService;

    public static VehiculeCli instance;

    private VehiculeCli(){ this.vehicleService = VehicleService.getInstance();}

    public static VehiculeCli getInstance(){
        if (instance == null) {
            instance = new VehiculeCli();
        }

        return instance;
    }

    public void create(){
        try {
            String vehiculeConstructeur = IOUtils.readString("Entrez le constructeur du véhicule:", true);
            int vehiculeNbrPlaces = IOUtils.readInt("Entrez le nombre de places dans le véhicule:");

            Vehicule vehicleToCreate = new Vehicule();
            vehicleToCreate.setConstructeur(vehiculeConstructeur);
            vehicleToCreate.setNb_places(vehiculeNbrPlaces);

            vehicleService.create(vehicleToCreate);
        }catch(ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void displayAllVehicles(){
        try{
            List<Vehicule> vehiculeList = vehicleService.findAll();
            for(Vehicule vehicle : vehiculeList){
                IOUtils.print("Constructeur: "+vehicle.getConstructeur()+"; Nombre de places: "+vehicle.getNb_places());
            }
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }
}
