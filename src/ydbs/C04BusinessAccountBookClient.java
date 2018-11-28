package ydbs;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class C04BusinessAccountBookClient extends JPanel{

	private static final long serialVersionUID = 3L;
	
	public C04BusinessAccountBookClient() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("거래처 화면");
		lblNewLabel.setBounds(138, 169, 130, 15);
		add(lblNewLabel);
		
		setVisible(true);
	}
}














































