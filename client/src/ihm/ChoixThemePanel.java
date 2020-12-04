package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connection.Client;
import data.Joueur;
import data.Manche;
import data.Question;
import data.Theme;

public class ChoixThemePanel extends JPanel
{
	private JPanel contenu, header;
	private BoutonTheme t1, t2, t3;
	private LabelGame consigneLabel;
	
	private Dimension dim;
	private int largeur = 400;
	private int hauteur = 600;
	
	private Client client;
	private Joueur joueur;
	private int idPartie;
	
	

	public ChoixThemePanel(Joueur joueur, Client client, int idPartie)
	{
		this.joueur = joueur;
		this.client = client;
		this.idPartie = idPartie;
		
		this.dim = new Dimension(largeur, hauteur);
		this.setPreferredSize(dim);
		this.setLayout(new BorderLayout());

		//-------------- PARTIE HEADER ------------------//
		header = new JPanel();
		header.setBackground(new Color(28, 28, 28));
		header.setPreferredSize(new Dimension(largeur, 40));
		header.setLayout(new FlowLayout());
		consigneLabel = new LabelGame("Choisissez un thème parmi les 3");
		header.add(consigneLabel);
		this.add(header, BorderLayout.NORTH);

		//-------------- PARTIE CONTENU --------------------//
		contenu = new JPanel();
		contenu.setBackground(new Color(28, 28, 28));
		contenu.setPreferredSize(new Dimension(largeur, hauteur - 40));
		contenu.setLayout(new FlowLayout());
		initContenu();
		this.add(contenu, BorderLayout.CENTER);

	}
	
	public void initContenu()
	{		
		String reponse = client.envoyerInstruction("GET:3ThemesAlea");
		
		String donnees[] = reponse.split(":");
		
		if(donnees[0].equals("ERREUR"))
		{
			JOptionPane.showMessageDialog(null, donnees[1]);
		}
		else if(donnees[0].equals("OK"))
		{			
			String donneesParties[] = donnees[1].split(";");
			
			contenu.setLayout(new GridLayout(3, 1));

			t1 = new BoutonTheme(new Theme(Integer.parseInt(donneesParties[0]), donneesParties[1]));
			t2 = new BoutonTheme(new Theme(Integer.parseInt(donneesParties[2]), donneesParties[3]));
			t3 = new BoutonTheme(new Theme(Integer.parseInt(donneesParties[4]), donneesParties[5]));
			
			contenu.add(t1);
			contenu.add(t2);
			contenu.add(t3);

			t1.addActionListener(new ThemeListener());
			t2.addActionListener(new ThemeListener());
			t3.addActionListener(new ThemeListener());
			this.revalidate();

		}		
	}
	
	private class ThemeListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			Theme themeChoisi = ((BoutonTheme)(e.getSource())).getTheme();
			
			String reponse = client.envoyerInstruction("GET:3QuestionsAlea " + themeChoisi.getId());
			//JOptionPane.showMessageDialog(null, reponse);
			
			String donnees[] = reponse.split(":");
			
			if(donnees[0].equals("ERREUR"))
			{
				JOptionPane.showMessageDialog(null, donnees[1]);
			}
			else if(donnees[0].equals("OK"))
			{
				String donneesQ[] = donnees[1].split(";");
				
				Question q1 = new Question(Integer.parseInt(donneesQ[0]), donneesQ[1], donneesQ[2], donneesQ[3], donneesQ[4], donneesQ[5]);
				Question q2 = new Question(Integer.parseInt(donneesQ[6]), donneesQ[7], donneesQ[8], donneesQ[9], donneesQ[10], donneesQ[11]);
				Question q3 = new Question(Integer.parseInt(donneesQ[12]), donneesQ[13], donneesQ[14], donneesQ[15], donneesQ[16], donneesQ[17]);

				themeChoisi.ajouterQuestion(q1);
				themeChoisi.ajouterQuestion(q2);
				themeChoisi.ajouterQuestion(q3);

				reponse = client.envoyerInstruction("SET:newManche " + idPartie + "/" + themeChoisi.getId() + "/" + q1.getId() + "/" + q2.getId() + "/" + q3.getId());

				Manche m = new Manche(1, joueur, null, joueur, themeChoisi, 1);
				
				removeAll();
				add(new QuizPanel(joueur, client, idPartie, m));
				revalidate();
			}
		}
	}
	
	private class BoutonTheme extends BoutonGame
	{
		private Theme theme;

		public BoutonTheme(Theme theme) 
		{
			super("Thème");

			this.theme = theme;
			this.setText(theme.getNom());
		}

		public Theme getTheme() 
		{
			return theme;
		}
	}
	
}
