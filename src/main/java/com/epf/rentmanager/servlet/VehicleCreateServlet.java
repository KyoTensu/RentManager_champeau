package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try{
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
            Vehicule vehicleToCreate = new Vehicule();
            vehicleToCreate.setConstructeur(request.getParameter("manufacturer"));
            vehicleToCreate.setModele(request.getParameter("modele"));
            vehicleToCreate.setNb_places(Integer.parseInt(request.getParameter("seats")));
            //System.out.println(vehicleToCreate.getConstructeur()+vehicleToCreate.getModele()+vehicleToCreate.getNb_places());
            vehicleService.create(vehicleToCreate);
            //response.sendRedirect("rentmanager/cars");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
