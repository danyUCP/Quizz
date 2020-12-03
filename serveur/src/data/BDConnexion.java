package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BDConnexion 
{
	private String url;
	private String name;
	private String id;
	private String mdp;
	
	private static BDConnexion bdc;
	private static Connection connexion;
	
	private BDConnexion()
	{
		this.url = "jdbc:postgresql://localhost/";
		this.name = "dbquizzz";
		this.id = "postgres";
		this.mdp = "postgres";
		
		try 
		{
			connexion = DriverManager.getConnection(url + name, id, mdp);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static String executerRequete(String req)
	{
		String s = "";
		
		try
		{
			Statement st = connexion.createStatement();
			ResultSet rs = st.executeQuery(req);
			ResultSetMetaData rm = rs.getMetaData();

			/*
			while(rs.next())
			{
				s += rs.getString("no_invent") + "\t";
				s += rs.getString("marque") + "\t";
				s += rs.getString("date_achat") + "\t";
				s += rs.getString("fournisseur") + "\n";
			}
			*/

			while(rs.next())
			{
				for(int i = 1 ; i <= rm.getColumnCount() ; i++)
					System.out.println(rm.getColumnLabel(i) + " : " + rs.getObject(i).toString());
			}
		} 
		catch (SQLException e) 
		{
			return "ERREUR:Requête PSQL incorrecte";
		}

		return s;
	}
	
	public static String selectDonnees(String req)
	{
		String s = "";
		
		try
		{
			Statement st = connexion.createStatement();
			ResultSet rs = st.executeQuery(req);
			ResultSetMetaData rm = rs.getMetaData();

			while(rs.next())
			{
				for(int i = 1 ; i <= rm.getColumnCount() ; i++)
					s += (i != 1 ? ";" : "") + rs.getObject(i).toString();
			}
			
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Compte inexistant";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	public static String getInfoParties(String idJoueur)
	{
		String s = "";

		try
		{
			Statement st = connexion.createStatement();

			String requete = "";
			requete += "SELECT id_partie,nom_j1,nom_j2,score_j1,score_j2,manche_actuelle FROM participer ";
			requete += "NATURAL JOIN partie ";
			requete += "WHERE id_joueur = " + idJoueur + " AND est_terminee = false ";

			ResultSet rs = st.executeQuery(requete);
			ResultSetMetaData rm = rs.getMetaData();

			System.out.println(requete);
			
			String data = "";
			while(rs.next())
			{
				for(int i = 1 ; i <= rm.getColumnCount() ; i++)
				{
					s += (i != 1 ? ";" : "");
					
					if(rs.getObject(i) != null)
						s += rs.getObject(i).toString();
					else
						s += "";
				}
				
				s += !rs.isLast() ? ";" : "";
			}


			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();

			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Aucune partie en cours";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	public static String rechercheThemes()
	{
		String s = "";
		
		try
		{
			Statement st = connexion.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT count(*) FROM theme");
			int nbThemes = 0;

			while(rs.next())
				nbThemes = rs.getInt(1);
			
			System.out.println("Il existe " + nbThemes + " thèmes");

			//Generation de 3 id_theme aléatoire
			ArrayList<Integer> themesAlea = new ArrayList<Integer>();
			
			for(int i = 0 ; i < 3 ; i++)
			{
				int id = ((int) (Math.random() * nbThemes)) + 1;

				if(!themesAlea.contains(id))
					themesAlea.add(id);
				else
				{
					while(themesAlea.contains(id))
						id = ((int) (Math.random() * nbThemes)) + 1;

					themesAlea.add(id);
				}
			}
			System.out.println(themesAlea);		

			for(int j = 0 ; j < 3 ; j++)
			{
				String requete = "";
				requete += "SELECT id_theme,nom_theme FROM theme ";
				requete += "WHERE id_theme = " + themesAlea.get(j);

				rs = st.executeQuery(requete);
				ResultSetMetaData rm = rs.getMetaData();

				while(rs.next())
				{
					for(int i = 1 ; i <= rm.getColumnCount() ; i++)
						s += (i != 1 ? ";" : "") + rs.getObject(i).toString();
				}
				s += (j != 2 ? ";" : "");
			}
			
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Thèmes inexistants";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	public static String rechercheQuestions(String idTheme)
	{
		String s = "";
		
		try
		{
			Statement st = connexion.createStatement();
			
			String requete = "";
			requete += "SELECT id_question FROM question ";
			requete += "WHERE id_theme = " + idTheme;
			ResultSet rs = st.executeQuery(requete);
			

			ArrayList<Integer> listeID = new ArrayList<Integer>();
			while(rs.next())
				listeID.add(rs.getInt(1));
			
			int nbQuest = listeID.size();
			System.out.println("Il existe " + nbQuest + " questions");
			System.out.println(listeID);
			
			if(nbQuest < 3)
				return "ERREUR:Questions inexistantes";
			
			//Generation de 3 id_question aléatoire
			ArrayList<Integer> questAlea = new ArrayList<Integer>();
			
			for(int i = 0 ; i < 3 ; i++)
			{
				int q = (int) (Math.random() * nbQuest);
				int iDq = listeID.get(q);

				if(!questAlea.contains(iDq))
					questAlea.add(iDq);
				else
				{
					while(questAlea.contains(iDq))
					{
						q = (int) (Math.random() * nbQuest);
						iDq = listeID.get(q);
					}

					questAlea.add(iDq);
				}
			}
			System.out.println(questAlea);		

			//Recherche et envoie des 3 questions
			for(int j = 0 ; j < 3 ; j++)
			{
				requete = "";
				requete += "SELECT id_question,enonce,rep_1,rep_2,rep_3,rep_4 FROM question ";
				requete += "WHERE id_question = " + questAlea.get(j);

				rs = st.executeQuery(requete);
				ResultSetMetaData rm = rs.getMetaData();

				while(rs.next())
				{
					for(int i = 1 ; i <= rm.getColumnCount() ; i++)
						s += (i != 1 ? ";" : "") + rs.getObject(i).toString();
				}
				s += (j != 2 ? ";" : "");
			}
			
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Questions inexistantes";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	
	public static String creerPartie(String idJoueur)
	{
		String s = "";
		
		try
		{
			Statement st = connexion.createStatement();
			
			String requete = "";
			requete += "SELECT count(*) FROM participer ";
			requete += "NATURAL JOIN partie ";
			requete += "WHERE id_joueur = " + idJoueur +" AND est_terminee = false ";

			
			ResultSet rs = st.executeQuery(requete);
			int nbPartiesEnCours = 0;

			while(rs.next())
				nbPartiesEnCours = rs.getInt(1);
			
			//Generation de 3 id_theme aléatoire
			
			requete = "";
			requete += "SELECT nom_joueur FROM joueur ";
			requete += "WHERE id_compte = " + idJoueur;

			rs = st.executeQuery(requete);
			
			String nomJoueur = null;
			
			while(rs.next())
				nomJoueur = rs.getString(1);
						
			if(nbPartiesEnCours > 9)
				return "ERREUR:La maximum de parties non terminées est de 10. Terminez celles en cours";
			if(nomJoueur == null)
				return "ERREUR:Joueur inexistant";
			
			System.out.println("Il existe " + nbPartiesEnCours + " parties non terminées pour : " + nomJoueur);

			//Insertion de la partie
			requete = "";
			requete += "INSERT INTO partie (nom_j1) ";
			requete += "VALUES ('" + nomJoueur + "') ";
			requete += "RETURNING id_partie,derniere_modif ";

			rs = st.executeQuery(requete);
			
			rs.next();
			int newPartieID = rs.getInt(1);
			String date = rs.getDate(2).toString();
			
			requete = "";
			requete += "INSERT INTO participer (id_joueur,id_partie,est_j1) ";
			requete += "VALUES (" + idJoueur + "," + newPartieID + ",true) ";
			requete += "RETURNING * ";
			
			rs = st.executeQuery(requete);

			System.out.println("Nouvelle partie créée (id : " + newPartieID + ") le " + date);
			
			s = newPartieID + ";" + date;

			
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();

			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Problème dans la création de la partie";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	public static String creerManche(String donnees)
	{
		String s = "";
		
		String donnee[] = donnees.split("/");
		
		String idPartie = donnee[0];
		String idTheme = donnee[1];
		String idQ1 = donnee[2];
		String idQ2 = donnee[3];
		String idQ3 = donnee[4];

		
		try
		{
			Statement st = connexion.createStatement();
			
			String requete = "";
			requete += "SELECT count(*) FROM manche ";
			requete += "WHERE id_partie = " + idPartie + " ";

			
			ResultSet rs = st.executeQuery(requete);
			int nbMancheEnCours = 0;

			while(rs.next())
				nbMancheEnCours = rs.getInt(1);
			
			//Generation de 3 id_theme aléatoire
			
			requete = "";
			requete += "SELECT nom_theme FROM theme ";
			requete += "WHERE id_theme = " + idTheme;

			rs = st.executeQuery(requete);
			
			String nomTheme = null;
			
			while(rs.next())
				nomTheme = rs.getString(1);
			
			if(nbMancheEnCours > 5)
				return "ERREUR:La maximum de manche d'une partie est de 6.";
			if(nomTheme == null)
				return "ERREUR:Thème inexistant";
			

			//Insertion de la manche
			requete = "";
			requete += "INSERT INTO manche (num_manche,nom_theme,id_partie) ";
			requete += "VALUES (1,'" + nomTheme + "'," + idPartie + ") ";
			requete += "RETURNING id_manche,num_manche,nom_theme,id_partie ";

			rs = st.executeQuery(requete);
			
			rs.next();
			int newMancheID = rs.getInt(1);
			int numManche = rs.getInt(2);
			
			System.out.println("Création de la manche " + newMancheID + " ayant pour thème : " + nomTheme);
			
			requete = "";
			requete += "INSERT INTO comporter (id_manche,id_question,num_question) ";
			requete += "VALUES (" + newMancheID + "," + idQ1 + ",1), (" + newMancheID + "," + idQ2 + ",2), (" + newMancheID + "," + idQ3 + ",3) ";
			requete += "RETURNING * ";
			
			rs = st.executeQuery(requete);

			System.out.println("Nouvelle manche créée (id : " + newMancheID + "), questions : " + idQ1 + " " + idQ2 + " " + idQ3);
			
			s = newMancheID + ";" + numManche + ";" + nomTheme  + ";" + idPartie;

			
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();

			return "ERREUR:Requête PSQL incorrecte";
		}
		
		if(s.isEmpty())
			s = "ERREUR:Problème dans la création de la Manche";
		else
			s = "OK:" + s;
		
		return s;
	}
	
	public static BDConnexion connecter()
	{
		if(connexion == null)
		{
			bdc = new BDConnexion();
			System.out.println("Connexion BD Quizzz établie");
		}
		else
			System.out.println("Connexion BD déjà existante");

		
		return bdc;
	}
	
	public static void deconnecter()
	{
		try 
		{
			connexion.close();
			System.out.println("Connexion à la BD fermée\n");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
