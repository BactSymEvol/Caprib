package vue;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PrepareBlastFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private File blastpath;
	private BlastFiles blastFiles;

	
	public PrepareBlastFrame() {
		this.setTitle("CAPRI-Bv2019");
	      setDefaultCloseOperation(2);
	      setBounds(100, 100, 504, 500);
	      contentPane = new JPanel();
	     contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(this.contentPane);
	      contentPane.setLayout(new BorderLayout(0, 0));
	      blastFiles = new BlastFiles();
	      contentPane.add(this.blastFiles, "Center");
		
		
	}
	public void setPath(File blastPath) {
		this.blastpath = blastPath;
		this.blastFiles.setPath(blastPath);
	}

	public File getPath() {
		return this.blastpath;
	}

}
