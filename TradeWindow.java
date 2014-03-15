/**
 * @(#)TradeWindow.java
 *
 *
 * @Cameron Crockrom 'Camtendo' and Justin Begeman
 * @version 1.00 2011/4/20
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class TradeWindow extends JFrame implements ActionListener
{
	JFrame jf;
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
	
	boolean host;
	ServerSocket ss;
	Socket socket;

	DataInputStream inTacular;
	DataOutputStream outTacular;

	InetAddress address;
	
	public TradeWindow()
	{
		connectedName = "";
	    host = true;
	    jf = new JFrame("LAN Trading");
	   	jf.setSize(250,350);
	   	jf.setIconImage(JokemonDriver.getIcon());
	   	jf.setResizable(false);
	   	jf.setLayout(null);
	 	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	   	jf.setLocationRelativeTo(null);
	   	cp = jf.getContentPane();
	
		resetButton = new JButton();
		resetButton.setBounds(10,215,230,25);
		resetButton.setText("Close Window");
	
		nameDisplay = new JLabel();
		nameDisplay.setBounds(10,5,230,25);
		nameDisplay.setText("Name: "+JokemonDriver.name);
	
		portDisplay = new JLabel();
		portDisplay.setBounds(10,45,230,25);
	
	 	portInput = new JTextField("EDIT ONLY IF NECESSARY");
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
	   	resetButton.addActionListener(this);
	
	
		if (host)
		ipDisplay.setText("IP Not Needed");
		else
		ipDisplay.setText("Their IP: ");
	
		portDisplay.setText("Port: "+JokemonDriver.port);
		
		cp.add(ipDisplay);
	   	cp.add(ipInput);
	   	cp.add(portInput);
	   	cp.add(portDisplay);
	   	cp.add(hostButton);
	   	cp.add(connectButton);
	   	cp.add(connectDisplay);
	   	cp.add(nameDisplay);
	   	cp.add(resetButton);
	   	
	   	jf.setVisible(true);
	}

    public void closeWindow()
    {
    	jf.setVisible(false);
    }

    public void actionPerformed(ActionEvent e)
    {
    	if (ipInput == e.getSource())
    	{
    		JokemonDriver.ipAddress = ipInput.getText();
    		if (host)
    		{
    			ipDisplay.setText("IP Not Needed");
    			ipInput.setText("IP Not Needed!");
    		}
			else
			ipDisplay.setText("Friend's IP: " + JokemonDriver.ipAddress);
    	}
    	if (portInput == e.getSource())
    	{
    		try
    		{
    			JokemonDriver.port = Integer.parseInt(portInput.getText());
    			portDisplay.setText("Port: " + JokemonDriver.port);
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
    			hostButton.setText("Current Mode: CLIENT");

    		}
    		else if (!host)
    		{
    			host = true;
    			hostButton.setText("Current Mode: HOST");
    		}
    		if (host)
			ipDisplay.setText("IP Not Needed");
			else
			ipDisplay.setText("Their IP: " + JokemonDriver.ipAddress);
    	}
    	if (nameEntry == e.getSource())
    	{
    		JokemonDriver.netName = nameEntry.getText();
    		nameDisplay.setText("Name: " + JokemonDriver.netName);
    	}
    	if (connectButton == e.getSource())
    	{
    		if (host)
    		{
    			try
    			{
    				removeActionListeners();
	    			ss = new ServerSocket(JokemonDriver.port);
	    			connectDisplay.setText("Waiting for Partner...");
	    			socket = ss.accept();
	    			sin = socket.getInputStream();
				    sout = socket.getOutputStream();
				    inTacular = new DataInputStream(sin);
				    outTacular = new DataOutputStream(sout);
	    			connectedName = inTacular.readUTF();
	    			JokemonDriver.friendName=connectedName;
			    	connectDisplay.setText("Connected to " + connectedName);
	    			outTacular.writeUTF(JokemonDriver.name);
	    			outTacular.flush();
	    			
	    			repaint();
	    			
	    			try
	    			{
	    				Thread.sleep(5000);
	    			}
	    			catch(Exception ex){}
	    			
	    			JokemonDriver.CONNECTED=true;
	    			System.out.println("Connected to Client.");
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
    				connectDisplay.setText("Seeking...");
    				address = InetAddress.getByName(JokemonDriver.ipAddress);
    				socket = new Socket(JokemonDriver.ipAddress, JokemonDriver.port);
    				sin = socket.getInputStream();
			    	sout = socket.getOutputStream();
			    	inTacular = new DataInputStream(sin);
			    	outTacular = new DataOutputStream(sout);
			    	outTacular.writeUTF(JokemonDriver.name);
			    	outTacular.flush();
			    	connectedName = inTacular.readUTF();
			    	JokemonDriver.friendName=connectedName;
			    	connectDisplay.setText("Connected to " + connectedName);
			    	
			    	repaint();
			    	
			    	try
	    			{
	    				Thread.sleep(5000);
	    			}
	    			catch(Exception ex){}
	    			
	    			JokemonDriver.CONNECTED=true;
	    			System.out.println("Connected to Host.");

    			}
    			catch(Exception ee)
    			{
    				ee.printStackTrace();
    				connectDisplay.setText("Error with Host");
    			}

    		}
    		
    		collapse();
    	}
    	if (resetButton == e.getSource())
    	{
    		jf.setVisible(false);
    	}
    }
    public static void main(String[] args)
    {
    	new TradeWindow();
    }

    public void removeActionListeners()
    {
    	ipInput.removeActionListener(this);
   		portInput.removeActionListener(this);
   		hostButton.removeActionListener(this);
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
    
    //Continuously checks for trade condition, then executes it
    public void tradeLogic()
    {
    	try
    	{
    		if(host)
	    	{
	    		//Step 1
	    		for(int i=0; i<6; i++)
	    		{
	    			JokemonDriver.friendPokemon[i]=inTacular.readUTF();
	    			
	    			if(JokemonDriver.partyPokemon[i]!=null)
	    			outTacular.writeUTF(JokemonDriver.partyPokemon[i].species.toString());
	    			else
	    			outTacular.writeUTF("null");
	    			outTacular.flush();
	    		}
	    		
	    		//Step 2
	    		JokemonDriver.friendOfferIndex=Integer.parseInt(inTacular.readUTF());
	    		
	    		outTacular.writeUTF(""+JokemonDriver.offerIndex);
	    		outTacular.flush();
	    		
	    		//Step 3
	    		JokemonDriver.friendTradeConfirm=Boolean.parseBoolean(inTacular.readUTF());
	    		
	    		outTacular.writeUTF(""+JokemonDriver.tradeConfirm);
	    		outTacular.flush();
	    		
	    		//Check for trade
	    		if(JokemonDriver.tradeConfirm&&JokemonDriver.friendTradeConfirm)
	    			commenceTrade();
	    	}
	    	else
	    	{
	    		//Step 1
	    		for(int i=0; i<6; i++)
	    		{
	    			if(JokemonDriver.partyPokemon[i]!=null)
	    			outTacular.writeUTF(JokemonDriver.partyPokemon[i].species.toString());
	    			else
	    			outTacular.writeUTF("null");
	    			outTacular.flush();
	    			
	    			JokemonDriver.friendPokemon[i]=inTacular.readUTF();
	    		}
	    		
	    		//Step 2
	    		outTacular.writeUTF(""+JokemonDriver.offerIndex);
	    		outTacular.flush();	
	    		
	    		JokemonDriver.friendOfferIndex=Integer.parseInt(inTacular.readUTF());
	    		
	    		//Step 3
	    		outTacular.writeUTF(""+JokemonDriver.tradeConfirm);
	    		outTacular.flush();
	    		
	    		JokemonDriver.friendTradeConfirm=Boolean.parseBoolean(inTacular.readUTF());
	    		
	    		//Check for trade
	    		if(JokemonDriver.tradeConfirm&&JokemonDriver.friendTradeConfirm)
	    			commenceTrade();
	    	}
    	}
    	catch(Exception exception)
    	{
    		exception.printStackTrace();
    		System.exit(0);
    	}
    }
    
    //Actual Trade method
    private void commenceTrade()
    {
    	try
    	{
    		if(host)
    		{
    			Pokemon temp=new Pokemon(Pokemon.Species.BULBASAUR);
    			
    			//Strings
    			temp.species=Pokemon.Species.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].species);
				outTacular.flush();
				temp.nickname=inTacular.readUTF();
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].nickname);
				outTacular.flush();
				temp.originalTrainer=inTacular.readUTF();
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].originalTrainer);
				outTacular.flush();
				temp.status=Pokemon.Status.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].status);
				outTacular.flush();
				temp.substatus=Pokemon.Substatus.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].substatus);
				outTacular.flush();
				temp.move[0]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[0]);
				outTacular.flush();
				temp.move[1]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[1]);
				outTacular.flush();
				temp.move[2]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[2]);
				outTacular.flush();
				temp.move[3]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[3]);
				outTacular.flush();

				//Integers
				temp.level=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].level));
				outTacular.flush();
				temp.exp=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].exp));
				outTacular.flush();
				temp.health=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].health));
				outTacular.flush();
				temp.healthMax=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].healthMax));
				outTacular.flush();
				temp.HP_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].HP_EV));
				outTacular.flush();
				temp.ATK_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].ATK_EV));
				outTacular.flush();
				temp.DEF_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].DEF_EV));
				outTacular.flush();
				temp.SPCL_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPCL_EV));
				outTacular.flush();
				temp.SPEED_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPEED_EV));
				outTacular.flush();
				temp.HP_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].HP_IV));
				outTacular.flush();
				temp.ATK_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].ATK_IV));
				outTacular.flush();
				temp.DEF_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].DEF_IV));
				outTacular.flush();
				temp.SPCL_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPCL_IV));
				outTacular.flush();
				temp.SPEED_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPEED_IV));
				outTacular.flush();
				temp.TRUE_PP[0]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[0]));
				outTacular.flush();
				temp.TRUE_PP[1]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[1]));
				outTacular.flush();
				temp.TRUE_PP[2]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[2]));
				outTacular.flush();
				temp.TRUE_PP[3]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[3]));
				outTacular.flush();
				temp.TRUE_PPMAX[0]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[0]));
				outTacular.flush();
				temp.TRUE_PPMAX[1]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[1]));
				outTacular.flush();
				temp.TRUE_PPMAX[2]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[2]));
				outTacular.flush();
				temp.TRUE_PPMAX[3]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[3]));
				outTacular.flush();
				
				//Booleans
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].IS_TRADED);
				outTacular.flush();
				temp.IS_TRADED=Boolean.parseBoolean(inTacular.readUTF());

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex]=new  Pokemon(temp.species, temp.move[0], temp.move[1], temp.move[2], temp.move[3], temp.level,
				temp.HP_IV,  temp.ATK_IV, temp.DEF_IV, temp.SPCL_IV, temp.SPEED_IV,
				temp.nickname, temp.status, temp.idNumber, temp.originalTrainer);
				
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("HP",temp.HP_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("ATK",temp.ATK_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("DEF",temp.DEF_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("SPCL",temp.SPCL_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("SPEED",temp.SPEED_EV);

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].health=temp.health;
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].healthMax=temp.healthMax;
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].exp=temp.exp;

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[0]=temp.TRUE_PP[0];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[1]=temp.TRUE_PP[1];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[2]=temp.TRUE_PP[2];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[3]=temp.TRUE_PP[3];

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[0]=temp.TRUE_PPMAX[0];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[1]=temp.TRUE_PPMAX[1];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[2]=temp.TRUE_PPMAX[2];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[3]=temp.TRUE_PPMAX[3];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].IS_TRADED=true;
    		}
    		else
    		{
    			Pokemon temp=new Pokemon(Pokemon.Species.BULBASAUR);
    			
    			//Strings
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].species);
				outTacular.flush();
				temp.species=Pokemon.Species.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].nickname);
				outTacular.flush();
				temp.nickname=inTacular.readUTF();
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].originalTrainer);
				outTacular.flush();
				temp.originalTrainer=inTacular.readUTF();
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].status);
				outTacular.flush();
				temp.status=Pokemon.Status.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].substatus);
				outTacular.flush();
				temp.substatus=Pokemon.Substatus.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[0]);
				outTacular.flush();
				temp.move[0]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[1]);
				outTacular.flush();
				temp.move[1]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[2]);
				outTacular.flush();
				temp.move[2]=Pokemon.Move.valueOf(inTacular.readUTF());
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].move[3]);
				outTacular.flush();
				temp.move[3]=Pokemon.Move.valueOf(inTacular.readUTF());

				//Integers
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].level));
				outTacular.flush();
				temp.level=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].exp));
				outTacular.flush();
				temp.exp=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].health));
				outTacular.flush();
				temp.health=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].healthMax));
				outTacular.flush();
				temp.healthMax=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].HP_EV));
				outTacular.flush();
				temp.HP_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].ATK_EV));
				outTacular.flush();
				temp.ATK_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].DEF_EV));
				outTacular.flush();
				temp.DEF_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPCL_EV));
				outTacular.flush();
				temp.SPCL_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPEED_EV));
				outTacular.flush();
				temp.SPEED_EV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].HP_IV));
				outTacular.flush();
				temp.HP_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].ATK_IV));
				outTacular.flush();
				temp.ATK_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].DEF_IV));
				outTacular.flush();
				temp.DEF_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPCL_IV));
				outTacular.flush();
				temp.SPCL_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].SPEED_IV));
				outTacular.flush();
				temp.SPEED_IV=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[0]));
				outTacular.flush();
				temp.TRUE_PP[0]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[1]));
				outTacular.flush();
				temp.TRUE_PP[1]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[2]));
				outTacular.flush();
				temp.TRUE_PP[2]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[3]));
				outTacular.flush();
				temp.TRUE_PP[3]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[0]));
				outTacular.flush();
				temp.TRUE_PPMAX[0]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[1]));
				outTacular.flush();
				temp.TRUE_PPMAX[1]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[2]));
				outTacular.flush();
				temp.TRUE_PPMAX[2]=Integer.parseInt(inTacular.readUTF(),2);
				outTacular.writeUTF(Integer.toBinaryString(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[3]));
				outTacular.flush();
				temp.TRUE_PPMAX[3]=Integer.parseInt(inTacular.readUTF(),2);
				
				//Booleans
				outTacular.writeUTF(""+JokemonDriver.partyPokemon[JokemonDriver.offerIndex].IS_TRADED);
				outTacular.flush();
				temp.IS_TRADED=Boolean.parseBoolean(inTacular.readUTF());

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex]=new  Pokemon(temp.species, temp.move[0], temp.move[1], temp.move[2], temp.move[3], temp.level,
				temp.HP_IV,  temp.ATK_IV, temp.DEF_IV, temp.SPCL_IV, temp.SPEED_IV,
				temp.nickname, temp.status, temp.idNumber, temp.originalTrainer);
				
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("HP",temp.HP_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("ATK",temp.ATK_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("DEF",temp.DEF_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("SPCL",temp.SPCL_EV);
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].setEV("SPEED",temp.SPEED_EV);

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].health=temp.health;
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].healthMax=temp.healthMax;
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].exp=temp.exp;

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[0]=temp.TRUE_PP[0];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[1]=temp.TRUE_PP[1];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[2]=temp.TRUE_PP[2];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PP[3]=temp.TRUE_PP[3];

				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[0]=temp.TRUE_PPMAX[0];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[1]=temp.TRUE_PPMAX[1];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[2]=temp.TRUE_PPMAX[2];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].TRUE_PPMAX[3]=temp.TRUE_PPMAX[3];
				JokemonDriver.partyPokemon[JokemonDriver.offerIndex].IS_TRADED=true;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		JokemonDriver.save();
    		System.exit(0);
    	}
    		
    	JokemonDriver.tradeConfirm=false;
    	JokemonDriver.friendTradeConfirm=false;
    	Pokedex.seen(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].pokedexNumber-1);
    	Pokedex.caught(JokemonDriver.partyPokemon[JokemonDriver.offerIndex].pokedexNumber-1);
    	JokemonDriver.save();
    }
    
    //Hides Trade Window
    public void collapse()
    {
    	jf.setVisible(false);
    }
}