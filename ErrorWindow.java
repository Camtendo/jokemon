/**
 * @(#)AFrame.java
 *
 *
 * @author
 * @version 1.00 2010/11/20
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.net.*;

public class ErrorWindow extends JFrame implements ActionListener
{
	private int aNum;
	private boolean visible;
	private Container cp;

	private JButton okButton;

    public ErrorWindow()
    {
    	aNum = 0;
    	this.setTitle("ERROR!");
    	this.setSize(300,120);
   		this.setResizable(false);
   		this.setLayout(null);
 		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

 		URL iconU=JokemonDriver.class.getResource("icon.png");
    	Image icon=Toolkit.getDefaultToolkit().getImage(iconU);
    	this.setIconImage(icon);

   		this.setVisible(false);
   		visible = false;
   		//Gets the resolution of the screen in pixels
   		Toolkit toolkit=Toolkit.getDefaultToolkit();
   		Dimension screensize=toolkit.getScreenSize();

   		this.setLocation(screensize.width/2-150,screensize.height/4);
   		cp = this.getContentPane();

   		okButton = new JButton("Ok!");
   		okButton.setBounds(110,this.getHeight()-50,80,20);

   		okButton.addActionListener(this);
   		cp.add(okButton);
   		
   		this.toFront();
    }

    public static void main(String[] args)
    {
    	ErrorWindow af = new ErrorWindow();
    }
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource() == okButton)
    	{
    		this.setVisible(false);
    		Battle.forceToFront();
    		visible = false;
    		aNum = 0;
    		cp.removeAll();
    		cp.add(okButton);
    	}
    }
    public void addMessage(String message)
    {
    	this.setTitle("ERROR!");
    	if (!visible)
    	{
    		visible = true;
    		this.setVisible(true);
    	}

    	JLabel j1 = new JLabel(message);
    	int height = (25*aNum)+5;
    	this.setSize(300,100 + (aNum*25));
    	okButton.setBounds(110,this.getHeight()-50,80,20);
    	aNum++;

    	j1.setBounds(15,height,this.getWidth() - 30,25);
    	j1.setHorizontalAlignment(JLabel.CENTER);

    	cp.add(j1);
    	
    	this.toFront();
    }
    public void addMessage(String message, String title)
    {
    	this.setTitle(title);
    	if (!visible)
    	{
    		visible = true;
    		this.setVisible(true);
    	}

    	//JLabel j1 = new JLabel(message);
    		JTextPane j1=new JTextPane();
    		j1.setText(message);
    		j1.setBackground(null);
            j1.setEditable(false);
            j1.setBorder(null);

    	int height = (25*aNum)+5;
    	this.setSize(300,100 + (aNum*25));
    	okButton.setBounds(110,this.getHeight()-50,80,20);
    	aNum++;

    	j1.setBounds(15,height,this.getWidth() - 30,25+20);
    	cp.add(j1);

    	okButton.requestFocusInWindow();
    	
    	this.toFront();
    }
    
    public void addLargeMessage(String message, String title)
    {
    	this.setTitle(title);
    	if (!visible)
    	{
    		visible = true;
    		this.setVisible(true);
    	}

    	//JLabel j1 = new JLabel(message);
    		JTextPane j1=new JTextPane();
    		j1.setText(message);
    		j1.setBackground(null);
            j1.setEditable(false);
            j1.setBorder(null);

    	int height = (25*aNum)+5;
    	this.setSize(300,200 + (aNum*25));
    	okButton.setBounds(110,this.getHeight()-50,80,20);
    	aNum++;

    	j1.setBounds(15,height,this.getWidth() - 30,this.getHeight()-75);
    	cp.add(j1);

    	okButton.requestFocusInWindow();
    	this.repaint();
    	
    	this.toFront();
    }

}