package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("singleton")
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao){ this.reservationDao = reservationDao;}

    public long create(Reservation reservation) throws ServiceException{
        try{
            return reservationDao.create(reservation);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public void update(Reservation resaOld, Reservation resaNew) throws ServiceException{
        try{
            reservationDao.update(resaOld, resaNew);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Reservation findResaById(long id) throws ServiceException{
        try{
            return reservationDao.findResaById(id);
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findByClientId(long id) throws ServiceException{
        try{
            return reservationDao.findResaByClientId(id);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public List<Reservation> findByVehicleId(long id)throws ServiceException{
        try{
            return reservationDao.findResaByVehicleId(id);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll()throws ServiceException{
        try{
            return reservationDao.findAll();
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public long delete(Reservation reservation) throws ServiceException{
        try{
            return reservationDao.delete(reservation);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public int countResa() throws ServiceException{
        try{
            return reservationDao.countResa();
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean verifResa(Reservation resa) throws ServiceException{
        try{
            for(Reservation resaIt : reservationDao.findAll()){
                if((resa.getDebut().isBefore(resaIt.getDebut()) && resa.getFin().isBefore(resaIt.getDebut())) || (resa.getDebut().isAfter(resaIt.getFin()) && resa.getFin().isAfter(resaIt.getFin()))){
                    
                }else{
                    return false;
                }
            }
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
