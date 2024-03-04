package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ClientService {

	private ClientDao clientDao;
	//public static ClientService instance;

	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException {
		long createdClientId;
		try{
			if(client.getNom().isEmpty() || client.getPrenom().isEmpty()){
				throw new ServiceException("Nom ou Prénom vide");
			}else{
				client.setNom(client.getNom().toUpperCase());
				createdClientId = clientDao.create(client);
			}
			return createdClientId;
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findById(long id) throws ServiceException {
		try{
			return clientDao.findById(id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}
	
}
