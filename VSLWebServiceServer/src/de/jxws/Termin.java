package de.jxws;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sun.org.apache.xml.internal.serialize.*;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Termin {
	
//	private String terminID;
	private String terminBeginn;	//10.02.2011 19:00
	private String terminEnde;		//10.02.2011 19:00
	private String terminThema;		//Geburtstag
	private String terminOrt;		//K-Town
	private String terminWiederholung; //365
	
	public Termin(String beginn, String ende, String thema, String ort, String wiederholung) {
//		terminID = id;
		terminBeginn = beginn;
		terminEnde = ende;
		terminThema = thema;
		terminOrt = ort;
		terminWiederholung = wiederholung;
	}
	
	/* Eigenschaften setzen */
	public void setTerminBeginn(String terminBeginn) {
		this.terminBeginn = terminBeginn;
	}
	public void setTerminEnde(String terminEnde) {
		this.terminEnde = terminEnde;
	}
	public void setTerminThema(String terminThema) {
		this.terminThema = terminThema;
	}
	public void setTerminOrt(String terminOrt) {
		this.terminOrt = terminOrt;
	}
	public void setTerminWiederholung(String terminWiederholung) {
		this.terminWiederholung = terminWiederholung;
	}
	
	/* Eigenschaften abfragen */
	public String getTerminBeginn() {
		return terminBeginn;
	}
	public String getTerminEnde() {
		return terminEnde;
	}
	public String getTerminThema() {
		return terminThema;
	}
	public String getTerminOrt() {
		return terminOrt;
	}
	public String getTerminWiederholung() {
		return terminWiederholung;
	}
}
