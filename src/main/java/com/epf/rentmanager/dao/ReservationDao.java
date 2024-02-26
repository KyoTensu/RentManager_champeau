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

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
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
	
	public long delete(Reservation reservation) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, reservation.getId());

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();
			results.next();
			long deletedId = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();
			return deletedId;
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
}
