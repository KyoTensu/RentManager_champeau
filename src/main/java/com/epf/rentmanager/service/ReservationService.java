package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            for(Reservation resaIt : reservationDao.findResaByVehicleId(resa.getVehicle_id())){
                if((resa.getDebut().isBefore(resaIt.getDebut()) && resa.getFin().isBefore(resaIt.getDebut())) || (resa.getDebut().isAfter(resaIt.getFin()) && resa.getFin().isAfter(resaIt.getFin()))){
                    
                }else{
                    return false;
                }
            }

            List<Reservation> resaVerifList = reservationDao.findResaByClientId(resa.getClient_id());
            resaVerifList.removeIf(reservation -> reservation.getVehicle_id() != resa.getVehicle_id());
            resaVerifList.add(resa);
            List<Integer> resaVerifDurations = new ArrayList<>();
            for(int i=0; i <= resaVerifList.size()-2; i ++){
                resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(i).getDebut(), resaVerifList.get(i).getFin())).intValue()+1);
                resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(i).getFin(), resaVerifList.get(i+1).getDebut())).intValue());
            }
            resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(resaVerifList.size()-1).getDebut(), resaVerifList.get(resaVerifList.size()-1).getFin())).intValue()+1);
            int sum = 0;
            List<Integer> listOfSum = new ArrayList<>();
            for(int i=0; i <= resaVerifDurations.size()-1; i++){
                if(i%2==0){
                    sum += resaVerifDurations.get(i);
                }else if(resaVerifDurations.get(i) == 1 || resaVerifDurations.get(i) == 0){

                }else{
                    listOfSum.add(sum);
                    sum = 0;
                }
            }
            listOfSum.add(sum);
            listOfSum.removeIf(integer -> integer <= 7);
            if(!listOfSum.isEmpty()){
                return false;
            }

            List<Reservation> resaVehicleVerifList = reservationDao.findResaByVehicleId(resa.getVehicle_id());
            resaVehicleVerifList.add(resa);
            List<Integer> resaVehicleVerifDurations = new ArrayList<>();
            for(int i=0; i <= resaVehicleVerifList.size()-2; i++){
                resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(i).getDebut(), resaVehicleVerifList.get(i).getFin())).intValue()+1);
                resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(i).getFin(), resaVehicleVerifList.get(i+1).getDebut())).intValue());
            }
            resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(resaVehicleVerifList.size()-1).getDebut(), resaVehicleVerifList.get(resaVehicleVerifList.size()-1).getFin())).intValue()+1);
            int sumVehicle = 0;
            List<Integer> listOfVehicleSum = new ArrayList<>();
            for(int i=0; i <= resaVehicleVerifDurations.size()-1; i++){
                if(i%2==0){
                    sumVehicle += resaVehicleVerifDurations.get(i);
                }else if(resaVehicleVerifDurations.get(i) == 1 || resaVehicleVerifDurations.get(i) == 0){

                }else{
                    listOfVehicleSum.add(sumVehicle);
                    sumVehicle = 0;
                }
            }
            listOfVehicleSum.add(sumVehicle);
            listOfVehicleSum.removeIf(integer -> integer <= 30);
            if(!listOfVehicleSum.isEmpty()){
                return false;
            }

            return true;
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean verifResaUpdate(Reservation resa, Reservation resaOld) throws ServiceException{
        try{
            List<Reservation> findWithoutActual = reservationDao.findResaByVehicleId(resa.getVehicle_id());
            findWithoutActual.removeIf(reservation -> reservation.getId() == resaOld.getId());
            for(Reservation resaIt : findWithoutActual){
                if((resa.getDebut().isBefore(resaIt.getDebut()) && resa.getFin().isBefore(resaIt.getDebut())) || (resa.getDebut().isAfter(resaIt.getFin()) && resa.getFin().isAfter(resaIt.getFin()))){

                }else{
                    return false;
                }
            }

            List<Reservation> resaVerifList = reservationDao.findResaByClientId(resa.getClient_id());
            resaVerifList.removeIf(reservation -> reservation.getId() == resaOld.getId());
            resaVerifList.removeIf(reservation -> reservation.getVehicle_id() != resa.getVehicle_id());
            resaVerifList.add(resa);
            List<Integer> resaVerifDurations = new ArrayList<>();
            for(int i=0; i <= resaVerifList.size()-2; i ++){
                resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(i).getDebut(), resaVerifList.get(i).getFin())).intValue()+1);
                resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(i).getFin(), resaVerifList.get(i+1).getDebut())).intValue());
            }
            resaVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVerifList.get(resaVerifList.size()-1).getDebut(), resaVerifList.get(resaVerifList.size()-1).getFin())).intValue()+1);
            int sum = 0;
            List<Integer> listOfSum = new ArrayList<>();
            for(int i=0; i <= resaVerifDurations.size()-1; i++){
                if(i%2==0){
                    sum += resaVerifDurations.get(i);
                }else if(resaVerifDurations.get(i) == 1 || resaVerifDurations.get(i) == 0){

                }else{
                    listOfSum.add(sum);
                    sum = 0;
                }
            }
            listOfSum.add(sum);
            listOfSum.removeIf(integer -> integer <= 7);
            if(!listOfSum.isEmpty()){
                return false;
            }

            List<Reservation> resaVehicleVerifList = reservationDao.findResaByVehicleId(resa.getVehicle_id());
            resaVehicleVerifList.removeIf(reservation -> reservation.getId() == resaOld.getId());
            resaVehicleVerifList.add(resa);
            List<Integer> resaVehicleVerifDurations = new ArrayList<>();
            for(int i=0; i <= resaVehicleVerifList.size()-2; i++){
                resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(i).getDebut(), resaVehicleVerifList.get(i).getFin())).intValue()+1);
                resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(i).getFin(), resaVehicleVerifList.get(i+1).getDebut())).intValue());
            }
            resaVehicleVerifDurations.add(Long.valueOf(ChronoUnit.DAYS.between(resaVehicleVerifList.get(resaVehicleVerifList.size()-1).getDebut(), resaVehicleVerifList.get(resaVehicleVerifList.size()-1).getFin())).intValue()+1);
            int sumVehicle = 0;
            List<Integer> listOfVehicleSum = new ArrayList<>();
            for(int i=0; i <= resaVehicleVerifDurations.size()-1; i++){
                if(i%2==0){
                    sumVehicle += resaVehicleVerifDurations.get(i);
                }else if(resaVehicleVerifDurations.get(i) == 1 || resaVehicleVerifDurations.get(i) == 0){

                }else{
                    listOfVehicleSum.add(sumVehicle);
                    sumVehicle = 0;
                }
            }
            listOfVehicleSum.add(sumVehicle);
            listOfVehicleSum.removeIf(integer -> integer <= 30);
            if(!listOfVehicleSum.isEmpty()){
                return false;
            }

            return true;
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
