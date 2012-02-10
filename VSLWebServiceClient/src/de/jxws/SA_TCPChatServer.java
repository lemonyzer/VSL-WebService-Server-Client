package de.jxws;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SA_TCPChatServer{
	
	class WaitingForClientThread extends Thread {
		public void run() {
			try {
				while (true)  {
					incommingConnectionSocket = lokalChatServer.accept();
				}
			} catch (IOException ex) {
				isWaitingForClient = false;
			}
		}
	}

	
	private ServerSocket lokalChatServer;		//ServerSocket des lokalen TCP Chat Servers
	private int port;
	private Socket lokalclientSocket;			//Socket des lokalen TCP Clients mit dem der Client eine Verbindung zu einem Externen Chat Server aufbauen kann.
	private Socket incommingConnectionSocket;
	private SA_TCPChatConnection incommingChatClient; 	//Socket des TCP Clients der Versuch sich mit dem lokal laufenden ChatServer zu verbinden
	private ConsolenClient_TalkDog console;
	private boolean exit; 
	private boolean isWaitingForClient;
	

	/**
	 * Erzeugt einen TCPServer auf dem übergebenen Parameter port.
	 * @param port TCP Port des Servers
	 */
	public SA_TCPChatServer(int port) {
		console = new ConsolenClient_TalkDog();
		this.port=port;
		exit=false;
		isWaitingForClient=true;
		incommingConnectionSocket=null;
		run();
	}
	
	/**
	 * ermöglicht es den Thread zu beenden.
	 */
	public void shutdown() {
		exit=true;
	}

//	@Override
	public void run() {
//		console.TalkDogLokalLine("TCPChatServer: hello!");
		try {
			lokalChatServer = new ServerSocket(port);
			lokalChatServer.setSoTimeout(0); //kein Timeout! 
//			console.TalkDogLokalLine("Starte Server an Port: " + port);
		} catch (Exception E) {
//			console.TalkDogLocalError("Chat Server konnte nicht gestartet werden");
		} 
		if(lokalChatServer != null) {
			/* Server wurde gestartet */
			new WaitingForClientThread().start();
			while (!lokalChatServer.isClosed() && isWaitingForClient) {
				// TODO hier bleibt er hängen!
				try {
					Thread.currentThread().sleep(1);
				}
				catch (InterruptedException ex) {
					
				}
				if (incommingConnectionSocket != null) {
					incommingChatClient = new SA_TCPChatConnection(incommingConnectionSocket);		//neuen Thread erzeugen (wird automatisch gestartet)
					incommingConnectionSocket = null;
				}
//				try {
//					console.TalkDogLokalLine("Warte auf neuen Client");
//					incommingConnectionSocket = lokalChatServer.accept();
//					/* ein Verbindung zum Client besthet */ 
////					console.TalkDogLokalLine("Client: " + incommingConnectionSocket.getInetAddress().getHostAddress() + ":" + incommingConnectionSocket.getPort() + " verbunden");
//				} catch (Exception E) {
////					console.TalkDogLocalError("Fehler beim Verbindungsaufbau");
//				}
//				if (!incommingConnectionSocket.isClosed()) {
//					/* Verbindung zum Client wird in Thread ausgelagert! */
////					console.TalkDogLokalLine("Chat mit Client wird geöffnet.");
//					incommingChatClient = new SA_TCPChatConnection(incommingConnectionSocket);		//neuen Thread erzeugen (wird automatisch gestartet)
//					//incommingChatClient.start();												//Thread starten.
//				}
//				else {
////					console.TalkDogLocalError("incommingChatClient Closed == true!");
//				}
			}
//			console.TalkDogLokalLine("TCPChatServer: bye!");
//			console.TalkDogLokalLine("TCPChatServer: bye!");
//			console.TalkDogLokalLine("TCPChatServer: bye!");
		}
//		super.run();
	}
	
	public static void main(String[] args) {
		SA_TCPChatServer chatServer = new SA_TCPChatServer(1988);
//		chatServer.run();
	}
	
}
