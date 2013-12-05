package com.veganroach;

import javax.swing.*;
import java.awt.*;

/**
 * @author 2mac
 */
public class AboutWindow extends JFrame {
	public AboutWindow(){
		super("About TradeHelp");
		System.out.println("\n");
		System.out.println("Opened About window.");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,2,5,5));
		setVisible(true);
		setResizable(false);
		setSize(350,200);

		add(new JLabel("Distribution:"));
		add(new JLabel("Community"));
		add(new JLabel("Java Version:"));
		add(new JLabel(System.getProperty("java.version")));
		add(new JLabel("TradeHelp Version:"));
		add(new JLabel(Main.SOFTWARE_VERSION));
		add(new JLabel("mats.xml Version:"));
		add(new JLabel(Data.getMatsXmlVersion()));
		add(new JLabel("recipes.xml Version:"));
		add(new JLabel(Data.getRecipesXmlVersion()));
	}
}
