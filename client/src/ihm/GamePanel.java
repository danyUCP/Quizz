package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import connection.Client;
import data.Joueur;
import data.Partie;
import data.Score;

public class GamePanel extends JPanel
{
	private JPanel contenu, menu, header;
	private BoutonGame nouvellePartie, continuerPartie, adversaireAlea;
	private BoutonGame deconnecter;
	private LabelGame joueurLabel;
	
	private Dimension dim;
	private int largeur = 400;
	private int hauteur = 600;
	
	private Client client;
	private Joueur joueur;
	
	public GamePanel(Joueur joueur, Client client)
	{
		this.joueur = joueur;
		this.client = client;
		
		this.dim = new Dimension(largeur, hauteur);
		this.setPreferredSize(dim);
		this.setLayout(new BorderLayout());

		//-------------- PARTIE HEADER ------------------//
		header = new JPanel();
		header.setBackground(new Color(28, 28, 28));
		header.setPreferredSize(new Dimension(largeur, 40));
		header.setLayout(new FlowLayout());
		initNavigateur();
		this.add(header, BorderLayout.NORTH);

		//-------------- PARTIE MENU --------------------//
		menu = new JPanel();
		menu.setBackground(new Color(28, 28, 28));
		menu.setPreferredSize(new Dimension(largeur, hauteur - 40));
		menu.setLayout(new FlowLayout());
		initMenu();
		this.add(menu, BorderLayout.CENTER);

	}
	
	public void initMenu()
	{
		menu.setLayout(new GridLayout(3, 1, 0, 40));
		nouvellePartie = new BoutonGame("Nouvelle Partie");
		continuerPartie = new BoutonGame("Continuer Partie");
		adversaireAlea = new BoutonGame("Adversaire Aléatoire");
				
		menu.add(nouvellePartie);
		menu.add(continuerPartie);
		menu.add(adversaireAlea);	
		
		nouvellePartie.addActionListener(new MenuListener());
		continuerPartie.addActionListener(new MenuListener());
		adversaireAlea.addActionListener(new MenuListener());
		
	}
	
	private class MenuListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			removeAll();
			
			if(e.getSource() == nouvellePartie)
			{
				String reponse = client.envoyerInstruction("BEGIN:newPartie " + joueur.getId());
				String donnees[] = reponse.split(":");
				
				if(donnees[0].equals("ERREUR"))
				{
					JOptionPane.showMessageDialog(null, donnees[1]);
				}
				else if(donnees[0].equals("OK"))
				{
					String donneesParties[] = donnees[1].split(";");

					int idPartie = Integer.parseInt(donneesParties[0]);
					add(new ChoixThemePanel(joueur, client, idPartie));
				}
			}
			else if(e.getSource() == continuerPartie)
				add(new PartiePanel(joueur, client));
			else if(e.getSource() == adversaireAlea)
			{
				String reponse = client.envoyerInstruction("GET:adversaireAlea " + joueur.getId());
				String donnees[] = reponse.split(":");
				
				if(donnees[0].equals("ERREUR"))
				{
					JOptionPane.showMessageDialog(null, donnees[1]);
				}
				else if(donnees[0].equals("OK"))
				{
					String donneesParties[] = donnees[1].split(";");

					int idPartie = Integer.parseInt(donneesParties[0]);
					Joueur j1 = new Joueur(donneesParties[1]);
					Joueur j2 = new Joueur(donneesParties[2]);
					Score scorePartie = new Score(Integer.parseInt(donneesParties[3]), Integer.parseInt(donneesParties[4]));
					int mancheActuelle = Integer.parseInt(donneesParties[5]);
					
					if(j1.getPseudo().equals(""))
						j1.setPseudo("Pas d'adversaire");
					if(j2.getPseudo().equals(""))
						j2.setPseudo("Pas d'adversaire");
						
					Partie p = new Partie(idPartie, j1, j2, scorePartie, mancheActuelle);
					
					
					add(new ManchePanel(joueur, client, p));
				}
			}
			

			revalidate();
			repaint();			
		}
	}
	
	public void initNavigateur()
	{
		header.setLayout(new BorderLayout());

		joueurLabel = new LabelGame("Bonjour " + joueur.getPseudo());
		header.add(joueurLabel, BorderLayout.CENTER);

		deconnecter = new BoutonGame("Déconnexion");
		deconnecter.setFont(new Font("Verdana", Font.PLAIN, 12));
		header.add(deconnecter, BorderLayout.EAST);
		
		deconnecter.addActionListener(new NavListener());
	}
	
	private class NavListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == deconnecter)
				fermer();
		}
	}
	
	public void fermer()
	{
		Fenetre frame = (Fenetre) (SwingUtilities.getRoot(this));
		
		client.envoyerInstruction("DISCONNECT");
		
		this.removeAll();
		frame.resetAccueil();
	}


}
