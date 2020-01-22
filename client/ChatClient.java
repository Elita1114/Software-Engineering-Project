// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;


import common.*;
import javafx.scene.control.Alert;

import java.io.*;

import client.Controllers.ComplaintController;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  /**
   * The Login ID of the user.
   */
  String loginID;

  User loggedUser;
  boolean logged;
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
    this.loginID = "ANONYMOUS";
    this.logged = false;
    sendToServer("#login ANONYMOUS");
  }

  /**
   * Constructs an instance of the chat client.
   *
   * @param loginID The user ID.
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginID, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = loginID;
    this.logged = false;
    openConnection();
    sendToServer("#login " + loginID);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  if(msg.toString().equals("#gotCatalog")) {
	  		clientUI.getData(msg);
	  }
	  else if(msg.toString().equals("#gotUser")) {
		  loggedUser = (User)msg;
		  this.logged = true;
	  }
	  else if(msg.toString().equals("#gotOrder")) {
		  System.out.println("gotOrder");
	  }
	  else if(msg.toString().equals("#addComplaint")) {
		  ComplaintController.complaintFlag=((ReturnStatus)msg).status;
	  }
	  else{
		  clientUI.display(msg.toString());
	  }
  }

   /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(UserRequest user_request)
  {
	  System.out.println("trying to send to server in handle ");
	  try {
    	  sendToServer(user_request);
      }
      catch(IOException e){
        clientUI.display("Could not send message to server.  Terminating client.");
        quit();
      }
  }
  
  public void handleMessageFromClientUI(String message)
  {
    // detect commands
    if (message.charAt(0) == '#')
    {
      runCommand(message);
    }
    else
    {
      try{
    	  sendToServer(message);
      }
      catch(IOException e){
        clientUI.display("Could not send message to server.  Terminating client.");
        quit();
      }
    }
  }

  /**
   * This method executes client commands. Benjamin Bergman, Oct 22, 
   * 2009
   *
   * @param message string from the client console
   */
  private void runCommand(String message)
  {
    // a bunch of ifs

    if (message.equalsIgnoreCase("#quit"))
    {
      quit();
    }
    else if (message.equalsIgnoreCase("#logoff"))
    {
      try
      {
        closeConnection();
      }
      catch(IOException e) {}
      clientUI.display("You have logged off.");
    }
    else if (message.toLowerCase().startsWith("#setport"))
    {
      // requires the command, followed by a space, then the port number
      try 
      {
        int newPort = Integer.parseInt(message.substring(9));
        setPort(newPort);
        // error checking for syntax a possible addition
        clientUI.display
          ("Port changed to " + getPort());
      }
      catch (Exception e)
      {
        System.out.println("Unexpected error while setting client port!");
      }
    }
    else if (message.toLowerCase().startsWith("#sethost"))
    {
      setHost(message.substring(9));
      clientUI.display
        ("Host changed to " + getHost());
    }
    else if (message.toLowerCase().startsWith("#login"))
    {
      if (isConnected())
      {
        clientUI.display("You must logout before you can login.");
        return;
      }
      // login
      // if no id, login anonymous
      loginID = message.substring(7);
      try
      {
        openConnection();
        sendToServer("#login " + loginID);
      }
      catch (Exception e)
      {
        clientUI.display("Connection could not be established.");
      }
    }
    else if (message.equalsIgnoreCase("#gethost"))
    {
      clientUI.display("Current host: " + getHost());
    }
    else if (message.equalsIgnoreCase("#getport"))
    {
      clientUI.display("Current port: " + Integer.toString(getPort()));
    }
    else if (message.equalsIgnoreCase("#productslist"))
    {
    	System.out.println("product list");
    	try {
			sendToServer("#productslist");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else if (message.toString().startsWith("#changeprice "))
    {
    	try {
			sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else {
    	try
        {
    		System.out.println("in chat client: "+message);
    		sendToServer(message);
        }
        catch(IOException e)
        {
          clientUI.display
            ("Could not send message to server.  Terminating client.");
          quit();
        }
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  public boolean getlogged()
  {
	  return logged;
  }
  /**
   * Reacts to a closed connection while waiting for
   * messages from the server. Overrides method in 
   * <code>AbstractClient</code>. Added by Benjamin 
   * Bergman, Oct 22, 2009.
   *
   * @param exception the exception raised.
   */
  protected void connectionException(Exception exception)
  {
    clientUI.display
      ("The connection to the Server (" + getHost() + ", " + getPort() + 
      ") has been disconnected");
  }
}
//End of ChatClient class
