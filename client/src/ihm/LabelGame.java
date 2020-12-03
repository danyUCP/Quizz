package ihm;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelGame extends JLabel
{
	private String texte;
	
	public LabelGame(String texte)
	{
		super(texte);
		
		this.texte = texte;
		

		this.setFont(new Font("Verdana", Font.PLAIN, 16));
		this.setBackground(new Color(50, 50, 50));
		this.setForeground(Color.WHITE);
		this.setVerticalAlignment(SwingConstants.CENTER);
	    this.setHorizontalAlignment(SwingConstants.CENTER);

	}

}
