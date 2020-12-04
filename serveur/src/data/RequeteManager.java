package data;

import java.util.ArrayList;

public class RequeteManager 
{
	private String requete;
	private static ArrayList<String> listeCommandes;
	private static ArrayList<String> listeInstructions;


	public RequeteManager()
	{
		if(listeCommandes == null)
			initListeCommandes();
		
		if(listeInstructions == null)
			initListeInstructions();
	}
	
	private void initListeCommandes()
	{
		listeCommandes = new ArrayList<String>();
		
		listeCommandes.add("CREATE");
		listeCommandes.add("CONNECT");
		listeCommandes.add("BEGIN");
		listeCommandes.add("GET");
		listeCommandes.add("SET");
		listeCommandes.add("DISCONNECT");
		//listeCommandes.add();
	}
	
	private void initListeInstructions()
	{
		listeInstructions = new ArrayList<String>();
				 
		listeInstructions.add("3ThemesAlea");
		listeInstructions.add("infoParties");
		listeInstructions.add("infoManches");
		listeInstructions.add("adversaireAlea");
		listeInstructions.add("3QuestionsAlea");
		listeInstructions.add("newPartie");
		listeInstructions.add("newManche");
		listeInstructions.add("scorePartie");
		listeInstructions.add("scoreManche");



		//listeInstructions.add();
	}
	
	public String executerReq(String requete)
	{
		String[] donnees = requete.split(":");
		String prefixe = donnees[0].toUpperCase();
		String reponse = "";

		
		if(!listeCommandes.contains(prefixe))
			reponse = "La commande " + prefixe + " n'existe pas";
		else
		{
			System.out.println("Commande " + prefixe + " reconnue");
			//System.out.println("Données : " + donnees[1] + "\n");			
			
			String[] elements;

			switch(prefixe)
			{
			
				case "CREATE":
					if(donnees.length < 2)
					{
						reponse = "ERREUR:Données de la requête incorrecte";			
						break;
					}
					
					elements = donnees[1].split(" ");
					if(elements.length != 2)
					{
						reponse = "ERREUR:Identifiants incorrectes";			
						break;
					}
					reponse = creerCompte(elements[0], elements[1]);
					break;
				case "CONNECT":
					if(donnees.length < 2)
					{
						reponse = "ERREUR:Données de la requête incorrecte";			
						break;
					}
					
					elements = donnees[1].split(" ");
					if(elements.length != 2)
					{
						reponse = "ERREUR:Identifiants incorrectes";			
						break;
					}
					
					reponse = connecterCompte(elements[0], elements[1]);
					break;
				case "BEGIN":
					if(donnees.length < 2)
					{
						reponse = "ERREUR:Données de la requête incorrecte";			
						break;
					}
					reponse = demarrerPartie(donnees[1]);
					break;
				case "GET":
					if(donnees.length < 2)
					{
						reponse = "ERREUR:Données de la requête incorrecte";			
						break;
					}
					reponse = getDonnees(donnees[1]);
					break;
				case "SET":
					if(donnees.length < 2)
					{
						reponse = "ERREUR:Données de la requête incorrecte";			
						break;
					}
					reponse = setDonnees(donnees[1]);
					break;
				case "DISCONNECT":
					reponse = "DISCONNECT";
					break;
			}
		}
		
		return reponse;
	}
	
	private String creerCompte(String pseudo, String mdp)
	{
		String reponse = "";

		if(pseudo.length() < 3 || pseudo.length() > 50)
			reponse = "ERREUR:Longueur pseudo incorrecte";
		else if(mdp.length() < 3 || mdp.length() > 300)
			reponse = "ERREUR:Longueur mdp incorrecte";
		else
		{
			System.out.println("Création du compte");
			System.out.println("Pseudo : " + pseudo);
			System.out.println("Mot de passe : " + mdp);
			
			reponse = BDConnexion.creerCompte(pseudo, mdp);
		}

		return reponse;
	}
	
