/**
 * Server.java
 * @author Aryan Layes
 * Montag, 09. Januar 2012
 * Version 1.0
 */

package de.jxws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.jws.WebMethod;
import javax.jws.WebService;


// TODO: ACHTUNG  
@WebService(endpointInterface="de.jxws.Server")
// TODO: ACHTUNG

public class ServerImplementor implements Server {
	
	private File rootVerzeichnis;
	private File userAdminVerzeichnis;
	private static String currentServerPath;
	private String setupFilePath;
	private File flog;
	private File flastlog;
	private TalkDog console;
	
	private Logger log;
	private java.util.Date now; // aktueller Zeitstempel	
	private static final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH.mm.ss"); // format des aktuellen Zeitstempels
	
	private HashMap<String, User> dbuser;
	private HashMap<String, Integer> dbkonst;
	private HashMap<String,String> usedData;
	private HashMap<String, ArrayList<Termin>> todoLists;
	/**
	 * Konstruktor der Serverklasse
	 */
	public ServerImplementor() {
		
	/* Konstanten */
		dbkonst = new HashMap<String, Integer>();
		dbkonst.put("LOGIN_SUCCESS", 0);
		dbkonst.put("LOGIN_FAIL_USERNAME", 1);
		dbkonst.put("LOGIN_FAIL_PASSWORT", 2);
		dbkonst.put("LOGIN_FAIL_ACTIVATION", 3);
		
		dbkonst.put("User.ADMINISTRATOR", 999);
		dbkonst.put("User.USER", 666);
		dbkonst.put("User.GAST", 111);
		
	/* Logging */
		log = Logger.getLogger( ServerImplementor.class.getName() );
		log.setLevel(Level.ALL);							//keine Logevents sollen gefiltert werden. 
		flog = new File(System.getProperty("user.dir")+"\\server.log");
		flastlog = new File(System.getProperty("user.dir")+"\\server_last.log");
		
		FileHandler fh;
	    SimpleFormatter formatter = new SimpleFormatter();
		try {
			fh = new FileHandler("log.txt");
			fh.setFormatter(formatter);
			log.addHandler(fh);
		} catch (SecurityException e) {
			console.TalkDogLocalError("SecurityException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			console.TalkDogLocalError("IOException:" + e);
			e.printStackTrace();
		}
		/*HasMap aus userlist.txt*/
		dbuser = new HashMap<String, User>();			// Userdatenbank, wird später über XML, Textdatei, Registry oder SQL gespeichert
		createUserListfromData();
		createTodoListfromData();
		usedData= new HashMap<String,String>();
		//TODO Arman
		//Pfade Definieren
		currentServerPath = ".\\src\\de\\jxws\\serverfiles";
		setupFilePath=".\\src\\de\\jxws\\setupfiles";
		
		
	/*Rest initialisierung */
		User admin = new User("admin", "layes");
		admin.setAccepted(true);
		admin.setRights(User.ADMINISTRATOR);
		dbuser.put(admin.getUserName(), admin);
		
		User user1 = new User("user1", "user1");
		user1.setAccepted(true);
		user1.setRights(User.USER);
		user1.setLastseen(Long.parseLong("1328500710808"));
		dbuser.put(user1.getUserName(), user1);
		
		User user2 = new User("user2", "user2");
		user2.setAccepted(true);
		user2.setRights(User.USER);
		user2.setLastseen(new Long("1328500880178"));
		dbuser.put(user2.getUserName(), user2);
		
		
		console = new TalkDog();
		rootVerzeichnis = new File(System.getProperty("user.dir"));

	/* Verzeichnisverwaltung */
//		userRootVerzeichnis = new File(System.getProperty("user.dir")+"\\_user");
//		userAdminVerzeichnis = new File(System.getProperty("user.dir")+"\\_user\\admin");
//		if(!userRootVerzeichnis.exists()) {
//			/* _user Verzeichnis existiert nicht */
//			log.fine("userVerzeichnis existiert nicht und wird erstellt.");
//			userRootVerzeichnis.mkdirs();
//		}
//		if(!userRootVerzeichnis.isDirectory()) {
//			/* _user Verzeichnis ist eine DATEI */
//			log.log(Level.WARNING, "userVerzeichnis ist eine Datei und kein Ordner! Bitte datei umbennen oder löschen!");
//			log("Userverzeichnis ist eine Datei und kein Ordner! Bitte datei umbennen oder löschen!");
//		}
//		if(!userAdminVerzeichnis.exists()) {
//			/* es existiert kein Admin Verzeichnis */
//			log.fine("userAdminVerzeichnis existiert nicht und wird erstellt.");
//			userAdminVerzeichnis.mkdirs();
//			FileWriter fUserAdmin;
//			try {
//				fUserAdmin = new FileWriter(userAdminVerzeichnis+"\\admin.txt");
//				
//			} catch (IOException e) {
//				log.log(Level.WARNING, "Problem beim erstellen der Userdatei admin.txt");
//				e.printStackTrace();
//			}
//		}
//		if(!userAdminVerzeichnis.isDirectory()) {
//			log.log(Level.WARNING, "userAdminVerzeichnis ist eine Datei und kein Ordner! Bitte Datei umbennen oder löschen!");
//			log("Adminverzeichnis ist eine Datei und kein Ordner! Bitte datei umbennen oder löschen!");
//		}
		
	}
	
	
	/**
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		now = new java.util.Date();
		FileWriter fServerLog;
		FileWriter fServerLogAktuell;
		
		try {
			// FileOutputStream, der Daten an Datei server.log anhängt
			fServerLog = new FileWriter(flog,true);
			//Nachricht mit Zeilenumbruch in logdatei schreiben.
			fServerLog.write(sdf.format(now) + " " + msg + System.getProperty("line.separator"));
			fServerLog.flush();
		    // Schließt den Stream
			fServerLog.close();
		} catch (IOException e) {
			console.TalkDogLocalError("IOException server.log");
			log.log( Level.WARNING, "oh oh", e ); 
			e.printStackTrace();
		}
		try {
			// FileOutputStream, der Daten an Datei server_last.log überschreibt
			fServerLogAktuell = new FileWriter(flastlog);
			//Nachricht mit Zeilenumbruch in logdatei schreiben.
			fServerLogAktuell.write(sdf.format(now) + " " + msg + System.getProperty("line.separator"));
			fServerLogAktuell.flush();
		    // Schließt den Stream
			fServerLogAktuell.close();
		} catch (IOException e) {
			console.TalkDogLocalError("IOException server_last.log");
			log.log( Level.WARNING, "oh oh", e ); 
			e.printStackTrace();
		}

	}
	
	/**
	 * Liefert den Pfad zum aktuellen Benutzerverzeichnis zurück
	 * @return
	 */
	public String getDir() {
		for (int i = 0; i < rootVerzeichnis.listFiles().length; i++) {
			System.out.println( rootVerzeichnis.listFiles()[i] );
		}
		return rootVerzeichnis.listFiles().toString();
	}
	/**
	 * Liefert true, wenn der Server an ist.
	 * @return
	 */
	public boolean connect() {
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ServerImplementor dateServer = new ServerImplementor();
		dateServer.getDir();
		dateServer.login("aryan", "a", "127.0.0.1");
		dateServer.registration("admin", "admin", "127.0.0.1");
		dateServer.registration("aryan", "a", "127.0.0.1");
		dateServer.login("aryan", "a", "127.0.0.1");
		dateServer.activateuser("aryan", "a", "127.0.0.1", "admin");
		dateServer.activateuser("admin", "layes", "127.0.0.1", "aryan");
		dateServer.activateuser("aryan", "a", "127.0.0.1", "admin");
		dateServer.changepasswort("aryan", "a", "127.0.0.1", "logo");
		dateServer.login("aryan", "a", "127.0.0.1");
		dateServer.login("aryan", "logo", "127.0.0.1");
		dateServer.addTermin("aryan", "logo", "127.0.0.1", "10.02.1988", "10.02.2020", "Das Leben das Ryan", "", "keine");
		TalkDog console = new TalkDog(); 
		ArrayList<String> Kalender = new ArrayList<String>();
		try {
			Kalender = dateServer.showKalender("aryan", "logo", "127.0.0.1");
			for (String string : Kalender) {
				console.TalkDogLokalLine(string);
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//System.out.println(dateServer.getDir());

	}
	
	/**
	 * Kontrolliert ob der angegebene Benutzer in der DB existiert und das angegebene Passwort mit dem in der DB übereinstimmt 
	 * desweiteren muss die Registrierung vom Administrator akzeptiert worden sein.
	 * @param username
	 * @param passwort
	 * @return
	 */
//	public boolean login(String username, String passwort) {
//		User aktuellerNutzer;
//		now = new java.util.Date();
//		if(dbuser.containsKey(username)) {
//			/* Benutzername ist korrekt */
//			aktuellerNutzer = dbuser.get(username);
//			if(aktuellerNutzer.getUserPasswort().equals(passwort)) {
//				/* Passwort ist korrekt */
//				if(aktuellerNutzer.getAccepted()) {
//					/* Benutzer ist von Administrator aktiviert worden */
//					aktuellerNutzer.setLastseen(sdf.format(now));
//					log.fine(sdf.format(now)+ " " + username + " erfolgreich eingeloggt.");
//					return true;
//				}
//				else {
//					/* Benutzer ist von Administrator noch nicht aktiviert worden */
//					log.log(Level.WARNING, username + " ist noch nicht akzeptiert!");
//					return false;
//				}
//					
//			}
//			else {
//				/* Passwort ist nicht korrekt */
//				log.log(Level.WARNING, username + " falsches Passwort!");
//				return false;
//			}
//		}
//		/* Benutzername existiert nicht */
//		log.log(Level.WARNING, username + " existiert nicht!");
//		return false;
//	}
	@Override
	public int login(String username, String passwort, String ip) {
		User aktuellerNutzer;
		now = new java.util.Date();
		if(dbuser.containsKey(username)) {
			/* Benutzername ist korrekt */
			aktuellerNutzer = dbuser.get(username);
			aktuellerNutzer.setIp(ip);
			if(aktuellerNutzer.getUserPasswort().equals(passwort)) {
				/* Passwort ist korrekt */
				if(aktuellerNutzer.getAccepted()) {
					/* Benutzer ist von Administrator aktiviert worden */
					aktuellerNutzer.setLastseen(System.currentTimeMillis());
					log.fine(sdf.format(now)+ " " + username + " erfolgreich eingeloggt.");
					return ServerImplementor.LOGIN_SUCCESS;
				}
				else {
					/* Benutzer ist von Administrator noch nicht aktiviert worden */
					log.log(Level.WARNING, username + " ist noch nicht akzeptiert!");
					return ServerImplementor.LOGIN_FAIL_ACTIVATION;
				}
				
			}
			else {
				/* Passwort ist nicht korrekt */
				log.log(Level.WARNING, username + " falsches Passwort!");
				return ServerImplementor.LOGIN_FAIL_PASSWORT;
			}
		}
		/* Benutzername existiert nicht */
		log.log(Level.WARNING, username + " existiert nicht!");
		return ServerImplementor.LOGIN_FAIL_USERNAME;
	}
	/**
	 * Ausloggen des Users wenn letzte 10 min keine aktivität
	 * @param username
	 */
	public void logoff(String username)
	{
		
	}
	
	/**
	 * Ermöglicht die Registrierung eines neuen Benutzers
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public boolean registration(String username, String passwort, String ip) {
		username = username.toLowerCase();		//nur Kleinbuchstaben verwenden!
		if(dbuser.containsKey(username)) {
			/* Benutzername existiert bereits */
			log.log(Level.WARNING, "User mit Benutzername " + username + " existiert bereits!");
			return false;
		}
		else
		{
			/* Benutzername existiert noch nicht */
			try {
				User newUser = new User(username, passwort);
				newUser.setLastseen(System.currentTimeMillis());	// Zeitstempel setzen!
				newUser.setIp(ip);
				dbuser.put(username, newUser);
				return true;
			} catch (Exception e) {
				log.log(Level.WARNING, "Fehler beim anlegen der Regisration in DB. Username: " + username + ", Passwort: " + passwort);
				return false;
			}
		}
	}

	/**
	 * Ermöglicht es Usern mit Administrationsrechte aktivierte Registrationen zu deaktivieren.
	 * @param username
	 * @param passwort
	 * @param inactive_username
	 * @return
	 */
	 @Override
	public boolean deleteuser(String username, String passwort, String ip, String registration) {
		if(login(username,passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Anmeldung erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			if(aktuellerUser.getRights().equals(User.ADMINISTRATOR)) {
				/* Angemeldeter User besitzt Administrationsrechte */
				if(dbuser.containsKey(registration)) {
					/* nicht aktivierte Benutzerregistration existiert */
					dbuser.remove(registration);
					log.fine("Registration von " + registration + " wurde gelöscht.");
					return true;
				}
				else {
					log.log(Level.WARNING, registration + " existiert nicht!");
					return false;
				}
			}
			else {
				log.log(Level.WARNING, username + " besitzt keine Administrationsrechte!");
				return false;
			}
			
		}
		return false;
	 }
	
	/**
	 * Ermöglicht die Änderung des Passworts des Benutzers.
	 * @param username
	 * @param altesPasswort
	 * @param neuesPasswort
	 * @return
	 */
	 @Override
	public boolean changepasswort(String username, String altesPasswort, String ip, String neuesPasswort) {
		if(login(username, altesPasswort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Anmeldung erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			aktuellerUser.setUserPasswort(neuesPasswort);
			return true;
		}
		return false;
	}
	
	/**
	 * Ermöglicht es Usern mit Administrationsrechte nicht aktivierte Registrationen zu aktivieren.
	 * @param username
	 * @param passwort
	 * @param inactive_username
	 * @return
	 */
	 @Override
	public boolean activateuser(String username, String passwort, String ip, String inactive_username) {
		if(login(username,passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Anmeldung erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			if(aktuellerUser.getRights().equals(User.ADMINISTRATOR)) {
				/* Angemeldeter User besitzt Administrationsrechte */
				if(dbuser.containsKey(inactive_username)) {
					/* nicht aktivierte Benutzerregistration existiert */
					dbuser.get(inactive_username).setAccepted(true);
					log.fine("Registration von " + inactive_username + " wurde aktiviert.");
					return true;
				}
				else {
					log.log(Level.WARNING, inactive_username + " existiert nicht!");
					return false;
				}
			}
			else {
				log.log(Level.WARNING, username + " besitzt keine Administrationsrechte!");
				return false;
			}
			
		}
		return false;
	}
	 
		/**
		 * Ermöglicht es Usern mit Administrationsrechte aktivierte Registrationen zu deaktivieren.
		 * @param username
		 * @param passwort
		 * @param inactive_username
		 * @return
		 */
		 @Override
		public boolean deactivateuser(String username, String passwort, String ip, String active_username) {
			if(login(username,passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
				/* Anmeldung erfolgreich */
				User aktuellerUser = dbuser.get(username);
				aktuellerUser.setIp(ip);
				if(aktuellerUser.getRights().equals(User.ADMINISTRATOR)) {
					/* Angemeldeter User besitzt Administrationsrechte */
					if(dbuser.containsKey(active_username)) {
						/* nicht aktivierte Benutzerregistration existiert */
						dbuser.get(active_username).setAccepted(false);
						log.fine("Registration von " + active_username + " wurde deaktiviert.");
						return true;
					}
					else {
						log.log(Level.WARNING, active_username + " existiert nicht!");
						return false;
					}
				}
				else {
					log.log(Level.WARNING, username + " besitzt keine Administrationsrechte!");
					return false;
				}
				
			}
			return false;
		}
	
	/**
	 * Liefert eine Tabelle von Nutzern deren Registrierung noch nicht aktiviert wurde. 
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public ArrayList<String> showNotActivatedUsers(String username, String passwort, String ip) {
		if(login(username, passwort, ip)== ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			if(aktuellerUser.getRights().equals(User.ADMINISTRATOR)) {
				/* Benutzer ist Administrator */
				ArrayList<String> notActivatedUserList = new ArrayList<String>();	// Rückgabe.
				for (User user : dbuser.values()) {
					if(!user.getAccepted()) {
						/* User nicht Accepted */
						notActivatedUserList.add(user.getUserName() + "\t" + user.getLastseen());
					}
				}
				return notActivatedUserList;
				
			}
		}
		return null;
	}
	
	/**
	 * Liefert eine Tabelle von Nutzern die in den letzten 10 Minuten aktiv waren.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public ArrayList<String> showUsersActiveInLastTenMin(String username, String passwort, String ip) {
		if(login(username, passwort, ip)== ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			ArrayList<String> usersActiveInLastTenMin = new ArrayList<String>();
			long diff;
			long diffMinuten;
			long diffSekunden;
			for (User user : dbuser.values()) {
//				java.util.Date lastseen = (sdf) user.getLastseen();
				now = new Date();
				long lastseen;
				try {
					lastseen = user.getLastseen();
					diff = System.currentTimeMillis() - lastseen;
					diffMinuten = TimeUnit.MILLISECONDS.toMinutes(diff);
					diffSekunden = TimeUnit.MILLISECONDS.toSeconds(diff);
//					console.TalkDogLokalLine( now.getTime() + " - " + lastseen.getTime());
//					console.TalkDogLokalLine( user.getUserName() + " war vor " + diffMinutes + " Minuten online");
					console.TalkDogLokalLine("User: " + user.getUserName() + " Zeitdifferenz (mm:ss): " + diffMinuten + ":" + diffSekunden);
					if(diffMinuten < 10) {
						/* User war in den letzten 10 Minuten aktiv */
						usersActiveInLastTenMin.add(user.getUserName() + "\t" + diffMinuten + " Minuten, " + diffSekunden + " Sekunden");
					}
				} catch (Exception e) {
					log.log(Level.WARNING, "Datum " + user.getLastseen()+ " von User: " + user.getUserName() + ", kann nicht in Date umgewandelt werden.");
				}
			}
			return usersActiveInLastTenMin;
		}
		return null;
	}
	
	/**
	 * Liefert eine Tabelle von Nutzern die in den letzten 10 Minuten aktiv waren mit IP.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public ArrayList<String> showIPsToChat(String username, String passwort, String ip) {
		if(login(username, passwort, ip)== ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			ArrayList<String> usersActiveInLastTenMin = new ArrayList<String>();
			long diff;
			long diffMinuten;
			long diffSekunden;
			for (User user : dbuser.values()) {
//				java.util.Date lastseen = (sdf) user.getLastseen();
				now = new Date();
				long lastseen;
				try {
					lastseen = user.getLastseen();
					diff = System.currentTimeMillis() - lastseen;
					diffMinuten = TimeUnit.MILLISECONDS.toMinutes(diff);
					diffSekunden = TimeUnit.MILLISECONDS.toSeconds(diff);
//					console.TalkDogLokalLine( now.getTime() + " - " + lastseen.getTime());
//					console.TalkDogLokalLine( user.getUserName() + " war vor " + diffMinutes + " Minuten online");
					console.TalkDogLokalLine("User: " + user.getUserName() + " Zeitdifferenz (mm:ss): " + diffMinuten + ":" + diffSekunden);
					if(diffMinuten < 10) {
						/* User war in den letzten 10 Minuten aktiv */
						usersActiveInLastTenMin.add(user.getUserName() + "\t" + diffMinuten + " Minuten, " + diffSekunden + " Sekunden, mit IP: " + user.getIp());
					}
				} catch (Exception e) {
					log.log(Level.WARNING, "Datum " + user.getLastseen()+ " von User: " + user.getUserName() + ", kann nicht in Date umgewandelt werden.");
				}
			}
			return usersActiveInLastTenMin;
		}
		return null;
	}

	/**
	 * Liefert die IP des Chatpartners.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public String getIPFromUser(String username, String passwort, String ip, String chatpartner) {
		if(login(username, passwort, ip)== ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			aktuellerUser.setLastseen(System.currentTimeMillis());
			if(dbuser.containsKey(chatpartner)) {
				/* chat partner existiert */
				return dbuser.get(chatpartner).getIp();
			}
			else
				/* chat partner existiert nicht*/
				return "";
		}
		/* login fail! */
		return "";
	}
	
	/**
	 * Ermöglicht das Anlegen eines neuen Termins im Kalender des Nutzers
	 * @param username
	 * @param passwort
	 * @param beginn
	 * @param ende
	 * @param thema
	 * @param ort
	 * @param wiederholung
	 * @return
	 */
	@Override
	public boolean addTermin(String username, String passwort, String ip, String beginn, String ende, String thema, String ort, String wiederholung) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			File calenderList = new File(".\\src\\de\\jxws\\setupfiles\\calenderlists\\calender_"+username+".txt");
            try 
            {
            	if(calenderList.exists())
            	{
            		FileWriter bw = new FileWriter(calenderList,true);
					bw.write(beginn+";"+ende+";"+thema+";"+ort+";"+wiederholung+System.getProperty("line.separator"));
					bw.close();
					
            	}
            } 
            catch (IOException e) {
				e.printStackTrace();
			}
            
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			aktuellerUser.addTermin(new Termin(beginn, ende, thema, ort, wiederholung));
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Ermöglicht das Löschen eines Termins aus dem Kalender des Nutzers
	 * @param username
	 * @param passwort
	 * @param index
	 * @return
	 */
	@Override
	public boolean removeTermin(String username, String passwort, String ip, int index) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			//TODO: Arman
			createCalenderfromInstanz(username,passwort);
			//TODO: Arman
			return aktuellerUser.removeTermin(index);
		}
		else
			return false;
	}
	
	/**
	 * Schreibt die Textdatei aus Inzanz für Termine
	 * @param username
	 * @param passwort
	 */
	 @Override
	public void createCalenderfromInstanz(String username,String passwort) {
		User aktuellerUser=dbuser.get(username);		
		File calenderList = new File(".\\src\\de\\jxws\\setupfiles\\calenderlists\\calender_"+username+".txt");
         
		for(int i=0;i<aktuellerUser.getTerminList().size();i++)
		{
		    try 
            {
            	if(calenderList.exists())
            	{
            		FileWriter bw = new FileWriter(calenderList);
					bw.write(aktuellerUser.getTerminForDataremove(i));
					bw.close();
					
            	}
            } 
		    catch (IOException e)
		    {
		    	e.printStackTrace();
			}
		 }
	}
	/**
	 * Ermöglicht das nachträgliche Bearbeiten des Termins im Kalender des Nutzers
	 * @param username
	 * @param passwort
	 * @param index
	 * @param beginn
	 * @param ende
	 * @param thema
	 * @param ort
	 * @param wiederholung
	 * @return
	 */
	 @Override
	public boolean changeTermin(String username, String passwort, String ip, int index, String beginn, String ende, String thema, String ort, String wiederholung) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			return aktuellerUser.changeTermin(index, new Termin(beginn, ende, thema, ort, wiederholung));
		}
		else
			return false;
	}
	
	/**
	 * Liefert alle Termineinträge des angemeldetetn Users.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public ArrayList<String> showKalender(String username, String passwort, String ip) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			return(aktuellerUser.getTerminList());
		}
		return null;
		// TODO: handle exception
	}
	/**
	 * Liefert alle Termineinträge des gewünschten Users.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@Override
	public ArrayList<String> showKalenderFrom(String username, String passwort, String ip, String user) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			if(dbuser.containsKey(user)) {
				/* Angegebener Nutzer existiert */
				return(dbuser.get(user).getTerminList());
			}
			else {
				/* Angegebener Nutzer existiert nicht! */
				log.log(Level.WARNING, user + " existiert nicht!");
				ArrayList<String> error = new ArrayList<String>();
				error.add(user + "existiert nicht!");
				return error;
			}
		}
		return null;
		// TODO: handle exception
	}

	
	/**
	 * Liefert Kalendereinträge als String zurück
	 * @param username
	 * @param passwort
	 */
	 @Override
	public String calenderToString(String username,String passwort)
	{
		String tempstring="";
		//TODO:Arman, ich musste "" hinzufügen
		for(String s: showKalender(username,passwort,""))
		{
			tempstring+=s+"\n";
		}
		return tempstring;
	}

	/**
	 * gib Konstante
	 * @param key
	 */
	@Override
	public int getKonstante(String key) {
		if(dbkonst.containsKey(key)) {
			return dbkonst.get(key);
		}
		return -999;
	}

	/**
	 * Gib Rechte für User zurück
	 * @param username
	 * @param passwort
	 */
	@Override
	public int getRights(String username, String passwort, String ip) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			String aktuellerUserRights = aktuellerUser.getRights();
			if ( aktuellerUserRights.equals(User.ADMINISTRATOR) ) {
				return Integer.parseInt(User.ADMINISTRATOR);
			}
			else if (aktuellerUserRights.equals(User.USER) ) {
				return Integer.parseInt(User.USER);
			}
			else if (aktuellerUserRights.equals(User.GAST) ) {
				return Integer.parseInt(User.GAST);
			}
		}
		return 0;
	}

	@Override
	public ArrayList<String> showAllUsers(String username, String passwort, String ip) {
		if(login(username, passwort, ip) == ServerImplementor.LOGIN_SUCCESS) {
			/* Login erfolgreich */
			User aktuellerUser = dbuser.get(username);
			aktuellerUser.setIp(ip);
			if(aktuellerUser.getRights().equals(User.ADMINISTRATOR)) {
				ArrayList<String> allUserList = new ArrayList<String>();	// Rückgabe.
				/* Benutzer ist Administrator */
				allUserList.add("UserName" + "\t" + "Lastseen" + "\t\t" + "IP" + "\t\t" + "Rights" + "\t" + "Accepted" );
				for (User user : dbuser.values()) {
					if(user.getUserName().length()<5) {
						if(user.getIp().length()<8)
							allUserList.add(user.getUserName() + "\t\t" + sdf.format(user.getLastseen()) + "\t" + user.getIp() + "\t\t" + user.getRights() + "\t" + user.getAccepted() );
						else
							allUserList.add(user.getUserName() + "\t\t" + sdf.format(user.getLastseen()) + "\t" + user.getIp() + "\t" + user.getRights() + "\t" + user.getAccepted() );
					}
					else
						if(user.getIp().length()<8)
							allUserList.add(user.getUserName() + "\t" + sdf.format(user.getLastseen()) + "\t" + user.getIp() + "\t\t" + user.getRights() + "\t" + user.getAccepted() );
						else
							allUserList.add(user.getUserName() + "\t" + sdf.format(user.getLastseen()) + "\t" + user.getIp() + "\t" + user.getRights() + "\t" + user.getAccepted() );

						
				}
				return allUserList;
				
			}
		}
		return null;
	}
	
	/**
	 * Liefert selectierte Dateiinhalt als String zurück
	 * @return
	 * @throws IOException 
	 */
	 @Override
	public String getServerFileasString(String filepath)
	{
		String tempFileasString="";
		String line="";
		try
		{
			BufferedReader rd = new BufferedReader(
					new FileReader(
							new File(filepath)));
			while (true) 
			{
				try 
				{
					line = rd.readLine();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				if (line != null) 
				{
					tempFileasString+=line;
					tempFileasString+= System.getProperty("line.separator");
				} 
				else 
				{
					break;
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return tempFileasString;
			
	}

	/**
	 * erstellt aus übergebenen Strings eine Datei mit deren Inhalt(vom Client)
	 * @param clientfileasString
	 * @param filename
	 * @return
	 */
	 @Override
	public void createFilefromClient(String clientfileasString,String filename)
	{
		try 
		{
			File tempFileinServer = new File(currentServerPath+"\\"+filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFileinServer));
            bw.write(clientfileasString);
            bw.flush();
            bw.close();
			tempFileinServer.createNewFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Liefert alle Dateien auf Serverpfad als Strings zurück
	 * @return
	 */
	 @Override
	public String[] refreshList()
	{
		File[] temp_files = new File(currentServerPath).listFiles();
		String[] temp_files_string= new String[temp_files.length];
		
		for(int i=0;i<temp_files.length;i++)
		{
			if(temp_files[i].isDirectory())
			{
				temp_files_string[i]=null;
			}
			else
				temp_files_string[i]=temp_files[i].getName();
		}
		return temp_files_string;
	}
	
	public String giveAllUsernames()
	{
		String tempstringf="";
		for(User s: dbuser.values())
		{
			tempstringf+=s.getUserName()+"\n";
		}
		return tempstringf;
	}

	/**
	 * Baut Userdatenbank aus userlist.txt
	 */
	 @Override
	public void createUserListfromData()
	{
		String userlstAsString=
				getServerFileasString(".\\src\\de\\jxws\\setupfiles\\userlists\\userlist.txt");
		//String aufsplitten nach Userline
		String[]tmparray1=userlstAsString.split(";"+System.getProperty("line.separator"));
		String[]tmparray2=null;
		//Userliste aufsplitten für Instansen
		for(String usrline:tmparray1)
		{
			//Userdaten in Attribute aufsplitten
			tmparray2=usrline.split(":");
			System.out.println(tmparray2[0].toLowerCase());
			//TODO:Arman, hab den benutzernamen in lowercase umgewandelt!!! sonst gibts probleme mit dem dateisystem!
			dbuser.put(tmparray2[0].toLowerCase(), new User(tmparray2[0].toLowerCase(),tmparray2[1]));
			//Aktiv setzen wenn Spalte 4 = true entspricht
			if(tmparray2[4].equals("true"))
			{
				dbuser.get(tmparray2[0].toLowerCase()).setAccepted(true);
			}
			else
			{
				dbuser.get(tmparray2[0].toLowerCase()).setAccepted(false);
			}
			//Rechte setzten Spalte 3
			dbuser.get(tmparray2[0].toLowerCase()).setRights(tmparray2[3]);
		}
	}
	/**
	 * Sperre Datei
	 * @param username
	 * @param filename
	 */
	 @Override
	public void setDataUsed(String username,String filename)
	{

		System.out.println("sperre Datei");
		usedData.put(username, filename);
	}
	/**
	 * Abfrage ob Datei benutzt wird, wenn ja dann return true
	 * @param filename
	 * @return
	 */
	 @Override
	public boolean isDataUsed(String filename)
	{
		if(usedData.containsValue(filename))
			return true;
		else
			return false;
	}
	/**
	 * Datei wieder freigeben
	 * @param username
	 */
	 @Override
	public void releaseData(String username)
	{
		usedData.remove(username);
		System.out.println("Release Data");
	}
	 //TODO:Arman, musste override rausnehmen.
	//@Override
	public String giveDataUser(String filename)
	{return "";
	}
	/**
	 * Aus Dateien werden KalenderListen erstellt
	 * 
	 */
	 @Override
	public void createTodoListfromData()
	{
		String todoLstAsString="";
		String[]tmparray1=null;
		String[]tmparray2=null;
		//Iteration über Usernames ( jeder Benutzer hat einen Kalender)
		for(User usr: dbuser.values())
		{
			//Strings aus Dateien auslesen
			if(!new File(".\\src\\de\\jxws\\setupfiles\\calenderlists\\calender_"+usr.getUserName()+".txt").exists())
			{
				try 
				{
					(new File(".\\src\\de\\jxws\\setupfiles\\calenderlists\\calender_"+usr.getUserName()+".txt")).createNewFile();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			todoLstAsString=
					getServerFileasString(".\\src\\de\\jxws\\setupfiles\\calenderlists\\calender_"+usr.getUserName()+".txt");
			if(todoLstAsString!=null)
			{
				//String aufsplitten nach Termine
				tmparray1=todoLstAsString.split(System.getProperty("line.separator"));
				//Termine aufsplitten für Instansen
				for(String todoline:tmparray1)
				{
					//Todo in Attribute aufsplitten
					tmparray2=todoline.split(";");
					usr.addTermin(new Termin(tmparray2[0],tmparray2[1],tmparray2[2],tmparray2[3],""));
				}	
			}
		}
	}
	/**
	 * IPs zuweisen beim anmelden des Users
	 * @param user
	 * @param ip_intern
	 * @param ip_extern
	 */
	public void giveIP(String user,String ip_intern,String ip_extern)
	{
		dbuser.get(user).setExternIp(ip_extern);
		dbuser.get(user).setInternIp(ip_intern);
	}
	public String getinternIPfromUser(String user)
	{
		return dbuser.get(user).getInternIp();
	}
	public String getexternIPfromUser(String user)
	{
		return dbuser.get(user).getExternIp();
	}


}
