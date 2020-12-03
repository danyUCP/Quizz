package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BoutonGame extends JButton implements MouseListener
{
	public BoutonGame(String nom)
	{
		super("  " + nom + "  ");
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.setFocusPainted(false);
		this.setFont(new Font("Verdana", Font.PLAIN, 18));
		this.setVerticalTextPosition(SwingConstants.CENTER);
	    this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setBackground(new Color(50, 50, 50));
		this.setForeground(Color.WHITE);
		
		this.addMouseListener(this);
	}

	public void mouseEntered(MouseEvent e) 
	{
		if(this.isEnabled())
			this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	}

	public void mouseExited(MouseEvent e) 
	{
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
