package data;

import java.util.ArrayList;

public class Theme 
{
	private String nom;
	private ArrayList<Question> questions;
	
	
	public Theme(String nom) 
	{
		this.nom = nom;
		this.questions = new ArrayList<Question>();
	}
	
	public void ajouterQuestion(Question q)
	{
		this.questions.add(q);
	}
	
	public void ajouterQuestion(String enonce, String r1, String r2, String r3, String r4)
	{
		Question q = new Question(enonce, r1, r2, r3, r4);
		this.questions.add(q);
	}
	
	public Question getQuestionNumero(int i)
	{
		return this.questions.get(i);
	}
	
	
	public String toString() 
	{
		String s = "";
		
		s += "Thème : " + nom + "\n\n";
		
		for(int i = 0 ; i < questions.size() ; i++)
			s += (i + 1) + ") " + questions.get(i) + "\n";
		
		
		return s;
	}
	
	
}
