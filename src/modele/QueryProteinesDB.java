package modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modele.AdminDatabases;
import modele.GetConnection;

public class QueryProteinesDB extends AdminDatabases {
	private ArrayList<String> groupA;
	private ArrayList<String> groupB;
	private ArrayList<String> mergedGroup;
	private Connection con;
	private ResultSet resultset;
	private String db;
	File rsltQuery;

	public QueryProteinesDB(ArrayList<String> groupA, ArrayList<String> groupB, String db) {
		this.groupA = groupA;
		this.groupB = groupB;
		this.db = db;
	}

	public ArrayList<String> mergeList() {
		this.mergedGroup = new ArrayList<String>(this.groupA);
		this.mergedGroup.addAll(this.groupB);
		return this.mergedGroup;
	}

	public int getSize() {
		return this.mergedGroup.size();
	}

	public void queryProteins() {
		String a = String.join("\',\'", this.mergeList());
		File refpath = new File("");
		FileWriter output = null;
		BufferedWriter mybuffer = null;

		try {
			this.rsltQuery = new File(refpath.getCanonicalFile() + File.separator + "externalPrograms" + File.separator
					+ this.db + ".csv");
			output = new FileWriter(this.rsltQuery);
			mybuffer = new BufferedWriter(output);
			mybuffer.append(
					"Blast Id;Query;Subject;LocusTag;product Accession; Name; Symbol;identity;evalue;Identical AA;Similar AA;Different AA;Gaps Query;Gaps Subject;stop codon\n");
			this.con = (new GetConnection(this.db)).getConnection(this.db);
			Statement e = this.con.createStatement();
			String sqlQuery = "SELECT blast.*, locusTag, non_redundant_refseq, name, symbol, proteines.identity,"
					+ " proteines.evalue, proteines.identiques, proteines.similaires, proteines.differentes, "
					+ "proteines.gapsQuery, proteines.gapsSubject, proteines.stop FROM proteines INNER JOIN blast ON blast.idBlast = proteines.idBlast INNER JOIN featureTable ON proteines.idProtein = featureTable.idProtein WHERE blast.subject IN (\'"
					+ a + "\') " + "ORDER BY featureTable.locusTag";

			int id;
			String query;
			String subject;
			String protein;
			String non_redundant_refseq;
			String name;
			String symbol;
			int identity;
			String evalue;
			String identic;
			String similar;
			String different;
			String gapquery;
			String gapSubject;
			String stopCodon;
			for (this.resultset = e.executeQuery(sqlQuery); this.resultset.next(); mybuffer
					.append(id + ";" + query + ";" + subject + ";" + protein + ";" + non_redundant_refseq + ";" + name + ";"
							+ symbol + ";" + identity + ";" + evalue + ";" + identic + ";" + similar + ";" + different
							+ ";" + gapquery + ";" + gapSubject + ";" + stopCodon + "\n")) {
				id = this.resultset.getInt("blast.idBlast");
				query = this.resultset.getString("blast.query");
				subject = this.resultset.getString("blast.subject");
				protein = this.resultset.getString("featureTable.locusTag");
				non_redundant_refseq = this.resultset.getString("featureTable.non_redundant_refseq");
				name = this.resultset.getString("featureTable.name");
				symbol = this.resultset.getString("featureTable.symbol");
				identity = this.resultset.getInt("proteines.identity");
				evalue = this.resultset.getString("proteines.evalue");
				identic = this.resultset.getString("proteines.identiques");
				similar = this.resultset.getString("proteines.similaires");
				different = this.resultset.getString("proteines.differentes");
				gapquery = this.resultset.getString("proteines.gapsQuery");
				gapSubject = this.resultset.getString("proteines.gapsSubject");
				stopCodon = this.resultset.getString("proteines.stop");
				if (similar.isEmpty()) {
					similar = "0";
				}

				if (symbol.isEmpty()) {
					symbol = "-";
				}

				if (identic.isEmpty()) {
					identic = "0";
				}

				if (different.isEmpty()) {
					different = "0";
				}

				if (gapquery.isEmpty()) {
					gapquery = "0";
				}

				if (gapSubject.isEmpty()) {
					gapSubject = "0";
				}

				if (stopCodon.isEmpty()) {
					stopCodon = "0";
				}
			}

			System.out.println("hecho");
			mybuffer.close();
			output.close();
		} catch (Exception arg21) {
			arg21.printStackTrace();
		}

	}
	
	public ResultSet queryProteinsRst(int identity) {
		String a = String.join("\',\'", this.mergeList());
		ResultSet rs = null;
		try {
			this.con = (new GetConnection(this.db)).getConnection(this.db);
			Statement e = this.con.createStatement();
			String sqlQuery = "SELECT blast.*, locusTag, non_redundant_refseq, name, symbol, proteines.identity,"
					+ " proteines.evalue, proteines.identiques, proteines.similaires, proteines.differentes, "
					+ "proteines.gapsQuery, proteines.gapsSubject, proteines.stop FROM proteines INNER JOIN blast ON blast.idBlast = proteines.idBlast INNER JOIN featureTable ON proteines.idProtein = featureTable.idProtein WHERE blast.subject IN (\'"
					+ a + "\') " + "AND proteines.identity >= "+identity+ " ORDER BY featureTable.locusTag";

			rs = e.executeQuery(sqlQuery); 
				
			
		} catch (Exception arg21) {
			arg21.printStackTrace();
		}
		return rs;

	}

	
	public String saveFile(String path) {
		String answer = null;

		try {
			this.rsltQuery = new File(path);
			answer = "The file was saved ";
		} catch (Exception arg3) {
			;
		}

		return answer;
	}
}
