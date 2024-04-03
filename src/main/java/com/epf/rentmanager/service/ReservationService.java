package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
}
