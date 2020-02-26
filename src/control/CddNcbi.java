package control;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import vue.CddPanel;


public class CddNcbi {
	private String report;
	private String middlereport;
	private String finalreport;
	private String mergedfinalreport;
	private String mergedFeatfinalreport;
	private String featFile="";
	private String cdsid;
	private HashMap<String, String> proteinMutations;
	private String dataMode;
	private CddPanel cddpanel;
	ArrayList<String> groupA=new ArrayList<String>();
	ArrayList<String> groupB=new ArrayList<String>();
	
	
	
	private OsValidation ov;
	public CddNcbi(String report, String dataMode, String middlereport) {
		this.report = report;
		this.middlereport = middlereport;
		this.finalreport = middlereport + "_final.tsv";
		this.mergedfinalreport = middlereport + "_merged.tsv";
		this.mergedFeatfinalreport = featFile + "_mergedFeat.tsv";
		this.proteinMutations = new HashMap<String, String>();
		this.dataMode = dataMode;
		ov=new OsValidation();
	}
	
	public void run() {
		try {
			//get proteinMutations
			this.extractAccessionCodes();
			this.writeCDDin();
			System.out.println(this.proteincounting());
			this.searchCDD();
			this.extractsCdsid();
		} catch (IOException arg1) {
			arg1.printStackTrace();
		}
	}

	public String getCdsid() {
		return this.cdsid;
	}

	private HashMap<String, String> extractAccessionCodes() throws IOException {
		try {
			FileReader input = new FileReader(this.report);
			BufferedReader e = new BufferedReader(input);
			String line = "";					
			while (line != null) {
				line = e.readLine();				
				if ((line != null)) {
					String[] splitLine = line.split(";");
					
					if(splitLine.length>1  && splitLine[0].equals("Group A: ")){
						groupA=new ArrayList<String>(Arrays.asList(splitLine[1].split(",")));
						
					}
					else if(splitLine.length>1  && splitLine[0].equals("Group B: ")){
						groupB=new ArrayList<String>(Arrays.asList(splitLine[1].split(",")));
						
					}
					if(splitLine.length>=5 && !splitLine[0].equals("Protein")){
						String refseq = splitLine[1];
						String aa = splitLine[4];
						
						if (aa.length() > 2) {
							this.proteinMutations.put(refseq, aa);
						}
											
					}									
				}
			}
			
			input.close();
		} catch (FileNotFoundException arg7) {
			System.out.println("File Error");
			arg7.printStackTrace();
		}

		return this.proteinMutations;
	}

	public void showHash() {
		try {
			this.extractAccessionCodes();
		} catch (IOException arg2) {
			arg2.printStackTrace();
		}

		Iterator<Entry<String, String>> arg1 = this.proteinMutations.entrySet().iterator();

		while (arg1.hasNext()) {
			Entry<String, String> element = (Entry<String, String>) arg1.next();
			System.out.println(element);
		}

	}

	private int proteincounting() {
		return this.proteinMutations.size();
	}
	/**
	 * to obtain input for cdd query
	 * @throws IOException
	 */
	private void writeCDDin() throws IOException {
		
		FileWriter output = null;
		BufferedWriter mybuffer = null;

		try {
			output = new FileWriter(this.middlereport);
			mybuffer = new BufferedWriter(output);
			Iterator<Entry<String, String>> arg3 = this.proteinMutations.entrySet().iterator();

			while (arg3.hasNext()) {
				Entry<String, String> e = (Entry<String, String>) arg3.next();
				String protein = (String) e.getKey();
				mybuffer.append(protein + "\n");
			}
		} catch (Exception arg8) {
			arg8.printStackTrace();
		} finally {
			mybuffer.close();
			output.close();
		}
	}

	public String getMergedFeatureFileName() {
		return this.mergedFeatfinalreport;
	}
	private String setMergedFeatureFilename(){
		this.mergedFeatfinalreport = featFile + "_mergedFeat.tsv";
		return mergedFeatfinalreport;
	}
	
	public String getMergedFileName() {
		return this.mergedfinalreport;
	}

