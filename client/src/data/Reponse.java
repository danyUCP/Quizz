package data;

public class Reponse 
{
	private String nom;
	private Boolean correct;
	
	public Reponse(String nom, Boolean correct) 
	{
		this.nom = nom;
		this.correct = correct;
	}

	
	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public Boolean estCorrect() 
	{
		return correct;
	}

	public void setCorrect(Boolean correct) 
	{
		this.correct = correct;
	}



	public String toString() 
	{
		return (correct ? "O" : "X") + " " + nom;
	}
	
	
	
	

}
