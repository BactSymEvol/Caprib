package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlastToDB {
	private int idBlast;
	private int idProtein;
	private String protein;
	private String identity;
	private String e_value;
	private String identical;
	private String similarity;
	private String subject_length;
	private String similar;
	private String different;
	private String gaps_Query;
	private String gaps_Subject;
	private String stop_codon;
	private Connection con;
	private String dbname;
	private String subject;
	private File blastfiltered;
	
	public BlastToDB(String dbname, File blastf, String subject) {
		this.dbname = dbname;
		this.subject = subject;
		this.blastfiltered = blastf;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/caprib_" + this.dbname;
		String username = "root";
		String password = "";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException arg9) {
			arg9.printStackTrace();
		}
		try {
			this.con = DriverManager.getConnection(url, username, password);
		} catch (SQLException arg8) {
			arg8.printStackTrace();
		}

	}

	@SuppressWarnings("finally")
	public boolean fillBlastTable() throws Exception {
		boolean emptyFile = true;
		boolean firstLine = true;
		String line = "";
		try {
			FileReader fr = new FileReader(this.blastfiltered);
			BufferedReader mybuffer = new BufferedReader(fr);			
			//
			while (line != null) {
				line = mybuffer.readLine();
				if (line != null) {										
					if (!firstLine && !line.split(";")[0].isEmpty() ) {						
						emptyFile=false;
					}else {
						firstLine=false;
					}
			
				}
			}
			fr.close();		
			//
			if(!emptyFile){			
				PreparedStatement e = this.con.prepareStatement("INSERT INTO blast(IdBlast, query, subject) VALUES (NULL,?,?)");
				e.setString(1, this.dbname);
				e.setString(2, this.subject);
				e.executeUpdate();
			}
		} catch (SQLException arg4) {
				arg4.printStackTrace();
		} finally {
				if(!emptyFile){
					System.out.println("Transfert to Blast table completed.");
					return true;
				}else{
					return false;
				}				
		}
			
	}

	public void lire() throws Exception {
		try {
			FileReader e = new FileReader(this.blastfiltered);
			BufferedReader mybuffer = new BufferedReader(e);
			String line = "";
			boolean firstLine = true;
			String sqlQuery = "SELECT idBlast FROM blast WHERE subject = ?";
			PreparedStatement ps = this.con.prepareStatement(sqlQuery);
			ps.setString(1, this.subject);

			ResultSet rs;
			for (rs = ps.executeQuery(); rs.next(); this.idBlast = rs.getInt(1)) {
				;
			}

			while (true) {
				String[] splitLine;
				label64 : do {
					while (line != null) {
						line = mybuffer.readLine();
						if (!firstLine) {
							continue label64;
						}

						splitLine = line.split(";");
						this.protein = splitLine[0];
						if (this.protein.equals("Proteine")) {
							firstLine = false;
						} else {
							System.out.println("wrong filtered blast file.");
						}
					}

					e.close();
					return;
				} while (line == null);

				splitLine = line.split(";", -1);
				
				this.protein = splitLine[0];
				this.protein = protein.replaceAll(" ", "");
				this.subject_length = splitLine[1];
				this.identity = splitLine[2];
				this.similarity=splitLine[3];
				this.e_value = splitLine[4];
				
				if (splitLine.length >5){
					
					this.identical = splitLine[5];
					this.similar = splitLine[6];
					this.different = splitLine[7];
					this.gaps_Query = splitLine[8];
					this.gaps_Subject = splitLine[9];
					this.stop_codon = splitLine[10];					
								
					if (this.similar.length() == 0) {
						this.similar = "0";
					}
	
					if (this.identical.length() == 0) {
						this.identical = "0";
					}
	
					if (this.different.length() == 0) {
						this.different = "0";
					}
	
					if (this.gaps_Query.length() == 0) {
						this.gaps_Query = "0";
					}
	
					if (this.gaps_Subject.length() == 0) {
						this.gaps_Subject = "0";
					}
	
					if (this.stop_codon.length() == 0) {
						this.stop_codon = "0";
					}
				}else{
					this.identical = "0";
					this.similar = "0";
					this.different = "0";
					this.gaps_Query = "0";
					this.gaps_Subject = "0";
					this.stop_codon = "0";										
				}
				String sqlQuery2 = "SELECT idProtein FROM featureTable WHERE locusTag = ?";
				ps = this.con.prepareStatement(sqlQuery2);
				ps.setString(1, this.protein);

				for (rs = ps.executeQuery(); rs.next(); this.idProtein = rs.getInt(1)) {
					;
				}

				ps = this.con.prepareStatement(
						"INSERT INTO proteines(idblast,idProtein,identity,similarity,evalue,subject_length ,identiques,similaires,differentes,gapsQuery,gapsSubject,Stop) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, this.idBlast);
				ps.setInt(2, this.idProtein);				
				ps.setString(3, this.identity);
				ps.setString(4, this.similarity);
				ps.setString(5, this.e_value);
				ps.setString(6, this.subject_length);
				ps.setString(7, this.identical);
				ps.setString(8, this.similar);
				ps.setString(9, this.different);
				ps.setString(10, this.gaps_Query);
				ps.setString(11, this.gaps_Subject);
				ps.setString(12, this.stop_codon);
				ps.executeUpdate();
			}
		} catch (IOException arg9) {
			
			arg9.printStackTrace();
		}
	}
}
