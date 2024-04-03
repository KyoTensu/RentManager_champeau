package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class VehicleDao {

	private VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, model) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle;";

	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;";
	
	public long create(Vehicule vehicle) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, vehicle.getConstructeur());
			ps.setInt(2, vehicle.getNb_places());
			ps.setString(3, vehicle.getModel());

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

	public long delete(Vehicule vehicle) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(DELETE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, vehicle.getId());

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();

			results.close();
			ps.close();
			connexion.close();
			return ps.getGeneratedKeys().getInt(1);
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public Vehicule findById(long id) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, id);

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();

			results.close();
			ps.close();
			connexion.close();

			Vehicule vehicleResult = new Vehicule();
			vehicleResult.setId(results.getInt(1));
			vehicleResult.setConstructeur(results.getString(2));
			vehicleResult.setNb_places(results.getInt(3));
			vehicleResult.setModel(results.getString(4));

			return vehicleResult;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public List<Vehicule> findAll() throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_VEHICLES_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();

			List<Vehicule> listeVehicles = new ArrayList<>();
			//System.out.println("avant while");

			while(results.next()){
				Vehicule vehicleIter = new Vehicule();
				vehicleIter.setId(results.getInt(1));
				vehicleIter.setConstructeur(results.getString(2));
				vehicleIter.setNb_places(results.getInt(3));
				vehicleIter.setModel(results.getString(4));

				listeVehicles.add(vehicleIter);
			}

			results.close();
			ps.close();
			connexion.close();

			return listeVehicles;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public int countVehicles() throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(COUNT_VEHICLES_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();
			results.next();
			int vehicleNbr = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();

			return vehicleNbr;
		} catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
	

}
