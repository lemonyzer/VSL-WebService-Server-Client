package de.jxws;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

	public static final String ADMINISTRATOR="999";
	public static final String USER="666";
	public static final String GAST="111";
	
	private String userName;		//Benutzername
	private String userPasswort;	//Passwort des Benutzers
	private String ip;				//letzte IP Adresse des Benutzers
	private long lastseen;		//letzter Zeitstempel des Nutzers
	private String rights;			//Rechte des Benutzers
	private String ip_intern;
	private String ip_extern;
	private boolean accepted;		//Benutzer wurde vom Administrator akzeptiert
	private boolean loggedin;
	
	private ArrayList<Termin> kalender; // Kalender des Benutzers
	
	public User(String name, String passwort) {
		userName = name;
		userPasswort = passwort;
		kalender = new ArrayList<Termin>();
		lastseen = 0;
		rights = GAST;
		ip="0.0.0.0";
		ip_intern="0.0.0.0";
		ip_extern="0.0.0.0";
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setInternIp(String ip)
	{
		this.ip_intern= ip;
	}

	public String getInternIp()
	{
		return ip_intern;
	}
	public void setExternIp(String ip)
	{
		this.ip_extern= ip;
	}

	public String getExternIp()
	{
		return ip_extern;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserPasswort(String userPasswort) {
		this.userPasswort = userPasswort;
	}
	public void setLastseen(long lastseen) {
		this.lastseen = lastseen;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	public String getIp() {
		return ip;
	}
	public String getRights() {
		return rights;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPasswort() {
		return userPasswort;
	}
	public long getLastseen() {
		return lastseen;
	}
	public boolean getAccepted() {
		return accepted;
	}
	public boolean getLoggedIn() {
		return loggedin;
	}
	
	/* Kalender */
	public void addTermin(Termin termin) {
		kalender.add(termin);
	}
	public boolean removeTermin(int index) {
		if(index <= kalender.size()) {
			kalender.remove(index);
			return true;
		}
		return false;
	}
	public void removeTerminByString(String termin)
	{
		getTerminList().remove(termin);
	}
	public boolean changeTermin(int index, Termin newtermin) {
		if(index <= kalender.size()) {
			Termin oldtermin = kalender.get(index);
			oldtermin = newtermin;
			return true;
		}
		return false;
	}
	public Termin getTermin(int index) {
		if(index <= kalender.size()) {
			return kalender.get(index);
		}
		return null;
	}
	public ArrayList<String> getTerminList() {
		ArrayList<String> terminListe = new ArrayList<String>();
		terminListe.add("Thema\tBeginn\tEnde\tOrt\tWiederholung");
		for (int i = 0; i < kalender.size(); i++) {
			terminListe.add(kalender.get(i).getTerminThema() + "\t"
					+ kalender.get(i).getTerminBeginn()  + "\t"
					+ kalender.get(i).getTerminEnde()  + "\t"
					+ kalender.get(i).getTerminOrt()  + "\t"
					+ kalender.get(i).getTerminWiederholung()
					);
		}
		return terminListe;
	}
	public String getTerminForDataremove(int i)
	{
		return kalender.get(i).getTerminThema() + 
				";" + kalender.get(i).getTerminBeginn() + 
				";" + kalender.get(i).getTerminEnde() +
				";" + kalender.get(i).getTerminOrt() +
				";" + kalender.get(i).getTerminWiederholung()+System.getProperty("line.separator");
	}
}
