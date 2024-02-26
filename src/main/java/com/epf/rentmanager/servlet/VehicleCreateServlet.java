package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehicles/create")
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
            VehicleService vehicleService = VehicleService.getInstance();
            Vehicule vehicleToCreate = new Vehicule();
            vehicleToCreate.setConstructeur(request.getParameter("manufacturer"));
            vehicleToCreate.setModele(request.getParameter("modele"));
            vehicleToCreate.setNb_places(Integer.parseInt(request.getParameter("seats")));
            //System.out.println(vehicleToCreate.getConstructeur()+vehicleToCreate.getModele()+vehicleToCreate.getNb_places());
            vehicleService.create(vehicleToCreate);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
