package de.jxws;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPChatServer extends Thread{

	
	private ServerSocket lokalChatServer;		//ServerSocket des lokalen TCP Chat Servers
	private int port;
	private Socket lokalclientSocket;			//Socket des lokalen TCP Clients mit dem der Client eine Verbindung zu einem Externen Chat Server aufbauen kann.
	private Socket incommingConnectionSocket;
	private TCPChatConnection incommingChatClient; 	//Socket des TCP Clients der Versuch sich mit dem lokal laufenden ChatServer zu verbinden
	private ConsolenClient_TalkDog console;
	private boolean exit; 
	

	/**
	 * Erzeugt einen TCPServer auf dem übergebenen Parameter port.
	 * @param port TCP Port des Servers
	 */
	public TCPChatServer(int port) {
		console = new ConsolenClient_TalkDog();
		this.port=port;
		exit=false;
	}
	
	/**
	 * ermöglicht es den Thread zu beenden.
	 */
	public void shutdown() {
		exit=true;
	}

	@Override
	public void run() {
		console.TalkDogLokalLine("TCPChatServer: hello!");
		try {
			lokalChatServer = new ServerSocket(port);
			lokalChatServer.setSoTimeout(0); //kein Timeout! 
			console.TalkDogLokalLine("Starte Server an Port: " + port);
		} catch (Exception E) {
			console.TalkDogLocalError("Chat Server konnte nicht gestartet werden");
		} 
		if(lokalChatServer != null) {
			/* Server wurde gestartet */
			while (!lokalChatServer.isClosed()) {
				try {
					console.TalkDogLokalLine("Warte auf neuen Client");
					incommingConnectionSocket = lokalChatServer.accept();
					/* ein Verbindung zum Client besthet */ 
					console.TalkDogLokalLine("Client: " + incommingConnectionSocket.getInetAddress().getHostAddress() + ":" + incommingConnectionSocket.getPort() + " verbunden");
				} catch (Exception E) {
					console.TalkDogLocalError("Fehler beim Verbindungsaufbau");
				}
				if (!incommingConnectionSocket.isClosed()) {
					/* Verbindung zum Client wird in Thread ausgelagert! */
					console.TalkDogLokalLine("Chat mit Client wird geöffnet.");
					incommingChatClient = new TCPChatConnection(incommingConnectionSocket);		//neuen Thread erzeugen (wird automatisch gestartet)
					//incommingChatClient.start();												//Thread starten.
				}
				else
					console.TalkDogLocalError("incommingChatClient Closed == true!");
			}
			console.TalkDogLokalLine("TCPChatServer: bye!");
			console.TalkDogLokalLine("TCPChatServer: bye!");
			console.TalkDogLokalLine("TCPChatServer: bye!");
		}
		super.run();
	}
	
	
}
