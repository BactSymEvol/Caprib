package vue;

import control.Calculs;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import modele.AdminDatabases;
import modele.GetConnection;
import modele.GroupListModel;
import modele.QueryProteinesDB;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class Groups extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> databases;
	private AdminDatabases admin = new AdminDatabases();
	private ArrayList<String> organisms = new ArrayList<String>();
	private String dbname;
	private JComboBox<String> organismComboBox = new JComboBox<String>();
	private GroupListModel glModel = new GroupListModel();
	private GroupListModel modelA = new GroupListModel();
	private GroupListModel modelB = new GroupListModel();
	
	private Calculs repport;
	
	private JList<String> listAll;
	private JList<String> listGroupA;
	private JList<String> listGroupB;
	private JTextField combination;
	private final JTable table = new JTable();
	private Vector<String> columns = new Vector<String>();
	private Vector<Vector<String>> dataVector= new Vector<Vector<String>>();
	private JScrollPane scrollpaneCSV;
	private DefaultTableModel tableModel;
	private File refpath = new File("");
	private JFileChooser dlg = new JFileChooser(this.refpath.getAbsolutePath() + File.separator + "project");	
	
	public Groups() {            
	  
	  JTabbedPane tabbedPane = new JTabbedPane(1);
	  tabbedPane.setBounds(10, 5,750, 550);
	  JPanel combinations= new JPanel();
	  JPanel results_table= new JPanel();
      tabbedPane.addTab("Combinations",combinations);
      tabbedPane.addTab("Variations table",results_table);
          
      this.setBorder(new LineBorder(new Color(0, 0, 0)));
      this.setLayout((LayoutManager)null);
      combinations.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("Please choose a reference db to begin: ");
      lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      lblNewLabel.setBounds(10, 5, 257, 23);
      combinations.add(lblNewLabel);
      
      this.listAll = new JList<String>();
      this.listGroupA = new JList<String>();
      this.listGroupB = new JList<String>();
      this.databases = this.admin.prepareDBList();
      if(this.databases.isEmpty()) {
         this.organismComboBox.addItem("");
      } else {
         Iterator<String> btnSetOrganism = this.databases.iterator();
         while(btnSetOrganism.hasNext()) {
            String btnReset = (String)btnSetOrganism.next();
            this.organismComboBox.addItem(btnReset);
         }
      }
      organismComboBox.setBounds(270, 5, 136, 23);
      combinations.add(this.organismComboBox);
      
      JButton btnReset1 = new JButton("Reset database");
      btnReset1.setBounds(537, 5, 120, 23);
      btnReset1.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		glModel.clearList();
      		modelA.clearList();
      		modelB.clearList();
      		listAll.repaint();
      		listGroupA.repaint();
      		listGroupB.repaint();
      	}
      });
      combinations.add(btnReset1);
      
  
      
      JButton btnSetOrganism1 = new JButton("Set database");
      btnSetOrganism1.setBounds(410, 5, 120, 23);
      btnSetOrganism1.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		glModel.clearList();
      		setDBname();
      		listAll.setModel(glModel);
      		modelA=new GroupListModel();
      		modelB=new GroupListModel();
      		listGroupA.setModel(modelA);
      		listGroupB.setModel(modelB);      		
      	}
      });
      combinations.add(btnSetOrganism1);
      
      JScrollPane scrollPaneGAll = new JScrollPane(this.listAll, 20, 30);
      scrollPaneGAll.setBounds(10, 30, 200, 450);
      combinations.add(scrollPaneGAll);
      
      JScrollPane scrollPaneGA = new JScrollPane(this.listGroupA, 20, 30);
      scrollPaneGA.setBounds(321, 30, 200, 220);
      combinations.add(scrollPaneGA);
      JScrollPane scrollPaneGB = new JScrollPane(this.listGroupB, 20, 30);
      scrollPaneGB.setBounds(321, 255, 200, 220);
      combinations.add(scrollPaneGB);
      
      
      
      
      //Button togroupA
      JButton btnGroupA = new JButton("To group A");
      btnGroupA.setBounds(215, 110, 100, 25);
      btnGroupA.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		toGroup(modelA);
      	}
      });
      combinations.add(btnGroupA);
      
      //Button take back the items from groupA
      JButton btnfromGroupA = new JButton("<-");
      btnfromGroupA.setBounds(215, 140, 100, 25);
      btnfromGroupA.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		backToList(listGroupA, modelA);
      	}
      });
      combinations.add(btnfromGroupA );
      
      //Button togroupB      
      JButton btnGroupB = new JButton("To group B");
      btnGroupB.setBounds(215, 340, 100, 25);
      btnGroupB.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		toGroup(modelB);
      	}
      });
      combinations.add(btnGroupB);
    //Button take back the items from groupB
      JButton btnfromGroupB = new JButton("<-");
      btnfromGroupB.setBounds(215, 370, 100, 23);
      btnfromGroupB.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		backToList(listGroupB, modelB);
      	}
      });
      combinations.add(btnfromGroupB );      
      //combination buttons
      JLabel lblNewLabel_1 = new JLabel("Identity threshold: ");
      lblNewLabel_1.setBounds(557, 40, 120, 25);
      combinations.add(lblNewLabel_1);         
      JSpinner threshold = new JSpinner();
      threshold.setBounds(557, 70, 50, 25);
      threshold.setModel(new SpinnerNumberModel(60, 0, 100, 1));
      combinations.add(threshold);      
      JButton btnQuery = new JButton("Query");
      btnQuery.setBounds(610, 70, 70, 25);
      btnQuery.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		setCalcul((int)threshold.getValue());
      	}
      });
      combinations.add(btnQuery);      
      JButton btnID = new JButton("I vs D");
      btnID.setBounds(557, 100, 123, 25);
      btnID.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(repport!=null){      			
      			try {
      				boolean b= printRepport(repport.getIvsD(),true);
      				if(b){
      					combination.setText(btnID.getText());   
      				}
      				
      			} catch (SQLException e1) {				
      				e1.printStackTrace();
      			}}	      		
      	}
      });
      combinations.add(btnID);      
      JButton btnISD = new JButton("IS vs D");
      btnISD.setBounds(557, 130, 123, 25);
      btnISD.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(repport!=null){ 
      		try {
      			boolean b= printRepport(repport.getISvsD(), true);				
				
      			if(b){
      				combination.setText(btnISD.getText());
      			}
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}        		
      	}}
      });
      combinations.add(btnISD);
      JButton btnISc = new JButton("Stop codon");
      btnISc.setBounds(557, 250, 123, 25);
      btnISc.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(repport!=null){ 
      		try {
      			boolean b=printRepport(repport.getStopCodon(), true);
				if(b){
					combination.setText(btnISc.getText());				
				}
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}      		
      	}}
      });
      
      combinations.add(btnISc);
      JButton btnIS = new JButton("I vs S");
      btnIS.setBounds(557, 160, 123, 25);
      btnIS.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		if(repport!=null){
      			try {
      				boolean b= printRepport(repport.getIvsS(), true);
      				if(b){
      					combination.setText(btnIS.getText());      				
      				}
      			} catch (SQLException e1) {				
      				e1.printStackTrace();
      			}}       		
			}
      });
      combinations.add(btnIS);
      
      JButton gapsIN = new JButton("Gaps In");
      gapsIN.setBounds(557, 190, 123, 25);
      gapsIN.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		if(repport!=null){
      			try {
      				boolean b=printRepport(repport.getGapIn(), false);
      				if(b){
      					combination.setText(gapsIN.getText());      				
      				}
      			} catch (SQLException e1) {				
      				e1.printStackTrace();
      			}}
      	}
      });
      combinations.add(gapsIN);
      
      
      JButton gapsOut = new JButton("Gaps Out");
      gapsOut.setBounds(559, 220, 118, 25);
      gapsOut.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(repport!=null){
      			try {
      				boolean b=printRepport(repport.getGapOut(),false);
      				if(b){
      					combination.setText(gapsOut.getText());
      				}
      				
      				
      			} catch (SQLException e1) {				
      				e1.printStackTrace();
      			}}			
      	}
      });
      combinations.add(gapsOut);
      
      
      this.combination = new JTextField();
      this.combination.setHorizontalAlignment(0);
      this.combination.setFont(new Font("Tahoma", 1, 13));
      this.combination.setEditable(false);
      this.combination.setBounds(352, 4, 129, 25);
      results_table.add(this.combination);
      this.combination.setColumns(10);
      this.add(tabbedPane);
      results_table.setLayout(null);
      JLabel lblCombination = new JLabel("Combination: ");
      lblCombination.setFont(new Font("Tahoma", 1, 13));
      lblCombination.setBounds(252, 4, 100, 25);
      
      results_table.add(lblCombination);
      this.columns.addElement("Locus Tag");
      this.columns.addElement("Refseq");
      this.columns.addElement("Protein");
      this.columns.addElement("Symbol");
      this.columns.addElement("Aminoacid position");
      
      this.tableModel = new DefaultTableModel(this.dataVector, this.columns);
      this.table.setModel(this.tableModel);      
      ListSelectionModel listSelectionModel = table.getSelectionModel();
      listSelectionModel.addListSelectionListener(new ListSelectionListener(){		
		public void valueChanged(ListSelectionEvent e) {
			if(!listSelectionModel.isSelectionEmpty()){
				table.setCellSelectionEnabled(true);
				int[] selectedRow = table.getSelectedRows();
				int[] selectedColumns = table.getSelectedColumns();
				for (int i = 0; i < selectedRow.length; i++) {
			          for (int j = 0; j < selectedColumns.length; j++) {			            
			          }			          
			        }
			}				
		}
    	  
      });
      
      this.scrollpaneCSV = new JScrollPane(this.table);
      this.scrollpaneCSV.setBounds(10, 40, 700, 459);
      results_table.add(this.scrollpaneCSV);  
      
     
      
   }

	private void setDBname() {
		this.dbname = (String) this.organismComboBox.getSelectedItem();
		new GetConnection(this.dbname);
		this.organisms.clear();
		this.organisms = this.admin.prepareGenomeList(this.dbname);

		for (int i = 0; i < this.organisms.size(); ++i) {
			this.glModel.addElement((String) this.organisms.get(i));
		}

		System.out.println(this.organisms);
	}

	private void toGroup(GroupListModel liste) {
		try{
			Object o = this.listAll.getSelectedValue();		
		
		if (o != null) {
			int pos = this.glModel.getindex(o);
			String organismChoosed = this.glModel.getElementAt(pos);			
			liste.addElement(organismChoosed);
			this.glModel.deleteElement(o);
		}}catch(IndexOutOfBoundsException e){
			System.out.println("list is empty");
		} 		
	}
	private void backToList(JList<String> list,  GroupListModel model ){
		try{
			Object o = list.getSelectedValue();				
			if (o != null) {
				int pos = model.getindex(o);
				String organismChoosed = model.getElementAt(pos);
				this.glModel.addElement(organismChoosed);
				model.deleteElement(o);									
				}
			}catch(IndexOutOfBoundsException e){
				System.out.println("list is empty");
			}
	}
	
	private void addTable(HashMap<String, Set<Integer>>intersection){	
		this.dataVector.clear();
		this.dataVector = this.repport.HashToVector(intersection);
		this.tableModel.setDataVector(this.dataVector, this.columns);
		this.table.setModel(this.tableModel); 
		this.scrollpaneCSV.updateUI();
	}
		
	
	/**
	 * Method to set a Calculs object
	 * @param threshold
	 */
	private void setCalcul(int threshold ){		
		try {
			QueryProteinesDB qpdb=null;
			ResultSet rs= null;
			repport = null;
			qpdb = new QueryProteinesDB(this.modelA.getList(), this.modelB.getList(), this.dbname);
			rs=qpdb.queryProteinsRst(threshold);
			repport = new Calculs(rs, this.modelA.getList(), this.modelB.getList());
			//Print query in results folder
			qpdb.queryProteins();
			//
			JOptionPane.showMessageDialog(this, "The query congigured with %I=" +threshold+" is finished.\n\n	Please select the combination to be performed",
					"Query completed ....", 1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
			
	private boolean printRepport(HashMap<String, Set<Integer>>intersection, boolean no_gap){
		if(repport!= null){			
			int option = dlg.showSaveDialog(this);
			if (option == 0) {
				String output = dlg.getSelectedFile().getPath();
				try {
					repport.getGeneralReport(output, intersection,no_gap);
					addTable(intersection);
					
					
				} catch (IOException e) {				
					e.printStackTrace();
				}return true;
			}else{
				return false;
			}
		}else {
			return false;
		}
		
	}
}
