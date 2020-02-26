package vue;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class JoinProteins extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public JoinProteins() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("How many reference organisms do you want to analyse?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 326, 14);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(354, 9, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(10, 56, 46, 14);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(114, 53, 222, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(361, 52, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(10, 102, 89, 23);
		add(btnNewButton_1);

	}
}
