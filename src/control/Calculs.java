package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import java.util.Map.Entry;


/**
 * @author JuanGuerra
 * This class help to compare the proteins presents in two groups A and B in order to detect amino acids variations.
 * 
 */
public class Calculs {	
	private ArrayList<String> groupA;
	private ArrayList<String> groupB;
	private HashMap<String, LinkedList<Subject>> proteinsmap2;	
	private Integer countEspeces;
	private ResultSet rs;
	static Set<String> conservedProteins; 
	
	public Calculs(File queryproteins, ArrayList<String> groupA, ArrayList<String> groupB) {
		this.groupA = groupA;
		this.groupB = groupB;
	}
	/**
	 * Constructor initialize  
	 * @param rs ResultSet from QueryProteinsDB
	 * @param groupA A list with the names of the organisms that belong to group A 
	 * @param groupB A list with the names of the organisms that belong to group B
	 * @throws Exception
	 */
	
	public Calculs(ResultSet rs, ArrayList<String> groupA, ArrayList<String> groupB) throws Exception {
		this.rs = rs;
		this.groupA = groupA;
		this.groupB = groupB;
		//this.proteinsmap2= getProteinHashMapRS();
		conservedProteins = getProteinSet();
		
	}
	/**
	 * Method to count the organism compared
	 * @return Integer
	 */
	public Integer countOrganisms() {
		//return the totally number of organisms to compare
		this.countEspeces = Integer.valueOf(this.groupA.size() + this.groupB.size());
		return this.countEspeces;
	}
	/**
	 * Count the conserved proteins
	 * @return Integer
	 */
	public Integer countConservedProteins(){
		return conservedProteins.size();
	}
	
