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

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

            try{
                Client clientToCreate = new Client();
                clientToCreate.setPrenom(req.getParameter("last_name"));
                clientToCreate.setNom(req.getParameter("first_name"));
                clientToCreate.setEmail(req.getParameter("email"));
                clientToCreate.setNaissance(LocalDate.parse(req.getParameter("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                clientService.create(clientToCreate);
                resp.sendRedirect(req.getContextPath() + "/users");
            }catch (ServiceException e){
                System.out.println(e.getMessage());
            }
    }
}
