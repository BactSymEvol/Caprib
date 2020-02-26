package vue;


import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import control.Projects;
import modele.ButtonCloseJTP;


public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private int tabNumber;
	private final JTabbedPane pane = new JTabbedPane();
	private File f;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					new Main("Capri-Bv2019").runTest();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
		public Main(String title) {
			super(title);
			this.setDefaultCloseOperation(3);
			this.initMenu();
			this.getContentPane().add(this.pane);
		}

		public void runTest() {
			this.pane.removeAll();			
			this.setBounds(0, 0, 800,600);			
			this.setLocationRelativeTo((Component) null);
			this.setVisible(true);
		}

		private void initTabComponent(int i) {
			ButtonCloseJTP jtp = new ButtonCloseJTP(this.pane);
			this.pane.setTabComponentAt(i, jtp);
			this.pane.setSize(750, 550);
			this.tabNumber = jtp.getIndex();
		}

		private void initMenu() {
	      JMenuBar menuBar = new JMenuBar();
	      JMenu optionsMenu = new JMenu("File");
	      menuBar.add(optionsMenu);
	      JMenuItem mntmNewProject = new JMenuItem("New Project");
	      mntmNewProject.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		createProject();
	      	}
	      });
	      
	      mntmNewProject.setAccelerator(KeyStroke.getKeyStroke(78, 8));
	      optionsMenu.add(mntmNewProject);
	      
	      JMenuItem mntmOpenProject = new JMenuItem("Open Project");
	      mntmOpenProject.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		openProjectOption();
	      	}
	      });
	     
	      mntmOpenProject.setAccelerator(KeyStroke.getKeyStroke(79, 8));
	      
	      optionsMenu.add(mntmOpenProject);
	      	      
	      this.setJMenuBar(menuBar);
	      
	      JMenu mnDatabase = new JMenu("Database");
	      menuBar.add(mnDatabase);
	      JMenuItem mntmEdit = new JMenuItem("DB Edition");
	      mntmEdit.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		ThreePanel tpdb;
				try {
					tpdb = new ThreePanel();
					tabNumber = pane.getTabCount();//int value 
		      		pane.add("Database", tpdb);	      		
		      		initTabComponent(tabNumber);		      		
		      		pane.requestFocusInWindow();
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}	      		
	      	}
	      });
	     
	      mntmEdit.setAccelerator(KeyStroke.getKeyStroke(69, 8));
	      mnDatabase.add(mntmEdit);
	      
	      JMenuItem mntmProteinQuery = new JMenuItem("Protein Query");
	      mntmProteinQuery.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		SimpleProteinQuery spq=new SimpleProteinQuery();
	      		tabNumber = pane.getTabCount();
	      		pane.add("Protein Query", spq);
	      		initTabComponent(tabNumber);
	      	}
	      });
	      mnDatabase.add(mntmProteinQuery);	      
	      
	      	      
	      JMenu mnOperations = new JMenu("Operations");
	      menuBar.add(mnOperations);
	      JMenuItem mntmCombinations = new JMenuItem("Combinations");
	      mntmCombinations.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		Groups group = new Groups();	      		
	      		tabNumber = pane.getTabCount();
	      		pane.add("Operations", group);
	      		initTabComponent(tabNumber);	      		
	      	}	
	      });
	      mntmCombinations.setAccelerator(KeyStroke.getKeyStroke(67, 8));
	      
	      mnOperations.add(mntmCombinations);
	      
	      JMenuItem mntmCdd = new JMenuItem("CDD NCBI");
	      mntmCdd.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		try {
					CddPanel cdd = new CddPanel();
					tabNumber = pane.getTabCount();
		      		pane.add("Conserved domains", cdd);	      		
		      		initTabComponent(tabNumber);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	      	}
	      });
	      mntmCdd.setAccelerator(KeyStroke.getKeyStroke(73, 8));
	      
	      mnOperations.add(mntmCdd);
	      
	      JMenu mnHelp = new JMenu("Help");
	      menuBar.add(mnHelp);
	      
	      JMenuItem mntmTutorial = new JMenuItem("Tutorial");
	      mntmTutorial.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		File f=new File("");
	      		try {
	      			File path=new File(f.getCanonicalPath()+File.separator+"tutorial"+File.separator+"index.html");
		      		
	      			URI e1 = path.toURI();
	    			
	    			Desktop dt = null;
	    			if (Desktop.isDesktopSupported()) {
	    				dt = Desktop.getDesktop();
	    			}

	    			if (dt != null) {
	    				dt.browse(e1);
	    			}
	    		} catch (IOException arg2) {
	    			arg2.printStackTrace();
	    			JOptionPane.showMessageDialog((Component) null, "Wrong URL", "Error", 0);
	    		}

	    	}
	      	
	      });
	      mnHelp.add(mntmTutorial);
	   }

		private void createProject() {
			String projectName = JOptionPane.showInputDialog("Please write a name for your project");
			if (projectName != null) {
				this.f = new File("project" + File.separator + projectName);
				if (projectName.isEmpty()) {
					JOptionPane.showMessageDialog((Component) null, "you must write a name for your project", "Warning", 2);
				} else if (!this.f.exists()) {
					Projects organisme = new Projects(projectName);

					try {
						PrepareBlastFrame e1 = new PrepareBlastFrame();
						e1.setPath(new File(organisme.openProject()));
						e1.setVisible(true);
					} catch (IOException arg3) {
						arg3.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog((Component) null, "this project really exist", "Warning", 2);
				}
			} else {
				JOptionPane.showMessageDialog((Component) null, "you didn\'t create a new project", "Warning", 2);
			}

		}

		private void openProjectOption() {
			File ref = new File("");
			JFileChooser dlg = new JFileChooser(ref.getAbsolutePath() + File.separator + "project");
			dlg.setFileSelectionMode(1);
			dlg.isDirectorySelectionEnabled();
			int option = dlg.showOpenDialog(this);
			if (option == 0) {
				File file = dlg.getSelectedFile();
				PrepareBlastFrame frame = new PrepareBlastFrame();
				frame.setPath(file);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(2);
			} else {
				JOptionPane.showMessageDialog((Component) null, "You haven\'t chosen a project", "Warning", 2);
			}

		}
	}



