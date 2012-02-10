/* Damit das ganze ohne Tomcat oder Webserver lauffähig ist benötigen wir diesen Publisher der den Endpoint definiert */

package de.jxws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.InetAddress;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.xml.ws.Endpoint;

public class ServerPublisher {
	
	/**
	 * Fragt im Internet nach der IP
	 * @return
	 * @throws IOException
	 */
	public static String getExternalIP() throws IOException {
		String antwort;
		URL whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));
		antwort = in.readLine();
		return antwort; //you get the IP as a String
	}
	
	/**
	 * Liefert die Lokale IP, und falls es nicht klappt die interne std. IP
	 * @return
	 */
	public static String getLokalIP() {
		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}

	public static void main(String[] args) {
		String ip = getLokalIP();
		BufferedReader tastatur = new BufferedReader(new InputStreamReader(System.in));
		String eingabe="";
		Endpoint server = null;
		System.out.println("SYSTEM> RUN|START, STATUS, STOP, EXIT");

		System.out.println("|| Server wird gestartet.");
		server = Endpoint.publish("http://"+ip+":8080/jaxws/server", new ServerImplementor());
		System.out.println("|| Endpunkt ist veröffentlicht\n" +
				"IP:"+ip
				);
		JOptionPane.showMessageDialog( null, "TestWsServer beenden" );
		server.stop();
		if(!server.isPublished())
			System.out.println("|| Endpunkt ist nicht veröffentlicht");
		else {
			/* Server läuft noch */
			do {
				System.out.print("root> ");
				try {
					eingabe=tastatur.readLine().toLowerCase();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(eingabe.equals("run") || eingabe.equals("start")) {
					System.out.println("|| Server wird gestartet.");
					
					server = Endpoint.publish("http://" + ip + ":8080/jaxws/server", new ServerImplementor());
					System.out.println("|| Endpunkt ist veröffentlicht\n" +
							"IP:"+ip
							);
					JOptionPane.showMessageDialog( null, "TestWsServer beenden" );
					server.stop();
					System.out.println("|| Endpunkt ist nicht veröffentlicht");
					
				}
				else if(eingabe.equals("status")) {
					try {
						if(server.isPublished())
							System.out.println("|| Endpunkt ist veröffentlicht");
						else
							System.out.println("|| Endpunkt ist nicht veröffentlicht");
					} catch (Exception e) {
						System.out.println("|| Endpunkt ist nicht veröffentlicht");
						// TODO: handle exception
					}				
				}
				else if(eingabe.equals("stop")) {
					System.out.println("|| Server wird gestoppt.");
					try {
						server.stop();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				else if(eingabe.equals("exit")) {
					System.out.println("|| Server wird beendet.");
					break;
				}
				else {
					System.out.println("XX Unbekannter Befehl.");
				}
			}while(!eingabe.equals("exit"));
			try {
				server.stop();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
