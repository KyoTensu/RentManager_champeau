package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Reservation;
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
import java.util.List;

@WebServlet("/rents")
public class ResaListServlet extends HttpServlet {
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{
            List<Reservation> resas = reservationService.findAll();
            /*List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);*/
            List<String> vehicleNames = new ArrayList<>();
            List<String> clientNames = new ArrayList<>();

            for(int i=0; i < resas.size(); i++){
                vehicleNames.add(vehicleService.findById(resas.get(i).getVehicle_id()).getConstructeur() + " " + vehicleService.findById(resas.get(i).getVehicle_id()).getModel());
                clientNames.add(clientService.findById(resas.get(i).getClient_id()).getPrenom() + " " + clientService.findById(resas.get(i).getClient_id()).getNom());
            }

            req.setAttribute("resas", resas);
            req.setAttribute("listVehicleName", vehicleNames);
            req.setAttribute("listClientName", clientNames);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
