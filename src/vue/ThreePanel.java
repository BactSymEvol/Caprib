package vue;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.AdminDatabases;
import modele.BlastToDB;
import modele.TableToDB;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class ThreePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel dbPanel;
	private JPanel feed;
	private JPanel delete; 
	private File ref2 = new File(".");
	private JFileChooser dlg = new JFileChooser(this.ref2.getAbsolutePath() + File.separator + "project");
	//panel1
	private JTextField tfdatabasename;
	private JTextField featuretablefile;
	private AdminDatabases dbn;
	private TableToDB featureT;
	//panel2
	private JTextField tfsubject;
	private JTextField blastfilteredfile;
	private AdminDatabases admin = new AdminDatabases();
	private JComboBox<String> comboBox;
	private File blastffile;
	private String query;
	private String subject;
	private BlastToDB btodb;	
	//panel3
	private JTextField dbchoosed;
	private JComboBox<String> comboBoxDeldb;
	private JComboBox<String> comboBoxDelOrganism;
	private String dbname;
	public ThreePanel() throws SQLException {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		JScrollPane scroll= new JScrollPane(this);
		scroll.setLayout(new ScrollPaneLayout());
		scroll.setPreferredSize(new Dimension(500, 400));
		
		dbPanel = new JPanel();
		dbPanel.setBounds(5, 11, 260, 275);
		dbPanel.setToolTipText("Here you can creat a new database from featuretable file");
		dbPanel.setBorder(new TitledBorder(null, "New database", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		feed = new JPanel();
		feed.setBounds(270, 11, 260, 275);
		feed.setBorder(new TitledBorder(null, "Feed database", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		delete = new JPanel();
		delete.setBounds(535, 11, 233, 275);
		delete.setBorder(new TitledBorder(null, "delete", TitledBorder.LEADING, TitledBorder.TOP, null, null));		
		dbPanel.setLayout(null);
		
		JLabel lblPleaseEnterAn = new JLabel("Please enter a name for the database:");
		lblPleaseEnterAn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseEnterAn.setBounds(10, 17, 233, 25);
		dbPanel.add(lblPleaseEnterAn);
		
		tfdatabasename = new JTextField();
		tfdatabasename.setToolTipText("It could be the reference organisme name");
		tfdatabasename.setFont(new Font("Dialog", Font.PLAIN, 12));
		tfdatabasename.setBounds(10, 59, 240, 25);
		dbPanel.add(tfdatabasename);
		tfdatabasename.setColumns(10);
		
		JButton btnCreateDb = new JButton("Create database");
		btnCreateDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createDatabase();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}	
			}
		});
		btnCreateDb.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreateDb.setBounds(10, 227, 240, 25);
		dbPanel.add(btnCreateDb);
		
		JLabel lblNewLabel = new JLabel("Please pick the associated table");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 101, 233, 25);
		dbPanel.add(lblNewLabel);
		
		featuretablefile = new JTextField();
		featuretablefile.setFont(new Font("Dialog", Font.PLAIN, 12));
		featuretablefile.setBounds(10, 143, 240, 25);
		dbPanel.add(featuretablefile);
		featuretablefile.setColumns(10);
		
		JButton btnChooseTable = new JButton("Choose Table");
		btnChooseTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnChooseFTActionPerformed() ;
			}
		});
		btnChooseTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnChooseTable.setBounds(10, 185, 240, 25);
		dbPanel.add(btnChooseTable);
		setLayout(null);
		
		add(dbPanel);
		add(feed);
		feed.setLayout(null);
		
		//panel 2		
		JLabel lblSelectADatabase = new JLabel("Select a database to feed ");
		lblSelectADatabase.setToolTipText("Select a database to feed ");
		lblSelectADatabase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectADatabase.setBounds(10, 11, 241, 25);
		feed.add(lblSelectADatabase);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox.setBounds(10, 41, 241, 25);
		fillFromResultset(comboBox);
		
		feed.add(comboBox);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the compared organism");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseEnterThe.setBounds(10, 74, 241, 25);
		feed.add(lblPleaseEnterThe);
		
		tfsubject = new JTextField();
		tfsubject.setColumns(10);
		tfsubject.setBounds(10, 107, 241, 25);
		feed.add(tfsubject);
		
		JLabel label_4 = new JLabel("Choose a blast filtered file");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(10, 140, 241, 25);
		feed.add(label_4);
		
		blastfilteredfile = new JTextField();
		blastfilteredfile.setEditable(false);
		blastfilteredfile.setColumns(10);
		blastfilteredfile.setBounds(10, 173, 241, 25);
		feed.add(blastfilteredfile);
		
		JButton btnChooseBF = new JButton("Choose filtered file");
		btnChooseBF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnChooseFilteredActionPerformed();
			}
		});
		btnChooseBF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnChooseBF.setBounds(10, 206, 241, 25);
		feed.add(btnChooseBF);
		
		JButton btnAppendOrganism = new JButton("Append ");
		btnAppendOrganism.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnChooseAppendOrganismActionPerformed();
				
			}
		});
		btnAppendOrganism.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAppendOrganism.setBounds(10, 239, 241, 25);
		feed.add(btnAppendOrganism);
		add(delete);
		delete.setLayout(null);
		
		JLabel lblPleaseChosseA = new JLabel("Please choose a database to delete");
		lblPleaseChosseA.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseChosseA.setBounds(10, 11, 213, 25);
		delete.add(lblPleaseChosseA);
		
		comboBoxDeldb = new JComboBox<String>();
		fillFromResultset(comboBoxDeldb);
		
		comboBoxDeldb.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBoxDeldb.setBounds(10, 41, 213, 25);		
		delete.add(comboBoxDeldb);
		
		
		
		JButton btnSelectOrganism = new JButton("Set database");
		btnSelectOrganism.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDBname();
			}
		});
		btnSelectOrganism.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSelectOrganism.setBounds(10, 74, 213, 25);
		delete.add(btnSelectOrganism);
		
		dbchoosed = new JTextField();
		dbchoosed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dbchoosed.setEditable(false);
		dbchoosed.setColumns(10);
		dbchoosed.setBounds(10, 107, 213, 25);
		delete.add(dbchoosed);
		
		JButton btnDeleteOrganism1 = new JButton("Delete database");
		btnDeleteOrganism1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog((Component) null, "Do you want to erase this database?");
				if (answer == 0) {
					try {
						admin.deleteDB(dbchoosed.getText());			
						fillFromResultset(comboBox);
						comboBox.updateUI();
						fillFromResultset(comboBoxDeldb);
						comboBoxDeldb.updateUI();	
						dbchoosed.setText("");
					} catch (Exception arg3) {
						arg3.printStackTrace();
					}
				}
			}
		});
		btnDeleteOrganism1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteOrganism1.setBounds(10, 140, 213, 25);		
		delete.add(btnDeleteOrganism1);
		
		JLabel lblPleaseChosseAn = new JLabel("Please choose an organism to delete");
		lblPleaseChosseAn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPleaseChosseAn.setBounds(10, 173, 213, 25);
		delete.add(lblPleaseChosseAn);
		
		comboBoxDelOrganism = new JComboBox<String>();
		comboBoxDelOrganism.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxDelOrganism.setBounds(10, 206, 213, 25);
		fillOrganismFromResultset(comboBoxDelOrganism);
		delete.add(comboBoxDelOrganism);
		
		JButton btnDeleteGenome = new JButton("Delete compared organism");
		btnDeleteGenome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog((Component) null, "Do you want to erase this genome?");
				
				if (answer == 0) {
					try {
						admin.deleteSubject(dbchoosed.getText(), (String)comboBoxDelOrganism.getSelectedItem());// this.comboBoxDeldb.getSelectedItem();			
						fillOrganismFromResultset(comboBoxDelOrganism);
						comboBoxDelOrganism.updateUI();
					} catch (Exception arg3) {
						arg3.printStackTrace();
					}
				}
			}
		});
		btnDeleteGenome.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteGenome.setBounds(10, 239, 213, 25);
		delete.add(btnDeleteGenome);
	}
	
	private void btnChooseFTActionPerformed() {		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Feature table files", new String[]{"ptt", "txt"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			String filename = dlg.getSelectedFile().getName();
			this.featuretablefile.setText(filename);
			File ftable = new File(file);
			this.featureT = new TableToDB(this.getDBname(), ftable);
			System.out.println(file);
		}		
	}
	
	private void createDatabase() throws SQLException{
		int ctrl=0;
		if (!tfdatabasename.getText().isEmpty()){
			String[] words = tfdatabasename.getText().split(" ");
			ctrl=words.length;
		}
		if (!tfdatabasename.getText().isEmpty()){
			String[] words = tfdatabasename.getText().split("_");
			ctrl=words.length;
		}
		if (!tfdatabasename.getText().isEmpty()	&& !featuretablefile.getText().isEmpty()&&ctrl==1) {
			dbn = new AdminDatabases(tfdatabasename.getText());
			dbn.startDB();		
			fillFromResultset(comboBox);
			comboBox.updateUI();
			fillFromResultset(comboBoxDeldb);
			comboBoxDeldb.updateUI();
			try {
				featureT.liret();
			} catch (Exception arg2) {
				arg2.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog((Component) null, "Incorrect name, please use a name without spaces and without the '_' character",
					"warning", 2);
		}
		
	}
	
	private String getDBname() {
		return this.tfdatabasename.getText();
	}

	//panel 2 methods
	private void btnChooseFilteredActionPerformed() {
					
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Filtered blast files", new String[]{"csv"});
		dlg.setFileFilter(filter);
		int option = dlg.showOpenDialog(this);
		if (option == 0) {
			String file = dlg.getSelectedFile().getPath();
			String filename = dlg.getSelectedFile().getName();
			this.blastfilteredfile.setText(filename);
			this.blastffile = new File(file);
			System.out.println(file);				
		}
	}

	private void btnChooseAppendOrganismActionPerformed() {
		this.query = (String) this.comboBox.getSelectedItem();
		this.subject = this.tfsubject.getText();
		this.btodb = new BlastToDB(this.query, this.blastffile, this.subject);
		boolean fillBlastTable=false;
		try {
			fillBlastTable=this.btodb.fillBlastTable();
			System.out.println("archivo bueno: "+fillBlastTable);
			if(fillBlastTable){
				this.btodb.lire();
			JOptionPane.showMessageDialog(this, "The "+tfsubject.getText()+" proteines were transfered to "+ (String) comboBox.getSelectedItem() +" database.\n"+
					"\nPlease press OK to continue", "finished ....", 1);
			}else{
				System.out.println("The filtered file is empty");
				JOptionPane.showMessageDialog((Component) null, "The filtered file is empty or is a wrong file", "Warning", 2);
			}
		} catch (Exception arg1) {
			arg1.printStackTrace();
		}

		System.out.println(this.query + " " + this.subject);
	}
	//panel 3 methods
	private void setDBname() {
		this.dbname = (String) this.comboBoxDeldb.getSelectedItem();
		this.dbchoosed.setText(this.dbname);
		this.dbn = new AdminDatabases(this.dbname);		
		fillOrganismFromResultset(comboBoxDelOrganism);
		comboBoxDelOrganism.updateUI();
		
	}

	private void fillFromResultset(JComboBox<String> comboBox) throws SQLException{
		
		ResultSet db= admin.getResultset();
		if (db!= null){
		
		try {
			comboBox.removeAllItems();
			while(db.next()){
				String database=db.getString(1);
				if (database.contains("caprib_")) {
					String data = database.substring(database.lastIndexOf("_") + 1, database.length());
					comboBox.addItem(data);
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		}
	}
	
	private void fillOrganismFromResultset(JComboBox<String> comboBox){
		comboBox.removeAllItems();
		if (!dbchoosed.getText().isEmpty()){			
		ResultSet db= admin.getResultsetOrganisms(dbchoosed.getText());
		 
		try {
			comboBox.removeAllItems();
			while(db.next()){					
				comboBox.addItem(db.getString(1));
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		}
	}
	
}
