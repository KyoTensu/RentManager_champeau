package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ClientDao {

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	
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
			PreparedStatement ps = connexion.prepareStatement(DELETE_CLIENT_QUERY);

			ps.setInt(1, client.getId());

			ps.execute();

			ps.close();
			connexion.close();
			return client.getId();
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public void update(Client clientOld, Client clientNew) throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(UPDATE_CLIENT_QUERY);

			ps.setString(1, clientNew.getNom());
			ps.setString(2, clientNew.getPrenom());
			ps.setString(3, clientNew.getEmail());
			ps.setDate(4, Date.valueOf(clientNew.getNaissance()));
			ps.setInt(5, clientOld.getId());

			ps.execute();

			ps.close();
			connexion.close();
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public Client findById(long id) throws DaoException {
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(FIND_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, id);

			ps.execute();

			ResultSet results = ps.getResultSet();
			results.next();

			Client clientResult = new Client();
			clientResult.setId(Long.valueOf(id).intValue());
			clientResult.setNom(results.getString(1));
			clientResult.setPrenom(results.getString(2));
			clientResult.setEmail(results.getString(3));
			clientResult.setNaissance(results.getDate(4).toLocalDate());

			results.close();
			ps.close();
			connexion.close();

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

			List<Client> listeClients = new ArrayList<>();

			while(results.next()){
				Client clientIter = new Client();

				clientIter.setId(results.getInt(1));
				clientIter.setNom(results.getString(2));
				clientIter.setPrenom(results.getString(3));
				clientIter.setEmail(results.getString(4));
				clientIter.setNaissance(results.getDate(5).toLocalDate());

				listeClients.add(clientIter);
			}

			results.close();
			ps.close();
			connexion.close();

			return listeClients;
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

	public int countClient() throws DaoException{
		try{
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement ps = connexion.prepareStatement(COUNT_CLIENTS_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.execute();

			ResultSet results = ps.getResultSet();
			results.next();
			int clientNbr = results.getInt(1);

			results.close();
			ps.close();
			connexion.close();

			return clientNbr;
		}catch(SQLException e){
			throw new DaoException(e.getMessage());
		}
	}

}
