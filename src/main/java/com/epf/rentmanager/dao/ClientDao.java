package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();
			results.next();
			int createdId = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();
			return createdId;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}
	
	public long delete(Client client) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, client.getId());

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

	public Client findById(long id) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, id);

			ps.execute();

			ResultSet results = ps.getGeneratedKeys();

			results.close();
			ps.close();
			connexion.close();

			Client clientResult = new Client();
			clientResult.setId(results.getInt(1));
			clientResult.setNom(results.getString(2));
			clientResult.setPrenom(results.getString(3));
			clientResult.setEmail(results.getString(4));
			clientResult.setNaissance(results.getDate(5).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

			return clientResult;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public List<Client> findAll() throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_CLIENTS_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();
			//results.next();
			//System.out.println(results.getInt(1));
			//System.out.println("avant while");

			List<Client> listeClients = new ArrayList<>();

			while(results.next()){
				Client clientIter = new Client();

				clientIter.setId(results.getInt(1));
				clientIter.setNom(results.getString(2));
				clientIter.setPrenom(results.getString(3));
				clientIter.setEmail(results.getString(4));
				clientIter.setNaissance(results.getDate(5).toLocalDate());

				//System.out.println(clientIter.getEmail());
				listeClients.add(clientIter);

				//System.out.println(listeClients);
			}

			results.close();
			ps.close();
			connexion.close();

			//System.out.println("après while "+listeClients);

			return listeClients;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

}
