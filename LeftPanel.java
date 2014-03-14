import java.awt.*;
import javax.swing.*;

public class LeftPanel extends JPanel
{
	public int level;
	public int lives;
	public int safeJumps;

	public JLabel levelLabel;
	public JLabel livesLabel;
	public JLabel safeLabel;

	public LeftPanel()
	{
		level = 1;
		lives = 3;
		safeJumps = 3;
		setupPanel();
	}

	public void updateLabels()
	{
		levelLabel.setText(level + "");
		livesLabel.setText(lives + "");
		safeLabel.setText(safeJumps + "");
	}

	public void setupPanel()
	{
		this.setLayout(new GridLayout(0,1));
		Font f1 = new Font("Sans Serif", Font.BOLD, 30);

		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(0,1));

		JLabel lbl1 = new JLabel("Level", JLabel.CENTER);
		lbl1.setFont(f1);
		levelLabel = new JLabel(level + "", JLabel.CENTER);
		levelLabel.setFont(f1);

		Font f2 = new Font("Sans Serif", Font.BOLD, 20);

		JLabel lbl2 = new JLabel("Lives:", JLabel.CENTER);
		lbl2.setFont(f2);
		livesLabel = new JLabel(lives +"", JLabel.CENTER);
		livesLabel.setFont(f2);

		JLabel lbl3 = new JLabel("Jumps:", JLabel.CENTER);
		lbl3.setFont(f2);
		safeLabel = new JLabel(safeJumps +"", JLabel.CENTER);
		safeLabel.setFont(f2);

		statsPanel.add(lbl1);
		statsPanel.add(levelLabel);

		statsPanel.add(lbl2);
		statsPanel.add(livesLabel);

		statsPanel.add(lbl3);
		statsPanel.add(safeLabel);

		this.add(statsPanel);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(0,1));

		lowerPanel.add(new JLabel("Movement:", JLabel.CENTER));
		lowerPanel.add(new JLabel("q  w  e", JLabel.CENTER));
		lowerPanel.add(new JLabel("a  s  d", JLabel.CENTER));
		lowerPanel.add(new JLabel("z  x  c", JLabel.CENTER));
		lowerPanel.add(new JLabel("", JLabel.CENTER));
		lowerPanel.add(new JLabel("Jump:", JLabel.CENTER));
		lowerPanel.add(new JLabel("<space>", JLabel.CENTER));
		lowerPanel.add(new JLabel("", JLabel.CENTER));

		this.add(lowerPanel);


	}



}