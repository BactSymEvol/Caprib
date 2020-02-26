package vue;

import control.ParseBlast;
import control.PrepareBlastFiles;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class BlastFiles extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField protFastaTF;
	private JTextField DNAfastaTF;
	private JTextField featureTableTF;
	private File pathProject;
	private JSpinner spinner;
	private PrepareBlastFiles pbf;
	private File ref = new File("");
	private JFileChooser dlg = new JFileChooser(this.ref.getAbsolutePath() + File.separator + "project");
	private JTextField blastfilepath;
	private String blastFileName;
	private String filteredfile;
	private JSpinner identityLimit;
	
	public BlastFiles() {
		this.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Prepare files", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
	      
	      JLabel lblNewLabel = new JLabel("Please insert protein fasta file (reference)");
	      lblNewLabel.setBounds(6, 18, 434, 34);
	      this.protFastaTF = new JTextField();
	      protFastaTF.setBounds(6, 63, 219, 25);
	      this.protFastaTF.setColumns(10);
	      JButton btnProtFasta = new JButton("Choose query fasta file");
	      btnProtFasta.setBounds(225, 63, 219, 25);
	      btnProtFasta.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		protFastaTF.setText(btnChooseActionPerformed());
	      	}
	      });
	      JLabel lblPleaseInsertFuture = new JLabel("Please insert feature table (reference)");
	      lblPleaseInsertFuture.setBounds(6, 86, 434, 34);
	      this.featureTableTF = new JTextField();
	      featureTableTF.setBounds(6, 129, 219, 25);
	      this.featureTableTF.setColumns(10);
	      JButton btnOpenFeatureTable = new JButton("Choose feature table");
	      btnOpenFeatureTable.setBounds(225, 129, 219, 25);
	      btnOpenFeatureTable.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		featureTableTF.setText(btnChooseActionPerformed2());
	      	}
	      });
	      JLabel lblNewLabel_1 = new JLabel("Please insert DNA fasta file (subject) ");
	      lblNewLabel_1.setBounds(6, 154, 434, 34);
	      this.DNAfastaTF = new JTextField();
	      DNAfastaTF.setBounds(6, 199, 219, 25);
	      this.DNAfastaTF.setColumns(10);
	      JButton btnDNAFasta = new JButton("Choose subject fasta file");
	      btnDNAFasta.setBounds(225, 199, 219, 25);
	      btnDNAFasta.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		DNAfastaTF.setText(btnChooseActionPerformed());
	      	}
	      });
	      JLabel lblNewLabel_2 = new JLabel("Please choose expect value for blast ");
	      lblNewLabel_2.setBounds(6, 222, 219, 34);
	      this.spinner = new JSpinner();
	      spinner.setModel(new SpinnerNumberModel(new Integer(-10), null, null, new Integer(1)));
	      spinner.setBounds(225, 231, 219, 25);
	      JButton btnNewButton_2 = new JButton("Blast");
	      btnNewButton_2.setBounds(225, 264, 219, 25);
	      btnNewButton_2.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		btnPrepareFilesActionPerformed();
	      	}
	      });
	      setLayout(null);
	      this.add(lblNewLabel);
	      this.add(this.protFastaTF);
	      this.add(btnProtFasta);
	      this.add(lblPleaseInsertFuture);
	      this.add(this.featureTableTF);
	      this.add(btnOpenFeatureTable);
	      this.add(lblNewLabel_1);
	      this.add(this.DNAfastaTF);
	      this.add(btnDNAFasta);
	      this.add(lblNewLabel_2);
	      this.add(this.spinner);
	      this.add(btnNewButton_2);
	      
	      JPanel filterPanel = new JPanel();
	      filterPanel.setBorder(new TitledBorder(null, "Filter Blast", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	      filterPanel.setBounds(6, 300, 438, 113);
	      add(filterPanel);
	      filterPanel.setLayout(null);
	      
	      JLabel label = new JLabel("Please choose blast to filter");
	      label.setBounds(10, 15, 213, 16);
	      label.setFont(new Font("Dialog", Font.PLAIN, 12));
	      filterPanel.add(label);
	      
	      blastfilepath = new JTextField();
	      blastfilepath.setEditable(false);
	      blastfilepath.setBounds(10, 35, 213, 25);
	      filterPanel.add(blastfilepath);
	      blastfilepath.setColumns(10);
	      
	      JButton button = new JButton("Choose Blast");
	      button.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		btnChooseBlastActionPerformed();
	      	}
	      });
	      button.setFont(new Font("Dialog", Font.PLAIN, 12));
	      button.setBounds(225, 35, 203, 25);
	      filterPanel.add(button);
	      
	      JLabel lblPleaseChooseA = new JLabel("Please choose a identity threshold");
	      lblPleaseChooseA.setFont(new Font("Dialog", Font.PLAIN, 12));
	      lblPleaseChooseA.setBounds(10, 63, 213, 16);
	      filterPanel.add(lblPleaseChooseA);
	      
	      identityLimit = new JSpinner();
	      identityLimit.setModel(new SpinnerNumberModel(60, 0, 100, 1));
	      identityLimit.setFont(new Font("Dialog", Font.PLAIN, 12));
	      identityLimit.setBounds(10, 82, 213, 25);
	      filterPanel.add(identityLimit);
	      
	      JButton btnNewButton = new JButton("Filter");
	      btnNewButton.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		
	      		parseTheBlast();
	      	}
	      });
	      btnNewButton.setBounds(225, 84, 203, 23);
	      filterPanel.add(btnNewButton);
	}
	private void parseTheBlast() {
		
		if(!blastfilepath.getText().isEmpty()){
			this.filteredfile = pathProject+File.separator+"Filtered"+File.separator+blastFileName+this.identityLimit.getValue().toString()+".csv";
			
			ParseBlast parse = new ParseBlast(new File(blastfilepath.getText()), new File(this.filteredfile),
					this.identityLimit.getValue());
			parse.run();
			blastfilepath.setText("");
		}else{
			JOptionPane.showMessageDialog((Component) null, "you must choose a blast file", "Warning", 2);
		}
		
		
	}
	public void setPath(File pathProject) {
		this.pathProject = pathProject;
	}

	private String btnChooseActionPerformed() {
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fasta files",
				new String[]{"faa", "fna", "fasta", "dna", "fa"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			return file;
		} else {
			JOptionPane.showMessageDialog((Component) null, "You hadn\'t choose any file", "Warning", 2);
			return null;
		}
	}

	private String btnChooseActionPerformed2() {		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Feature table files", new String[]{"ptt", "txt"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			return file;
		} else {
			JOptionPane.showMessageDialog((Component) null, "You hadn\'t choose any file", "Warning", 2);
			return null;
		}
	}

	private void btnPrepareFilesActionPerformed() {
		this.pbf = new PrepareBlastFiles(this.pathProject, new File(this.featureTableTF.getText()),
				new File(this.protFastaTF.getText()), new File(this.DNAfastaTF.getText()), this.spinner.getValue(),
				this);
		this.pbf.prepareFiles();
	}
	
	private void btnChooseBlastActionPerformed() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("blast files", new String[]{"txt"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			this.blastfilepath.setText(file);
			blastFileName=dlg.getSelectedFile().getName();			
		}

	}
}
