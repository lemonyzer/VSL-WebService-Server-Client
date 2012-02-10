package de.jxws;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

public class SA_TCPChatClient {

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

	public SA_TCPChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		run();
	}
	
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public void run() {
		JOptionPane.showMessageDialog( null, "Client Chat gestartet, verbinde mit: " + hostname );
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
	
	public static void main(String[] args) {
		if(args.length == 1) {
			SA_TCPChatClient chatClient = new SA_TCPChatClient(args[0],1988);
//			chatClient.start();
		}
	}
}
