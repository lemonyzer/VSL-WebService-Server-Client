package de.jxws;

public class TalkDog {

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
	
}
