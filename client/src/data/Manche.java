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
	
	public Manche(Joueur j1, Joueur j2, String nomTheme)
	{
		this.theme = new Theme(nomTheme);
		
		this.j1 = j1;
		this.j2 = j2;
		
		this.reponsesJ1 = new ArrayList<Integer>();
		this.reponsesJ2 = new ArrayList<Integer>();
		
		this.scoreManche = new Score(0, 0);
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

		s += "Joueur 1 : " + j1 + "\n";
		s += "Joueur 2 : " + j2 + "\n\n";
		
		s += theme + "\n";
		
		s += "R�ponse " + j1 + " : \n";
		for(int i = 0 ; i < reponsesJ1.size() ; i++)
		{
			int r = reponsesJ1.get(i);
			s += (r + 1) + ". " + theme.getQuestionNumero(i).getReponseNumero(r) + "\n";
		}
		
		s += "\nR�ponse " + j2 + " : \n";
		for(int i = 0 ; i < reponsesJ2.size() ; i++)
		{
			int r = reponsesJ2.get(i);
			s += (r + 1) + ". " + theme.getQuestionNumero(i).getReponseNumero(r) + "\n";
		}
		
		s += "\nScore de la manche : " + j1 + " " + scoreManche + " " + j2 + "\n";
		
		
		
		return s;
	}

	
	
	
	

}