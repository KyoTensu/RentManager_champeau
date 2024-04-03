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

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Client client = clientService.findById(Long.parseLong(req.getParameter("id")));
            List<Reservation> resaList = reservationService.findByClientId(Long.parseLong(req.getParameter("id")));
            //Vehicule vehicule = vehicleService.findById().getConstructeur()

            req.setAttribute("resaList", resaList);
            req.setAttribute("client", client);
            req.setAttribute("resaNbr", resaList.size());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
