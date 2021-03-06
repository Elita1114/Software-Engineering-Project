// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;


import common.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

import client.Controllers.ComplaintController;
import client.Controllers.HandleComplaintController;

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
  private StoresList storeslist;
  private User loggedUser;
  private boolean logged;
  private boolean wrongdetails;
  public boolean flagServerAns;
  public boolean useralreadyExist;
  private boolean loggedalready;

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
    this.setWrongdetails(false);
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
    this.setWrongdetails(false);
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
	  else if(msg.toString().equals("#gotStores")) {
		  storeslist = (StoresList)msg;
		  System.out.println(storeslist);
		  System.out.println(storeslist.stores);
		  this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#setdelivered")) {
		  this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#deleteorder")) {
		  this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#addedtocart")) {
		  this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#useralreadylogged")) {
		  this.loggedalready=true;
	  }
	  else if(msg.toString().equals("#removefromcart")) {
		  this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#wrongdetails")) {
		  this.setWrongdetails(true); 
	  }
	  else if(msg.toString().equals("#gotComplaints")) {
		  HandleComplaintController.complaintsList=(ComplaintsList)msg;
		  HandleComplaintController.flag=true;
	  }
	  else if(msg.toString().equals("#userGetComplaints")) {
		  ComplaintController.complaintsList=(ComplaintsList)msg;
		  ComplaintController.flag=true;
	  }
	  else if(msg.toString().equals("#isUrgent")) {
		  MyThread.flagAnswer=true;
		  MyThread.flagUrgent=((ReturnStatus)msg).status;
	  }
	  else if(msg.toString().equals("#gotOrder")) {
		  System.out.println("gotOrder");
	  }
	  else if(msg.toString().equals("#addComplaint")) {
		  ComplaintController.complaintFlag=((ReturnStatus)msg).status;
	  }
	  else if(msg.toString().equals("#updateComplaint")) {
		  HandleComplaintController.updateComplaint=((ReturnStatus)msg).status;
	  }
	  else if(msg.toString().equals("#dropCatalog")) {
		  flagServerAns=true;
		  System.out.println("drop flag true\n");
	  }
	  else if(msg instanceof Cart) {
		  System.out.println("Got Cart");
		  this.flagServerAns=true;
		  clientUI.getData(msg);
	  }
	  else if(msg instanceof MonthlyReportList) {
		  System.out.println("Got reports");
	  		clientUI.getData(msg);
			this.flagServerAns=true;
	  }
	  else if(msg instanceof OrdersList) {
		  System.out.println("Got orders");
	  		clientUI.getData(msg);
			this.flagServerAns=true;
	  }
	  else if(msg.toString().equals("#UpdateItem")) {
		  flagServerAns=true;
		  System.out.println("update catalog item flag true\n");
	  }
	  else if(msg.toString().equals("#delCatalogItem")) {
		  flagServerAns=true;
		  System.out.println("delete catalog item flag true\n");
	  }
	  else if(msg.toString().equals("#addCatalogItem")) {
		  flagServerAns=true;
		  System.out.println("add catalog item flag true\n");
	  }
	  else if(msg.toString().equals("#findUser")) {
		  flagServerAns=true;
	  		clientUI.getData(msg);
		  System.out.println("find User flag true\n");
	  }
	  else if(msg.toString().equals("#wrongUser")) {
		  flagServerAns=true;
		  System.out.println("find User flag true\n");
	  }
	  else if(msg.toString().equals("#UpdateUser")) {
		  flagServerAns=true;
		  System.out.println("update User flag true\n");
	  }
	  else if(msg.toString().equalsIgnoreCase("#useralreadyExist")) {
		  useralreadyExist= true;
		  flagServerAns=true;
		  System.out.println("update User flag true\n");
	  }
	  else if(msg.toString().equalsIgnoreCase("#signupsuccessfully")) {
		  flagServerAns=true;
	  }
	  else if(msg.toString().equals("#customItem")) {
		  flagServerAns=true;
		  System.out.println("custom Item flag true\n");
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
		  System.out.println(user_request);
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

public User getLoggedUser() {
	return loggedUser;
}

public void setLoggedUser(User loggedUser) {
	this.loggedUser = loggedUser;
}

public boolean isWrongdetails() {
	return wrongdetails;
}

public void setWrongdetails(boolean wrongdetails) {
	this.wrongdetails = wrongdetails;
}

public StoresList getStoreslist() {
	return storeslist;
}

public void setStoreslist(StoresList storeslist) {
	this.storeslist = storeslist;
}

public boolean isLogged() {
	return logged;
}

public void setLogged(boolean logged) {
	this.logged = logged;
}

public boolean isLoggedalready() {
	return loggedalready;
}

public void setLoggedalready(boolean loggedalready) {
	this.loggedalready = loggedalready;
}
}
//End of ChatClient class
