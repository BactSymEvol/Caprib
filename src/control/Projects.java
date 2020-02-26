package control;

import java.io.File;
import java.io.IOException;
/**
 * This class helps to create and set the folder for each project
 * @author juan_
 *
 */
public class Projects {
	private File organism;
	private File fasta;
	private File blast;
	private File filtered;
	private File results;
	/**
	 * Constructor
	 * @param organism
	 */
	public Projects(String organism) {
		this.organism = new File("project" + File.separator + organism);
		this.organism.mkdir();
		this.fasta = new File("project" + File.separator + organism + File.separator + "Fasta");
		this.fasta.mkdir();
		this.blast = new File("project" + File.separator + organism + File.separator + "Blast");
		this.blast.mkdir();
		this.filtered = new File("project" + File.separator + organism + File.separator + "Filtered");
		this.filtered.mkdir();
		this.results = new File("project" + File.separator + organism + File.separator + "Results");
		this.results.mkdir();
	}
	
	public String openProject() throws IOException {
		return this.organism.getCanonicalPath();
	}

	public File getFasta() {
		return this.fasta.getAbsoluteFile();
	}

	public File getBLast() {
		return this.blast.getAbsoluteFile();
	}

	public File getFiltered() {
		return this.filtered.getAbsoluteFile();
	}

	public File getResults() {
		return this.results.getAbsoluteFile();
	}

}