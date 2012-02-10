package de.jxws;

/**
 * Diese Klasse übernimmt die Kommunikation mit jeweils einem Client und läuft als eigener Thread.
 * Parallel können also mehrere Client bedient werden.
 * @author Aryan Layes
 * @date 16.10.2011
 * @version 1.0
 * Matrikelnummer: 860 661
 * Studiengang: Informationstechnik
 * Schwerpunkt: Technische Informatik
 * Semester: 6
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TCPChatConnection extends Thread {
	
	class InputThread extends Thread {
		public void run() {
			try {
				while (true)
					line = inFromClient.readLine(); // Blocking
			} catch (IOException ex) {
				chatFenster.statusAnhaengen("Connection lost");
				isConnected = false;
			}
		}
	}
	private TCPChatFenster chatFenster;
	private static final int nbConnections = 1;
	
	private String line;
	private boolean isConnected;
	
	private ArrayList<String> bufferedarray;
	
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;


	/**
	* Konstruktor
	* ModuloOperationenServer und GanzzahlOperationenServer können auf beliebigen
	* Rechnern ausgeführt werden, es müssen nur die IP-Adressen angepasst werden.
	*/
	public TCPChatConnection(Socket connectionSocket) {
		line = null;
		isConnected = false;
		inFromClient=null;
		outToClient=null;
		
		this.connectionSocket = new Socket();
		this.connectionSocket = connectionSocket;
		start();
	}
	
	/**
	 * überschriebene Methode von Thread.
	 */
	public void run() {
		JOptionPane.showMessageDialog( null, "Chat gestartet." );
		chatFenster = new TCPChatFenster("Server");
		while(!connectionSocket.isClosed())
		{
			chat();
		}
	}
	
	/**
	 * Wertet die Eingehenden TCP Pakete aus und bestimmt ob und welche Rechnung durchgeführt
	 * werden soll. Falls eine Rechnung erfolgen muss wird der Befehl an den zuständigen
	 * Rechenserver geleitet.
	 */
	public void chat() {
		String line = null;
		try {
			isConnected=true;
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			outToClient = new PrintWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
			
			chatFenster.setOutput(outToClient);
			chatFenster.statusAnhaengen("Connected to " + connectionSocket.getInetAddress());
			new InputThread().start();
			while (isConnected) {
				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException ex) {
				}
				if (line != null) {
					chatFenster.incommingAnhaengen(line);
					line = null;
				}
			}
			inFromClient.close();
			outToClient.close();
			connectionSocket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		JOptionPane.showMessageDialog( null, "Chat beendet." );
	}

	/**
	 * Meldungen werden in der Konsole ausgegeben.
	 */
	public void TalkDogLokal(String msg) { // "Fehlmelder" LOKAL
		System.out.println("|| " + msg);	
	}
	
	/**
	 * Meldungen werden in den Buffer geschrieben.
	 */
	public void TalkDog(String msg) { // "Fehlmelder"
		doBuffer(msg + "\n");
	}
	
	/**
	 * Meldungen werden in eine Array Liste geschrieben.
	 */
	public void doBuffer(String msg) { // "Fehlmelder" LOKAL
		bufferedarray.add(msg);
	}
	
	/**
	 * Eingehende UDP Datagramme werden auf der Konsole ausgegeben.
	 */
	public void TalkDogIncommingUDP(InetAddress address, int port, String msg) {
		System.out.println(">> " + address.getHostName() + ":" + port + " >> " + msg);
	}
	
	/**
	 * Ausgehende UDP Datagramme werden auf der Konsole ausgegeben.
	 */
	public void TalkDogOutgoingUDP(InetAddress address, int port, String msg) {
		System.out.println("<< " + address.getHostAddress() + ":" + port + " << " + msg);
	}
	
	/**
	 * Eingehende TCP Pakete werden auf der Konsole ausgegeben.
	 */
	public void TalkDogIncommingTCP(InetAddress address, int port, String msg) {
		System.out.println(">> " + address.getHostName() + ":" + port + " >> " + msg);
	}
	
	/**
	 * Ausgehende TCP Pakete werden auf der Konsole ausgegeben.
	 */
	public void TalkDogOutgoingTCP(InetAddress address, int port, String msg) {
		System.out.println("<< " + address.getHostAddress() + ":" + port + " << " + msg);
	}
	
	//	/**
//	 * SendBuffer übernimmt die eigentliche Kommunikation zwischen TCPClient und CMDServer.
//	 * Es wird im Buffer geschaut wie viele Zeilen vorhanden sind und an den TCPClient übermittelt,
//	 * dass dieser weiß auf wie viele weitere Zeilen er warten muss.
//	 * Dannach werden die einzelnen Zeilen iterativ und chronologisch korrekt an den TCPClient.
//	 */
//	public void SendBuffer() { // "Fehlmelder" LOKAL
//		boolean weiter = true;
//		// if(bufferedarray.size()>0)
//		// {
//		try {
//			TalkDogOutgoingTCP(connectionSocket.getInetAddress(), connectionSocket.getPort(), ""+bufferedarray.size());
//			outToClient.writeBytes(bufferedarray.size() + "\n"); // SENDE Anzahl der folgenden Zeilen
//		} catch (Exception E) {
//			TalkDogLokal("Fehler bei der Übertragung der Anzahl der folgenden Zeilen!");
//			weiter = false;
//		}
//		if (weiter) {
//			for (int i = 0; i < bufferedarray.size(); i++) {
//				try {
//					TalkDogOutgoingTCP(connectionSocket.getInetAddress(), connectionSocket.getPort(), bufferedarray.get(i));
//					outToClient.writeBytes(bufferedarray.get(i));
//					//System.out.println("<<Client:"  + connectionSocket.getInetAddress() + ":" + connectionSocket.getPort() + " << " +  bufferedarray.get(i));
//				} catch (Exception e) { TalkDogLokal("Konnte nicht übertragen werden: " + bufferedarray.get(i));	}
//				
//			}
//			bufferedarray.clear();
//		}
//	}
//	
//	/**
//	 * wenn ein TCPClient vorzeitig die Verbindung beendet, wird der Buffer gelöscht der Nachrichten an den Client enthält.
//	 */
//	public void clearClientBuffer() {
//		bufferedarray.clear();
//	}
//	
		
//	protected void finalize() throws Throwable {
//		outToClient.close();// do finalization here
//		inFromClient.close();// do finalization here
//		connectionSocket.close();
//		super.finalize(); // not necessary if extending Object.
//	}
	
}