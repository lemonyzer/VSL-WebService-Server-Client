package de.jxws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

public class ConsolenClient {
	
	private SA_TCPChatConnection incommingChatClient;
	private Test_TCPChatClient chatClient;

	public class Test_TCPChatServer extends Thread {
		
		
		private ServerSocket lokalChatServer;		//ServerSocket des lokalen TCP Chat Servers
		private int port;
		private Socket lokalclientSocket;			//Socket des lokalen TCP Clients mit dem der Client eine Verbindung zu einem Externen Chat Server aufbauen kann.
		private Socket incommingConnectionSocket;
//		private SA_TCPChatConnection incommingChatClient; 	//Socket des TCP Clients der Versuch sich mit dem lokal laufenden ChatServer zu verbinden
		private ConsolenClient_TalkDog console;
		private boolean exit; 
		private boolean isWaitingForClient;

		/**
		 * Erzeugt einen TCPServer auf dem übergebenen Parameter port.
		 * @param port TCP Port des Servers
		 */
		public Test_TCPChatServer(int port) {
			console = new ConsolenClient_TalkDog();
			this.port=port;
			exit=false;
			isWaitingForClient=true;
			incommingConnectionSocket=null;
//			run();
		}
		
		/**
		 * ermöglicht es den Thread zu beenden.
		 */
		public void shutdown() {
			exit=true;
		}
		
		/**
		 * ermöglicht das Anzeigen das Fensters
		 */
		public void showWindow() {
			if(incommingChatClient != null) {
				incommingChatClient.showWindow();
			}
			else
				console.TalkDogLocalError("Es existiert keine Server Session!");
		}
		
		@Override
		public void run() {
			console.TalkDogTCPChatServer("hello!");
			try {
				lokalChatServer = new ServerSocket(port);
				lokalChatServer.setSoTimeout(0); //kein Timeout! 
//				console.TalkDogLokalLine("Starte Server an Port: " + port);
			} catch (Exception E) {
//				console.TalkDogLocalError("Chat Server konnte nicht gestartet werden");
			} 
			if(lokalChatServer != null) {
				/* Server wurde gestartet */
//				new WaitingForClientThread().start();
				while (!lokalChatServer.isClosed()) {
					try {
						console.TalkDogTCPChatServer("Warte auf Client!");
						incommingConnectionSocket = lokalChatServer.accept();
						// TODO hier bleibt er hängen!
						/* ein Verbindung zum Client besthet */ 
						console.TalkDogTCPChatServer("Client: " + incommingConnectionSocket.getInetAddress().getHostAddress() + ":" + incommingConnectionSocket.getPort() + " verbunden");
					} catch (Exception E) {
						console.TalkDogTCPChatServerError("Fehler beim Verbindungsaufbau");
					}
					if (!incommingConnectionSocket.isClosed()) {
						/* Verbindung zum Client wird in Thread ausgelagert! */
						console.TalkDogTCPChatServer("Chat mit Client wird geöffnet.");
						incommingChatClient = new SA_TCPChatConnection(incommingConnectionSocket);		//neuen Thread erzeugen (wird automatisch gestartet)
						//incommingChatClient.start();												//Thread starten.
					}
					else {
						console.TalkDogTCPChatServerError("incommingChatClient Closed == true!");
					}
				}
//				console.TalkDogLokalLine("TCPChatServer: bye!");
//				console.TalkDogLokalLine("TCPChatServer: bye!");
//				console.TalkDogLokalLine("TCPChatServer: bye!");
			}
			super.run();
		}
		
	}
	
	public class Test_TCPChatClient extends Thread {
		

		class InputThread extends Thread {
			public void run() {
				try {
					while (true)
						line = inFromServer.readLine(); // Blocking
				} catch (IOException ex) {
					isConnected = false;
				}
			}
		}

		private BufferedReader inFromServer = null;
		private PrintWriter outToServer = null;
		private Socket socket = null;
		private String line = null;
		private boolean isConnected = false;
		private TCPChatFenster chatFenster = null;
		private String hostname;
		private int port;

		public Test_TCPChatClient(String hostname, int port) {
			this.hostname = hostname;
			this.port = port;
//			run();
		}
		
