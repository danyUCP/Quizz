package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import connection.Client;
import data.Joueur;

public class Fenetre extends JFrame
{
	private static JPanel global;
	private JPanel accueil, header, connexion;
	private JPanel login, log1, log2, controles;

	private LabelGame titre, bienvenue, pseudoLabel, mdpLabel;
	private JTextField pseudo, mdp;
	private Image logo;

	private BoutonGame connecter, creer;
	
	private int largeur = 400;
	private int hauteur = 600;
	
	private Client client;
	
	
	public Fenetre()
	{
		this.setTitle("Quizzz");
		this.setSize(largeur, hauteur);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		
		//-------------- CONTENT PANE ------------------//
		global = new JPanel();
	    global.setPreferredSize(new Dimension(largeur, hauteur));
		global.setBackground(Color.CYAN);
		global.setLayout(new BorderLayout());
		this.setContentPane(global);
		
		
		//-------------- PAGE D'ACCUEIL ------------------//
		accueil = new JPanel();
		accueil.setBackground(new Color(28, 28, 28));
		accueil.setLayout(new GridLayout(3, 1));
		
		header = new JPanel();
		header.setBackground(new Color(28, 28, 28));
		header.setLayout(new BorderLayout());
		titre = new LabelGame("QUIZZZ");
		titre.setFont(new Font("Verdana", Font.BOLD, 36));
		header.add(titre, BorderLayout.CENTER);
		accueil.add(header);
		
		connexion = new JPanel();
		connexion.setBackground(new Color(28, 28, 28));
		connexion.setLayout(new GridLayout(3, 1));		
		


		pseudoLabel = new LabelGame("Pseudo : ");		
		pseudo = new JTextField(25);
		mdpLabel = new LabelGame("Mot de passe : ");		
		mdp = new JTextField(20);

		log1 = new JPanel();
		log1.setBackground(new Color(28, 28, 28));
		log1.add(pseudoLabel);
		log1.add(pseudo);
		log2 = new JPanel();
		log2.setBackground(new Color(28, 28, 28));
		log2.add(mdpLabel);
		log2.add(mdp);

		controles = new JPanel();
		controles.setBackground(new Color(28, 28, 28));
		controles.setLayout(new FlowLayout());
		
		connecter = new BoutonGame("Se connecter");
		creer = new BoutonGame("Créer le compte");		
		controles.add(connecter);
		controles.add(creer);
		
		connexion.add(log1);
		connexion.add(log2);
		connexion.add(controles, BorderLayout.SOUTH);
		accueil.add(connexion);
		
		
		global.add(accueil, BorderLayout.CENTER);
		
		
		connecter.addActionListener(new BoutonListener());
		creer.addActionListener(new BoutonListener());
		
		
		this.setVisible(true);
	}
	
	public void resetAccueil()
	{
		global.removeAll();

		//-------------- PAGE D'ACCUEIL ------------------//
		accueil = new JPanel();
		accueil.setBackground(new Color(28, 28, 28));
		accueil.setLayout(new GridLayout(3, 1));
		
		header = new JPanel();
		header.setBackground(new Color(28, 28, 28));
		header.setLayout(new BorderLayout());
		titre = new LabelGame("QUIZZZ");
		titre.setFont(new Font("Verdana", Font.BOLD, 36));
		header.add(titre, BorderLayout.CENTER);
		accueil.add(header);
		
		connexion = new JPanel();
		connexion.setBackground(new Color(28, 28, 28));
		connexion.setLayout(new GridLayout(3, 1));		
		


		pseudoLabel = new LabelGame("Pseudo : ");		
		pseudo = new JTextField(25);
		mdpLabel = new LabelGame("Mot de passe : ");		
		mdp = new JTextField(20);

		log1 = new JPanel();
		log1.setBackground(new Color(28, 28, 28));
		log1.add(pseudoLabel);
		log1.add(pseudo);
		log2 = new JPanel();
		log2.setBackground(new Color(28, 28, 28));
		log2.add(mdpLabel);
		log2.add(mdp);

		controles = new JPanel();
		controles.setBackground(new Color(28, 28, 28));
		controles.setLayout(new FlowLayout());
		
		connecter = new BoutonGame("Se connecter");
		creer = new BoutonGame("Créer le compte");		
		controles.add(connecter);
		controles.add(creer);
		
		connexion.add(log1);
		connexion.add(log2);
		connexion.add(controles, BorderLayout.SOUTH);
		accueil.add(connexion);
		
		
		global.add(accueil, BorderLayout.CENTER);
		
		
		connecter.addActionListener(new BoutonListener());
		creer.addActionListener(new BoutonListener());

		this.revalidate();
	}


	private class BoutonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//global.removeAll();
			if(client == null || client.estDeconnecte())
				client = new Client();

			if(e.getSource() == connecter)
			{
				String nomJoueur = pseudo.getText();
				String mdpJoueur = mdp.getText();
				
				String reponse = client.envoyerInstruction("CONNECT:" + nomJoueur + " " + mdpJoueur);
				
				String donnees[] = reponse.split(":");
				
				if(donnees[0].equals("ERREUR"))
				{
					JOptionPane.showMessageDialog(null, donnees[1]);
				}
				else if(donnees[0].equals("OK"))
				{
					String donneesJoueur[] = donnees[1].split(";");
					Joueur joueur = new Joueur(Integer.parseInt(donneesJoueur[0]), donneesJoueur[1]);
					global.removeAll();
					global.add(new GamePanel(joueur, client), BorderLayout.CENTER);
				}
			}
			else if(e.getSource() == creer)
			{
				String nomJoueur = pseudo.getText();
				String mdpJoueur = mdp.getText();
				
				String reponse = client.envoyerInstruction("CREATE:" + nomJoueur + " " + mdpJoueur);
				
				String donnees[] = reponse.split(":");
				
				if(donnees[0].equals("ERREUR"))
				{
					JOptionPane.showMessageDialog(null, donnees[1]);
				}
				else if(donnees[0].equals("OK"))
				{
					String donneesJoueur[] = donnees[1].split(";");
					Joueur joueur = new Joueur(Integer.parseInt(donneesJoueur[0]), donneesJoueur[1]);
					global.removeAll();
					global.add(new GamePanel(joueur, client), BorderLayout.CENTER);
				}		
			}

			global.revalidate();	
		}
	}


	public static JPanel getGlobal() 
	{
		return global;
	}
	
	
	

}
