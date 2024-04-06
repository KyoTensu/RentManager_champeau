package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ClientService {

	private ClientDao clientDao;

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

	public long delete(Client client) throws ServiceException{
		try{
			return clientDao.delete(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public void update(Client clientOld, Client clientNew) throws ServiceException{
		try{
			clientDao.update(clientOld, clientNew);
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

	public int countClient() throws ServiceException{
		try{
			return clientDao.countClient();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean clientVerif(Client client) throws ServiceException{
		try{
			boolean isSame = false;
			if(ChronoUnit.YEARS.between(client.getNaissance(), LocalDate.now()) >= 18){
				for(Client clientIt : clientDao.findAll()){
					if(Objects.equals(clientIt.getEmail(), client.getEmail())){
						isSame = true;
						break;
					}
				}
				if(isSame){
					return false;
				}
				else{
					if(client.getPrenom().length() >= 3 && client.getNom().length() >= 3){
						return true;
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean clientVerifUpdate(Client client, Client clientOld) throws ServiceException{
		try{
			boolean isSame = false;
			if(ChronoUnit.YEARS.between(client.getNaissance(), LocalDate.now()) >= 18){
				List<Client> findAllWithoutActual = clientDao.findAll();
				findAllWithoutActual.removeIf(client1 -> client1.getId() == clientOld.getId());
				for(Client clientIt : findAllWithoutActual){
					if(Objects.equals(clientIt.getEmail(), client.getEmail())){
						isSame = true;
						break;
					}
				}
				if(isSame){
					return false;
				}
				else{
					if(client.getPrenom().length() >= 3 && client.getNom().length() >= 3){
						return true;
					}else{
						return false;
					}
				}
			}else{
				return false;
			}
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
	
}
