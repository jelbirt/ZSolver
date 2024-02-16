/*
 * @Author Jacob Elbirt
 * Full source code for full-stack application using Java (.jar)
 * to interact with a third-party puzzle solving tool and
 * game client to automate the solving of 5x5 slide puzzles
 */


 import java.awt.EventQueue;

 import javax.swing.JFrame;
 import javax.swing.JButton;
 import javax.swing.JLabel;
 import javax.swing.JScrollPane;
 
 import javax.swing.JTextField;
 import javax.swing.SwingConstants;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 
 import java.awt.Font;
 import java.awt.GridBagLayout;
 import java.awt.GridBagConstraints;
 import java.awt.Insets;
 import java.awt.Robot;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.InputEvent;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 import java.util.Random;
 
 import javax.swing.JCheckBox;
 import javax.swing.JTextArea;
 import javax.swing.JSlider;
 import javax.swing.JPanel;
 
 public class SolverGUI implements ActionListener, ChangeListener, KeyListener, Runnable {
 
     private Thread exeThread;
     private JFrame frame;
     private JLabel welcomeLBL;
     private JLabel pasteLBL;
     private JTextArea CMDtextArea;
     private JLabel invArrows;
     private String inputDelay = "3000";  //default value of 3s
     private String baseDelay;
     private String varDelay;
     private JPanel basePanel;
     private JPanel varPanel;
     private JSlider bValSlider;
     private JLabel bValSliderLBL;
     private JTextField bDelayField;
     private JSlider vValSlider;
     private JLabel vValSliderLBL;
     private JTextField vDelayField;
     private JLabel autoReturnLBL;
     private JCheckBox autoReturnCBX;
     private JButton btnSubmit;
     
     private String userInput = "";
     private char[] charArray;
     private String move;
     private Robot inputBot;
     private JLabel lblNewLabel;
     private JTextField iDelayField;
 
 
     /**
      * Launch the application.
      */
     public static void main(String[] args) {
         EventQueue.invokeLater(new Runnable() {
             public void run() {
                 try {
                     SolverGUI window = new SolverGUI();
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
     public SolverGUI() {
         initialize();
     }
 
     /**
      * Initialize the contents of the frame.
      */
     private void initialize() {
         frame = new JFrame();
         frame.setTitle("Puzzle Solver");
         frame.setBounds(100, 100, 702, 553);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         GridBagLayout gridBagLayout = new GridBagLayout();
         gridBagLayout.columnWidths = new int[]{38, 0, 494, 20, 0};
         gridBagLayout.rowHeights = new int[]{95, 16, 0, 85, 35, 0, 0, 0, 0, 0, 0, 0};
         gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0};
         gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
         frame.getContentPane().setLayout(gridBagLayout);
         
         welcomeLBL = new JLabel("Welcome to Puzzle Solver Z");
         welcomeLBL.setFont(new Font("Tahoma", Font.PLAIN, 18));
         GridBagConstraints gbc_welcomeLBL = new GridBagConstraints();
         gbc_welcomeLBL.anchor = GridBagConstraints.NORTHWEST;
         gbc_welcomeLBL.insets = new Insets(30, 0, 5, 5);
         gbc_welcomeLBL.gridx = 2;
         gbc_welcomeLBL.gridy = 0;
         frame.getContentPane().add(welcomeLBL, gbc_welcomeLBL);
         
         pasteLBL = new JLabel("Paste your Arrow-Key command string from Alt1 Toolkit Below");
         pasteLBL.setFont(new Font("Tahoma", Font.PLAIN, 13));
         GridBagConstraints gbc_pasteLBL = new GridBagConstraints();
         gbc_pasteLBL.anchor = GridBagConstraints.NORTHWEST;
         gbc_pasteLBL.insets = new Insets(0, 0, 5, 5);
         gbc_pasteLBL.gridx = 2;
         gbc_pasteLBL.gridy = 1;
         frame.getContentPane().add(pasteLBL, gbc_pasteLBL);
         
         invArrows = new JLabel("**Make sure your settings for \"Invert Arrow Keys\" match for Alt1 Toolkit**");
         invArrows.setFont(new Font("Tahoma", Font.BOLD, 13));
         GridBagConstraints gbc_invArrows = new GridBagConstraints();
         gbc_invArrows.insets = new Insets(0, 0, 5, 5);
         gbc_invArrows.anchor = GridBagConstraints.SOUTH;
         gbc_invArrows.fill = GridBagConstraints.HORIZONTAL;
         gbc_invArrows.gridx = 2;
         gbc_invArrows.gridy = 2;
         frame.getContentPane().add(invArrows, gbc_invArrows);
         
         CMDtextArea = new JTextArea();
         GridBagConstraints gbc_CMDtextArea = new GridBagConstraints();
         gbc_CMDtextArea.gridheight = 2;
         gbc_CMDtextArea.fill = GridBagConstraints.BOTH;
         gbc_CMDtextArea.insets = new Insets(0, 0, 5, 5);
         gbc_CMDtextArea.gridx = 2;
         gbc_CMDtextArea.gridy = 3;
         JScrollPane scrollPane = new JScrollPane();
         scrollPane.setViewportView(CMDtextArea);
         frame.getContentPane().add(scrollPane, gbc_CMDtextArea);
         CMDtextArea.addKeyListener(this);
         
         bValSliderLBL = new JLabel("Base Delay Value (minimum delay between moves)");
         bValSliderLBL.setHorizontalAlignment(SwingConstants.LEFT);
         bValSliderLBL.setFont(new Font("Tahoma", Font.PLAIN, 13));
         GridBagConstraints gbc_bValSliderLBL = new GridBagConstraints();
         gbc_bValSliderLBL.insets = new Insets(0, 0, 5, 5);
         gbc_bValSliderLBL.anchor = GridBagConstraints.SOUTHWEST;
         gbc_bValSliderLBL.gridx = 2;
         gbc_bValSliderLBL.gridy = 5;
         frame.getContentPane().add(bValSliderLBL, gbc_bValSliderLBL);	
         
         basePanel = new JPanel();
         GridBagConstraints gbc_basePanel = new GridBagConstraints();
         gbc_basePanel.anchor = GridBagConstraints.WEST;
         gbc_basePanel.insets = new Insets(0, 5, 5, 5);
         gbc_basePanel.fill = GridBagConstraints.VERTICAL;
         gbc_basePanel.gridx = 2;
         gbc_basePanel.gridy = 6;
         frame.getContentPane().add(basePanel, gbc_basePanel);
         
         bValSlider = new JSlider();
         bValSlider.setName("BASEVALUESLD");
         bValSlider.setMaximum(500);
         bValSlider.setValue(120);
         bValSlider.setMajorTickSpacing(100);
         basePanel.add(bValSlider);
         bValSlider.setPaintLabels(true);
         bValSlider.setPaintTicks(true);
         bValSlider.addChangeListener(this);
         
         baseDelay = Integer.toString(bValSlider.getValue());
         bDelayField = new JTextField();
         bDelayField.setName("BASEVALUETXT");
         bDelayField.setText(baseDelay);
         basePanel.add(bDelayField);
         bDelayField.setColumns(10);
         
         lblNewLabel = new JLabel("    Delay Time from SUBMIT");
         basePanel.add(lblNewLabel);
         
         iDelayField = new JTextField();
         iDelayField.setText(inputDelay);
         basePanel.add(iDelayField);
         iDelayField.setColumns(5);
         
         vValSliderLBL = new JLabel("Variable Delay Value (max randomized value added to delay)");
         vValSliderLBL.setHorizontalAlignment(SwingConstants.LEFT);
         vValSliderLBL.setFont(new Font("Tahoma", Font.PLAIN, 13));
         GridBagConstraints gbc_vValSliderLBL = new GridBagConstraints();
         gbc_vValSliderLBL.insets = new Insets(0, 0, 5, 5);
         gbc_vValSliderLBL.anchor = GridBagConstraints.SOUTHWEST;
         gbc_vValSliderLBL.gridx = 2;
         gbc_vValSliderLBL.gridy = 7;
         frame.getContentPane().add(vValSliderLBL, gbc_vValSliderLBL);	
         
         varPanel = new JPanel();
         GridBagConstraints gbc_varPanel = new GridBagConstraints();
         gbc_varPanel.anchor = GridBagConstraints.WEST;
         gbc_varPanel.insets = new Insets(0, 5, 5, 5);
         gbc_varPanel.fill = GridBagConstraints.VERTICAL;
         gbc_varPanel.gridx = 2;
         gbc_varPanel.gridy = 8;
         frame.getContentPane().add(varPanel, gbc_varPanel);
         
         vValSlider = new JSlider();
         vValSlider.setName("VARVALUESLD");
         vValSlider.setMaximum(100);
         vValSlider.setValue(80);
         vValSlider.setMajorTickSpacing(20);
         varPanel.add(vValSlider);
         vValSlider.setPaintLabels(true);
         vValSlider.setPaintTicks(true);
         vValSlider.addChangeListener(this);
         
         varDelay = Integer.toString(vValSlider.getValue());
         vDelayField = new JTextField();
         vDelayField.setName("VARVALUETXT");
         vDelayField.setText(varDelay);
         varPanel.add(vDelayField);
         vDelayField.setColumns(10);
         
         autoReturnLBL = new JLabel("          Auto-Return to RS Client");
         autoReturnLBL.setToolTipText("RS Client must have been the client focused prior to Puzzle Solver - Uses Alt+Tab and Right Click to automatically focus RS Client");
         varPanel.add(autoReturnLBL);
         
         autoReturnCBX = new JCheckBox("");
         autoReturnCBX.setHorizontalAlignment(SwingConstants.TRAILING);
         autoReturnCBX.setSelected(true);
         varPanel.add(autoReturnCBX);
         
         btnSubmit = new JButton("Submit");
         btnSubmit.addActionListener(this);
         GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
         gbc_btnSubmit.anchor = GridBagConstraints.EAST;
         gbc_btnSubmit.insets = new Insets(20, 20, 20, 0);
         gbc_btnSubmit.gridx = 2;
         gbc_btnSubmit.gridy = 10;
         frame.getContentPane().add(btnSubmit, gbc_btnSubmit);
         
     }
     
     
     @Override
     public void stateChanged(ChangeEvent e) {
         if(e.toString().contains("BASEVALUESLD")) {
             baseDelay = Integer.toString(bValSlider.getValue());
             bDelayField.setText(baseDelay);
         } else if (e.toString().contains("VARVALUESLD")) {
             varDelay = Integer.toString(vValSlider.getValue());
             vDelayField.setText(varDelay);
         }
 
     }
     
     @Override public void actionPerformed(ActionEvent e) {
         CMDtextArea.requestFocus();
         exeThread = new Thread(new Runnable() {
             @Override
             public void run() {
                 userInput = CMDtextArea.getText().trim();
                 trueTrim(userInput);
                 if (baseDelay != bDelayField.getText()) {
                     bValSlider.setValue(Integer.parseInt(bDelayField.getText()));
                 }
                 if (varDelay != vDelayField.getText()) {
                     vValSlider.setValue(Integer.parseInt(vDelayField.getText()));
                 }
                 if (inputDelay != iDelayField.getText()) {
                     inputDelay = iDelayField.getText();
                 }
                 try {
                     execute(userInput);
                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
             }
         });
         String error = "";
         if(error.equals("")) {
             System.out.println(userInput);
         }
         exeThread.start();
     }
     
     public void trueTrim(String inputString) {
         charArray = inputString.toCharArray();
         for (int i=0;i<userInput.length(); ++i) {
             if (Character.isDigit(charArray[i])) {
                 userInput = userInput.substring(i, userInput.length());
                 break;
             }
         }
         if(userInput.contains("\nSolving")) {
             userInput = userInput.substring(0, userInput.indexOf("\nSolving"));
         } else {
             userInput = userInput.substring(0, userInput.indexOf("\nSolved"));
         }
         userInput = userInput.replaceAll("\n", "");
         charArray = userInput.toCharArray();
         for (int i=0;i<userInput.length();++i) {
             if (Character.isDigit(charArray[i])) {
                 String holder = "";
                 holder += charArray[i];
                 userInput = userInput.replace(holder, "");
             }
         }
         userInput = userInput.replace(".", "");
         charArray = userInput.toCharArray();
     }
     
     public void execute(String inputString) throws Exception {
         CMDtextArea.requestFocusInWindow();
         System.out.println(userInput);
         inputBot = new Robot();
         if (autoReturnCBX.isSelected()) {
             focusReturn();
         }
         inputBot.delay(Integer.parseInt(inputDelay));
         while(!userInput.isEmpty()) {
             CMDtextArea.requestFocusInWindow();
             move = userInput.substring(0,2);
             System.out.println(move);
             if (move.equals("up")) {
                 inputBot.keyPress(KeyEvent.VK_UP);
                 inputBot.delay(30);
                 inputBot.keyRelease(KeyEvent.VK_UP);
             } else if (move.equals("le")) {
                 move = userInput.substring(0,4);
                 inputBot.keyPress(KeyEvent.VK_LEFT);
                 inputBot.delay(30);
                 inputBot.keyRelease(KeyEvent.VK_LEFT);
             } else if (move.equals("do")) {
                 move = userInput.substring(0,4);
                 inputBot.keyPress(KeyEvent.VK_DOWN);
                 inputBot.delay(30);
                 inputBot.keyRelease(KeyEvent.VK_DOWN);
             } else if (move.equals("ri")) {
                 move = userInput.substring(0,5);		// sets substring to 5 characters for "right" rather than 4 for "down" or "left"
                 inputBot.keyPress(KeyEvent.VK_RIGHT);
                 inputBot.delay(30);
                 inputBot.keyRelease(KeyEvent.VK_RIGHT);
             }
             inputBot.delay(rando());
             userInput = userInput.substring(move.length(),userInput.length());
             System.out.println(userInput);
         }
         System.out.println("Movements complete.");
         CMDtextArea.setText("");
     }
     
     public int rando() {
         Random randGen = new Random(System.currentTimeMillis());
         int rando = Integer.parseInt(baseDelay) + (int) Math.floor(randGen.nextDouble() * Integer.parseInt(varDelay));	// BASE value and VARIANCE values
         System.out.println("delay is " + rando);		
         return rando;
     }
 
     public void focusReturn () {
         inputBot.keyPress(KeyEvent.VK_ALT);
         inputBot.keyPress(KeyEvent.VK_TAB);
         inputBot.delay(15);
         inputBot.keyRelease(KeyEvent.VK_TAB);
         inputBot.keyRelease(KeyEvent.VK_ALT);
         inputBot.delay(30);
         inputBot.mousePress(InputEvent.BUTTON3_DOWN_MASK);		// TODO 
         inputBot.delay(30);
         inputBot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
     }
     
     @Override
     public void keyPressed(KeyEvent e) {
         System.out.println("KEYLISTENER RUNNING");
         if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
             System.exit(0);
         }
     }
     
     @Override
     public void keyTyped(KeyEvent e) {
     }
 
     @Override
     public void keyReleased(KeyEvent e) {	
     }
 
     @Override
     public void run() {
         }
         
         
     }
     
     /* 
      * 		Example input string:
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
 79.uprightdown[
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