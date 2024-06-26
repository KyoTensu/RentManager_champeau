package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	
	public long create(Vehicule vehicle) throws ServiceException {
		try{
			if(vehicle.getConstructeur().isEmpty() || vehicle.getNb_places() <= 1){
				throw new ServiceException();
			}else{
				return vehicleDao.create(vehicle);
			}
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public long delete(Vehicule vehicle) throws ServiceException{
		try{
			return vehicleDao.delete(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public void update(Vehicule vehicleOld, Vehicule vehicleNew) throws ServiceException{
		try{
			vehicleDao.update(vehicleOld, vehicleNew);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicule findById(long id) throws ServiceException {
		try{
			return vehicleDao.findById(id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public int countVehicles() throws ServiceException{
		try{
			return vehicleDao.countVehicles();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean verifVehicle(Vehicule vehicle) throws ServiceException{

			if(vehicle.getConstructeur().isEmpty() || vehicle.getModel().isEmpty()){
				return false;
			}else{
				if(vehicle.getNb_places() >= 2 && vehicle.getNb_places() <= 9){
					return true;
				}else{
					return false;
				}
			}
	}
	
}
