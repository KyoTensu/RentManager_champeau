package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Scope("singleton")
public class ClientCli {
    private ClientService clientService;

    private ClientCli(ClientService clientService){ this.clientService = clientService;}

    public void create(){
        try {
            String clientName = IOUtils.readString("Entrez le nom du client :", true);
            String clientPrenom = IOUtils.readString("Entrez le prenom du client :", true);
            String clientMail = IOUtils.readString("Entrez l'adresse mail du client :", false);
            LocalDate clientBirth = IOUtils.readDate("Entrez la date de naissance du client :", false);

            Client clientToCreate = new Client();
            clientToCreate.setNom(clientName);
            clientToCreate.setPrenom(clientPrenom);
            clientToCreate.setEmail(clientMail);
            clientToCreate.setNaissance(clientBirth);

            clientService.create(clientToCreate);
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }

    public void displayallclients(){
        try{
            List<Client> ClientList = clientService.findAll();
            for(Client client : ClientList){
                IOUtils.print("Nom: "+client.getNom()+"; Prénom: "+client.getPrenom()+"; E-mail: "+client.getEmail()+"; Date de naissance: "+client.getNaissance());
            }
        }catch (ServiceException e){
            IOUtils.print(e.getMessage());
        }
    }
}