	private void searchCDD() {
		File refpath = new File("");

		try {
			JOptionPane.showMessageDialog(this.cddpanel,
					"preparing CDD NCBI query...\nPlease wait until a finish message occurs\n\nPlease press OK to continue",
					"preparing ....", 1);
			File e = new File(refpath.getCanonicalFile() + File.separator + "externalPrograms" + File.separator);
			Runtime rt = Runtime.getRuntime();						
			if	(ov.isWindows()){
				String combineLine = "cmd /c perl " + e + File.separator + "cdd.pl " + this.middlereport + " "
					+ this.dataMode + " " + this.finalreport;
				System.out.println(combineLine);
				Process p = rt.exec(combineLine);
				 // any error message?
	            StreamGobbler errorGobbler = new 
	                StreamGobbler(p.getErrorStream(), "ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler = new 
	                StreamGobbler(p.getInputStream(), "OUTPUT");
	                
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            // any error???
	            int exitVal = p.waitFor();
	            System.out.println("ExitValue: " + exitVal);
											
				Thread.sleep(6000L);				
				String mergeLine = "cmd /c perl " + e + File.separator + "mergeCDD.pl " + this.report + " "
					+ this.finalreport + " " + this.mergedfinalreport;	
				System.out.println(mergeLine);
				p = rt.exec(mergeLine);
				errorGobbler = new 
		                StreamGobbler(p.getErrorStream(), "ERROR"); 
				outputGobbler = new 
		                StreamGobbler(p.getInputStream(), "OUTPUT");
				// kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            // any error???
	            exitVal = p.waitFor();
	            System.out.println("ExitValue: " + exitVal);
				
			}
			else if(ov.isUnix() || ov.isMac()){
				 
		           
		         Process p = rt.exec("perl "+e + File.separator + "cdd.pl "+ this.middlereport+" "+ this.dataMode+" "+this.finalreport);	
	
		            // any error message?
		            StreamGobbler errorGobbler = new 
		                StreamGobbler(p.getErrorStream(), "ERROR");            
		            
		           
		                
		            // kick them off
		            errorGobbler.start();
		            
		                                    
		            // any error???
		            int exitVal = p.waitFor();
		            System.out.println("ExitValue: " + exitVal);
		            				
				
				
		        Thread.sleep(5000L);
				String mergeLine = "perl " + e + File.separator + "mergeCDD.pl " + this.report + " "
						+ this.finalreport + " " + this.mergedfinalreport;
				p = rt.exec(mergeLine);
				p.waitFor();
				Thread.sleep(5000L);
				
			}
		} catch (InterruptedException | IOException arg6) {
			arg6.printStackTrace();
		}finally {
			JOptionPane.showMessageDialog(this.cddpanel, "CDD NCBI query completed...\n\nPlease press OK",
					"finished ....", 1);
		}
	
	}

	public void mergeFeature(String featfile) {
		File refpath = new File("");
		this.featFile=featfile;
		setMergedFeatureFilename();
		
		try {
			
			File scriptsPath = new File(
						refpath.getCanonicalFile() + File.separator + "externalPrograms" + File.separator);
			if	(ov.isWindows()){
				String e = "cmd /c perl " + scriptsPath + File.separator + "mergeCDDfeature.pl " + this.report + " "
						+ featFile + " " + this.mergedFeatfinalreport;				
				Runtime rt = Runtime.getRuntime();				
				Process p = rt.exec(e);				
				 // any error message?
	            StreamGobbler errorGobbler = new 
	                StreamGobbler(p.getErrorStream(), "ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler = new 
	                StreamGobbler(p.getInputStream(), "OUTPUT");
	                
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            // any error???
	            int exitVal = p.waitFor();
	            System.out.println("ExitValue: " + exitVal);
										
				Thread.sleep(6000L);			
				
						
			}
			else if(ov.isUnix() || ov.isMac()){
				String mergeU = "perl " + scriptsPath + File.separator + "mergeCDDfeature.pl " + this.report + " "
						+ featFile + " " + this.mergedFeatfinalreport;
				System.out.println(mergeU);
				Runtime rt = Runtime.getRuntime();						
				Process p = rt.exec(mergeU);
				p.waitFor();				
			}
		} catch (InterruptedException | IOException arg6) {
			arg6.printStackTrace();
		}
	}

	public void extractsCdsid() {
		try {
			FileReader e1 = new FileReader(this.finalreport);
			BufferedReader mybuffer = new BufferedReader(e1);
			String line = "";

			while (line != null) {
				line = mybuffer.readLine();
				String[] splitLine = line.split("\t");
				Pattern pat = Pattern.compile("^#cdsid");
				Matcher mat = pat.matcher(splitLine[0]);
				if (mat.matches()) {
					String[] chain = splitLine[1].split("[-]");
					this.cdsid = chain[0] + "-" + chain[1] + "-" + chain[2];
					break;
				}
			}

			e1.close();
		} catch (IOException arg7) {
			arg7.printStackTrace();
		}

	}
	public Vector<Vector<String>> getDataVector(){
		return new ReadMerged(this.getMergedFileName()).sortMerged();
	}
	public Vector<Vector<String>> getDataVector(String file){
		return new ReadMerged(file).sortMerged();
	}
	public Vector<Vector<String>> getDataVectorF(){
		return new ReadMerged(this.getMergedFeatureFileName()).sortMerged();
	}
	public Vector<Vector<String>> getDataVectorF(String feature){
		return new ReadMerged(feature).sortMerged();
	}

}