	public void showConservedProteins() {
		Iterator<String>  iterCP = Calculs.conservedProteins.iterator();//this.proteinsmap2.entrySet().iterator();
		
		while (iterCP.hasNext()) {
			String element = iterCP.next();
			//System.out.println(element);
			//element.getValue().keySet().toString();	
			//System.out.println(element.getValue().keySet().toString());
			
		}

	}
	/**
	 * Method to convert the HashMap to Vector in order to show it into a JTable
	 * @param intersectionHash HashMap<String, Set<Integer>
	 * @return Vector<Vector<String>>
	 */
	public Vector<Vector<String>> HashToVector(HashMap<String, Set<Integer>> intersectionHash) {
		Vector<Vector<String>> data = new Vector<Vector<String>>();				
		Iterator<Entry<String, Set<Integer>>> iter1 = intersectionHash.entrySet().iterator();
		while (iter1.hasNext()) {
			Entry<String, Set<Integer>> element = (Entry<String, Set<Integer>>) iter1.next();
			String elementValue = ((Set<Integer>) element.getValue()).toString();
			String protein = (String) element.getKey();
			Vector<String> tempVector = new Vector<String>();
			tempVector.addElement(protein);	
			
			if(proteinsmap2.containsKey(protein)){	
				tempVector.addElement(proteinsmap2.get(protein).getFirst().get_refseq());
				tempVector.addElement(proteinsmap2.get(protein).getFirst().get_protein_name());
				tempVector.addElement(proteinsmap2.get(protein).getFirst().get_symbol());
			}
			
			tempVector.addElement(elementValue);
			data.addElement(tempVector);
		}		
		return data;
	}
	
	
	/**
	 * Method to convert the amino acids to set  
	 * @param aminoacidList ArrayList<String>
	 * @return Set<Integer>
	 */
	private Set<Integer> list_to_Set(ArrayList<String> aminoacidList) {
		TreeSet<Integer> aminoacidSet = new TreeSet<Integer>();
		Iterator<String> arg3 = aminoacidList.iterator();
		while (arg3.hasNext()) {
			String s = (String) arg3.next();			
			//if is just a position from identical amino acids list
			if (this.isNumeric(s)) {
				aminoacidSet.add(Integer.valueOf(Integer.parseInt(s)));
			}
			//if is a list with numbers an letters from gap, similar or different amino acids query 
			else if(s.length()!=0 ){
				String[] ss=(String[]) s.split(":");
				if (this.isNumeric(ss[0])) {
					aminoacidSet.add(Integer.valueOf(Integer.parseInt(ss[0])));
				}
			}
		}

		return aminoacidSet;
	}
	/**
	 * Method that take a position as key and the amino acid variant as value	
	 * @param aminoacidList ArrayList<String>
	 * @return HashMap<Integer,String>
	 */
	private HashMap<Integer,String> ListPositionsToHash(ArrayList<String> aminoacidList){			
		HashMap<Integer,String> variations=new HashMap<Integer,String>();
		Iterator<String> iter =aminoacidList.iterator();		
		while(iter.hasNext()){
			String s = iter.next();			
			if(s.length()!=0 & !s.equals("0")){				
				String[] ss=(String[]) s.split(":");				
				variations.put(Integer.parseInt(ss[0]),ss[1]);							
			}			
		}		
		return variations;
	}
	/**
	 * Method to know if the value is a number
	 * @param value String value
	 * @return boolean
	 */
	private boolean isNumeric(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException arg2) {
			return false;
		}
	}
	/**
	 * Method to know if a protein is present into a conserved proteins hash
	 * @param name String
	 * @return boolean
	 */
	private boolean isThere(String name) {
		return conservedProteins.contains(name);		
	}
	/**
	 * Method to obtain the candidates proteins with the LocusTag as key and an ArrayList of Subject
	 * the Subject is an object with the organism name and two hash maps with Synonymous and non synonymous mutations    
	 * @return HashMap<String, ArrayList<Subject>>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, LinkedList<Subject>> getProteinHashMapRS(HashMap<String, Set<Integer>> intersection) throws SQLException {
		HashMap<String, ArrayList<String>> sujeto2=new HashMap<String, ArrayList<String>>();
		LinkedList<Subject> variants_subject=new LinkedList<Subject>();		
		proteinsmap2=new HashMap<String, LinkedList<Subject>>();
		Integer countP = 0;
		String tempP = "";
		Integer limit = this.countOrganisms();
		rs.beforeFirst();
		while (rs.next()) {
			String protein = rs.getString("featureTable.locusTag");
			String idSubject=rs.getString("blast.subject");
			String similar = rs.getString("proteines.similaires");
			String different = rs.getString("proteines.differentes");			
			String refseq = rs.getString("featureTable.non_redundant_refseq");
			String name = rs.getString("featureTable.name");
			String symbol = rs.getString("featureTable.symbol");
			
			
			//hash with positions mutations ex {7:R/K, 10G/L}
			ArrayList<String> positions=new ArrayList<String>();
			positions.addAll(Arrays.asList(similar.split(",")));			
			positions.addAll(Arrays.asList(different.split(",")));
			HashMap<Integer,String> positions_hash= ListPositionsToHash(positions);
			
			//if is a candidate protein
			if (intersection.containsKey(protein)){
				if(tempP.length()==0){				
				sujeto2.clear();
				variants_subject.clear();
				tempP = protein;				
				sujeto2.put(idSubject, positions);
				variants_subject.add(new Subject(idSubject,refseq,name,symbol,positions_hash));
				
				countP += 1;
			} 
			else if (tempP.equals(protein)) {
				sujeto2.put(idSubject, positions);				
				variants_subject.add(new Subject(idSubject,refseq,name,symbol,positions_hash));
				
				countP += 1;
				if (countP == limit) {		
					
					LinkedList<Subject> subject_temp=(LinkedList<Subject>) variants_subject.clone();					
					
					proteinsmap2.put(protein,subject_temp );
					variants_subject.clear();													
					tempP = "";
					countP = 0;					
				}
			}
			else if (!tempP.equals(protein)) {
				variants_subject.clear();
				tempP = protein;				
				sujeto2.put(idSubject, positions);				
				variants_subject.add(new Subject(idSubject,refseq,name,symbol,positions_hash));				
				countP = 1;
			}
		}}
		return proteinsmap2;
	}
	/**
	 * Method to obtain a conserved proteins set
	 * @return conservedProteins: set with LocusTag from conserved proteins
	 * @throws SQLException
	 */
	
	private Set<String> getProteinSet() throws SQLException {		
		Set<String> conservedP =new TreeSet<String>();
		Integer countP = 0;
		String tempP = "";
		Integer limit = this.countOrganisms();
		rs.beforeFirst();
		while (rs.next()) {
			String protein = rs.getString("featureTable.locusTag");						
			if (tempP.length()==0) {								
				tempP = protein;							
				countP += 1;
			} 
			else if (tempP.equals(protein)) {
				countP += 1;
				if (countP == limit) {						
					conservedP.add(protein);														
					tempP = "";
					countP = 0;					
				}
			}
			else if (!tempP.equals(protein)) {				
				tempP = protein;								
				countP = 1;
			}
		}
		return conservedP;
	}
	
	
	
	
	/**
	 * 	Method to obtain a positions set for each protein into an organism group  
	 * @param group ArrayList<String>
	 * @param indentical boolean to choice identical positions
	 * @param similar boolean to choice similar positions
	 * @param diferent boolean to choice diferent positions
	 * @param stopc boolean to choice stop codon positions
	 * @param gapin boolean to choice gap in the reference
	 * @param gapout boolean to choice gap in the organisms belong groupB 
	 * @param groupA boolean to choice if gapin or gapout is true
	 * @return HashMap<String, Set<Integer>> 
	 * @throws SQLException
	 */
	private HashMap<String, Set<Integer>> combineGral(ArrayList<String> group, boolean indentical,
		boolean similar, boolean diferent, boolean stopc,boolean gapin,boolean gapout, boolean groupA) throws SQLException{
		
		HashMap<String, Set<Integer>> combine = new HashMap<String, Set<Integer>>();
		Set<Integer> tempSet = new TreeSet<Integer>();			
		int count=0;
		rs.beforeFirst();
		//String proteinTemp="";
		while(this.rs.next()){
			ArrayList<String> choice = new ArrayList<String>();			
			String protein = this.rs.getString("featureTable.locusTag");
			String specieid = this.rs.getString("blast.subject");
			
			if ((this.isThere(protein) && group.contains(specieid))){					
				//choices
				if(indentical){							
					String indenticalPositions= rs.getString("proteines.identiques");
					choice.addAll(Arrays.asList(indenticalPositions.split(",")));					
				}
				if(similar){
					String similarPositions= rs.getString("proteines.similaires");					
					choice.addAll(Arrays.asList(similarPositions.split(",")));					
				}
				if(diferent){
					String differentsPositions= rs.getString("proteines.differentes");
					choice.addAll(Arrays.asList(differentsPositions.split(",")));					
				}
				if(stopc){
					String stopCodonPositions= rs.getString("proteines.stop");
					choice.addAll(Arrays.asList(stopCodonPositions.split(",")));					
				}
				if (gapin){
					String gapInPositions= rs.getString("proteines.gapsQuery");
					choice.addAll(Arrays.asList(gapInPositions.split(",")));					
				}
				if(gapout){
					String gapOutPositions= rs.getString("proteines.gapsSubject");
					choice.addAll(Arrays.asList(gapOutPositions.split(",")));					
				}				
								
				//calculs			
				
				if (tempSet.isEmpty()) {					
					tempSet = list_to_Set(choice);					
					if(tempSet.isEmpty()) {
						tempSet.add(0);						
					}					
					count += 1;
					
				} else if (!tempSet.isEmpty()) {								
					Set<Integer> newSet = list_to_Set(choice);	
					
					if(groupA){
						tempSet.addAll(newSet);						
					}
					else if(!groupA){
						tempSet.retainAll(newSet);						
					}					
					if(tempSet.isEmpty()) {
						tempSet.add(0);						
					}					
					count += 1;
					
				}
				
				if (count == group.size()) {
					TreeSet<Integer> newSet1 = new TreeSet<Integer>((Collection<Integer>) tempSet);					
					combine.put(protein, newSet1);									
					tempSet.clear();					
					count = 0;					
				}			
			}else if(!this.isThere(protein)){				
				count=0;
			}
			
		}	
		System.out.println("communs proteins : "+combine.size());
				
		return combine;
	}
	/**
	 * Method to combine both groups A and B and finally obtain what position have changed through evolution
	 * @param combinedA HashMap<String, Set<Integer>> from group A
	 * @param combinedB HashMap<String, Set<Integer>> from group B
	 * @return HashMap<String, Set<Integer>>
	 * @throws SQLException 
	 */
	private HashMap<String, Set<Integer>> intersection (HashMap<String, Set<Integer>> combinedA,
			HashMap<String, Set<Integer>> combinedB, boolean gap) throws SQLException{	
		
		HashMap<String, Set<Integer>> intersection_hash = new HashMap<String, Set<Integer>>();
		Iterator<Entry<String, Set<Integer>>> iter = combinedA.entrySet().iterator();		
		while (iter.hasNext()) {			
			Entry<String, Set<Integer>> element = (Entry<String, Set<Integer>>) iter.next();
			String protein = (String) element.getKey();
			Set<Integer> positions = (Set<Integer>) element.getValue();
			Set<Integer> positionsB = (Set<Integer>) combinedB.get(protein);			
			if (positions != null && positionsB != null) {
				positions.retainAll(positionsB);
				if (gap){
					positionsB.removeAll(positions);
					if (!positionsB.isEmpty() && positionsB.size() >= 1 && !positionsB.toString().equals("[0]")) {
						
						intersection_hash.put(protein, positionsB);
					}
				}else{
					if (!positions.isEmpty() && !positions.toString().equals("[0]")) {
						
						intersection_hash.put(protein, positions);
					}
				}				
			}
		}
		
		this.proteinsmap2= getProteinHashMapRS(intersection_hash);
		
		return intersection_hash;
	}
	
	private Integer countMutations( HashMap<String, Set<Integer>> intersection){
		Iterator<Entry<String, Set<Integer>>> iter = intersection.entrySet().iterator();
		int count=0;
		while(iter.hasNext()){
			Entry<String, Set<Integer>>  e = (Entry<String, Set<Integer>> ) iter.next();
			count+= e.getValue().size();
		}
		return count;
	}
	

	/**
	 * Method to print an analysis result
	 * @param filename String
	 * @param intersection HashMap<String, Set<Integer>>
	 * @throws IOException
	 */
	public void getGeneralReport(String filename, HashMap<String, Set<Integer>> intersection,boolean no_gap)throws IOException {
		FileWriter output = null;
		BufferedWriter mybuffer = null;	
		
		try {
			output = new FileWriter(filename);
			mybuffer = new BufferedWriter(output);
			mybuffer.append("Group A: ;"+String.join(", ", this.groupA)+ "\n");			
			mybuffer.append("Group B: ;"+String.join(", ", this.groupB)+ "\n");
			mybuffer.append("Common proteins: ;" + countConservedProteins() + "\n");
			mybuffer.append("Proteins candidates: ;" + intersection.size() + "\n");	
			mybuffer.append("Mutations found: ;"+this.countMutations(intersection)+"\n");
			mybuffer.append("Protein;Refseq;Name;Symbol;Variant position");	//variants all\n	
			//System.out.println("Commun Proteins:"+ "\n");
			
			//System.out.println("End of commun proteins"+ "\n");
			Iterator<Entry<String, Set<Integer>>> iter = intersection.entrySet().iterator();
						
			if(no_gap){
				for(String organism : groupB){
					mybuffer.append(";"+organism);					
				}					
			}			
			
			mybuffer.append("\n");	
			
			HashMap<String, HashMap<String,Integer>> stat_variations=new HashMap<String, HashMap<String,Integer>>(); 
			
			while(iter.hasNext()){
				Entry<String, Set<Integer>>  e = (Entry<String, Set<Integer>> ) iter.next();
				String protein = (String) e.getKey();
				Set<Integer> positions = (Set<Integer>) e.getValue();
				mybuffer.append(protein + ";");
				if(proteinsmap2.containsKey(protein)){					
					LinkedList<Subject> organisms_linked=proteinsmap2.get(e.getKey());					
					Subject first=organisms_linked.getFirst();
					mybuffer.append(first.get_refseq()+";");
					mybuffer.append(first.get_protein_name()+";");
					mybuffer.append(first.get_symbol()+";");					
					mybuffer.append(positions+";");
					String name;
					
					if(no_gap){						
						for(String organismB : groupB){
							
							for(Subject organism : organisms_linked){									
								name=organism.get_name();
								
								
								if(organismB.equals(name)){
									
									if( stat_variations.containsKey(name)){
										
										HashMap<String,Integer> internal_variations=organism.count_variations( organism.show_Variation(positions));
										Iterator<Entry<String, Integer>> it = internal_variations.entrySet().iterator();
										while(it.hasNext()){
											Entry<String, Integer>  e5 = (Entry<String,Integer> )it.next();
											if(stat_variations.get(name).containsKey(e5.getKey())){
												stat_variations.get(name).replace(e5.getKey(), stat_variations.get(name).get(e5.getKey())+e5.getValue());
											}else{
												stat_variations.get(name).put(e5.getKey(), e5.getValue());
											}									
										}
										}
									else{
										stat_variations.put(organism.get_name(),organism.count_variations( organism.show_Variation(positions)));
										}
										
									
									mybuffer.append(organism.show_Variation(positions).toString());
									mybuffer.append(";");
								}
							}
						}
						
									
					}					
				}
				mybuffer.append("\n");				
			}
			mybuffer.append("\n");
			mybuffer.append("Stats: counting variations\n");
			mybuffer.append("According to Grantham's distance, most similar amino acids are leucine and isoleucine (value = 5)"
					+ " and the most distant are cysteine and tryptophan (value 215)\n");
			mybuffer.append("\n");
			mybuffer.append("According to Experimental Exchangeability (value EX x 1000), the best replacement for other residues is Alanine with a EX destination (EXdest) equals to 0,410\n"+ 
			"and lysine is the most readily replaced with an EX source (EXsrc) equals to 0,41)\n");
					
			mybuffer.append("for instance 96=T/F:103:273 means  there is a mutation at position 96, which corresponds to a T for an F, with a Grantham value of 103 and an EX value of 273\n");
			mybuffer.append("\n");
			Iterator<Entry<String, HashMap<String,Integer>>> iterStat = stat_variations.entrySet().iterator();
			
			while(iterStat.hasNext()){				
				Entry<String, HashMap<String,Integer>> entrySV=(Entry<String, HashMap<String,Integer>>)iterStat.next();
				if(!entrySV.getValue().isEmpty()){
					mybuffer.append(entrySV.getKey() + ";");
					mybuffer.append(entrySV.getValue().toString()+"\n");
				}
			}			
		} catch (IOException e) {			
			e.printStackTrace();
		}finally {
			
			mybuffer.close();
			output.close();
		}		
	}
	
	public ArrayList<ArrayList<String>> getFrame(HashMap<String, Set<Integer>> intersection,boolean no_gap){
		ArrayList<ArrayList<String>> frame= new ArrayList<ArrayList<String>>();
		ArrayList<String> line=new ArrayList<String>();
		////organism variations count 
		HashMap<String,HashMap<String,Integer>> ovu= new HashMap<String,HashMap<String,Integer>>();
		
		Iterator<Entry<String, Set<Integer>>> iter = intersection.entrySet().iterator();		
		
		line.add("Protein");
		line.add("Refseq");
		line.add("Name");
		line.add("Symbol");
		line.add("Variant position");
		
		LinkedList<Subject> organisms_linked_bb=proteinsmap2.get(intersection.keySet().toArray()[0]);
		if(no_gap){
			for(Subject organism : organisms_linked_bb){
				line.add(organism.get_name());				
			}					
		}
		frame.add(line);
		while(iter.hasNext()){
			ArrayList<String> line_in=new ArrayList<String>();
			Entry<String, Set<Integer>>  e = (Entry<String, Set<Integer>> ) iter.next();
			String protein = (String) e.getKey();
			Set<Integer> positions = (Set<Integer>) e.getValue();
			
			line_in.add(protein);				
				if(proteinsmap2.containsKey(protein)){					
					LinkedList<Subject> organisms_linked=proteinsmap2.get(e.getKey());					
					Subject first=organisms_linked.getFirst();
					line_in.add(first.get_refseq());
					line_in.add(first.get_protein_name());
					line_in.add(first.get_symbol());					
					line_in.add(positions.toString());
					if(no_gap){						
						for(Subject organism : organisms_linked){
							HashMap<Integer, String> candidates=organism.show_Variation(positions);
							candidates.entrySet().iterator();							
							line_in.add(organism.show_Variation(positions).toString());														
							ovu.put(organism.get_name(), organism.count_variations(organism.show_Variation(positions)));							
						}					
					}					
				}
				frame.add(line_in);				
			}
		
		
		return frame;	
		
	}
		
	/**
	 * Combine Identical against Different	
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getIvsD() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, true, false, false,  false, false,false, false);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false, false, true,  false, false,false, false);		
		return intersection(combinedA, combinedB, false);
	}
	/**
	 * Combine Identical and similar against Different
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getISvsD() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, true, true, false,  false, false,false, false);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false, false, true,  false, false,false, false);		
		return intersection(combinedA, combinedB, false);
	}
	/**
	 * Combine Identical against similar
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getIvsS() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, true, false, false,  false, false,false, false);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false, true, false,  false, false,false, false);		
		return intersection(combinedA, combinedB, false);
	}
	/**
	 * Combine all against stop codon
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getStopCodon() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, true, true, true, false, false,false, false);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false, false, false, true, false,false, false);		
		return intersection(combinedA, combinedB, false);
	}
	/**
	 * Gap only in the group A
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getGapIn() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, false, false, false,  false, true,false, true);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false,  false,  false, false,true,false, false);		
		return intersection(combinedA, combinedB, true);
	}
	/**
	 * Gap only in the group B
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Set<Integer>> getGapOut() throws SQLException{
		HashMap<String, Set<Integer>> combinedA=this.combineGral(this.groupA, false, false, false,  false, false,true, true);
		HashMap<String, Set<Integer>> combinedB=this.combineGral(this.groupB, false,  false,  false, false,false,true, false);		
		return intersection(combinedA, combinedB, true);
	}
}
