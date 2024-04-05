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
import java.util.List;

@WebServlet("/rents/details")
public class ResaDetailServlet extends HttpServlet {

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
            Reservation resa = reservationService.findResaById(Long.parseLong(req.getParameter("id")));
            Client client = clientService.findById(reservationService.findResaById(Long.parseLong(req.getParameter("id"))).getClient_id());
            Vehicule vehicle = vehicleService.findById(reservationService.findResaById(Long.parseLong(req.getParameter("id"))).getVehicle_id());

            req.setAttribute("resa", resa);
            req.setAttribute("client", client);
            req.setAttribute("vehicle", vehicle);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
