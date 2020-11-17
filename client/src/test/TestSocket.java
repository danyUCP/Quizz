package test;

import connection.SocketClient;

public class TestSocket 
{
	public static void main(String[] args) 
	{
		
		SocketClient socket = new SocketClient();
		
		socket.envoyerRequete("a");
		System.out.println(socket.ecouter());
		
		
		socket.deconnecter();
		
		
		
		/*
		Socket soc = null;
		
		try 
		{
			soc = new Socket("127.0.0.1", 2020);

			System.out.println("Port de communication côté client : " + soc.getLocalPort());
			System.out.println("Nom de l'hôte distant : " + soc.getRemoteSocketAddress());

			//Ecriture dans le flux
			String requete = "Commande depuis le client\r\n";
			
			BufferedOutputStream bos = new BufferedOutputStream(soc.getOutputStream());
			bos.write(requete.getBytes());
			bos.flush();
			
			bos.write(("" + soc.getLocalPort()).getBytes());
			bos.flush();
			//Lecture dans le flux
			BufferedInputStream bis = new BufferedInputStream(soc.getInputStream());
			
			String contenu = "";
			int stream;
			while((stream = bis.read()) != -1)
			{
				contenu += (char)stream;
			}
			
			System.out.println(contenu);
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				soc.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		*/

	}

}
