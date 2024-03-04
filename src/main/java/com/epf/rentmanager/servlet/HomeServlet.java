package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
			VehicleService vehicleService = context.getBean(VehicleService.class);
			request.setAttribute("vehiclesNbr", vehicleService.countVehicles());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		}catch (ServiceException e){
			System.out.println(e.getMessage());
		}
	}

}
