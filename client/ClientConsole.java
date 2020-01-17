package client;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.URL;
import java.util.List;

import client.*;
import client.Controllers.CatalogController;
import common.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole extends Application implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  public boolean flagCatalog;
  public static boolean flag;
  private Catalog catalog;

  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  public ChatClient client;
  
  //Constructors ****************************************************
  public ClientConsole() {}
  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   * @param loginID The user's ID.
   */
  public ClientConsole(String loginID, String host, int port) 
  {
    try 
    {
      client= new ChatClient(loginID, host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
    flagCatalog=false;
    flag=true;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println(message);
  }

  
  public void getData(Object data) {
	  System.out.println("got catalog");
	  if(data.toString().equals("#gotCatalog")) {
		  catalog=(Catalog)data;
		  flagCatalog=true;
		  System.out.println("set flag");
		  
	  }
  }
  
  //Class methods ***************************************************
  
 
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The user ID.
   * @param args[1] The host to connect to.
   * @param args[2] The port to connect to.
   */
  public static void main(String[] args) 
  {
	 launch(args);
	/*
    String host = "";
    int port = 0;  //The port number
    String loginID = "";
    try
    {
      loginID = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      System.out.println("usage: java ClientConsole loginID [host [port]]");
      System.exit(1);
    }
    try
    {
      host = args[1];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
    }
    try {
      port = Integer.parseInt(args[2]);
    } catch (ArrayIndexOutOfBoundsException e){
      port = DEFAULT_PORT;
    }
    ClientConsole chat= new ClientConsole(loginID, host, port);


    chat.accept();  //Wait for console data
    
    */
  }
  
  @Override
	public void start(Stage primaryStage) throws IOException {
	  
	  Parameters params = getParameters();
	  List<String> args = params.getRaw();
	  
	  String host = "";
	   int port = 0;  //The port number
	    String loginID = "";
	    try
	    {
	      loginID = args.get(0);
	    }
	    catch(IndexOutOfBoundsException e)
	    {
	      System.out.println("usage: java ClientConsole loginID [host [port]]");
	      System.exit(1);
	    }
	    try
	    {
	      host = args.get(1);
	    }
	    catch(IndexOutOfBoundsException e)
	    {
	      host = "localhost";
	    }
	    try {
	      port = Integer.parseInt(args.get(2));
	    } catch (IndexOutOfBoundsException e){
	      port = DEFAULT_PORT;
	    }
	  ClientConsole chat= new ClientConsole(loginID, host, port);
	  chat.client.handleMessageFromClientUI("#getCatalog");
	  
	  do {
		  try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(chat.flagCatalog);  
	  }
	  while(!chat.flagCatalog);

	  /*MyThread thread=new MyThread();
	  thread.start();
			try {
				thread.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		*/
	  
	  
	  
	  
	  
	  System.out.println("after send");
	  
	  
	  
	  
	  System.out.println("after flag");

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/CatalogScene.fxml"));     
			  
			Parent root = (Parent)fxmlLoader.load(); 
						  
			CatalogController controller = fxmlLoader.<CatalogController>getController();
			controller.setX(5);
			controller.setCatalog(chat.catalog.getList());	
						
			Scene scene = new Scene(root); 
					
			primaryStage.setScene(scene);    
					
			primaryStage.show(); 
			flagCatalog=false;
		
	

	
	  /*
	  	URL url=getClass().getResource("/client/fxml/CatalogScene.fxml");
		if(url==null)
			System.out.println("null");
		AnchorPane pane=FXMLLoader.load(url);
		
		
		
		
		Scene scence= new Scene(pane);
		
		
		primaryStage.setScene(scence);
		primaryStage.setTitle("Calculator - 322244575; 212267959");
		primaryStage.show();
		*/
	}


}
//End of ConsoleChat class