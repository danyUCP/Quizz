package data;

public class Reponse 
{
	private String nom;
	private Boolean correct;
	private int tag;
	
	public Reponse(String nom, Boolean correct, int tag) 
	{
		this.nom = nom;
		this.correct = correct;
		this.tag = tag;
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

	public int getTag() 
	{
		return tag;
	}

	public void setTag(int tag) 
	{
		this.tag = tag;
	}


	public String toString() 
	{
		return (correct ? "O" : "X") + " " + nom + "(" + tag + ")";
	}
	
	
	
	

}
