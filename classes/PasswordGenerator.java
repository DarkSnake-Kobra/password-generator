/* Copyright 2016 Jordan Bottoms

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
import java.util.Scanner;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

public class PasswordGenerator extends JFrame
{
	private JTextField passBox;
	private JButton generatePw;
	private JButton close;
	private JPanel panel;
	final int WINDOW_WIDTH = 310;
	final int WINDOW_HEIGHT = 100;
	
	public PasswordGenerator()
	{
		setTitle("Password Generator");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelBuilder();
		add(panel);
		setVisible(true);
	}
	
	private void panelBuilder()
	{
		passBox = new JTextField(16);
		generatePw = new JButton("Generate Password");
		close = new JButton("Close");
		
		generatePw.addActionListener(new generatePwButton());
		close.addActionListener(new closeButton());
		panel = new JPanel();
		
		panel.add(passBox);
		panel.add(generatePw);
		panel.add(close);
		
	}
	/* Following class generates the password:
	 * A. starts by declaring variables
	 * B. Store the contents of a configuration file
	 * in the size variable rather then hard code the
	 * password size into the size variable.
	 * C. start the while loop using the defined size and checking
	 * the string length in order to decide to continue
	 * D. Creates a new instance of the random class which
	 * creates a random number between 0 and 2 
	 * for deciding on lower or uppecase and numbers.
	 * */
	private class generatePwButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			char c;
			int casing;
			
			String password = "";
			String characters = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int charLength = characters.length();
			int size = 0;
			int count = 0;
			int temp;
			char cTemp;
			try
			{
				File file = new File("config");
				Scanner inputFile = new Scanner(file);
			
				size = inputFile.nextInt();
				inputFile.close();
			}
			catch (FileNotFoundException d)
			{
				JOptionPane.showMessageDialog(null, d.getMessage());
				System.exit(0);
			}
			while(count < size)
			{
				temp = (int) (Math.random() * charLength + 0);
				cTemp = characters.charAt(temp);
				password = password + Character.toString(cTemp);
				count++;
			}
			passBox.setText(password);
			
			// call function to write to log
			String text = "";
			try
			{
				text = passBox.getText();
				FileWriter log = new FileWriter("log", true);
				PrintWriter logFile = new PrintWriter(log);
				logFile.println(text);
				logFile.close();
			}
			catch(IOException f)
			{
				JOptionPane.showMessageDialog(null, f.getMessage());
				System.exit(0);
			}
		
		}
	}	
	
private class closeButton implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}
}
	
	public static void main(String[] args)
	{
		new PasswordGenerator();
	}
}
