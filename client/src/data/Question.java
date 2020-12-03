package data;

import java.util.ArrayList;

public class Question 
{
	private int id;
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
					this.reponses.add(new Reponse(r1, true, 1));
					break;
				case 1:
					this.reponses.add(new Reponse(r2, false, 2));
					break;
				case 2:
					this.reponses.add(new Reponse(r3, false, 3));
					break;
				case 3:
					this.reponses.add(new Reponse(r4, false, 4));
					break;
			}
		}
		
		this.reponses.add(new Reponse("Pas de réponse", false, 5));
		
		//System.out.println(dispoQuestions);		
	}
	
	public Question(int id, String enonce, String r1, String r2, String r3, String r4) 
	{
		this.id = id;
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
					this.reponses.add(new Reponse(r1, true, 1));
					break;
				case 1:
					this.reponses.add(new Reponse(r2, false, 2));
					break;
				case 2:
					this.reponses.add(new Reponse(r3, false, 3));
					break;
				case 3:
					this.reponses.add(new Reponse(r4, false, 4));
					break;
			}
		}
		
		this.reponses.add(new Reponse("Pas de réponse", false, 5));
		
		//System.out.println(dispoQuestions);		
	}

	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
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
	
	public Reponse getReponseTag(int tag)
	{
		Reponse r = null;
		
		for(int i = 0 ; i < 4 ; i++)
		{
			if(this.getReponseNumero(i).getTag() == tag)
				r = this.getReponseNumero(i);
		}
		return r;
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
