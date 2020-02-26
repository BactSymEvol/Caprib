package vue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import modele.AdminDatabases;
import modele.ResultsetListModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
/**
 * 
 * @author juan_
 *with this class we ask to the database for some protein using the ref seq or the locus tag 
 */
public class SimpleProteinQuery extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField proteinName;
	private AdminDatabases admin = new AdminDatabases();
	private ArrayList<String> databases;
	private ResultSet proteinInfo;
	//private ResultSet ftCount;
	private ResultsetListModel rslm;//info common single protein
	private JLabel lbl;	
	
	private JScrollPane scrollPane;
	
	private JTable table=new JTable();
	
	
	public SimpleProteinQuery() {
		setLayout(null);
		this.setSize(750, 500);
		JLabel lblChooseADatabase = new JLabel("Choose a database:");
		lblChooseADatabase.setBounds(26, 26, 136, 14);
		add(lblChooseADatabase);
		
		JComboBox<String> comboBox = new JComboBox<String>();

		comboBox.setBounds(26, 51, 147, 20);
		add(comboBox);
		
		this.databases = this.admin.prepareDBList();
		if(this.databases.isEmpty()) {
	         comboBox.addItem("");
	      } else {
	         Iterator<String> btnSetOrganism = this.databases.iterator();

	         while(btnSetOrganism.hasNext()) {
	            String btnReset = (String)btnSetOrganism.next();
	            comboBox.addItem(btnReset);
	            
	         }
	      }		
		proteinName = new JTextField();
		proteinName.setColumns(10);
		proteinName.setBounds(182, 51, 153, 20);
		add(proteinName);		
		JLabel lblPlease = new JLabel("Please enter the protein name:");
		lblPlease.setBounds(172, 26, 259, 14);
		add(lblPlease);		
		lbl = new JLabel("Proteins in the database: 0");
		lbl.setBounds(26, 80, 194, 14);
		add(lbl);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(26, 99, 680, 340);
		scrollPane.setVisible(true);
		add(scrollPane);
		
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String dbChoosed=(String)comboBox.getSelectedItem();
				
				if(!proteinName.getText().isEmpty()){
					proteinInfo=admin.getProteinInfo(dbChoosed, proteinName.getText());				
					rslm =new ResultsetListModel(proteinInfo);					
					table.setModel(rslm);					
					admin.getProteinsCount(dbChoosed);					
					scrollPane.updateUI();
					
					lbl.setText("Proteins in the database: "+ rslm.getRowCount());
			}}
		});
		
		
		
		
		btnSearch.setBounds(339, 50, 101, 23);
	
		add(btnSearch);
		
		
	}
}
