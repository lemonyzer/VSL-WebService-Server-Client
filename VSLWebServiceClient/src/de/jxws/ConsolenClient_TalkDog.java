package de.jxws;

public class ConsolenClient_TalkDog {

	/**
	 * Gibt einen String auf der Konsole aus. Ohne Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogLokalNoLine(String string) {
		System.out.print("|| " + string);
	}
	
	/**
	 * Gibt einen String auf der Konsole aus. Mit Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogLokalLine(String string) {
		System.out.println("|| " + string);
	}
	
	/**
	 * Gibt einen String auf der Konsole aus. Mit Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogLokalLineIncomming(String string) {
		System.out.println(">> " + string);
	}
	
	
	/**
	 * Gibt eine Fehlernachricht auf der Konsole aus. Mit Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogLocalError(String string) {
		System.out.println("XX " + string);
	}
	
	/**
	 * Gibt eine Fehlernachricht des TCP Chat Servers auf der Konsole aus. Mit Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogTCPChatServer(String string) {
		System.out.println("ChatServer " + string);
	}
	/**
	 * Gibt eine Fehlernachricht des TCP Chat Servers auf der Konsole aus. Mit Return (Zeilenumsprung).
	 * @param string
	 */
	public void TalkDogTCPChatServerError(String string) {
		System.out.println("ChatServer XX " + string);
	}
	
}
