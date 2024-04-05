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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/rents/create")
public class ResaCreateServlet extends HttpServlet {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
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
            List<Client> clientList = clientService.findAll();
            List<Vehicule> vehiculeList = vehicleService.findAll();

            req.setAttribute("clientList", clientList);
            req.setAttribute("vehiculeList", vehiculeList);

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Reservation resaToCreate = new Reservation();
            resaToCreate.setClient_id(Integer.parseInt(req.getParameter("client")));
            resaToCreate.setVehicle_id(Integer.parseInt(req.getParameter("car")));
            resaToCreate.setDebut(LocalDate.parse(req.getParameter("begin"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            resaToCreate.setFin(LocalDate.parse(req.getParameter("end"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            if(reservationService.verifResa(resaToCreate)){
                reservationService.create(resaToCreate);
                resp.sendRedirect(req.getContextPath() + "/rents");
            }else{
                resp.sendRedirect(req.getContextPath() + "/rents/create");
            }

        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
