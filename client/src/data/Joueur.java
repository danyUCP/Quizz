package data;

public class Joueur 
{
	private String pseudo;
	private Boolean estJ1;

	public Joueur(String pseudo) 
	{
		this.pseudo = pseudo;
	}

	public String getPseudo() 
	{
		return pseudo;
	}

	public void setPseudo(String pseudo) 
	{
		this.pseudo = pseudo;
	}
	
	public Boolean estJ1() 
	{
		return estJ1;
	}

	public void setEstJ1(Boolean estJ1) 
	{
		this.estJ1 = estJ1;
	}

	
	public String toString() 
	{
		return pseudo;
	}
	
	

}
