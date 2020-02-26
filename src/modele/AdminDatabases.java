package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import modele.GetConnection;

public class AdminDatabases {
	private ArrayList<String> DBlist = new ArrayList<String>();
	private ArrayList<String> ogsmlist = new ArrayList<String>();	
	private ResultSet resultset;
	private ResultSet resultset2;
	private ResultSet proteinInfo;
	private ResultSet proteinFT;
	private ResultSet proteinCount;
	private GetConnection gc;
	private String db;	

	public AdminDatabases(String db) {
		this.db = db;
		this.gc = new GetConnection(this.db);
		
	}

	public AdminDatabases() {
		this.gc = new GetConnection();
	}

	public void checkDB() {
		try {
			this.prepareDBList();
			this.showDB();
		} catch (Exception arg1) {
			arg1.printStackTrace();
		}

	}

	public void startDB() {
		try {
			this.gc.getConnection();
		} catch (Exception arg3) {
			System.out.println("error on ODBC-JDBC-Bridge" + arg3);
		}

		try {
			this.createDB();
		} catch (Exception arg2) {
			arg2.printStackTrace();
		}

		try {
			this.createTable();
		} catch (Exception arg1) {
			arg1.printStackTrace();
		}

	}

	private void createDB() throws ClassNotFoundException, SQLException {
		Statement statement = this.gc.getConnection().createStatement();
		String sqlQuery = "CREATE DATABASE IF NOT EXISTS caprib_" + this.db;
		statement.executeUpdate(sqlQuery);
		statement.close();
	}

	private void createTable() throws Exception {
		try {
			Connection e = this.gc.getConnection(this.db);
			PreparedStatement create = e.prepareStatement(
					"CREATE TABLE IF NOT EXISTS blast(IdBlast SMALLINT NOT NULL AUTO_INCREMENT DEFAULT NULL, query VARCHAR(30), subject VARCHAR(50) UNIQUE, PRIMARY kEY(IdBlast))");
			create.executeUpdate();
			create = e.prepareStatement(
					"CREATE TABLE IF NOT EXISTS featureTable(idProtein SMALLINT NOT NULL AUTO_INCREMENT DEFAULT NULL,locusTag VARCHAR(20) UNIQUE, product_accession VARCHAR(30) , non_redundant_refseq VARCHAR(25),name VARCHAR(200),start VARCHAR(20), end VARCHAR(20), strand VARCHAR(5), length VARCHAR(20), symbol VARCHAR(20),PRIMARY kEY(idProtein))");
			create.executeUpdate();
			create = e.prepareStatement(
					"CREATE TABLE IF NOT EXISTS proteines (idBlast SMALLINT,idProtein SMALLINT ,identity VARCHAR(50), similarity VARCHAR(50),evalue VARCHAR(50),subject_length VARCHAR(20), identiques TEXT,similaires TEXT,differentes TEXT, gapsQuery TEXT, gapsSubject TEXT,stop VARCHAR(200), FOREIGN KEY (IdBlast) REFERENCES blast(IdBlast) on delete cascade,FOREIGN KEY (idProtein) REFERENCES featureTable(idProtein))");
			create.executeUpdate();
			create.close();
		} catch (Exception arg5) {
			System.out.println(arg5);
		} finally {
			System.out.println("the tables were created.");
		}
	}

	public void deleteDB(String dbname) throws Exception {
		try {
			Connection e = this.gc.getConnection(dbname);
			PreparedStatement delete = e.prepareStatement("drop database caprib_" + dbname + ";");
			delete.executeUpdate();
			delete.close();
		} catch (Exception arg6) {
			System.out.println(arg6);
		} finally {
			System.out.println("the db got dropped");
		}

	}