		public void showWindow() {
			if(chatFenster != null)
				chatFenster.setVisible(true);
			else
				console.TalkDogLocalError("Es existiert keine Client Session!");
		}
		
		public void setConnected(boolean isConnected) {
			this.isConnected = isConnected;
		}
		
		public void run() {
			//------------JOptionPane.showMessageDialog( null, "Client Chat gestartet, verbinde mit: " + hostname );
			try {
				chatFenster = new TCPChatFenster("Client");
				port=1988;
				socket = new Socket(hostname, port);
				socket.setSoTimeout(0);
				inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				chatFenster.setOutput(outToServer);
				chatFenster.statusAnhaengen("Connection to '" + hostname + "' on port " + port
						+ " established");
				isConnected = true;
				new InputThread().start();
				while (isConnected && chatFenster.isVisible()) {
					try {
						Thread.currentThread().sleep(1);
					} catch (InterruptedException ex) {
					}
					if (line != null) {
						chatFenster.incommingAnhaengen(line);
						line = null;
					}
				}
			} catch (IOException ex) {
			} finally {
				try {
					if (inFromServer != null)
						inFromServer.close();
					if (outToServer != null)
						outToServer.close();
					if (socket != null)
						socket.close();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog( null, "Chat durch Fehler beendet." );
				}
			}
			/* TODO super.run();*/
		}
		
		public void dispose() {
			if (chatFenster != null) {
				chatFenster.dispose();
			}
		}
		
	}
	
	
	
	private BufferedReader tastatureingabe;		//zur Eingabe über die Tastataur
	private String eingabe;						//beinhaltet den eingegebenen String
	//private Parser parser;						//zum Analisieren der Eingabe						
	////Analysiert die Eingabe und liefer einen String Befehl zum Absenden an die Server zurück
	
	/* Verbindungsdatenfelder */
	String url;
	
	private boolean connected;
	private Service service;
	private Server server;
	private String username;		//über eine Session verwendeter Username
	private String passwort;	//das dazugehörige Passwort
	
	private ConsolenClient_TalkDog console;
	
	private String ip;		// Externe IP Adresse um eine p2p Verbindung aufbauen zu können.
//	private SA_TCPChatServer chatServer;
	
//	private String cmdServerIP;					//IP des CMDServers
//	private int cmdServerPort;					//Port des CMDServers
	
	
	/**
	 * Konstruktor
	 * 
	 */
	public ConsolenClient() {
//		debugging = true;						//Debuggingnachrichten auf der Konsole angezeigt,
		console = new ConsolenClient_TalkDog();
		tastatureingabe = new BufferedReader(new InputStreamReader(System.in));
		eingabe = "";
		connected = false;
		
		/* Externe IP */
		try {
			ip = getExternalIP();
//			console.TalkDogLokalLine("Externe IP: " + ip);
			// da Projekt im Lokalen Netzwerk laufen soll, kommt hier nur eine Lokale IP zum Einsatz
			ip = getLokalIP();
			console.TalkDogLokalLine("Lokale IP:" + ip);
		} catch (IOException e1) {
			/* bei Fehler, setze Lokale Netzwerk IP bzw. private Host IP */ 
			console.TalkDogLocalError("Fehler beim erhalten der externen IP");
			ip = getLokalIP();
			if(ip.equals("127.0.0.1")) {
				console.TalkDogLokalLine("interne IP:" + ip);
			}
			else
				console.TalkDogLokalLine("Lokale IP:" + ip);
		}
		
		
		/* Chat */
		Test_TCPChatServer chatServer = new Test_TCPChatServer(1988);
		chatServer.start();
//		SA_TCPChatServer chatServer = new SA_TCPChatServer(1988);	// Chat Server des Clients.
		
		/* JAX-WS */
//		service = new ServerImplementorService();
		service = null;
//		server = service.getServerImplementorPort();
		server = null;
		
	}
	
