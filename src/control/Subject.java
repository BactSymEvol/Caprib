package control;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import modele.EXdistance;
import modele.Grantham;

import java.util.Map.Entry;

import javax.swing.JOptionPane;
/**
 * build a Subject for each protein
 * @author juan_
 *
 */
public class Subject {
	private String name;
	private String refseq;
	private String protein_name;
	private String symbol;
	private HashMap<Integer,String> variantsh;
	static Grantham gham = new Grantham();
	static EXdistance exd= new EXdistance();
	
	/**
	 * Constructor	
	 * @param organisme
	 * @param refseq
	 * @param protein_name
	 * @param symbol
	 * @param variants
	 */
	public Subject(String organisme, String refseq, String protein_name, String symbol, HashMap<Integer,String> variants){
		this.name= organisme;
		this.variantsh=variants;
		this.refseq=refseq;
		this.protein_name=protein_name;
		this.symbol= symbol;		
	}
		
	public HashMap<Integer,String> show_Variation(Set<Integer> intersection){
		Iterator<Integer> it = intersection.iterator();	
		HashMap<Integer,String> candidates=new HashMap<Integer,String>();
		
		while (it.hasNext()) {				
			int pos = it.next();			
			if(variantsh.containsKey(pos)){		
				String var =variantsh.get(pos).toUpperCase();
				String valueEXd=""; 
				if(var.length()==3){
					
				Integer valueEX=exd.getEX(var.substring(0,1), var.substring(2));
				
				if(valueEX >= 0){
					valueEXd = valueEX.toString();
				}else{
					valueEXd="NR";
				}
				
				Integer valueEXsrc=exd.getEX(var.substring(0,1), "EXsrc");
				Integer valueEXdest=exd.getEX("EXdest", var.substring(2));
				
					candidates.put(pos,  var+":"+gham.getGranthamEq(var.substring(0,1), var.substring(2)).toString()+":"+valueEXd+":EXsrc="+valueEXsrc.toString()+":EXdest="+valueEXdest.toString());
				}else{
					JOptionPane.showMessageDialog((Component) null, "'"+var+"'" +
							" is not a valide entry, please review your filtered file for "+ this.get_name()+
							" protein "+this.get_refseq(), "Warning", 2);
					System.out.println(var + " is not valide");
					candidates.put(pos, var+":NaN");
				}
					
				
			}			
		}
		return candidates;	
	}
	
	public HashMap<String,Integer> count_variations(HashMap<Integer,String> candidates){
		HashMap<String,Integer> countingVariations=new HashMap<String,Integer>();
		Iterator<Entry<Integer, String>> iter = candidates.entrySet().iterator();	
		while(iter.hasNext()){
			Entry<Integer, String>  e = (Entry<Integer, String> ) iter.next();
			String variation = (String) e.getValue();
			if (countingVariations.containsKey(variation)){
				int oldValue = countingVariations.get(variation);
				int newValue = oldValue + 1;
				countingVariations.replace(variation, oldValue, newValue);
			}else{
				countingVariations.put(variation, 1);
			}
		}
		return countingVariations;
	}
	 
	public String get_name(){
		return name;
	}
	public String get_protein_name(){
		return protein_name;
	}
	public String get_refseq(){
		return refseq;
	}	
	public String get_symbol(){
		if (symbol.length()!=0){
			return symbol;
		}else{
			return "-";
		}		
	}
	public String get_variants(){
		String out=name+":"+variantsh.toString();
		
		return  out;
	}
	
	public void repport_candidates(Set<Integer> intersection){
		Iterator<Integer> it = intersection.iterator();	
		HashMap<Integer,String> candidates=new HashMap<Integer,String>();
		
		while (it.hasNext()) {				
			int pos = it.next();			
			if(variantsh.containsKey(pos)){				
				candidates.put(pos, variantsh.get(pos));				
			}	
		}		
	}
	public boolean isThere(String organismName){
		return false;
	}
	
}
	
