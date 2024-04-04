package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ReservationDao {

	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_BY_ID_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";

	public long create(Reservation reservation) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();
			results.next();
			int createdId = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();
			return createdId;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public void update(Reservation resaOld, Reservation resaNew) throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(UPDATE_RESERVATION_QUERY);

			ps.setInt(1, resaNew.getClient_id());
			ps.setInt(2, resaNew.getVehicle_id());
			ps.setDate(3, Date.valueOf(resaNew.getDebut()));
			ps.setDate(4, Date.valueOf(resaNew.getFin()));
			ps.setInt(5, resaOld.getId());

			ps.execute();

			ps.close();
			connexion.close();
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public Reservation findResaById(long id) throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_RESERVATION_BY_ID_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, Long.valueOf(id).intValue());

			ps.execute();

			ResultSet results = ps.getResultSet();
			results.next();
			Reservation resaToSend = new Reservation();
			resaToSend.setId(results.getInt(1));
			resaToSend.setClient_id(results.getInt(2));
			resaToSend.setVehicle_id(results.getInt(3));
			resaToSend.setDebut(results.getDate(4).toLocalDate());
			resaToSend.setFin(results.getDate(5).toLocalDate());

			results.close();
			ps.close();
			connexion.close();

			return resaToSend;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(DELETE_RESERVATION_QUERY);

			ps.setInt(1, reservation.getId());

			ps.execute();

			ps.close();
			connexion.close();
			return reservation.getId();
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, clientId);

			ps.execute();

			ResultSet results = ps.getResultSet();

			List<Reservation> listeReservations = new ArrayList<>();

			while(results.next()){
				Reservation reservationIter = new Reservation();
				reservationIter.setId(results.getInt(1));
				//reservationIter.setClient_id(results.getInt(2));
				reservationIter.setVehicle_id(results.getInt(2));
				reservationIter.setDebut(results.getDate(3).toLocalDate());
				reservationIter.setFin(results.getDate(4).toLocalDate());

				listeReservations.add(reservationIter);
			}

			results.close();
			ps.close();
			connexion.close();

			return listeReservations;
		}catch (SQLException e){
			throw new DaoException();
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, vehicleId);

			ps.execute();

			ResultSet results = ps.getResultSet();

			List<Reservation> listeReservations = new ArrayList<>();

			while(results.next()){
				Reservation reservationIter = new Reservation();
				reservationIter.setId(results.getInt(1));
				reservationIter.setClient_id(results.getInt(2));
				//reservationIter.setVehicle_id(results.getInt(3));
				reservationIter.setDebut(results.getDate(3).toLocalDate());
				reservationIter.setFin(results.getDate(4).toLocalDate());

				listeReservations.add(reservationIter);
			}

			results.close();
			ps.close();
			connexion.close();

			return listeReservations;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_RESERVATIONS_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();

			List<Reservation> listeReservations = new ArrayList<>();

			while(results.next()){
				Reservation reservationIter = new Reservation();
				reservationIter.setId(results.getInt(1));
				reservationIter.setClient_id(results.getInt(2));
				reservationIter.setVehicle_id(results.getInt(3));
				reservationIter.setDebut(results.getDate(4).toLocalDate());
				reservationIter.setFin(results.getDate(5).toLocalDate());

				listeReservations.add(reservationIter);
			}

			results.close();
			ps.close();
			connexion.close();

			return listeReservations;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public int countResa() throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(COUNT_RESERVATIONS_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();
			results.next();
			int resaNbr = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();

			return resaNbr;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
}
