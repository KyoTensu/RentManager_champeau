package com.epf.rentmanager.servlet;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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

	@Autowired
	VehicleService vehicleService;

	@Autowired
	ClientService clientService;

	@Autowired
	ReservationService reservationService;

	@Override
	public void init() throws ServletException{
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			//ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
			//VehicleService vehicleService = context.getBean(VehicleService.class);
			request.setAttribute("vehiclesNbr", vehicleService.countVehicles());
			request.setAttribute("clientNbr", clientService.countClient());
			request.setAttribute("resaNbr", reservationService.countResa());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		}catch (ServiceException e){
			System.out.println(e.getMessage());
		}
	}

}
