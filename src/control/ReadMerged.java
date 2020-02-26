package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
/**
 * 
 * @author juan_
 *This class help us to read the file merged with perl.
 *Perl merge the results from NCBI CDD with our result from IvsD, ISvsD,IvsS, gap in, gap out 
 *and stop codon analyses
 */
public class ReadMerged {
	String report;
	private FileReader f; 
	private BufferedReader mybuffer;
	
	public ReadMerged(String report){
		this.report=report;
	}	
	public Vector<Vector<String>> readMerged() {
		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
		try {
			f = new FileReader(report);
			mybuffer = new BufferedReader(f);
			String line = "";
			boolean firstLine = true;

			while (true ) {
				
				while ((line = mybuffer.readLine())!= null) {					
					
					if (!firstLine) {
						Vector<String> tempVector = new Vector<String>();
						String[] splitLine = line.split("\t");
						String[] arg10 = splitLine;
						int arg9 = splitLine.length;

						for (int arg8 = 0; arg8 < arg9; ++arg8) {
							String info = arg10[arg8];
							tempVector.addElement(info);
						}

						dataVector.addElement(tempVector);
					} else {
						firstLine = false;
					}
				}
				f.close();
				break;
				
			}
		} catch (IOException arg11) {
			System.out.println("the file is Empty");
			arg11.printStackTrace();
		
		}		
		return dataVector;
	}
	public Vector<Vector<String>> sortMerged(){
		Vector<Vector<String>> vector=this.readMerged();
		
		Collections.sort(vector, new Comparator<Vector<String>>(){
		    @Override  public int compare(Vector<String> v1, Vector<String> v2) {		    	
		        return v1.get(0).compareTo(v2.get(0)); 
		}});
		
		return vector;
		
	}
}
