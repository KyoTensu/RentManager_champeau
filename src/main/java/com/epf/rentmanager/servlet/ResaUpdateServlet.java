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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/rents/update")
public class ResaUpdateServlet extends HttpServlet {
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
            req.setAttribute("actualClient", clientService.findById(Long.parseLong(req.getParameter("clientid"))));
            req.setAttribute("actualVehicle", vehicleService.findById(Long.parseLong(req.getParameter("vehicleid"))));
            req.setAttribute("actualDebut", reservationService.findResaById(Long.parseLong(req.getParameter("id"))).getDebut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            req.setAttribute("actualFin", reservationService.findResaById(Long.parseLong(req.getParameter("id"))).getFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Reservation resaToUpdate = new Reservation();
            resaToUpdate.setClient_id(Integer.parseInt(req.getParameter("client")));
            resaToUpdate.setVehicle_id(Integer.parseInt(req.getParameter("car")));
            resaToUpdate.setDebut(LocalDate.parse(req.getParameter("begin"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            resaToUpdate.setDebut(LocalDate.parse(req.getParameter("end"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            reservationService.update(reservationService.findResaById(Long.parseLong(req.getParameter("id"))), resaToUpdate);
            resp.sendRedirect(req.getContextPath() + "/rents");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
