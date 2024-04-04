package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            req.setAttribute("vehicle", vehicleService.findById(Long.parseLong(req.getParameter("id"))));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Vehicule vehicleToUpdate = new Vehicule();
            vehicleToUpdate.setConstructeur(req.getParameter("manufacturer"));
            vehicleToUpdate.setNb_places(Integer.parseInt(req.getParameter("seats")));
            vehicleToUpdate.setModel(req.getParameter("modele"));

            vehicleService.update(vehicleService.findById(Long.parseLong(req.getParameter("id"))), vehicleToUpdate);
            resp.sendRedirect(req.getContextPath() + "/cars");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
