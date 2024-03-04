package com.epf.rentmanager;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.FillDatabase;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.ui.cli.ClientCli;
import com.epf.rentmanager.ui.cli.ReservationCli;
import com.epf.rentmanager.ui.cli.VehiculeCli;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //ClientCli.getInstance().create();
        //ClientCli.getInstance().displayallclients();
        //VehiculeCli.getInstance().create();
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehiculeCli vehiculeCli = context.getBean(VehiculeCli.class);
        vehiculeCli.displayAllVehicles();
        //ReservationCli.getInstance().create();
        //ReservationCli.getInstance().create();
        //ReservationCli.getInstance().displayAllResa();
        //ReservationCli.getInstance().displayResaClient();
        //ReservationCli.getInstance().displayResaVehicle();
        //ReservationCli.getInstance().deleteResa();
        //IOUtils.print("Début de liste après suppresion");
        //ReservationCli.getInstance().displayAllResa();
        //IOUtils.print("Fin de liste après suppresion");
    }

}
