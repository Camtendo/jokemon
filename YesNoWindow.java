/**
 * @(#)AFrame.java
 *
 *
 * @author Justinian
 * @version 1.00 2010/11/20
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class YesNoWindow extends JFrame implements ActionListener
{
	public boolean visible;
	private Container cp;

	boolean yes;
	private JButton yesButton;
	private JButton noButton;
	private JLabel j1;


    public YesNoWindow()
    {
    	this.setTitle("????");
    	this.setSize(400,100);
   		this.setResizable(false);
   		this.setLayout(null);
 		  this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   		this.setVisible(false);
   		visible = false;
   		this.setLocationRelativeTo(null);
   		cp = this.getContentPane();
   		yes = false;

   		//okButton = new JButton("Ok!");
   		//okButton.setBounds(110,this.getHeight()-50,80,20);

   		//okButton.addActionListener(this);
   		yesButton = new JButton("Yes!");
   		yesButton.setBounds(100,this.getHeight()-60,80,20);
   		yesButton.addActionListener(this);
   		noButton = new JButton("No!");
   		noButton.setBounds(220,this.getHeight()-60,80,20);
   		noButton.addActionListener(this);
   		cp.add(yesButton);
   		cp.add(noButton);
    }

    public static void main(String[] args)
    {
    	YesNoWindow y1 = new YesNoWindow();
    	y1.addMessage("Yes or No?","This is a test");
    	while (y1.visible)
    	{
    		try
    		{
    			Thread.sleep(15);
    		}
    		catch(Exception e)
    		{

    		}
    	}
    	System.out.println(y1.getYes());

    }
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource() == yesButton)
    	{
    		yes = true;
    		this.setVisible(false);
    		visible = false;
    		Battle.confirmEvolution(true);
    	}
    	else if (e.getSource() == noButton)
    	{
    		yes = false;
    		this.setVisible(false);
    		visible = false;
    		Battle.confirmEvolution(false);
    	}
    	System.out.println(yes);
    }
    public void addMessage(String message, String message2)
    {
    	if (!visible)
    	{
    		visible = true;
    		this.setVisible(true);
    	}
    	this.setTitle(message2);
    	j1 = new JLabel(message);
    	j1.setBounds(15,5,this.getWidth() - 30,25);
    	j1.setHorizontalAlignment(JLabel.CENTER);

    	cp.add(j1);
    }
    public boolean getYes()
    {
    	return yes;
    }

}