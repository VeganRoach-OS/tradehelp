package com.veganroach;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 2mac
 */
public class MainWindow extends JFrame {
	private JMenuBar jmb;
	private JComboBox<String> i1;
	private JComboBox<String> i2;
	private JButton calc;
	private JSpinner qty1;
	private JTextField qty2;
	public MainWindow(String title){
		super(title);
		System.out.print("Creating main window...");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setLayout(new GridLayout(7,1,5,5));

		jmb = new JMenuBar();
		qty1 = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		qty1.setToolTipText("Enter the quantity of the sell item here.");
		i1 = new JComboBox<String>();
		i1.setToolTipText("This is the item being given.");
		qty2 = new JTextField("0");
		qty2.setToolTipText("You should receive this amount of the item below for your trade.");
		qty2.setEditable(false);
		i2 = new JComboBox<String>();
		i2.setToolTipText("This is the item being received.");
		calc = new JButton("Calculate");
		calc.setToolTipText("Click here to calculate the trade.");
		calc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				qty2.setText(Double.toString(Data.getExchangeRate((String)i1.getSelectedItem(),(String)i2.getSelectedItem(),(Integer)qty1.getValue())));
			}
		});

		for (String s : Data.names) {
			i1.addItem(s);
			i2.addItem(s);
		}

		setJMenuBar(jmb);
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				new AboutWindow();
			}
		});
		jmb.add(help);

		add(new JLabel("Trading", SwingConstants.CENTER));
		add(qty1);
		add(i1);
		add(new JLabel("for",SwingConstants.CENTER));
		add(i2);
		add(calc);
		add(qty2);
		System.out.println(" done.");
	}
}
