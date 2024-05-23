
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.CharArrayReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import java.awt.SystemColor;

public class SolverGUIARRAYS implements ActionListener {

	private JFrame frame;
	private JTextField txtUseSliderBelow;
	private JTextArea textArea;
	private String userInput = "";
	private char[] charArray;
	private String[] cmdArray;
	private String move;
	private Robot inputBot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SolverGUIARRAYS window = new SolverGUIARRAYS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SolverGUIARRAYS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Puzzle Solver");
		frame.setBounds(100, 100, 656, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{38, 0, 494, 20, 0};
		gridBagLayout.rowHeights = new int[]{95, 22, 16, 85, 35, 16, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Welcome to Puzzle Solver Z");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Paste your Arrow-Key command string from Alt1 Toolkit Below");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 2;
		frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 3;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textArea);
        frame.getContentPane().add(scrollPane, gbc_textArea);
	
		
		
		JLabel lblNewLabel_1 = new JLabel("**Make sure your settings for \"Invert Arrow Keys\" match for Alt1 Toolkit**");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 5;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel txtUseSliderBelow_1 = new JLabel("Use slider below to adjust random delay (Higher number = Slower completion)");
		txtUseSliderBelow_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_txtUseSliderBelow_1 = new GridBagConstraints();
		gbc_txtUseSliderBelow_1.insets = new Insets(0, 0, 5, 5);
		gbc_txtUseSliderBelow_1.anchor = GridBagConstraints.SOUTH;
		gbc_txtUseSliderBelow_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUseSliderBelow_1.gridx = 2;
		gbc_txtUseSliderBelow_1.gridy = 7;
		frame.getContentPane().add(txtUseSliderBelow_1, gbc_txtUseSliderBelow_1);
		

        
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this);
        
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(25);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 30, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 8;
		frame.getContentPane().add(slider, gbc);
        GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
        gbc_btnSubmit.anchor = GridBagConstraints.EAST;
        gbc_btnSubmit.insets = new Insets(20, 20, 0, 20);
        gbc_btnSubmit.gridx = 2;
        gbc_btnSubmit.gridy = 9;
        frame.getContentPane().add(btnSubmit, gbc_btnSubmit);
		
	}
	
    @Override public void actionPerformed(ActionEvent e) {
		userInput = textArea.getText().trim();
		// System.out.println(userInput.substring(0,50));
		ArrayList<String> cmdArray = trueTrim(userInput);
		try {
			execute(cmdArray);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

    	String error = "";
    	
    	if(error.equals("")) {
    		System.out.println(userInput);
    	}
    	// TODO : Methods for once user presses submit
    }

    
    public ArrayList<String> trueTrim(String inputString) {
        ArrayList<String> commands = new ArrayList<String>();
        String[] lineSplit = inputString.split("\n");
        for(int i=0;i<lineSplit.length;i++) {
               String line = lineSplit[i].trim();
               String[] numSplit = line.split("[.]");
               line = numSplit[1];
               while(!line.equals("")) {
                     if(line.indexOf("up") == 0) {
                            if(line.length() == 2) { line = ""; } else
                            line = line.substring(2,line.length());
                            commands.add("up");
                     } else if(line.indexOf("down") == 0) {
                            if(line.length() == 4) { line = ""; } else
                            line = line.substring(4,line.length());
                            commands.add("down");
                     } else if(line.indexOf("left") == 0) {
                            if(line.length() == 4) { line = ""; } else
                            line = line.substring(4,line.length());
                            commands.add("left");
                     } else if(line.indexOf("right") == 0) {
                            if(line.length() == 5) { line = ""; } else
                            line = line.substring(5,line.length());
                            commands.add("right");
                     } else {
                            System.out.println("ERROR!"); System.exit(0);
                            // TODO ERROR!!!!
                     }
               }
        }
        return commands;          
 }
    
    public void execute(ArrayList<String> cmdArray) throws Exception {
    	System.out.println(userInput);
    	inputBot = new Robot();
    	inputBot.delay(5000);
    	for (int i=0; i < cmdArray.size(); i++) {
    		move = cmdArray.get(i);
    		System.out.println(move);
    		if (move.equals("up")) {
    			inputBot.keyPress(KeyEvent.VK_UP);
    			inputBot.delay(50);
    			inputBot.keyRelease(KeyEvent.VK_UP);
    		} else if (move.equals("left")) {
    		    inputBot.keyPress(KeyEvent.VK_LEFT);
    		    inputBot.delay(50);
    		    inputBot.keyRelease(KeyEvent.VK_LEFT);
    		} else if (move.equals("down")) {
    			inputBot.keyPress(KeyEvent.VK_DOWN);
    			inputBot.delay(50);
    			inputBot.keyRelease(KeyEvent.VK_DOWN);
    		} else if (move.equals("right")) {
    			inputBot.keyPress(KeyEvent.VK_RIGHT);
    			inputBot.delay(50);
    			inputBot.keyRelease(KeyEvent.VK_RIGHT);
    		}
    		inputBot.delay(175);
    	}
    	System.out.println("Movements complete.");
    }
	// TODO : Separate specialized trim method      - focus management, randomized delay based on scroll bar, inputs+outputs	
	/* Example input string: 
     *
	 * Knot
Slide
Lockbox
Towers
Map
Click in the indicated direction of the empty spot to solve your puzzle.

1.leftupup
4.leftdownleft
7.upupright
10.upleftdown
16.downrightright
19.upleftdown
22.rightrightdown
25.leftleftleft
28.leftupright
31.rightdownleft
34.leftupright
37.rightrightright
40.downdownleft
43.leftdownleft
46.upleftdown
49.rightupup
52.rightupleft
55.downrightright
58.upleftdown
61.rightrightup
64.leftdowndown
67.downleftup
70.leftdownright
73.upleftleft
76.downrightup
79.uprightdown
82.rightupup
85.rightupleft
88.downrightdown
91.leftleftup
94.rightdownleft
97.leftupright
100.rightdowndown
103.rightdownleft
106.upupright
109.downleftup
112.rightupleft
115.downdownright
118.upupleft
121.downrightdown
124.downleftleft
127.leftupright
130.upleftdown
133.rightdownright
136.right
Solved, 136 moves
	 */
	 
}