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

import connection.Client;
import data.Joueur;
import data.Manche;
import data.Partie;
import data.Score;
import data.Theme;

public class ManchePanel extends JPanel
{
	private JScrollPane scroll;
	private JPanel contenu, header, footer;
	private ArrayList<LabelManche> manches;
	private BoutonGame retour, commencer;
	private LabelGame joueurLabel;

	
	private Dimension dim;
	private int largeur = 400;
	private int hauteur = 600;
	
	private Client client;
	private Joueur joueur;
	private Partie partie;
	
	public ManchePanel(Joueur joueur, Client client, Partie partie)
	{
		this.joueur = joueur;
		this.client = client;
		this.partie = partie;
		
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
		//contenu.setPreferredSize(new Dimension(largeur, hauteur - 40));
		initContenu();
		
		//-------------- PARTIE SCROLL --------------------//
		scroll = new JScrollPane(contenu);
		scroll.setBackground(new Color(28, 28, 28));
		//scroll.setPreferredSize(new Dimension(largeur, hauteur - 40));
		scroll.setMaximumSize(new Dimension(largeur, hauteur - 40));
		this.add(scroll, BorderLayout.CENTER);
		
		//-------------- PARTIE FOOTER ------------------//
		footer = new JPanel();
		footer.setBackground(new Color(28, 28, 28));
		footer.setPreferredSize(new Dimension(largeur, 40));
		footer.setLayout(new FlowLayout());
		commencer = new BoutonGame("Commencer");
		footer.add(commencer);
		this.add(footer, BorderLayout.SOUTH);
	}
	
	public void initContenu()
	{		
		String reponse = client.envoyerInstruction("GET:infoManches " + partie.getId());
		
		String donnees[] = reponse.split(":");
		
		if(donnees[0].equals("ERREUR"))
		{
			JOptionPane.showMessageDialog(null, donnees[1]);
		}
		else if(donnees[0].equals("OK"))
		{
			manches = new ArrayList<LabelManche>();
			
			String donneesParties[] = donnees[1].split(";");
			//JOptionPane.showMessageDialog(null, donneesParties.length);
			
			int nbManche = donneesParties.length / 6;
			
			for(int i = 0 ; i < nbManche * 6 ; i += 6)
			{
				int idManche = Integer.parseInt(donneesParties[i]);
				Joueur j1 = partie.getJ1();
				Joueur j2 = partie.getJ2();
				Theme theme = new Theme(donneesParties[i + 1]);
				Score scoreManche = new Score(Integer.parseInt(donneesParties[i + 2]), Integer.parseInt(donneesParties[i + 3]));
				int numManche = Integer.parseInt(donneesParties[i + 4]);
				boolean estTerminee = Boolean.parseBoolean(donneesParties[i + 5]);
				
				if(j1.getPseudo().equals(""))
					j1.setPseudo("Pas d'adversaire");
				if(j2.getPseudo().equals(""))
					j2.setPseudo("Pas d'adversaire");
					
				Manche m = new Manche(idManche, j1, j2, scoreManche, theme, numManche, estTerminee);
				//System.out.println(p);
				LabelManche b = new LabelManche(m);
				manches.add(b);
			}
			
			contenu.setLayout(new GridLayout(6, 1));
			contenu.setMaximumSize(new Dimension(largeur, 6 * 40));

			for(int i = 0 ; i < manches.size() ; i++)
				contenu.add(manches.get(i));
			
			this.revalidate();

		}
		/*
		nouvellePartie = new BoutonGame("Nouvelle Partie");
		continuerPartie = new BoutonGame("Continuer Partie");
		adversaireAlea = new BoutonGame("Adversaire Aléatoire");
				
		contenu.add(nouvellePartie);
		contenu.add(continuerPartie);
		contenu.add(adversaireAlea);	
		
		nouvellePartie.addActionListener(new MenuListener());
		continuerPartie.addActionListener(new MenuListener());
		adversaireAlea.addActionListener(new MenuListener());
		*/
		
	}
	
	private class LabelManche extends LabelGame
	{
		private Manche manche;

		public LabelManche(Manche manche) 
		{
			super("Partie");

			this.manche = manche;
			this.setSize(300, 40);
			this.setText("Manche " + manche.getNumeroManche() + " : " + manche.getTheme().getNom() + "         " + manche.getScoreManche());

		}

		public Manche getManche() 
		{
			return manche;
		}


		
	}
	
	private class MancheListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			removeAll();
			/*
			if(e.getSource() == nouvellePartie)
				add(new PartiePanel(joueur, client));
			else if(e.getSource() == continuerPartie)
				add(new PartiePanel(joueur, client));
			else if(e.getSource() == adversaireAlea)
				add(new PartiePanel(joueur, client));
			*/

			revalidate();
			repaint();			
		}
	}

	public void initNavigateur()
	{
		//header.setLayout(new GridLayout(1, 3, 0, 3));
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
				
			//contenu.revalidate();
			//contenu.repaint();
		}
	}
	
	public void retour()
	{	
		this.removeAll();
		Fenetre.getGlobal().removeAll();
		Fenetre.getGlobal().add(new PartiePanel(joueur, client), BorderLayout.CENTER);
		Fenetre.getGlobal().revalidate();
	}
}