	private String connecterCompte(String pseudo, String mdp)
	{
		String reponse = "";
		
		if(pseudo.length() < 3 || pseudo.length() > 50)
			reponse = "ERREUR:Longueur pseudo incorrecte";
		else if(mdp.length() < 3 || mdp.length() > 300)
			reponse = "ERREUR:Longueur mdp incorrecte";
		else
		{
			System.out.println("Connexion au compte");
			System.out.println("Pseudo : " + pseudo);
			System.out.println("Mot de passe : " + mdp);
			
			String requete = "";
			requete += "SELECT * FROM joueur ";
			requete += "WHERE nom_joueur = '" + pseudo + "' AND mdp = '" + mdp + "' ";
			
			System.out.println(requete);
			reponse = BDConnexion.connecterCompte(requete);
		}


		return reponse;
	}
	
	
	private String getDonnees(String instruction)
	{
		String reponse = "";
		
		String[] donnees = instruction.split(" ");
		
		if(donnees.length > 2)
			reponse = "ERREUR:Instruction incorrecte";
		else
		{
			if(listeInstructions.contains(donnees[0]))
			{
				if(donnees.length == 1)
				{
					switch(donnees[0])
					{
					case "3ThemesAlea":				
						System.out.println("Génération de 3 thèmes aléatoires");
						reponse = BDConnexion.rechercheThemes();
						break;
					default:
						reponse = "ERREUR:Instruction incorrecte";
						break;
					}
				}
				else if(donnees.length == 2)
				{
					switch(donnees[0])
					{
						case "infoParties":				
							System.out.println("Informations sur les parties du compte n°" + donnees[1]);
							reponse = BDConnexion.getInfoParties(donnees[1]);
							break;
						case "infoManches":				
							System.out.println("Informations sur les manches de la partie n°" + donnees[1]);
							reponse = BDConnexion.getInfoManches(donnees[1]);
							break; 
						case "adversaireAlea":				
							System.out.println("Recherche des parties adverses");
							reponse = BDConnexion.rechercheAdversaire(donnees[1]);
							break; 
						case "3QuestionsAlea":				
							System.out.println("Génération de 3 questions aléatoires, thème n°" + donnees[1]);
							reponse = BDConnexion.rechercheQuestions(donnees[1]);
							break;
						default:
							reponse = "ERREUR:Instruction incorrecte";
							break;
					}
				}
				else
					reponse = "ERREUR:Instruction incorrecte";
			}
			else
				reponse = "ERREUR:Instruction incorrecte";		
		}
		
		
		return reponse;
	}

	private String setDonnees(String instruction)
	{
		String reponse = "";
		
		String[] donnees = instruction.split(" ");
		
		if(donnees.length > 2)
			reponse = "ERREUR:Instruction incorrecte";
		else
		{
			if(listeInstructions.contains(donnees[0]))
			{
				if(donnees.length == 2)
				{
					switch(donnees[0])
					{
						case "newManche":				
							System.out.println("Création d'une manche");
							reponse = BDConnexion.creerManche(donnees[1]);
							break;
						default:
							reponse = "ERREUR:Instruction incorrecte";
							break;
					}
				}
				else
					reponse = "ERREUR:Instruction incorrecte";
			}
			else
				reponse = "ERREUR:Instruction incorrecte";		
		}
		
		
		return reponse;
	}
	
	private String demarrerPartie(String instruction)
	{
		String reponse = "";
		
		String[] donnees = instruction.split(" ");
		
		
		if(donnees.length > 2)
			reponse = "ERREUR:Instruction incorrecte";
		else
		{
			if(listeInstructions.contains(donnees[0]))
			{
				if(donnees.length == 2)
				{
					switch(donnees[0])
					{
					case "newPartie":				
						System.out.println("Création d'une nouvelle partie");
						reponse = BDConnexion.creerPartie(donnees[1]);
						break;
					default:
						reponse = "ERREUR:Instruction incorrecte";
						break;
					}
				}
				else if(donnees.length == 3)
				{
					switch(donnees[0])
					{
					case "joinPartie":				
						System.out.println("Connexion à une partie");
						//reponse = BDConnexion.rejoindrePartie(donnees[1]);
						reponse = "En construction";
						break;
					default:
						reponse = "ERREUR:Instruction incorrecte";
						break;
					}
				}
				else
					reponse = "ERREUR:Instruction incorrecte";
			}
			else
				reponse = "ERREUR:Instruction incorrecte";		
		}
		
		return reponse;
	}
}
