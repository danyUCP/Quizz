package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connection.SocketClient;
import data.Joueur;
import data.Manche;
import data.Question;
import data.Reponse;
import data.Theme;

public class QuizPanel extends JPanel
{
	private JPanel grilleReponses, questionPanel;
	private LabelGame questionLabel, questionNum;
	
	private Dimension dim;
	private int largeur = 400;
	private int hauteur = 600;
	
	private SocketClient client;
	private Joueur joueur;
	private int idPartie;
	private Manche manche;
	private Theme theme;
	private int numQuestion;
	

	public QuizPanel(Joueur joueur, SocketClient client, int idPartie, Manche manche)
	{
		this.joueur = joueur;
		this.client = client;
		this.idPartie = idPartie;
		this.manche = manche;
		
		this.dim = new Dimension(largeur, hauteur);
		this.setPreferredSize(dim);
		this.setLayout(new BorderLayout());
		
		this.numQuestion = 0;

		demarrerQuiz();
		
		
		
	}
	
	public void demarrerQuiz()
	{
		Question q = manche.getTheme().getQuestionNumero(numQuestion);
		//-------------- PARTIE HEADER ------------------//
		questionPanel = new JPanel();
		questionPanel.setBackground(new Color(28, 28, 28));
		questionPanel.setPreferredSize(new Dimension(largeur, 300));
		questionPanel.setLayout(new BorderLayout());
		questionNum = new LabelGame("Question " + (numQuestion + 1));
		questionLabel = new LabelGame("<html>" + q.getEnonce() + "</html>");
		questionPanel.add(questionNum, BorderLayout.NORTH);
		questionPanel.add(questionLabel, BorderLayout.CENTER);
		this.add(questionPanel, BorderLayout.NORTH);
		
		//-------------- PARTIE CONTENU --------------------//
		grilleReponses = new JPanel();
		grilleReponses.setBackground(new Color(28, 28, 28));
		grilleReponses.setPreferredSize(new Dimension(largeur, hauteur - 40));
		grilleReponses.setLayout(new GridLayout(2, 2, 10, 10));
		
		for(int i = 0 ; i < 4 ; i++)
		{
			BoutonReponse b = new BoutonReponse(q.getReponseNumero(i));
			b.addActionListener(new ReponseListener());
			grilleReponses.add(b);
		}
			
		this.add(grilleReponses, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}

	private class ReponseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			Reponse reponseChoisie = ((BoutonReponse)(e.getSource())).getReponse();
			
			if(numQuestion < 2)
			{
				manche.ajouterReponseJ1(reponseChoisie.getTag());
				removeAll();
				numQuestion++;
				demarrerQuiz();

			}
			else
			{
				manche.ajouterReponseJ1(reponseChoisie.getTag());
				
				JOptionPane.showMessageDialog(null, manche);
				/*
				//String reponse = client.envoyerInstruction();

				String donnees[] = reponse.split(":");

				if(donnees[0].equals("ERREUR"))
				{
					JOptionPane.showMessageDialog(null, donnees[1]);
				}
				else if(donnees[0].equals("OK"))
				{

					removeAll();
					add(new QuizPanel(joueur, client, idPartie, reponse));
					revalidate();
				}
				*/
				retour();
			}
		}
	}
	
	private class BoutonReponse extends BoutonGame
	{
		private Reponse reponse;

		public BoutonReponse(Reponse reponse) 
		{
			super("Réponse");

			this.reponse = reponse;
			this.setText(reponse.getNom());
		}

		public Reponse getReponse() 
		{
			return reponse;
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