	public void deleteSubject(String dbname, String genome) {
		try {
			Connection e = this.gc.getConnection(dbname);
			String sqlQuery = "SELECT idBlast FROM blast WHERE subject = ?";
			PreparedStatement ps = e.prepareStatement(sqlQuery);
			ps.setString(1, genome);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int idBlast = rs.getInt(1);
				PreparedStatement deleteId = e.prepareStatement("DELETE FROM blast where idBlast = " + idBlast + ";");
				deleteId.executeUpdate();
			}
			rs.close();
		} catch (Exception arg11) {
			System.out.println(arg11);
		} finally {
			System.out.println("the genome was removed");
		}

	}

	public ArrayList<String> prepareDBList() {
		try {
			Connection e = this.gc.getConnection();
			Statement statement = e.createStatement();
			String sqlQuery = "SHOW DATABASES";
			this.resultset = statement.executeQuery(sqlQuery);

			while (this.resultset.next()) {
				String database = this.resultset.getString(1);
				if (database.contains("caprib_")) {
					String data = database.substring(database.lastIndexOf("_") + 1, database.length());
					this.DBlist.add(data);
				}
			}
			resultset.close();
		} catch (Exception arg5) {
			System.out.println("error on retrieve Databases caprib FromDB");
		}

		return this.DBlist;
	}

	public ArrayList<String> prepareGenomeList(String db) {
		try {
			ogsmlist.clear();
			Connection e = this.gc.getConnection(db);
			Statement statement = e.createStatement();
			String sqlQuery = "SELECT subject FROM blast ";
			this.resultset = statement.executeQuery(sqlQuery);

			while (this.resultset.next()) {
				String genome = this.resultset.getString(1);
				this.ogsmlist.add(genome);
			}
			resultset.close();
		} catch (Exception arg5) {
			System.out.println("error on retrieve Databases caprib FromDB");
		}

		return this.ogsmlist;
	}

	private void showDB() {
		Iterator<String> arg1 = this.DBlist.iterator();
		while (arg1.hasNext()) {
			String s = (String) arg1.next();
			System.out.println(s);
		}

	}

	public ResultSet getResultset()  throws SQLException{		
		try {
			Connection e = this.gc.getConnection();
			Statement statement;
			statement = e.createStatement();
			String sqlQuery = "SHOW DATABASES";
			this.resultset = statement.executeQuery(sqlQuery);
			
		} catch (SQLException e1) {			
			
			e1.printStackTrace();
			
		}		
		return resultset;		
		
	}
	public ResultSet getResultsetOrganisms(String db){		
		try {			
			Connection e = this.gc.getConnection(db);
			Statement statement;
			statement = e.createStatement();
			String sqlQuery = "SELECT subject FROM blast ";
			resultset2 = statement.executeQuery(sqlQuery);
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}				
		return resultset2;
	}
	
	public ResultSet getProteinInfo(String db, String proteinName){
		try {			
			Connection e = this.gc.getConnection(db);
			Statement statement;
			statement = e.createStatement();		
			String sqlQuery = "SELECT blast.subject, locusTag, non_redundant_refseq, name, symbol,length, proteines.subject_length, proteines.identity,"
					+ " proteines.similarity, proteines.evalue FROM proteines INNER JOIN blast ON blast.idBlast = proteines.idBlast"
					+ " INNER JOIN featureTable ON proteines.idProtein = featureTable.idProtein "									
					+ "WHERE (featureTable.locusTag IN (\'"+ proteinName + "\'	)) OR "
							+ "(featureTable.non_redundant_refseq IN (\'"+ proteinName + "\'))"
							+ " ORDER BY proteines.identity DESC";
			
			
			proteinInfo = statement.executeQuery(sqlQuery);			
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}				
		return proteinInfo;
	}
	
	public ResultSet getProteinsFT(String db){
		//count proteins in the Featuretable
		try {
			Connection e = this.gc.getConnection(db);
			Statement statement;
			statement = e.createStatement();
			String sqlQuery = "SELECT COUNT(*) FROM featureTable";
			proteinFT=statement.executeQuery(sqlQuery);
			
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}	
		return proteinFT;
	}
	
	public ResultSet getProteinsCount(String db){
		//count proteins in protein table
		try {
			Connection e = this.gc.getConnection(db);
			Statement statement;
			statement = e.createStatement();
			String sqlQuery = "SELECT blast.subject, COUNT(proteines.idBlast)   FROM proteines "
					+ "INNER JOIN blast ON blast.idBlast = proteines.idBlast GROUP BY blast.subject";
					
			proteinCount=statement.executeQuery(sqlQuery);
			//statement.close();
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}	
		return proteinCount;
	}
	public ResultSet getCountProteinInfo(String db, String proteinName){
		try {			
			Connection e = this.gc.getConnection(db);
			Statement statement;
			statement = e.createStatement();		
			String sqlQuery = "SELECT Count (idProtein) FROM proteines INNER JOIN blast ON blast.idBlast = proteines.idBlast"
					+ " INNER JOIN featureTable ON proteines.idProtein = featureTable.idProtein "									
					+ "WHERE (featureTable.locusTag IN (\'"+ proteinName + "\'	)) OR "
							+ "(featureTable.non_redundant_refseq IN (\'"+ proteinName + "\'))";
			
			
			proteinInfo = statement.executeQuery(sqlQuery);			
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}				
		return proteinInfo;
	}
}
