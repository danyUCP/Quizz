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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import connection.Client;
import data.Joueur;
import data.Partie;
import data.Score;

public class PartiePanel extends JPanel
{
	private JScrollPane scroll;
	private JPanel contenu, header;
	private ArrayList<BoutonPartie> parties;
	private BoutonGame retour;
	private LabelGame joueurLabel;

	
	private Dimension dim;
	private int largeur = 400;
	private int hauteur = 600;
	
	private Client client;
	private Joueur joueur;
	
	public PartiePanel(Joueur joueur, Client client)
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
		
		//-------------- PARTIE CONTENU --------------------//
		contenu = new JPanel();
		contenu.setBackground(new Color(28, 28, 28));
		initContenu();
		
		//-------------- PARTIE SCROLL --------------------//
		scroll = new JScrollPane(contenu);
		scroll.setBackground(new Color(28, 28, 28));
		scroll.setMaximumSize(new Dimension(largeur, hauteur - 40));
		this.add(scroll, BorderLayout.CENTER);
	}
	
	public void initContenu()
	{		
		String reponse = client.envoyerInstruction("GET:infoParties " + joueur.getId());
		
		String donnees[] = reponse.split(":");
		
		if(donnees[0].equals("ERREUR"))
		{
			JOptionPane.showMessageDialog(null, donnees[1]);
			if(donnees[1].contentEquals("La connexion au serveur a été interrompue !") || donnees[1].equals("Aucune connexion avec le serveur detectée !"))
				fermer();
		}
		else if(donnees[0].equals("OK"))
		{
			parties = new ArrayList<BoutonPartie>();
			
			String donneesParties[] = donnees[1].split(";");
			//JOptionPane.showMessageDialog(null, donneesParties.length);
			
			int nbPartie = donneesParties.length / 6;
			
			for(int i = 0 ; i < nbPartie * 6 ; i += 6)
			{
				int idPartie = Integer.parseInt(donneesParties[i]);
				Joueur j1 = new Joueur(donneesParties[i + 1]);
				Joueur j2 = new Joueur(donneesParties[i + 2]);
				Score scorePartie = new Score(Integer.parseInt(donneesParties[i + 3]), Integer.parseInt(donneesParties[i + 4]));
				int mancheActuelle = Integer.parseInt(donneesParties[i + 5]);
				
				if(j1.getPseudo().equals(""))
					j1.setPseudo("Pas d'adversaire");
				if(j2.getPseudo().equals(""))
					j2.setPseudo("Pas d'adversaire");
					
				Partie p = new Partie(idPartie, j1, j2, scorePartie, mancheActuelle);
				//System.out.println(p);
				BoutonPartie b = new BoutonPartie(p);
				b.addActionListener(new PartieListener());
				parties.add(b);
			}
			
			contenu.setLayout(new GridLayout(10, 1));
			contenu.setMaximumSize(new Dimension(largeur, 10 * 40));

			for(int i = 0 ; i < parties.size() ; i++)
				contenu.add(parties.get(i));
			
			this.revalidate();
		}		
	}
	
	private class BoutonPartie extends BoutonGame
	{
		private Partie partie;

		public BoutonPartie(Partie partie) 
		{
			super("Partie");

			this.partie = partie;
			this.setSize(300, 40);
			this.setText(partie.getJ1() + " " + partie.getScorePartie() + " " + partie.getJ2());

		}

		public Partie getPartie() 
		{
			return partie;
		}


		
	}
	
	private class PartieListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			Partie partieChoisie = ((BoutonPartie)(e.getSource())).getPartie();

			removeAll();
			add(new ManchePanel(joueur, client, partieChoisie));
			revalidate();
		}
	}

	public void initNavigateur()
	{
		header.setLayout(new BorderLayout());

		joueurLabel = new LabelGame("Bonjour " + joueur.getPseudo());
		header.add(joueurLabel, BorderLayout.CENTER);

		retour = new BoutonGame("Retour");
		retour.setFont(new Font("Verdana", Font.PLAIN, 12));
		header.add(retour, BorderLayout.EAST);
		
		retour.addActionListener(new NavListener());
	}
	
	private class NavListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == retour)
				retour();
		}
	}
	
	public void retour()
	{	
		this.removeAll();
		Fenetre.getGlobal().removeAll();
		Fenetre.getGlobal().add(new GamePanel(joueur, client), BorderLayout.CENTER);
		Fenetre.getGlobal().revalidate();
	}
	
	public void fermer()
	{
		Fenetre frame = (Fenetre) (SwingUtilities.getRoot(Fenetre.getGlobal()));
		
		if(!client.estDeconnecte())
			client.envoyerInstruction("DISCONNECT");
		
		this.removeAll();
		frame.resetAccueil();
	}
}
