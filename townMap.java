//Created by Justinian
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class townMap extends JFrame implements ActionListener, MouseMotionListener
{
	private boolean visible;
	private JokemonDriver.Area area;
	private JLabel areaDisplay;
    private JButton closeWindow;
    private Point mouse;

    private void setUpGui()
	{
		area = JokemonDriver.Area.Stringville;
		mouse = new Point();
        int thingInt = 0;
		this.setTitle("Town Map");
    	this.setSize(195,239);
   		this.setResizable(false);
   		this.setLayout(null);
 		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   		this.setVisible(false);
   		visible = false;
   		this.setLocationRelativeTo(null);
        Container cp = this.getContentPane();

        JLabel map = new JLabel();
   		map.setBounds(1, -5, 188, 176);
   		map.setIcon(new ImageIcon(townMap.class.getResource("Logos/townmap.png")));
   		
   		closeWindow = new JButton();
   		closeWindow.setText("Close Window");
   		closeWindow.setBounds(0,192,188,20);
   		
   		areaDisplay = new JLabel();
   		areaDisplay.setText(area.toString().replace('_',' '));
   		areaDisplay.setHorizontalAlignment(JLabel.CENTER);
   		areaDisplay.setBounds(0,171,188,20);
   		
   		addMouseMotionListener(this);
   		closeWindow.addActionListener(this);
   		cp.add(map);
   		cp.add(closeWindow);
   		cp.add(areaDisplay);
	}
	
	public townMap()
	{
		setUpGui();
	}
	
	public void displayMap()
	{
		changeDisplay();
		openWindow();
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	private void changeDisplay()
	{
		areaDisplay.setText(area.toString().replace('_',' '));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == closeWindow)
			closeWindow();
	}
	
	private void closeWindow()
	{
		visible = false;
		this.setVisible(false);
	}
	
	private void openWindow()
	{
		visible = true;
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		townMap t1 = new townMap();
		t1.displayMap();
	}
	
	public void changeArea()
	{
		//Routes and Cities
		if (mouse.x >= 115 && mouse.y >= 168 && mouse.x <= 144 && mouse.y <= 175)
			area = JokemonDriver.Area.Route_0;
		if (mouse.x >= 98 && mouse.y >= 114 && mouse.x <= 114 && mouse.y <= 173)
			area = JokemonDriver.Area.Stringville;
		if (mouse.x >= 145 && mouse.y >= 167 && mouse.x <= 160 && mouse.y <= 175)
			area = JokemonDriver.Area.Args_Harbor;
		if (mouse.x >= 161 && mouse.y >= 168 && mouse.x <= 168 && mouse.y <= 175)
			area = JokemonDriver.Area.Route_14;
		if (mouse.x >= 153 && mouse.y >= 138 && mouse.x <= 159 && mouse.y <= 166)
			area = JokemonDriver.Area.Route_8;
		if (mouse.x >= 147 && mouse.y >= 129 && mouse.x <= 169 && mouse.y <= 136)
			area = JokemonDriver.Area.Route_6;
		if (mouse.x >= 169 && mouse.y >= 129 && mouse.x <= 183 && mouse.y <= 136)
			area = JokemonDriver.Area.Streamreader_Hotel;
		if (mouse.x >= 117 && mouse.y >= 130 && mouse.x <= 145 && mouse.y <= 137)
			area = JokemonDriver.Area.Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION;
		if (mouse.x >= 107 && mouse.y >= 122 && mouse.x <= 116 && mouse.y <= 137)
			area = JokemonDriver.Area.Nested_Village;
		if (mouse.x >= 98 && mouse.y >= 145 && mouse.x <= 106 && mouse.y <= 167)
			area = JokemonDriver.Area.Route_1;
		if (mouse.x >= 98 && mouse.y >= 137 && mouse.x <= 114 && mouse.y <= 145)
			area = JokemonDriver.Area.Route_2;
		if (mouse.x >= 84 && mouse.y >= 145 && mouse.x <= 98 && mouse.y <= 151)
			area = JokemonDriver.Area.Binary_City;
		if (mouse.x >= 55 && mouse.y >= 145 && mouse.x <= 82 && mouse.y <= 152)
			area = JokemonDriver.Area.Route_13;
		if (mouse.x >= 84 && mouse.y >= 117 && mouse.x <= 106 && mouse.y <= 131)
			area = JokemonDriver.Area.Route_3;
		if (mouse.x >= 37 && mouse.y >= 125 && mouse.x <= 82 && mouse.y <= 131)
			area = JokemonDriver.Area.Route_11;
		if (mouse.x >= 45 && mouse.y >= 125 && mouse.x <= 52 && mouse.y <= 131)
			area = JokemonDriver.Area.Enumville;
		if (mouse.x >= 37 && mouse.y >= 98 && mouse.x <= 45 && mouse.y <= 131)
			area = JokemonDriver.Area.Route_12;
		if (mouse.x >= 37 && mouse.y >= 83 && mouse.x <= 44 && mouse.y <= 97)
			area = JokemonDriver.Area.Polymorph_Town;
		if (mouse.x >= 11 && mouse.y >= 101 && mouse.x <= 32 && mouse.y <= 119 && JokemonDriver.VERSION.equals("Peaches"))
			area = JokemonDriver.Area.Peach_City;
		if (mouse.x >= 11 && mouse.y >= 101 && mouse.x <= 32 && mouse.y <= 119 && JokemonDriver.VERSION.equals("Cream"))
			area = JokemonDriver.Area.Cream_City;
		if (mouse.x >= 75 && mouse.y >= 109 && mouse.x <= 90 && mouse.y <= 115)
			area = JokemonDriver.Area.Villa_Del_Joe;
		if (mouse.x >= 82 && mouse.y >= 69 && mouse.x <= 90 && mouse.y <= 109)
			area = JokemonDriver.Area.Route_4;
		if (mouse.x >= 82 && mouse.y >= 60 && mouse.x <= 106 && mouse.y <= 68)
			area = JokemonDriver.Area.Route_5;
		if (mouse.x >= 99 && mouse.y >= 49 && mouse.x <= 107 && mouse.y <= 57)
			area = JokemonDriver.Area.Mount_Java;
		if (mouse.x >= 110 && mouse.y >= 50 && mouse.x <= 118 && mouse.y <= 85)
			area = JokemonDriver.Area.Route_9;
		if (mouse.x >= 111 && mouse.y >= 86 && mouse.x <= 117 && mouse.y <= 100)
			area = JokemonDriver.Area.Recursive_Hot_Springs;
		if (mouse.x >= 112 && mouse.y >= 102 && mouse.x <= 118 && mouse.y <= 116)
			area = JokemonDriver.Area.Route_10;
		if (mouse.x >= 82 && mouse.y >= 49 && mouse.x <= 98 && mouse.y <= 56)
			area = JokemonDriver.Area.Route_7;
		if (mouse.x >= 55 && mouse.y >= 49 && mouse.x <= 82 && mouse.y <= 56)
			area = JokemonDriver.Area.Champions_Walk;
		if (mouse.x >= 55 && mouse.y >= 24 && mouse.x <= 62 && mouse.y <= 49)
			area = JokemonDriver.Area.Victory_Road;			
	}
	
	public void mouseMoved(MouseEvent e)
	{
		mouse.setLocation(e.getX(),e.getY());
		changeArea();
		changeDisplay();
	}
	public void mouseDragged(MouseEvent e){}
	
}