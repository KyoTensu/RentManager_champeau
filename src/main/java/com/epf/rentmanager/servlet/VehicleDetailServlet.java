package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet("/cars/details")
public class VehicleDetailServlet extends HttpServlet {
    @Autowired
    ClientService clientService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Vehicule vehicle = vehicleService.findById(Long.parseLong(req.getParameter("id")));
            List<Reservation> resaList = reservationService.findByVehicleId(Long.parseLong(req.getParameter("id")));

            if(!resaList.isEmpty()){
                List<String> clientNames = new ArrayList<>(Arrays.asList(new String[resaList.getLast().getId()]));
                List<Client> clientList = new ArrayList<>(Arrays.asList(new Client[resaList.getLast().getId()]));
                for(Reservation resa : resaList){
                    clientNames.add(resa.getId(), clientService.findById(resa.getClient_id()).getPrenom() + " " + clientService.findById(resa.getClient_id()).getNom());
                    clientList.add(resa.getId(), clientService.findById(reservationService.findResaById(resa.getId()).getVehicle_id()));
                }
                clientList.removeIf(client -> Objects.equals(client, null));
                req.setAttribute("listClientName", clientNames);
                req.setAttribute("listClient", clientList);
                req.setAttribute("listClientSize", clientList.size());
            }
            req.setAttribute("resaList", resaList);
            req.setAttribute("vehicle", vehicle);
            req.setAttribute("resaNbr", resaList.size());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