	/**
	 * Fragt im Internet nach der IP
	 * @return
	 * @throws IOException
	 */
	private String getExternalIP() throws IOException {
		String antwort;
		URL whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));
		antwort = in.readLine();
		console.TalkDogLokalLine("Antwort des Servers: " + antwort);
		return antwort; //you get the IP as a String
	}
	
	/**
	 * Liefert die Lokale IP, und falls es nicht klappt die interne std. IP
	 * @return
	 */
	private String getLokalIP() {
		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}
	
	
	/**
	 * Diese Methode wird beim Clientstart ausgeführt.
	 * Sie sorgt dafür, dass eine Verbindung 
	 * und eine erfolgreiche Anmeldung am Server steht.
	 */
	public boolean initialisierung(){
//		console.TalkDogLokalLine("Aktuelle Zeit: " + System.currentTimeMillis());
		while(service==null) {
			console.TalkDogLokalLine("Std Server: http://" + getLokalIP() + ":8080/jaxws/server");
			console.TalkDogLokalNoLine("Bitte geben Sie die Adresse des Servers ein: ");
			try {
				eingabe = tastatureingabe.readLine();
				if(eingabe.equals("")) {
					url = "http://" + getLokalIP() + ":8080/jaxws/server";
				}
				else
					url = eingabe;
			} catch (IOException e) {
				console.TalkDogLocalError("Fehler bei der Eingabe der URL");
				e.printStackTrace();
			}
			try {
				service = Service.create(
						new URL( url + "?wsdl" ),
						new QName( "http://jxws.de/", "ServerImplementorService" ) );
				server = service.getPort( Server.class );
				return true;
			} catch( WebServiceException ex ) {
				console.TalkDogLocalError("Verbindung gescheitert!");
//						Thread.sleep(1000);
			} catch (MalformedURLException e) {
				console.TalkDogLocalError("MalformedURLException! url: " + url + "?wsdl");
				//e.printStackTrace();
			}
			try {
				/* falls nur eine IP in url eingegeben wurde */
				service = Service.create(
						new URL( "http://" + url + ":8080/jaxws/server" + "?wsdl" ),
						new QName( "http://jxws.de/", "ServerImplementorService" ) );
				server = service.getPort( Server.class );
				return true;
			} catch( WebServiceException ex ) {
				console.TalkDogLocalError("Verbindung gescheitert!");
//						Thread.sleep(1000);
			} catch (MalformedURLException e) {
				console.TalkDogLocalError("MalformedURLException! url: " + "http://" + url + ":8080/jaxws/server" + "?wsdl");
				//e.printStackTrace();
			}
		}
		return false;
	}
	

	/**
	 * Versucht eine Verbindung zum WebServer aufzubauen.
	 * @return
	 */
	public boolean connect() {
		boolean status;
		try {
			status = server.connect();
		} catch (Exception e) {
			status = false;
		}
		return status;
	}
	
	/** 
	 * Versucht einen Benutzer am WebServer anzumelden
	 * @return
	 */
	public boolean login() {
		boolean error=false;
		
		do {
			try {
				do {
					console.TalkDogLokalNoLine("Username: ");
					username = tastatureingabe.readLine().toLowerCase(); //nur Kleinbuchstaben
					if(username.length() == 0) {
						/* kein Text eingegeben */
						console.TalkDogLocalError("Kein Benutzername eingegeben");
						error = true;
					}
					else
						error=false;
				}while(error);
				do {
					console.TalkDogLokalNoLine("Passwort: ");
					passwort = tastatureingabe.readLine();
					if(passwort.length() == 0) {
						/* kein Text eingegeben */
						console.TalkDogLocalError("Kein Passwort eingegeben");
						error = true;
					}
					else
						error=false;
				}while(error);
				
				int result = server.login(username, passwort,ip); 	// Anmeldevorgang und Auswertung
				if(result == server.getKonstante("LOGIN_SUCCESS")) {
//				if(result == server.LOGIN_SUCCESS) {
					error=false;
					console.TalkDogLokalLine(username + " erfolgreich angemeldet!");
				}
				if(result == server.getKonstante("LOGIN_FAIL_ACTIVATION")) {
					error=true;
					console.TalkDogLocalError(username + " wurde noch nicht vom Administrator aktiviert.");
					return true; // nicht aktiviert aber darf sich anmelden.
				}
				if(result == server.getKonstante("LOGIN_FAIL_PASSWORT")) {
					error=true;
					console.TalkDogLocalError(username + " falsches Passwort!");
				}
				if(result == server.getKonstante("LOGIN_FAIL_USERNAME")) {
					/* Benutzer existiert noch nicht */
					error=true;
					console.TalkDogLocalError(username + " existiert nicht!");
					do {
						console.TalkDogLokalNoLine(username + " Benutzer registrieren? (ja/nein)");
						try {
							eingabe=tastatureingabe.readLine();
							if(eingabe.equals("ja")) {
								if(server.registration(username, passwort,ip)) {
									console.TalkDogLokalLine(username + " registriert. Administrator muss User akzeptieren!");
									error = true;
									return true;	// nicht aktiviert aber darf sich anmelden.
								}
								else {
									error = true;
									console.TalkDogLocalError(username + " NICHT registriert.");
									break;
								}
							}
							else if(eingabe.equals("nein")){
								error=true;
								break;
							}
						} catch (IOException e) {
							console.TalkDogLocalError("Abfrage zum Registrieren des Clients");
							e.printStackTrace();
						}
					}while(!eingabe.equals("ja") || !eingabe.equals("nein"));
					return false;
				}
				
			} catch (IOException e) {
				error=true;
				e.printStackTrace();
			}
		}while(error);
		
		return true;
	}
	
	/**
	 * Diese Methode geht die einzelnen Menüs durch, bis der Client geschlossen wird.
	 */
	public void menuRoot() {
		do {
			/* so lange keine EXIT bzw exit eingegeben wurde soll die Methode laufen */
			try {
				console.TalkDogLokalNoLine("ROOT: ");
				eingabe=tastatureingabe.readLine().toLowerCase();	//eingegebener String wird in Kleinbuchstaben gewandelt
			} catch (IOException e) {
				console.TalkDogLocalError("Fehler bei der Eingabe.");
				eingabe="";
				e.printStackTrace();
			}
			
/* Mögliche Eingaben im Hauptmenu */			
//			if(eingabe.equals("file")) {
//				// TODO
//				/* Benutzer öffnet Dateimanager */
//				menuFile();
//			}
			if(eingabe.equals("date")) {
				/* Benutzer öffnet Kalender */
				menuKalender();
			}
			else if(eingabe.startsWith("chat")) {
				/* Chat Menu öffnen */
				menuChat();
			}
			else if (eingabe.equals("admin")) {
				/* Benutzer möchte ins Administrationsmenu */
				if(server.getRights(username,passwort,ip) == server.getKonstante("User.ADMINISTRATOR")) {
					menuAdmin();
				}
				else 
					console.TalkDogLocalError("Sie sind kein Administrator!");
			}
			else if(eingabe.equals("?")) {
				/* Benutzer fragt nach Befehlsliste */
				console.TalkDogLokalLine("Mögliche Befehle: \n" +
						"date\n" +
						"admin\n" +
						"chat\n" +
						"exit");
			}
			else if(eingabe.equals("exit")) {
				eingabe="";
				break;
			}
			else {
				console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
			}
		}while(!eingabe.equals("exit"));
		eingabe="";	//entfernt zuletzt eingegebenen Text ("exit") sodass das nächste Menu nicht auch geschlossen wird.
	}
	
	/**
	 * Diese Methode geht die einzelnen Menüs durch, bis der Client geschlossen wird.
	 */
	public void menuAdmin() {
		do {
			/* so lange keine EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
			try {
				console.TalkDogLokalNoLine("ADMIN: ");
				eingabe=tastatureingabe.readLine().toLowerCase();	//eingegebener String wird in Kleinbuchstaben gewandelt
			} catch (IOException e) {
				console.TalkDogLocalError("Fehler bei der Eingabe.");
				eingabe="";
				e.printStackTrace();
			}
			
			/* Mögliche Eingaben im Untermenu ADMIN (Administrationsmenu) */			
			if(eingabe.equals("users aktiv")) {
				/* Benutzer fordert eine Userlist an */
				ArrayList<String> Liste;
				try {
					Liste = (ArrayList<String>) server.showUsersActiveInLastTenMin(username, passwort,ip);
					for (String string : Liste) {
						console.TalkDogLokalLineIncomming(string);
					}
				} catch (Exception e) {
					console.TalkDogLocalError("Fehler beim Anzeigen der Userliste.");
				}
			}
			else if(eingabe.equals("users all")) {
				/* Benutzer fordert eine Userlist an */
				ArrayList<String> Liste;
				try {
					Liste = (ArrayList<String>) server.showAllUsers(username, passwort,ip);
					for (String string : Liste) {
						console.TalkDogLokalLineIncomming(string);
					}
				} catch (Exception e) {
					console.TalkDogLocalError("Fehler beim Anzeigen der Userliste.");
				}
			}
			else if(eingabe.startsWith("accept ") || eingabe.startsWith("activate ")) {
				/* Admin akzeptiert registrierung */
				try {
					String user=eingabe.split(" ")[1];
					if(server.activateuser(username, passwort,ip, user))
						console.TalkDogLokalLine(user + " wurde aktiviert!");
					else
						console.TalkDogLocalError("Fehler bei der Aktivierung von " + user);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else if(eingabe.startsWith("disable ")) {
				/* Admin möchte Benutzer deaktivieren */
				try {
					String user=eingabe.split(" ")[1];
					if(!user.equals("admin")) {
						if(server.deactivateuser(username, passwort,ip, user))
							console.TalkDogLokalLine(user + " wurde deaktiviert!");
						else
							console.TalkDogLocalError("Fehler bei der Deaktivierung von " + user);
					}
					else
						console.TalkDogLocalError("admin darf nicht deaktiviert werden!");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			else if(eingabe.startsWith("delete ")) {
				/* Admin möchte Benutzer deaktivieren */
				try {
					String user=eingabe.split(" ")[1];
					if(!user.equals("admin")) {
						if(server.deleteuser(username, passwort,ip, user))
							console.TalkDogLokalLine(user + " wurde gelöscht!");
						else
							console.TalkDogLocalError("Fehler bei der Löschung von " + user);
					}
					else
						console.TalkDogLocalError("admin darf nicht gelöscht werden!");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			else if(eingabe.equals("exit")) {
				eingabe="";
				break;
			}
			else if(eingabe.equals("?")) {
				/* Benutzer fragt nach Befehlsliste */
				console.TalkDogLokalLine("Mögliche Befehle: \n" +
						"users aktiv |zeigt alle Nutzer an, die in den letzten 10 min. aktiv waren|\n" +
						"users all |zeigt alle Nutzer aus der Datenbank an|\n" +
						"accept <USER> |Benutzer aktivieren|\n" +
						"activate <USER> |Benutzer aktivieren|\n" +
						"disable <USER> |Benutzer deaktivieren|\n" +
						"delete <USER> |Benutzer löschen|\n" +
						"exit |ADMIN Menu verlassen|");
			}
			else {
				console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
			}
		}while(!eingabe.equals("exit"));
		eingabe="";
	}
//	/**
//	 * Diese Methode geht die einzelnen Menüs durch, bis der Client geschlossen wird.
//	 */
//	public void menuFile() {
//		do {
//			/* so lange keine EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
//			try {
//				console.TalkDogLokalNoLine("FILE: ");
//				eingabe=tastatureingabe.readLine().toLowerCase();	//eingegebener String wird in Kleinbuchstaben gewandelt
//			} catch (IOException e) {
//				console.TalkDogLocalError("Fehler bei der Eingabe.");
//				eingabe="";
//				e.printStackTrace();
//			}
//			
//			/* Mögliche Eingaben im Untermenu file (Dateimanager) */			
//			if(eingabe.equals("dir")) {
//				/* Benutzer fordert Dateiliste an */
//				
//			}
//			else if(eingabe.equals("open")) {
//				/* Benutzer fordert Datei zum Öffnen an */
//				
//			}
//			else if(eingabe.equals("upload")) {
//				/* Benutzer versucht Datei an Server zu schicken */
//				
//			}
//			else if(eingabe.equals("download")) {
//				/* Benutzer versucht Datei von Server herunter zu laden */
//				
//			}
//			else if(eingabe.equals("exit")) {
//				eingabe="";
//				break;
//			}
//			else {
//				console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
//			}
//		}while(!eingabe.equals("exit"));
//		eingabe="";
//	}
	/**
	 * Menu des Chats
	 */
	public void menuChat() {
		do {
			/* so lange keine EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
			try {
				console.TalkDogLokalNoLine("CHAT: ");
				eingabe=tastatureingabe.readLine().toLowerCase();	//eingegebener String wird in Kleinbuchstaben gewandelt
			} catch (IOException e) {
				console.TalkDogLocalError("Fehler bei der Eingabe.");
				eingabe="";
				e.printStackTrace();
			}
			
			/* Mögliche Eingaben im Untermenu file (Dateimanager) */			
			if(eingabe.equals("show users")) {
				/* Benutzer fordert Userlist an */
				ArrayList<String> Liste;
				try {
					Liste = (ArrayList<String>) server.showIPsToChat(username, passwort,ip);
					for (String string : Liste) {
						console.TalkDogLokalLineIncomming(string);
					}
				} catch (Exception e) {
					console.TalkDogLocalError("Fehler beim Anzeigen der Userliste.");
				}
			}
			else if(eingabe.startsWith("connect ")) {
				/* Benutzer startet Chat */
				String remoteip="1.1.1.1";
				try {
					String user=eingabe.split(" ")[1];
					remoteip = server.getIPFromUser(username, passwort,ip, user);
					if(remoteip.equals("") || remoteip.equals(""))
						console.TalkDogLocalError("Fehler beim Erhalten der IP von " + user);
					else {
						console.TalkDogLokalLine(user + " hat IP: " + remoteip);
						chatClient = new Test_TCPChatClient(remoteip, 1988);
						chatClient.start();
//						SA_TCPChatClient chatClient = new SA_TCPChatClient("127.0.0.1", 1988); //Chat Thread anlegen und starten!
//						chatClient.dispose();
//						chatClient.start();													//Chat im Thread starten!
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else if(eingabe.equals("show server")) {
				/* Benutzer lasst sich server fenster anzeigen */
				incommingChatClient.showWindow();
				
			}
			else if(eingabe.equals("show client")) {
				/* Benutzer lasst sich client fenster anzeigen */
				chatClient.showWindow();
			}
			else if(eingabe.equals("?")) {
				/* Benutzer fragt nach Befehlsliste */
				console.TalkDogLokalLine("Mögliche Befehle: \n" +
						"show users |zeigt alle Nutzer mit IP, die in den letzten 10 min. aktiv waren|\n" +
						"connect <USER> |startet eine Chat Session mit gewünschtem User|\n" +
						"show server |Server Fenster anzeigen|\n" +
						"show client |Client Fenster anzeigen|\n" +
						"? |Befehle von diesesm Menu|\n" +
						"exit |CHAT Menu verlassen|");
			}
			else if(eingabe.equals("exit")) {
				eingabe="";
				break;
			}
			else {
				console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
			}
		}while(!eingabe.equals("exit"));
		eingabe="";
	}
	
	/**
	 * Diese Methode geht die einzelnen Menüs durch, bis der Client geschlossen wird.
	 */
	public void menuKalender() {
		do {
			/* so lange kein EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
			try {
				console.TalkDogLokalNoLine("DATE: ");
				eingabe=tastatureingabe.readLine().toLowerCase();	//eingegebener String wird in Kleinbuchstaben gewandelt
			} catch (IOException e) {
				console.TalkDogLocalError("Fehler bei der Eingabe.");
				eingabe="";
				e.printStackTrace();
			}
			
			/* Mögliche Eingaben im Untermenu des Kalender */			
			if(eingabe.equals("show all")) {
				/* Benutzer fordert alle Kalendereinträge an */
				ArrayList<String> terminListe;
				try {
					terminListe = (ArrayList<String>) server.showKalender(username, passwort,ip);
					int termin_count=terminListe.size()-1;
					if(termin_count==0) {
						console.TalkDogLocalError("keine Termine vorhanden!");
					}
					else {
						for (String string : terminListe) {
							console.TalkDogLokalLineIncomming(string);
						}
					}
				} catch (Exception e) {
					console.TalkDogLocalError("Fehler beim Anzeigen der Terminliste.");
				}
			}
			else if(eingabe.startsWith("show from ")) {
				/* Benutzer fordert alle Kalendereinträge von einem bestimmten Nutzer an */
				String fromUsername = (String) eingabe.subSequence("show from ".length(),eingabe.length());
				console.TalkDogLokalLine("From Username: " + fromUsername);
				ArrayList<String> terminListe;
				try {
					terminListe = (ArrayList<String>) server.showKalenderFrom(username,passwort,ip,fromUsername);
					int termin_count=terminListe.size()-1;
					if(termin_count==0) {
						console.TalkDogLocalError(fromUsername + " hat keine Termine angelegt!");
					}
					else {
						for (String string : terminListe) {
							console.TalkDogLokalLineIncomming(string);
						}
					}
				} catch (Exception e) {
					console.TalkDogLocalError("Fehler beim Anzeigen der Terminliste von " + fromUsername + "!");
				}
			}
			else if(eingabe.equals("add")) {
				/* Benutzer legt neuen Termin an */
				String beginn="", ende="", thema="", ort="", wiederholung="";
				do {
					/* so lange keine EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
					try {
//						Runtime.getRuntime().exec("cls");
						console.TalkDogLokalLine("Neuer Termin");
						console.TalkDogLokalLine("---------------------");
						console.TalkDogLokalLine("Beginn:\t\t" + beginn);
						console.TalkDogLokalLine("Ende:\t\t" + ende);
						console.TalkDogLokalLine("Thema:\t\t" + thema);
						console.TalkDogLokalLine("Ort:\t\t\t" + ort);
						console.TalkDogLokalLine("Wiederholung:\t" + wiederholung);
						console.TalkDogLokalNoLine("DATE\\ADD: ");
						eingabe=tastatureingabe.readLine();
					} catch (IOException e) {
						console.TalkDogLocalError("Fehler bei der Eingabe.");
						eingabe="";
						e.printStackTrace();
					}
					if(eingabe.startsWith("beginn")) {
						beginn = eingabe.substring( ((String)"beginn").length() );
					}
					else if (eingabe.startsWith("ende")) {
						ende = eingabe.substring( ((String)"ende").length() );
					}
					else if (eingabe.startsWith("thema")) {
						thema = eingabe.substring( ((String)"thema").length() );
					}
					else if (eingabe.startsWith("ort")) {
						ort = eingabe.substring( ((String)"ort").length() );
					}
					else if (eingabe.startsWith("wiederholung")) {
						wiederholung = eingabe.substring( ((String)"wiederholung").length() );
					}
					else if (eingabe.equals("add")) {
						server.addTermin(username, passwort,ip, beginn, ende, thema, ort, wiederholung);
						break;
					}
					else if(eingabe.equals("?")) {
						/* Benutzer fragt nach Befehlsliste */
						console.TalkDogLokalLine("Mögliche Befehle: \n" +
								"beginn <dd.MM.yyyy HH.mm.ss>\n" +
								"ende <dd.MM.yyyy HH.mm.ss>\n" +
								"thema <Text>\n" +
								"ort <Text>\n" +
								"wiederholung <Text>\n" +
								"add |Termin wird eingetragen|\n" +
								"exit |zum Abbrechen|");
						console.TalkDogLokalNoLine("Weiter mit Return...");
						try {
							tastatureingabe.read();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (eingabe.equals("exit")) {
						break;
					}
					else {
						console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
						console.TalkDogLocalError("Geben Sie ? ein um eine Befehlsliste zu erhalten");
					}
				}while(!eingabe.equals("exit"));
				eingabe = "";
			}
			else if(eingabe.equals("remove")) {
				int terminid=-1;
				int termin_count=0;
				/* Benutzer löscht Kalendereinträge */
				ArrayList<String> terminListe;
				do {
					/* so lange keine EXIT bzw exit eingegeben wurde soll das Menu offen bleiben*/
					try {
						terminListe = (ArrayList<String>) server.showKalender(username, passwort,ip);
						termin_count=terminListe.size()-1;
						if(termin_count==0) {
							console.TalkDogLocalError("kein Termin vorhanden!");
							break;
						}
						int i=-1;
						for (String string : terminListe) {
							if(i==-1)
								console.TalkDogLokalLineIncomming("ID\t" +  string);
							else
								console.TalkDogLokalLineIncomming(i + "\t" + string);
							i++;
						}
					} catch (Exception e) {
						console.TalkDogLocalError("Fehler beim Anzeigen der Terminliste.");
					}
					try {
						// ID des zu löschenden Termins erhalten 
						console.TalkDogLokalNoLine("ID des zu löschenden Termins: ");
						eingabe=tastatureingabe.readLine();
						terminid=Integer.parseInt(eingabe);
					}
					catch (NumberFormatException e) {
						terminid=-1;
					}
					catch (IOException e) {
						console.TalkDogLocalError("Fehler bei der Eingabe.");
						eingabe="";
						e.printStackTrace();
					}
					if((terminid<termin_count) && (terminid>-1)) {
						server.removeTermin(username, passwort, ip, terminid);
					}
					else if(eingabe.equals("?")) {
						/* Benutzer fragt nach Befehlsliste */
						console.TalkDogLokalLine("Mögliche Befehle: \n" +
								"<ID>\n" +
								"exit |zum Abbrechen|");
					}
					else if (eingabe.equals("exit")) {
						break;
					}
				}while(!eingabe.equals("exit"));
				eingabe = "";
			}
			else if(eingabe.equals("?")) {
				/* Benutzer fordert Befehlsliste im Kalender an */
				console.TalkDogLokalLine("Mögliche Befehle: \n" +
						"show all\n" +
						"show from <USER>\n" +
						"add\n" +
						"remove\n" +
						"exit");
			}
			else if(eingabe.equals("exit")) {
				eingabe="";
				break;
			}
			else {
				console.TalkDogLocalError("Unbekannter Befehl " + eingabe);
			}
		}while(!eingabe.equals("exit"));
		eingabe = "";
	}
	
	/**
	 * Fragt ob der Client nach einer Session beenden werden soll und gibt das Ergebniss zurück
	 * @return
	 */
	public boolean exit() {
		do {
			console.TalkDogLokalNoLine("Client beenden? (ja/nein)");
			try {
				eingabe=tastatureingabe.readLine();
				if(eingabe.equals("ja")) {
//					chatServer.shutdown();
					return true;
				}
				else if(eingabe.equals("nein")){
					return false;
				}
			} catch (IOException e) {
				console.TalkDogLocalError("Abfrage zum Beenden des Clients");
				e.printStackTrace();
			}
		}while(!eingabe.equals("ja") || !eingabe.equals("nein"));
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
//		      String line;
//		      Process p = Runtime.getRuntime().exec("TCPChatClient.bat");
//		      BufferedReader bri = new BufferedReader
//		        (new InputStreamReader(p.getInputStream()));
//		      BufferedReader bre = new BufferedReader
//		        (new InputStreamReader(p.getErrorStream()));
//		      while ((line = bri.readLine()) != null) {
//		        System.out.println(line);
//		      }
//		      bri.close();
//		      while ((line = bre.readLine()) != null) {
//		        System.out.println(line);
//		      }
//		      bre.close();
//		      p.wait();
//		      System.out.println("Done.");
//		    }
//		    catch (Exception err) {
//		      err.printStackTrace();
//		    }
		
		ConsolenClient beast = new ConsolenClient();
		do {
			try {
				beast.initialisierung();
			} catch (Throwable e) {
				System.out.println("XX Fehler bei der Initialisierung des Clients!");
				e.printStackTrace();
			}
			// Serververbindungsdaten eingeben.
			if(beast.connect()) {		// Serververbindung überprüfen.
				/* Verbindung zum WebServer steht */
				while(!beast.login()) {
					/* so lange er nicht angemeldet ist User eingabe erneut anzeigen */
					if(beast.login()) {
						break;
					}
				}
				/* Anmeldung am WebServer erfolgreich */
				beast.menuRoot();
			}
			else {
				/* Verbindungsproblem */
				
			}
		} while(!beast.exit());
	}


}
