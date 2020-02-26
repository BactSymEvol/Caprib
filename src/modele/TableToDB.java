package modele;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import vue.ThreePanel;

public class TableToDB {
	private String non_redundant_refseq;
	private String name;
	private String symbol;
	private String locusTag;
	private String product_Accesion;
	private String start;
	private String end;
	private String strand;
	private String length;
	private String dbName;
	private File filename;
	private String control;
	private ThreePanel tp;
	

	public TableToDB(String dbName, File filename) {
		this.dbName = dbName;
		this.filename = filename;
	}

	public void liret() throws Exception {
		int count = 0;
		boolean rigthFile= false;
		try {
			
			Connection e = this.getConnection();
			FileReader input = new FileReader(this.filename);
			BufferedReader mybuffer = new BufferedReader(input);
			String line = "";
			boolean firstLine = true;

			while (line != null) {
				line = mybuffer.readLine();
				if (line != null) {
					String[] splitLine;					
					if (!firstLine) {
						splitLine = line.split("\t");						
						this.control = splitLine[0];						
						if (this.control.equals("CDS")&& !splitLine[18].isEmpty()) {
							
							this.start = splitLine[7];
							this.end = splitLine[8];
							this.strand = splitLine[9];
							this.product_Accesion = splitLine[10];
							this.non_redundant_refseq = splitLine[11];
							this.name = splitLine[13];
							this.symbol = splitLine[14];
							this.locusTag = splitLine[16];
							this.length = splitLine[18];
							PreparedStatement ps = e.prepareStatement(
									"INSERT INTO featureTable(idProtein, locusTag, product_accession, non_redundant_refseq,name,start, end, strand, length, symbol) VALUES (NULL, ?,?,?,?,?,?,?,?,?)");
							ps.setString(1, this.locusTag);
							ps.setString(2, this.product_Accesion);
							ps.setString(3, this.non_redundant_refseq);
							ps.setString(4, this.name);
							ps.setString(5, this.start);
							ps.setString(6, this.end);
							ps.setString(7, this.strand);
							ps.setString(8, this.length);
							ps.setString(9, this.symbol);
							ps.executeUpdate();
							++count;
						}
					} else {
						splitLine = line.split("\t");
						this.control = splitLine[0];
						if (this.control.equals("# feature")&& splitLine.length==20) {
							firstLine = false;
							rigthFile=true;
						} else {
							
							JOptionPane.showMessageDialog((Component) null, "Wrong feature-table file", "Warning", 2);
							
							System.out.println("wrong table file.");
							break;
						}
					}
				}
			}

			input.close();
		} catch (IOException arg11) {
			arg11.printStackTrace();
		} finally {
			if (rigthFile){
			JOptionPane.showMessageDialog(this.tp, this.dbName + " Database was created with " + count
					+ " proteins from reference organism\n\nPlease press OK to continue", "finished ....", 1);
			System.out.println("Transfert Completed.");
			}
		}

	}
	
	private Connection getConnection() throws Exception {
		try {
			String e = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/caprib_" + this.dbName;
			String username = "root";
			String password = "";
			Class.forName(e);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;
		} catch (Exception arg5) {
			System.out.println(arg5);
			return null;
		}
	}
}
