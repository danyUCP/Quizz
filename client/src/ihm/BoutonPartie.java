package ihm;

import data.Partie;

public class BoutonPartie extends BoutonGame
{
	private Partie partie;

	public BoutonPartie(Partie partie) 
	{
		super("Partie");

		this.partie = partie;
		this.setSize(300, 40);
		this.setText(partie.getJ1() + " " + partie.getScorePartie() + " " + partie.getJ2());

	}

	public Partie getPartie() 
	{
		return partie;
	}

	public void setPartie(Partie partie) 
	{
		this.partie = partie;
	}
	
	

	
}
