/**
 * @(#)TradeWindow.java
 *
 *
 * @Cameron Crockrom 'Camtendo' and Justin Begeman
 * @version 1.00 2010/10/7
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class TradeWindow extends JFrame implements ActionListener
{
	private JFrame jf;
	private Container cp;
	private JTextField ipInput;
	private JTextField portInput;
	private JLabel ipDisplay;
	private JLabel portDisplay;
	private JButton hostButton;
	private JButton resetButton;

	private JButton connectButton;
	private JLabel connectDisplay;
	private JLabel connectDisplay2;

	private JTextField nameEntry;
	private JLabel nameDisplay;

	InputStream sin;
	OutputStream sout;

	String connectedName;

	private MasterK main;
	boolean host;
	ServerSocket ss;
	Socket socket;

	DataInputStream in;
	DataOutputStream out;

	InetAddress address;

    public textWindow(MasterK m1) {
    connectedName = "";
    main = m1;
    host = true;
    jf = new JFrame("LAN Trading");
   	jf.setSize(250,350);
   	jf.setResizable(false);
   	jf.setLayout(null);
 	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   	jf.setVisible(true);
   	jf.setLocationRelativeTo(null);
   	cp = jf.getContentPane();

	resetButton = new JButton();
	resetButton.setBounds(10,215,230,25);
	resetButton.setText("Reset (If errors occur)");

	nameEntry = new JTextField();
	nameEntry.setBounds(10,25,230,25);

	nameDisplay = new JLabel();
	nameDisplay.setBounds(10,5,230,25);
	nameDisplay.setText("Name: " + main. netName);

	portDisplay = new JLabel();
	portDisplay.setBounds(10,45,230,25);

 	portInput = new JTextField();
   	portInput.setBounds(10,65,230,25);

   	ipInput = new JTextField();
   	ipInput.setBounds(10,105,230,25);

   	ipDisplay = new JLabel();
   	ipDisplay.setBounds(10,85,230,25);

   	hostButton = new JButton();
   	hostButton.setBounds(10,135,230,25);
   	hostButton.setText("Current Mode: HOST");

   	connectButton = new JButton();
   	connectButton.setBounds(10,175,230,25);
   	connectButton.setText("Connect");

   	connectDisplay = new JLabel();
   	connectDisplay.setBounds(10,195,230,25);
   	connectDisplay.setText("Awaiting Response");

   	ipInput.addActionListener(this);
   	portInput.addActionListener(this);
   	hostButton.addActionListener(this);
   	connectButton.addActionListener(this);
   	nameEntry.addActionListener(this);
   	resetButton.addActionListener(this);


	if (host)
	ipDisplay.setText("IP Not Needed");
	else
	ipDisplay.setText("Their IP: " + main.ipAddress);

	portDisplay.setText("Port: " + main.port);




	cp.add(ipDisplay);
   	cp.add(ipInput);
   	cp.add(portInput);
   	cp.add(portDisplay);
   	cp.add(hostButton);
   	cp.add(connectButton);
   	cp.add(connectDisplay);
   	cp.add(nameEntry);
   	cp.add(nameDisplay);
   	cp.add(resetButton);

    }

    public void closeWindow()
    {
    	jf.setVisible(false);
    }

    public void actionPerformed(ActionEvent e)
    {
    	if (ipInput == e.getSource())
    	{
    		main.ipAddress = ipInput.getText();
    		if (host)
			ipDisplay.setText("IP Not Needed");
			else
			ipDisplay.setText("Their IP: " + main.ipAddress);
    	}
    	if (portInput == e.getSource())
    	{
    		try
    		{
    			main.port = Integer.parseInt(portInput.getText());
    			portDisplay.setText("Port: " + main.port);
    		}
    		catch(Exception ee)
    		{
    			portInput.setText("INVALID PORT");
    		}

    	}
    	if (hostButton == e.getSource())
    	{
    		if (host)
    		{
    			host = false;
    			hostButton.setText("Current Mode: CHALLENGER");

    		}
    		else if (!host)
    		{
    			host = true;
    			hostButton.setText("Current Mode: HOST");
    		}
    		if (host)
			ipDisplay.setText("IP Not Needed");
			else
			ipDisplay.setText("Their IP: " + main.ipAddress);
    	}
    	if (nameEntry == e.getSource())
    	{
    		main.netName = nameEntry.getText();
    		nameDisplay.setText("Name: " + main.netName);
    	}
    	if (connectButton == e.getSource())
    	{
    		if (host)
    		{
    			try
    			{
    				removeActionListeners();
	    			ss = new ServerSocket(main.port);
	    			connectDisplay.setText("Waiting for Challenger...");
	    			socket = ss.accept();
	    			sin = socket.getInputStream();
				    sout = socket.getOutputStream();
				    DataInputStream in = new DataInputStream(sin);
				    DataOutputStream out = new DataOutputStream(sout);
	    			connectedName = in.readUTF();
			    	connectDisplay.setText("Connected to " + connectedName);
	    			out.writeUTF(main.netName);
	    			socket.close();
    			}
    			catch(Exception eee)
    			{
    				connectDisplay.setText("" + eee);
    			}



    		}
    		else if (!host)
    		{
    			removeActionListeners();
    			try
    			{
    				connectDisplay.setText("Seeking..");
    				address = InetAddress.getByName(main.ipAddress);
    				socket = new Socket(main.ipAddress, main.port);
    				sin = socket.getInputStream();
			    	sout = socket.getOutputStream();
			    	DataInputStream in = new DataInputStream(sin);
			    	DataOutputStream out = new DataOutputStream(sout);
			    	out.writeUTF(main.netName);
			    	connectedName = in.readUTF();
			    	connectDisplay.setText("Connected to " + connectedName);
			    	socket.close();

    			}
    			catch(Exception ee)
    			{
    				connectDisplay.setText("Error with Host");
    			}

    		}
    	}
    	if (resetButton == e.getSource())
    	{
    		main.windowReset();
    	}

    }
    public static void main(String[] args)
    {
    	MasterK m1 = new MasterK();
    	textWindow t1 = new textWindow(m1);
    }

    public void removeActionListeners()
    {
    	ipInput.removeActionListener(this);
   		portInput.removeActionListener(this);
   		hostButton.removeActionListener(this);
   		nameEntry.removeActionListener(this);
   		connectButton.removeActionListener(this);

    }
    public void addActionListeners()
    {
    	ipInput.addActionListener(this);
   		portInput.addActionListener(this);
   		hostButton.addActionListener(this);
   		connectButton.addActionListener(this);
   		nameEntry.addActionListener(this);

    }
}