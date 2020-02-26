package vue;

import control.CddNcbi;
import control.ReadMerged;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CddPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField combinedfile;
	private CddNcbi cddncbi;
	private ButtonGroup group = new ButtonGroup();
	private String dataMode = "rep";
	private JRadioButton concise;
	private JRadioButton standard;
	private JRadioButton full;
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModelF;
	private JTable cddTable = new JTable();
	private JTable cddFTable = new JTable();
	private JScrollPane scrollpaneMerged;
	private JScrollPane scrollpaneMergedF;
	private Vector<Vector<String>> dataVector;
	private Vector<Vector<String>> dataVectorF;
	private Vector<String> columns;
	private Vector<String> columns2;
	private JTextField mergeField;
	private File ref=new File("");
	private JTextField loadDomains;
	private JTextField loadFeatures;
	
	public CddPanel() throws IOException {	  
      this.setBorder(new TitledBorder((Border)null, "CDD - NCBI", 4, 2, (Font)null, (Color)null));
      
      this.setSize(750, 500);
      JScrollPane masterScroll=new JScrollPane(this);
      
      masterScroll.setLayout(new ScrollPaneLayout());      
      
      JPanel panel = new JPanel();
      panel.setBounds(10, 20, 230, 370);
      panel.setBorder(new TitledBorder(null, "New Analysis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      panel.setLayout((LayoutManager)null);
      JLabel lblPleaseChooseA = new JLabel("Please choose a file");
      lblPleaseChooseA.setFont(new Font("Tahoma", 0, 12));
      lblPleaseChooseA.setBounds(10, 15, 152, 18);
      panel.add(lblPleaseChooseA);
      this.combinedfile = new JTextField();
      this.combinedfile.setFont(new Font("Tahoma", 0, 12));
      this.combinedfile.setBounds(10, 40, 200, 25);
      panel.add(this.combinedfile);
      this.combinedfile.setColumns(10);
      JButton btnOpen = new JButton("Choose file");
      btnOpen.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		combinedfile.setText(btnChooseActionPerformed());
      	}
      });
      btnOpen.setFont(new Font("Tahoma", 0, 12));
      btnOpen.setBounds(100, 79, 110, 25);
      panel.add(btnOpen);
      JLabel lblNewLabel = new JLabel("Data mode");
      lblNewLabel.setFont(new Font("Tahoma", 0, 12));
      lblNewLabel.setBounds(10, 122, 85, 14);
      panel.add(lblNewLabel);
      this.concise = new JRadioButton("Concise", true);
      this.concise.setBounds(128, 122, 80, 25);
      this.concise.setActionCommand("Concise");
      this.standard = new JRadioButton("Standard");
      this.standard.setBounds(128, 148, 80, 23);
      this.standard.setActionCommand("Standard");
      this.full = new JRadioButton("Full");
      this.full.setBounds(128, 174, 80, 23);
      this.full.setActionCommand("Full");
      this.group.add(this.concise);
      this.group.add(this.standard);
      this.group.add(this.full);
      panel.add(this.concise);
      panel.add(this.standard);
      panel.add(this.full);
      this.concise.addActionListener(this);
      this.standard.addActionListener(this);
      this.full.addActionListener(this);      
      
      JButton btnOpenMerged = new JButton("Merge");
      btnOpenMerged.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {  
      		String file=btnChooseActionPerformed();
      		if(file!=null){
      			mergeField.setText(file);
      			cddncbi.mergeFeature(file);      			
	      		dataVectorF=(new ReadMerged(cddncbi.getMergedFeatureFileName())).sortMerged();
      			
	      		tableModelF.setDataVector(dataVectorF, columns2);
	  			cddFTable.setModel(tableModelF);
	  			scrollpaneMergedF.updateUI();
	  			
      		}
      	}
      });
      
      btnOpenMerged.setBounds(100, 340, 110, 25);
      panel.add(btnOpenMerged);
      
      
      JButton btnUrl = new JButton("CDD NCBI");
      btnUrl.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		goToNCBI();
      		btnOpenMerged.setEnabled(true);
      	}
      });
      btnUrl.setEnabled(false);      
      btnUrl.setBounds(10, 250, 104, 25);
      panel.add(btnUrl);
            
      
      JButton btnSearchCDD = new JButton("CDD search ");
      btnSearchCDD.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if (!combinedfile.getText().isEmpty()) {
      			cddncbi = new CddNcbi(combinedfile.getText(),dataMode , combinedfile.getText()+".in");
      			cddncbi.run();      			
      			
      			dataVector= cddncbi.getDataVector();
      			tableModel= new DefaultTableModel(dataVector,columns);
      			cddTable.setModel(tableModel);
      			cddTable.updateUI();
      			btnUrl.setEnabled(true);
      		}
      	}
      });
      btnSearchCDD.setBounds(100, 205, 110, 25);
      panel.add(btnSearchCDD);
      this.columns = new Vector<String>();
      this.columns.addElement("Protein");
      this.columns.addElement("Mutation position");
      this.columns.addElement("Query");
      this.columns.addElement("Hit type");
      this.columns.addElement("PSSM-ID");
      this.columns.addElement("From");
      this.columns.addElement("To");
      this.columns.addElement("E-Value");
      this.columns.addElement("Bitscore");
      this.columns.addElement("Accession");
      this.columns.addElement("Short name");
      this.columns.addElement("Incomplete");
      this.columns.addElement("Superfamily");
      this.dataVector = new Vector<Vector<String>>();
      this.tableModel = new DefaultTableModel(this.dataVector, this.columns);
      this.cddTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      this.cddTable.setModel(this.tableModel);
      
      TableColumnModel columnModel = cddTable.getColumnModel();
      for (int i = 0; i < columnModel.getColumnCount(); i++) {
      columnModel.getColumn(i).setPreferredWidth(50);
      }
      
      
      
      
      ListSelectionModel listSelectionModel = cddTable.getSelectionModel();
      listSelectionModel.addListSelectionListener(new ListSelectionListener(){		
		public void valueChanged(ListSelectionEvent e) {
			@SuppressWarnings("unused")
			String  selectedData= null;
			if(!listSelectionModel.isSelectionEmpty()){
				cddTable.setCellSelectionEnabled(true);
				int[] selectedRow = cddTable.getSelectedRows();
				int[] selectedColumns = cddTable.getSelectedColumns();
				for (int i = 0; i < selectedRow.length; i++) {
			          for (int j = 0; j < selectedColumns.length; j++) {
			            selectedData = (String) cddTable.getValueAt(selectedRow[i], selectedColumns[j]);		            
			          }			          
			    }	
			}				
		}    	  
      });
      
      this.scrollpaneMerged = new JScrollPane(this.cddTable);      
      this.columns2 = new Vector<String>();
      this.columns2.addElement("Protein");
      this.columns2.addElement("Mutation position");
      this.columns2.addElement("Query");
      this.columns2.addElement("Type");
      this.columns2.addElement("Title");
      this.columns2.addElement("coordinates");
      this.columns2.addElement("complete size");
      this.columns2.addElement("mapped size");
      this.columns2.addElement("source domain");
      this.tableModelF = new DefaultTableModel(this.dataVectorF, this.columns2);
      this.cddFTable.setModel(this.tableModelF);
      this.cddFTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      ListSelectionModel listSelectionModelF = cddFTable.getSelectionModel();
      listSelectionModelF.addListSelectionListener(new ListSelectionListener(){		
		public void valueChanged(ListSelectionEvent e) {
			@SuppressWarnings("unused")
			String  selectedData= null;
			if(!listSelectionModelF.isSelectionEmpty()){
				cddFTable.setCellSelectionEnabled(true);
				int[] selectedRow = cddFTable.getSelectedRows();
				int[] selectedColumns = cddFTable.getSelectedColumns();
				for (int i = 0; i < selectedRow.length; i++) {
			          for (int j = 0; j < selectedColumns.length; j++) {
			            selectedData = (String) cddFTable.getValueAt(selectedRow[i], selectedColumns[j]);			            
			          }			          
			    }
			}				
		}
    	  
      });
      this.scrollpaneMergedF = new JScrollPane(this.cddFTable);
      
      JTabbedPane tabbedPane = new JTabbedPane(1);
      tabbedPane.setBounds(250, 10,520, 500);
      tabbedPane.addTab("Domain Hits", (Icon)null, this.scrollpaneMerged, (String)null);
      tabbedPane.setEnabledAt(0, true);
      tabbedPane.add("Features", this.scrollpaneMergedF);
      //Ajouter un nouveau panel pour regarder les mutations et grantham value?
           
      this.setLayout((LayoutManager)null);
      setLayout(null);
      this.add(panel);
      JLabel lblPostAnalyses = new JLabel("Post analysis");
      lblPostAnalyses.setFont(new Font("Tahoma", 0, 12));
      lblPostAnalyses.setBounds(10, 230, 85, 23);
      panel.add(lblPostAnalyses);
      JLabel lblOpenAMerged = new JLabel("Open a feature file:");
      lblOpenAMerged.setFont(new Font("Tahoma", 0, 12));
      lblOpenAMerged.setBounds(10, 285, 200, 25);
      panel.add(lblOpenAMerged);
      
      this.mergeField = new JTextField();
      this.mergeField.setEditable(false);
      this.mergeField.setFont(new Font("Tahoma", 0, 12));
      this.mergeField.setBounds(10, 310, 200, 25);
      panel.add(this.mergeField);
      this.mergeField.setColumns(10);
      
      JPanel showresults = new JPanel();
      showresults.setBorder(new TitledBorder(null, "Load Results", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      showresults.setBounds(10, 390, 230, 120);
      add(showresults);
      showresults.setLayout(null);
      
      loadDomains = new JTextField();
      loadDomains.setBounds(10, 47, 140, 20);
      showresults.add(loadDomains);
      loadDomains.setColumns(10);
      
      loadFeatures = new JTextField();
      loadFeatures.setBounds(10, 92, 140, 20);
      showresults.add(loadFeatures);
      loadFeatures.setColumns(10);
      /**
       * Load merged file to visualize in Caprib
       */
      JButton loadDH = new JButton("Load");
      loadDH.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		
      		String file=btnChooseActionPerformed();
      		if(file !=null){
      		loadDomains.setText(file);
      		dataVector = (new ReadMerged(file)).sortMerged();      		
  			tableModel= new DefaultTableModel(dataVector,columns);
  			cddTable.setModel(tableModel);
  			cddTable.updateUI();
      		}
      	}
      });
      loadDH.setBounds(160, 46, 70, 23);
      showresults.add(loadDH);
      /**
       * Load feature merged to visualize in caprib
       */
      JButton loadF = new JButton("Load");
      loadF.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		
      		String file=btnChooseActionPerformed();
      		if (file!=null){
      			loadFeatures.setText(file);
      			dataVectorF=(new ReadMerged(file)).sortMerged();
      			tableModelF.setDataVector(dataVectorF, columns2);
      			cddFTable.setModel(tableModelF);
      			scrollpaneMergedF.updateUI();
      		}
      	}
      });
      loadF.setBounds(160, 91, 70, 23);
      showresults.add(loadF);
      
      JLabel lblLoadDomainHits = new JLabel("Load domain hits result");
      lblLoadDomainHits.setBounds(10, 22, 194, 14);
      showresults.add(lblLoadDomainHits);
      
      JLabel lblLoadFeatureResult = new JLabel("Load feature result");
      lblLoadFeatureResult.setBounds(10, 67, 250, 23);
      showresults.add(lblLoadFeatureResult);
      
      this.add(tabbedPane);
      
   }

	private String btnChooseActionPerformed() {		
		JFileChooser dlg = new JFileChooser(ref.getAbsolutePath() + File.separator + "project");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Report Files", new String[]{"tsv","csv", "txt"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			new File(dlg.getSelectedFile().getAbsolutePath());
			return file;
		} else {
			JOptionPane.showMessageDialog((Component) null, "You hadn\'t choose any file", "Warning", 2);
			return null;
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.concise) {
			this.dataMode = "rep";
			
		} else if (e.getSource() == this.standard) {
			this.dataMode = "std";
			
		} else if (e.getSource() == this.full) {
			this.dataMode = "full";
			
		}

	}

	private void goToNCBI() {
		try {
			URI e = new URI(
					"https://www.ncbi.nlm.nih.gov/Structure/bwrpsb/bwrpsb.cgi?cdsid=" + this.cddncbi.getCdsid());
			Desktop dt = null;
			if (Desktop.isDesktopSupported()) {
				dt = Desktop.getDesktop();
			}

			if (dt != null) {
				dt.browse(e);
			}
		} catch (URISyntaxException | IOException arg2) {
			arg2.printStackTrace();
			JOptionPane.showMessageDialog((Component) null, "Wrong URL", "Error", 0);
		}

	}
}
