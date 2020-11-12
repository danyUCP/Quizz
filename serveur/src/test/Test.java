package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) 
	{
		String url = "jdbc:postgresql://localhost/";
		String name = "postgres";
		String id = "postgres";
		String mdp = "postgres";

		Connection connexion = null;
		
		String req1 = "SELECT * FROM obj_inventaire WHERE marque = 'Dell'";
		String req2 = "SELECT * FROM composant";

		try 
		{
			connexion = DriverManager.getConnection(url + name, id, mdp);
			System.out.println("Connexion à la BD établie\n");
			
			Statement st = connexion.createStatement();
			ResultSet rs = st.executeQuery(req1);
			
			String s = "";
			
			while(rs.next())
			{
				s += rs.getString("no_invent") + "\t";
				s += rs.getString("marque") + "\t";
				s += rs.getString("date_achat") + "\t";
				s += rs.getString("fournisseur") + "\n";
			}
			
			System.out.println(s);
			
			rs = st.executeQuery(req2);
			s = "";
			
			while(rs.next())
			{
				s += rs.getString(1) + "\t";
				s += rs.getString(2) + "\t";
				s += rs.getString(3) + "\n";
			}
			
			System.out.println(s);
			
			connexion.close();
			System.out.println("Connexion à la BD fermée\n");
		} 
		catch (SQLException e)  
		{
			e.printStackTrace();
		}

	}

}
