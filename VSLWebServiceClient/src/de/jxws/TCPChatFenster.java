package de.jxws;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TCPChatFenster extends JFrame {

	private JPanel contentPane;
	private JTextArea taNorth; 
	private JTextField tfEingabe;
	
	private boolean sessionClosed=false;
	/**
	 * Gibt informationen darüber ob das Fenster geschlossen wurde (Chat Session beendet ist).
	 * @return
	 */
	public boolean isSessionClosed() {
		return sessionClosed;
	}
	
	private PrintWriter out;
	
	public TCPChatFenster(String titel) {
		super(titel);
		fensterErzeugen();
	}
	
	public void setOutput(PrintWriter out) {
		this.out = out;
	}
	
	private void fensterErzeugen() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        taNorth = new JTextArea(10,50);
//        taNorth.setSize(380, 180);
        taNorth.setEditable(false); // Dient nur zur ausgabe!	
        contentPane.add(taNorth,BorderLayout.NORTH);
        
        JPanel south = new JPanel();
        south.setLayout(new FlowLayout());
        tfEingabe = new JTextField(50);
        tfEingabe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	out.println(tfEingabe.getText());
            	out.flush();
            	outgoingAnhaengen(tfEingabe.getText());
            	tfEingabe.setText("");
            	}
        	});
        south.add(tfEingabe);
        contentPane.add(south,BorderLayout.SOUTH);
        
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
		setSize(400, 200);
		pack();
		setVisible(true);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				sessionClosed=true;
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void statusAnhaengen(String status) {
		taNorth.append("|| " + status + "\n");
	}
	public void incommingAnhaengen(String incomming) {
		taNorth.append(">>" + incomming + "\n");
	}
	public void outgoingAnhaengen(String outgoing) {
		taNorth.append("<< " + outgoing + "\n");
	}
	
}
