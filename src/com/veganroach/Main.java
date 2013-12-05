package com.veganroach;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author 2mac
 */
public class Main {
	public static final String SOFTWARE_VERSION = "1.1.1";
	public static void main(String[] args){
		try {
			PrintStream out = new PrintStream(new FileOutputStream("tradehelp.log"));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println("An error has occurred. Contact Developer.");
			e.printStackTrace();
		}
		System.out.println("TradeHelp Community Edition -- Version " + SOFTWARE_VERSION);
		System.out.println("JVM Version: " + System.getProperty("java.version"));
		Data.initVariables();
		System.out.println(Data.getXmlVersions());
		Data.initData();

		//JFrame.setDefaultLookAndFeelDecorated(true);
		MainWindow gui = new MainWindow("TradeHelp " + SOFTWARE_VERSION);
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(315, 250);
		gui.setBackground(Color.LIGHT_GRAY);
		gui.setVisible(true);
	}
}
