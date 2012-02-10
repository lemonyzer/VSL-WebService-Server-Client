package de.jxws;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.swing.DefaultListModel;




@WebService
public interface Server {
	
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_FAIL_USERNAME = 1;
	public static final int LOGIN_FAIL_PASSWORT = 2;
	public static final int LOGIN_FAIL_ACTIVATION = 3;
	
	/**
	 * Da auf einem WebService keine Konstanten abgefragt werden können liefert diese Methode die "kostanten" Werte 
	 * @param key
	 * @return
	 */
	@WebMethod
	public int getKonstante(String key);
	
	
	/**
	 * 
	 * @param msg
	 */
	public void log(String msg);
	
	/**
	 * Liefert den Pfad zum aktuellen Benutzerverzeichnis zurück
	 * @return
	 */
	@WebMethod
	public String getDir();
	
	/**
	 * Liefert true, wenn der Server an ist.
	 * @return
	 */
	@WebMethod
	public boolean connect();

	
	/**
	 * Kontrolliert ob der angegebene Benutzer in der DB existiert und das angegebene Passwort mit dem in der DB übereinstimmt 
	 * desweiteren muss die Registrierung vom Administrator akzeptiert worden sein.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public int login(String username, String passwort, String ip);

	/**
	 * Liefert die Rechte des aktuellen Benutzers
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public int getRights(String username, String passwort, String ip);
	
	/**
	 * Ermöglicht die Registrierung eines neuen Benutzers
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public boolean registration(String username, String passwort, String ip);
	
	/**
	 * Ermöglicht die Registrierung eines Benutzers zu entfernen
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public boolean deleteuser(String username, String passwort, String ip, String registration);
	
	/**
	 * Ermöglicht die Änderung des Passworts des Benutzers.
	 * @param username
	 * @param altesPasswort
	 * @param neuesPasswort
	 * @return
	 */
	@WebMethod
	public boolean changepasswort(String username, String altesPasswort, String ip, String neuesPasswort);
	
	/**
	 * Ermöglicht es Usern mit Administrationsrechte nicht aktivierte Registrationen zu aktivieren.
	 * @param username
	 * @param passwort
	 * @param inactive_username
	 * @return
	 */
	@WebMethod
	public boolean activateuser(String username, String passwort, String ip, String inactive_username);

	/**
	 * Ermöglicht es Usern mit Administrationsrechte aktivierte Registrationen zu deaktivieren.
	 * @param username
	 * @param passwort
	 * @param inactive_username
	 * @return
	 */
	@WebMethod
	public boolean deactivateuser(String username, String passwort, String ip, String inactive_username);
	
	/**
	 * Liefert eine Tabelle von allen Nutzern 
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showAllUsers(String username, String passwort, String ip);
	
	/**
	 * Liefert eine Tabelle von Nutzern deren Registrierung noch nicht aktiviert wurde. 
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showNotActivatedUsers(String username, String passwort, String ip);
	
	/**
	 * Liefert eine Tabelle von Nutzern die in den letzten 10 Minuten aktiv waren.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showUsersActiveInLastTenMin(String username, String passwort, String ip);
	
	/**
	 * Liefert eine Tabelle von Nutzern die in den letzten 10 Minuten aktiv waren mit IP.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showIPsToChat(String username, String passwort, String ip);
	
	/**
	 * Liefert die IP des Chatpartners.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public String getIPFromUser(String username, String passwort, String ip, String chatpartner);
	
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
	@WebMethod
	public boolean addTermin(String username, String passwort, String ip, String beginn, String ende, String thema, String ort, String wiederholung);
	
	/**
	 * Ermöglicht das Löschen eines Termins aus dem Kalender des Nutzers
	 * @param username
	 * @param passwort
	 * @param index
	 * @return
	 */
	@WebMethod
	public boolean removeTermin(String username, String passwort, String ip, int index);
	
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
	@WebMethod
	public boolean changeTermin(String username, String passwort, String ip, int index, String beginn, String ende, String thema, String ort, String wiederholung);
	
	/**
	 * Liefert alle Termineinträge des angemeldetetn Users.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showKalender(String username, String passwort, String ip);
	
	/**
	 * Liefert alle Termineinträge des gewünschten Users.
	 * @param username
	 * @param passwort
	 * @return
	 */
	@WebMethod
	public ArrayList<String> showKalenderFrom(String username, String passwort, String ip, String user);
		
	//@WebMethod
	//public DefaultListModel<String> getServerListModel(String serverPath);
	@WebMethod	
	public void createFilefromClient(String clientfileasString,String filename);
	
	@WebMethod
	public String getServerFileasString(String filename);	
	@WebMethod
	public String[] refreshList();
	@WebMethod
	public String calenderToString(String username,String passwort);
	@WebMethod
	public String giveAllUsernames();
	@WebMethod
	public void createUserListfromData();
	@WebMethod
	public void releaseData(String username);
	@WebMethod
	public boolean isDataUsed(String filename);
	@WebMethod
	public void setDataUsed(String username,String filename);
	public void createTodoListfromData();
	@WebMethod
	public void giveIP(String user,String ip_intern,String ip_extern);
	@WebMethod
	public String getinternIPfromUser(String user);
	@WebMethod
	public String getexternIPfromUser(String user);
	public void createCalenderfromInstanz(String username,String passwort);

}
