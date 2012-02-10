package de.jxws;

import java.net.*;
import java.io.*;

public class TCPChatClient extends Thread {

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

	public TCPChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		start();										//Wird direkt als Thread gestartet.
	}
	

	@Override
	public void run() {
		try {
			port=1988;
			socket = new Socket(hostname, port);
			socket.setSoTimeout(0);
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			chatFenster = new TCPChatFenster("Client");
			chatFenster.setOutput(outToServer);
			chatFenster.statusAnhaengen("Connection to \'" + hostname + "\' on port " + port
					+ " established");
			isConnected = true;
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
			}
		}
		super.run();
	}
}
