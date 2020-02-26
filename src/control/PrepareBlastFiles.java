/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package control;

import java.awt.Component;
import java.io.File;
import javax.swing.JOptionPane;
import vue.BlastFiles;

public class PrepareBlastFiles {
	private File pathProject;
	private File featuretable;
	private File fastaProt;
	private File fastaDNA;
	private Object spinner;
	private BlastFiles blastFiles;
	private OsValidation ov;
	
	public PrepareBlastFiles(File pathProject, File featuretable, File fastaProt, File fastaDNA, Object spinner,
			BlastFiles blastFiles) {
		
		this.pathProject = pathProject;
		this.featuretable = featuretable;
		this.fastaProt = fastaProt;
		this.fastaDNA = fastaDNA;
		this.spinner = spinner;
		this.blastFiles = blastFiles;
		ov=new OsValidation();
	}

	public PrepareBlastFiles(File f) {
		this.pathProject = f;
	}

	public File getPathProject() {
		return this.pathProject;
	}

	public void setPathProject(File pathBlast) {
	}

	public void setFastaProt(File fastaProt) {
		this.fastaProt = fastaProt;
	}

	public void setFeaturetable(File featuretable) {
		this.featuretable = featuretable;
	}

	public void setFastaDNA(File fastaDNA) {
		this.fastaDNA = fastaDNA;
	}
	//prepare blast files
	public void prepareFiles(File batchFile) {
	}
	public void prepareFiles() {
		File refpath = new File("");
		if (!this.fastaProt.exists()) {
			JOptionPane.showMessageDialog((Component) null, "you must choose a fasta protein file", "Warning", 2);
		} else if (!this.featuretable.exists()) {
			JOptionPane.showMessageDialog((Component) null, "you must choose a feature table file", "Warning", 2);
		} else if (!this.fastaDNA.exists()) {
			JOptionPane.showMessageDialog((Component) null, "you must choose a subject fasta file", "Warning", 2);
		} else {
			try {
				JOptionPane.showMessageDialog(this.blastFiles,
						"preparing files to blast...\nPlease wait until a finish message occurs\n\nPlease press OK to continue",
						"preparing ....", 1);
				File e = new File(refpath.getCanonicalFile() + File.separator + "externalPrograms" + File.separator);
				System.out.println(ov.isWindows());
				String dbname=this.fastaDNA.getName()+"db";
				if	(ov.isWindows()){
					String command = "xcopy " + this.fastaProt + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					command = "xcopy " + this.fastaProt + " " + e;
					Runtime.getRuntime().exec(command);
					command = "xcopy " + this.featuretable + " " + this.pathProject + File.separator + "Fasta";
					System.out.println(command);
					Runtime.getRuntime().exec(command);
					command = "xcopy " + this.featuretable + " " + e;
					Runtime.getRuntime().exec(command);
					command = "xcopy " + this.fastaDNA + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					command = "xcopy " + this.fastaDNA + " " + e;
					Runtime.getRuntime().exec(command);
					Thread.sleep(2000L);
					Runtime rt = Runtime.getRuntime();
					File combinedPath = new File(e + File.separator + "combined.fasta");
					command = "xcopy " + this.fastaDNA + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					File blastPath = new File(this.pathProject + File.separator + "Blast" + File.separator);
					Thread.sleep(2000L);
					String combineLine = "cmd /c perl " + e + File.separator + "combine_TABLE_FASTA.pl " + e
							+ File.separator + this.fastaProt.getName() + " " + e + File.separator
							+ this.featuretable.getName() + " " + e;
					Thread.sleep(3000L);
					System.out.println(combineLine);
					
					rt = Runtime.getRuntime();
					Process p = rt.exec(combineLine);
					p.waitFor();
					command = "xcopy " +combinedPath+" "+ this.pathProject + File.separator + "Fasta";
					System.out.println(command);
					Runtime.getRuntime().exec(command);
										
					String formatDB = "makeblastdb -in " +e + File.separator + this.fastaDNA.getName() +" -dbtype nucl -out " +
							dbname +" -parse_seqids";					
					
					System.out.println("nuevo path: " + formatDB);
					rt = Runtime.getRuntime();
					p = rt.exec(formatDB);
					p.waitFor();
					Thread.sleep(5000L);
					String outputFile = this.fastaProt.getName() + "_vs_" + this.fastaDNA.getName() + ".txt";					
					String blastAllLine ="tblastn -query "+combinedPath+" -db "+dbname+ " -out "+ 
					blastPath + File.separator+outputFile +	" -num_alignments 1 -evalue 1e"+ this.spinner.toString() + " -seg no";					
					
					System.out.println(blastAllLine);
					rt = Runtime.getRuntime();
					p = rt.exec(blastAllLine);
					p.waitFor();
					String deleteFiles = "cmd /c " + e + File.separator + "cleanAfterBlast.bat";
					System.out.println(deleteFiles);
					rt = Runtime.getRuntime();
					p = rt.exec(deleteFiles);
					p.waitFor();
				}
				else if	(ov.isUnix() || ov.isMac()){
					String command = "cp " + this.fastaProt + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					command = "cp " + this.fastaProt + " " + e;
					Runtime.getRuntime().exec(command);
					command = "cp " + this.featuretable + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					command = "cp " + this.featuretable + " " + e;
					Runtime.getRuntime().exec(command);
					command = "cp " + this.fastaDNA + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					command = "cp " + this.fastaDNA + " " + e;
					Runtime.getRuntime().exec(command);
					Thread.sleep(2000L);
					Runtime rt = Runtime.getRuntime();
					File combinedPath = new File(e + File.separator + "combined.fasta");
					command = "cp " + this.fastaDNA + " " + this.pathProject + File.separator + "Fasta";
					Runtime.getRuntime().exec(command);
					File blastPath = new File(this.pathProject + File.separator + "Blast" + File.separator);
					Thread.sleep(2000L);
					//gnome-terminal -e 
					String combineLine = "perl " + e + File.separator + "combine_TABLE_FASTA.pl " + e
							+ File.separator + this.fastaProt.getName() + " " + e + File.separator
							+ this.featuretable.getName() + " " + e;
					Thread.sleep(3000L);
					System.out.println(combineLine);
					Runtime.getRuntime().exec(combineLine);
					command = "cp " +combinedPath+" "+ this.pathProject + File.separator + "Fasta";
					System.out.println(command);
					Runtime.getRuntime().exec(command);
					String formatDB = "makeblastdb -in " +e + File.separator + this.fastaDNA.getName() +" -dbtype nucl -out " +
							dbname +" -parse_seqids";
					
					
					System.out.println("nuevo path: " + formatDB);
					rt = Runtime.getRuntime();
					Process p = rt.exec(formatDB);
					p.waitFor();
					Thread.sleep(5000L);
					String outputFile = this.fastaProt.getName() + "_vs_" + this.fastaDNA.getName() + ".txt";
					 		
					String blastAllLine ="tblastn -query "+combinedPath+" -db "+dbname+ " -out "+ 
							blastPath + File.separator+outputFile +	" -num_alignments 1 -evalue 1e"+ this.spinner.toString() + " -seg no";					
									
					System.out.println(blastAllLine);
					rt = Runtime.getRuntime();
					p = rt.exec(blastAllLine);
					p.waitFor();					
					
					String deleteFiles = "sh " +e + File.separator +"cleanAfterBlast.sh";					
					System.out.println(deleteFiles);
					/* String active = "chmod 700 "+ deleteFiles;*/
					rt = Runtime.getRuntime();
					p = rt.exec(deleteFiles);
					p.waitFor();
					//rt = Runtime.getRuntime();
					//p = rt.exec(deleteFiles);
					//p.waitFor();
				}
				JOptionPane.showMessageDialog(this.blastFiles, "preparing blast completed...\n\nPlease press OK",
						"finished ....", 1);
				
			} catch (Exception arg12) {
				System.out.println(arg12);
			}
		}

	}
	//parse blast using a filter
	


}