package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException{
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            Vehicule vehicleToCreate = new Vehicule();
            vehicleToCreate.setConstructeur(request.getParameter("manufacturer"));
            vehicleToCreate.setModel(request.getParameter("modele"));
            vehicleToCreate.setNb_places(Integer.parseInt(request.getParameter("seats")));

            if(vehicleService.verifVehicle(vehicleToCreate)){
                vehicleService.create(vehicleToCreate);
                response.sendRedirect(request.getContextPath() + "/cars");
            }else{
                response.sendRedirect(request.getContextPath() + "/cars/create");
            }
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
