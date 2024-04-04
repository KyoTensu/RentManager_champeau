package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
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

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            req.setAttribute("client", clientService.findById(Long.parseLong(req.getParameter("id"))));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(req, resp);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try{
            Client clientToUpdate = new Client();
            clientToUpdate.setNom(req.getParameter("last_name"));
            clientToUpdate.setPrenom(req.getParameter("first_name"));
            clientToUpdate.setEmail(req.getParameter("email"));
            clientToUpdate.setNaissance(LocalDate.parse(req.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            clientService.update(clientService.findById(Long.parseLong(req.getParameter("id"))),clientToUpdate);
            resp.sendRedirect(req.getContextPath() + "/users");
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }
}
