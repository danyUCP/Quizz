package data;

import java.util.ArrayList;

public class Partie 
{
	private Joueur j1;
	private Joueur j2;
	private Score scorePartie;
	private ArrayList<Manche> manches;
	private Boolean enAttente;
	private int mancheActuelle;
	private Joueur prims;
	
	
	public Partie() 
	{
		this.manches = new ArrayList<Manche>();
		this.scorePartie = new Score(0, 0);
		this.enAttente = true;
	}
	
	public void creerPartie(Joueur j1)
	{
		this.setJ1(j1);
		
		this.enAttente = true;
		this.prims = j1;
		this.mancheActuelle = 0;
		this.creerManche();
		this.tourJ1(this.getMancheNumero(0));
	}
	
	public void rejoindrePartie(Joueur j2)
	{
		if(this.j2 == null)
		{
			this.setJ2(j2);
			this.enAttente = false;
			this.getMancheNumero(0).setJ2(j2);;
			this.tourJ2(this.getMancheNumero(0));
		}
		else
			System.out.println("Impossible de rejoindre la partie" + j2);
	}
	/*
	public void commencerPartie()
	{
		//for(int i = 0 ; i < 6 ; i++)
		//{
			this.creerManche();
			this.tourJ1(this.getMancheNumero(0));
		//}
		
		
		//System.out.println(m1);

	}
	*/
	public void tourJ1(Manche m)
	{
		m.ajouterReponseJ1(1);
		m.comparerReponsesJ1(1, m.getTheme().getQuestionNumero(0));
		
		m.ajouterReponseJ1(3);
		m.comparerReponsesJ1(3, m.getTheme().getQuestionNumero(1));
		
		m.ajouterReponseJ1(2);
		m.comparerReponsesJ1(2, m.getTheme().getQuestionNumero(2));
	}
	
	public void tourJ2(Manche m)
	{
		m.ajouterReponseJ2(0);
		m.comparerReponsesJ2(0, m.getTheme().getQuestionNumero(0));
		
		m.ajouterReponseJ2(4);
		m.comparerReponsesJ2(4, m.getTheme().getQuestionNumero(1));
		
		m.ajouterReponseJ2(3);
		m.comparerReponsesJ2(3, m.getTheme().getQuestionNumero(2));
	}
	
	public void creerManche()
	{
		Manche m1 = new Manche(j1, j2, prims, "Test");
		
		Theme th1 = new Theme("Test");
		Question q1 = new Question("Qui a mangé la mousse au chocolat ?", "Edrisha", "Erice", "Gildas", "Daniel");
		Question q2 = new Question("Qui va gagner la prochaine Champions League ?", "PSG", "Barça", "Bayern", "Juventus");
		Question q3 = new Question("Qui est l'intrus ?", "Marc Lemaire", "Daniel François", "Laura Fustinoni", "Rebecca Simon");

		th1.ajouterQuestion(q1);
		th1.ajouterQuestion(q2);
		th1.ajouterQuestion(q3);
		
		m1.setTheme(th1);
		this.ajouterManche(m1);
		
	}
	
	public void mancheSuivante()
	{
		this.mancheActuelle++;
		
		if(this.prims == j1)
			this.prims = j2;
		else
			this.prims = j1;
		
		this.creerManche();
		this.tourJ1(this.getMancheNumero(this.mancheActuelle));
		this.tourJ2(this.getMancheNumero(this.mancheActuelle));
	}
	
	public void calculerScorePartie()
	{
		for(int i = 0 ; i < this.manches.size(); i++)
		{
			Score sc = this.getMancheNumero(i).getScoreManche();
			this.scorePartie.setPointsJ1(this.scorePartie.getPointsJ1() + sc.getPointsJ1());
			this.scorePartie.setPointsJ2(this.scorePartie.getPointsJ2() + sc.getPointsJ2());

			
		}
	}
	
	public void ajouterManche(Manche m)
	{
		this.manches.add(m);
	}
	
	
	public Manche getMancheNumero(int i)
	{
		return this.manches.get(i);
	}
	
	public int getNbManches()
	{
		return this.manches.size();
	}
	

	public Joueur getJ1() 
	{
		return j1;
	}

	public void setJ1(Joueur j1) 
	{
		this.j1 = j1;
	}

	public Joueur getJ2() 
	{
		return j2;
	}

	public void setJ2(Joueur j2) 
	{
		this.j2 = j2;
	}

	public Score getScorePartie() 
	{
		return scorePartie;
	}

	public void setScorePartie(Score scorePartie) 
	{
		this.scorePartie = scorePartie;
	}
	
	public Joueur getJoueur1Actuel() {
		return prims;
	}

	public void setJoueur1Actuel(Joueur joueur1Actuel) {
		this.prims = joueur1Actuel;
	}
	

	public ArrayList<Manche> getManches() 
	{
		return manches;
	}

	public void setManches(ArrayList<Manche> manches) 
	{
		this.manches = manches;
	}

	@Override
	public String toString() 
	{
		String s = "";
		
		s += "Joueur 1 : " + j1 + "\n";
		s += "Joueur 2 : " + j2 + "\n\n";
		
		for(int i = 0 ; i < manches.size() ; i++)
		{
			s += "\t--------------- MANCHE " + (i + 1) + " ---------------\n" + manches.get(i) + "\n";
		}
		
		s += "\nScore final : " + j1 + " " + scorePartie + " " + j2 + "\n";


		return s;
	}
	
	
	
	
	
	

}
