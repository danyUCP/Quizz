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

import connection.SocketClient;
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
	
	private SocketClient client;
	private Joueur joueur;
	
	public PartiePanel(Joueur joueur, SocketClient client)
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
		//contenu.setPreferredSize(new Dimension(largeur, hauteur - 40));
		initContenu();
		
		//-------------- PARTIE SCROLL --------------------//
		scroll = new JScrollPane(contenu);
		scroll.setBackground(new Color(28, 28, 28));
		//scroll.setPreferredSize(new Dimension(largeur, hauteur - 40));
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
				parties.add(b);
			}
			
			contenu.setLayout(new GridLayout(10, 1));
			contenu.setMaximumSize(new Dimension(largeur, 10 * 40));

			for(int i = 0 ; i < parties.size() ; i++)
				contenu.add(parties.get(i));
			
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
	
	private class MenuListener implements ActionListener
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
		Fenetre.getGlobal().add(new GamePanel(joueur, client), BorderLayout.CENTER);
		Fenetre.getGlobal().revalidate();
	}
}
