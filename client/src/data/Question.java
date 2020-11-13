package data;

import java.util.ArrayList;

public class Question 
{
	private String enonce;
	private ArrayList<Reponse> reponses;
	
	public Question(String enonce, String r1, String r2, String r3, String r4) 
	{
		this.enonce = enonce;
		
		this.reponses = new ArrayList<Reponse>();
		ArrayList<Integer> dispoQuestions = new ArrayList<Integer>();
		
		for(int i = 0 ; i < 4 ; i++)
		{
			int n = (int) (Math.random() * 4);

			if(!dispoQuestions.contains(n))
				dispoQuestions.add(n);
			else
			{
				while(dispoQuestions.contains(n))
					n = (int) (Math.random() * 4);
				
				dispoQuestions.add(n);
			}
			
			switch(n)
			{
				case 0:
					this.reponses.add(new Reponse(r1, true));
					break;
				case 1:
					this.reponses.add(new Reponse(r2, false));
					break;
				case 2:
					this.reponses.add(new Reponse(r3, false));
					break;
				case 3:
					this.reponses.add(new Reponse(r4, false));
					break;
			}
		}
		
		this.reponses.add(new Reponse("Pas de réponse", false));
		
		//System.out.println(dispoQuestions);		
	}

	public String getEnonce() 
	{
		return enonce;
	}

	public void setEnonce(String enonce) 
	{
		this.enonce = enonce;
	}

	public ArrayList<Reponse> getListeReponses() 
	{
		return reponses;
	}
	
	public Reponse getReponseNumero(int i)
	{
		return reponses.get(i);
	}
	
	public int getReponseCorrecte()
	{
		int rc = 0;
		
		for(int i = 0 ; i < 4 ; i++)
		{
			if(this.getReponseNumero(i).estCorrect())
				rc = i;
		}
		
		return rc;
	}

	public void setReponses(ArrayList<Reponse> reponses) 
	{
		this.reponses = reponses;
	}


	public String toString() 
	{
		String s = "";
		
		s += "Question : " + enonce + "\n";
		//s += "Réponses : \n";
		
		for(int i = 0 ; i < 4 ; i++)
			s += (i + 1) + ". " + reponses.get(i) + "\n";
		
		
		return s;
	}
	
	
	
	
	


}
