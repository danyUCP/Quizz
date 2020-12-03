package data;

import java.util.ArrayList;

public class Manche 
{
	private Theme theme;
	private Joueur j1;
	private Joueur j2;
	private ArrayList<Integer> reponsesJ1;
	private ArrayList<Integer> reponsesJ2;
	private Score scoreManche;
	private Joueur prims;
	private int id;
	private int numeroManche;
	
	public Manche(Joueur j1, Joueur j2, Joueur prims, String nomTheme)
	{
		this.theme = new Theme(nomTheme);
		
		this.j1 = j1;
		this.j2 = j2;
		this.prims = prims;
		
		this.reponsesJ1 = new ArrayList<Integer>();
		this.reponsesJ2 = new ArrayList<Integer>();
		
		this.scoreManche = new Score(0, 0);
	}
	
	public Manche(int id, Joueur j1, Joueur j2, Joueur prims, String nomTheme)
	{
		this.theme = new Theme(nomTheme);
		
		this.j1 = j1;
		this.j2 = j2;
		this.prims = prims;
		
		this.reponsesJ1 = new ArrayList<Integer>();
		this.reponsesJ2 = new ArrayList<Integer>();
		
		this.scoreManche = new Score(0, 0);
	}
	
	public Manche(int id, Joueur j1, Joueur j2, Joueur prims, Theme theme, int numeroManche)
	{
		this.theme = theme;
		
		this.j1 = j1;
		this.j2 = j2;
		this.prims = prims;
		
		this.reponsesJ1 = new ArrayList<Integer>();
		this.reponsesJ2 = new ArrayList<Integer>();
		
		this.scoreManche = new Score(0, 0);
		this.numeroManche = numeroManche;
	}
	
	public void ajouterQuestion(Question q)
	{
		this.theme.ajouterQuestion(q);
	}
	
	public void ajouterReponseJ1(int r)
	{
		this.reponsesJ1.add(r);
	}

	public void ajouterReponseJ2(int r)
	{
		this.reponsesJ2.add(r);
	}
	
	private void ajouterPointJ1()
	{
		int pointsJ1 =  scoreManche.getPointsJ1();
		
		this.scoreManche.setPointsJ1(pointsJ1 + 1);
	}
	
	private void ajouterPointJ2()
	{
		int pointsJ2 =  scoreManche.getPointsJ2();
		
		this.scoreManche.setPointsJ2(pointsJ2 + 1);
	}
	
	public Boolean comparerReponsesJ1(int r, Question q)
	{
		if(q.getReponseCorrecte() == r)
		{
			this.ajouterPointJ1();
			return true;
		}
		else
			return false;
	}
	
	public Boolean comparerReponsesJ2(int r, Question q)
	{
		if(q.getReponseCorrecte() == r)
		{
			this.ajouterPointJ2();
			return true;
		}
		else
			return false;
	}

	

	public Theme getTheme() 
	{
		return theme;
	}

	public void setTheme(Theme theme) 
	{
		this.theme = theme;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
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

	public ArrayList<Integer> getReponsesJ1() 
	{
		return reponsesJ1;
	}


	public ArrayList<Integer> getReponsesJ2() 
	{
		return reponsesJ2;
	}

	public Score getScoreManche() 
	{
		return scoreManche;
	}

	public void setScoreManche(Score scoreManche) 
	{
		this.scoreManche = scoreManche;
	}

	
	public String toString() 
	{
		String s = "";
		
		s += theme + "\n";
		
		s += "Réponse " + j1 + " : \n";
		for(int i = 0 ; i < reponsesJ1.size() ; i++)
		{
			int r = reponsesJ1.get(i);
			s += r + ". " + theme.getQuestionNumero(i).getReponseTag(r) + "\t";
			s += prims == j2 ? "Réponse " + j2 + " : " + (getReponsesJ2().get(i) + 1) + "\n" : "\n";

		}
		
		s += "\nRéponse " + j2 + " : \n";
		for(int i = 0 ; i < reponsesJ2.size() ; i++)
		{
			int r = reponsesJ2.get(i);
			s += r + ". " + theme.getQuestionNumero(i).getReponseTag(r) + "\t";
			s += prims == j1 ? "Réponse " + j1 + " : " + (getReponsesJ1().get(i) + 1) + "\n" : "\n";
		}
		
		s += "\nScore de la manche : " + j1 + " " + scoreManche + " " + j2 + "\n";
		
		
		
		return s;
	}

	
	
	
	

}
