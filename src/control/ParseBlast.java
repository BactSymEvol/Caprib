package control;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import vue.BlastFiles;

public class ParseBlast {
	private File pathBlast;
	private File pathParseBlast;
	private Object limit;
	private BlastFiles bfpanel;
	private OsValidation ov;
	
	public ParseBlast(File pathBlast, File pathParseBlast, Object limit) {
		//args: the blast file, filtered file && identity limit 
		this.pathBlast = pathBlast;
		this.pathParseBlast = pathParseBlast;
		this.limit = limit;
		ov=new OsValidation();
	}
	
	private void parsing() throws InterruptedException {
		File refpath = new File("");

		try {
			File e = new File(refpath.getCanonicalFile() + File.separator + "externalPrograms" + File.separator);
			
			if	(ov.isWindows()){
				String parsingLine = "cmd /c perl " + e + File.separator + "parseBlastProteins.pl " + this.pathBlast + " "
					+ this.pathParseBlast + " " + this.limit;
				System.out.println(parsingLine);
				Runtime rt=Runtime.getRuntime();
				Process p = rt.exec(parsingLine);
				StreamGobbler errorGobbler = new 
		                StreamGobbler(p.getErrorStream(), "ERROR");            
		            
		            // any output?
		            
		                
		            // kick them off
		            errorGobbler.start();
		   
		                                    
		            // any error???
		            int exitVal = p.waitFor();
		            System.out.println("ExitValue: " + exitVal);
		             
				
				
			}
			else if(ov.isUnix()|ov.isMac()){
				String parsingLine = "perl " + e + File.separator + "parseBlastProteins.pl " + this.pathBlast + " "
						+ this.pathParseBlast + " " + this.limit;
				System.out.println(parsingLine);
				
				Runtime rt=Runtime.getRuntime();
				Process p = rt.exec(parsingLine);
				StreamGobbler errorGobbler = new 
		                StreamGobbler(p.getErrorStream(), "ERROR");            
		            
		
		                
		            // kick them off
		            errorGobbler.start();
		                                    
		            // any error???
		            int exitVal = p.waitFor();
		            System.out.println("ExitValue: " + exitVal);
			}
			JOptionPane.showMessageDialog(bfpanel, "The blast file was filtered succesfully...\n\nPlease press OK",
					"finished ....", 1);
		} catch (IOException arg3) {
			arg3.printStackTrace();
		}

	}

	public void run() {
		try {
			this.parsing();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
}
