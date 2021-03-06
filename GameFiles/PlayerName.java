import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;


/**
 * Creates a dialog to ask the player for their name and stores their name.
 * 
 * @author Richard Dang and Benson Guo
 * @version 1.3, May 18, 2014
 */
public class PlayerName implements ActionListener
{
  /**
   * Allows access to JButton methods.
   */
  private JButton continueButton;
  /**
   * Allows access to JDialog methods.
   */
  private JDialog d;
  /**
   * Allows access to JTextField methods.
   */
  private JTextField tField;
  /**
   * Stores the player's name.
   */
  static String playerName;
  /**
   * Checks if the enter key has been pressed.
   */
  private KeyEventDispatcher key;
  
  
  /**
   * Class constructor that creates the name dialog, asks them for their name
   * and sets up the enter key to allow them to continue.
   */
  public PlayerName ()
  {
    TetristryApp.enableMI = false;
    TetristryApp.enableDisableMenuItems();
    nameDialog();
    enterKey();
  }
  
  /**
   * Creates the name dialog which includes a label asking the player for their 
   * name, a textfield for input and a button to continue to the game.
   * 
   * @param nameLabel JLabel: Allows access to JLabel methods. 
   * @param e reference: Allows access to IOException methods.
   * @throws IOException Thrown to indicate an error when importing the image.
   */
  public void nameDialog ()
  {
    d = new JDialog (TetristryApp.t, "Player Name");
    d.setSize (400,200);
    d.setResizable(false);
    d.setLayout (null);
    d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    
    JLabel nameLabel = new JLabel ("What is your name? (Max. 10 characters)");
    nameLabel.setFont (new Font ("Arial", Font.BOLD, 16));
    nameLabel.setBounds(new Rectangle(new Point(40, 20), nameLabel.getPreferredSize())); 
    d.add (nameLabel);
    d.setLocationRelativeTo (TetristryApp.t);
    d.setVisible (true);
    
    tField = new JTextField (25);
    tField.setBounds(new Rectangle(new Point(60, 60), tField.getPreferredSize())); 
    d.add (tField);
    
    try{
      BufferedImage continues = ImageIO.read(new File( "../Images/ContinueButton.png"));
      continueButton = new JButton(new ImageIcon(continues));
      continueButton.setBounds(new Rectangle(new Point(100, 100), continueButton.getPreferredSize())); 
      continueButton.setBorder(BorderFactory.createEmptyBorder());
      continueButton.setContentAreaFilled(false);
      d.add(continueButton);
      continueButton.addActionListener (this);
    }
    catch (IOException e)
    {}
  }
  
  /**
   * Used to allow the user to continue to the game by clicking the
   * enter button instead of using the mouse.
   */
  public void enterKey()
  {
    key=new KeyEventDispatcher() {  
      /**
       * Checks to see if the enter key is being pressed and continues to game if so.
       * 
       * @param keyHandled boolean: checks if the key is pressed.
       * @return true if the enter key is pressed, otherwise false.
       */
      public boolean dispatchKeyEvent(KeyEvent e) 
      {  
        boolean keyHandled = false;  
        if (e.getID() == KeyEvent.KEY_PRESSED) 
        {  
          if (e.getKeyCode() == KeyEvent.VK_ENTER)
          {  
            continueToGame(); 
            keyHandled = true;  
          }
        }  
        return keyHandled;  
      }  
    };
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(key);  
  }
  
  /**
   * Checks the player's name length and stores it if it is less than 10 characters long.
   * The dialog is closed after the name is accepted and the player goes to the game panel.
   */
  public void continueToGame ()
  {
    playerName = tField.getText().trim();
    if (playerName.length () <=10 && playerName.length () > 0)
    {
      KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(key);
      d.dispose();
      TetristryApp.gamePanel();
      TetristryApp.enableMI = true;
      TetristryApp.enableDisableMenuItems();
    }
    else 
      tField.setText ("");
  }
  
  /**
   * Uses action listener to check if one of the buttons are pressed
   * and performs the action assosiated with it.
   * During this time, the menu items are enabled.
   * Continue allows the player to continue to the game.
   * 
   * @param ae ActionEvent: allows access to ActionEvent methods
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getSource() == continueButton) 
    {
      continueToGame ();
    }
    else nameDialog ();
  }
  
}