package client;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.URL;
import java.util.List;

import client.*;
import client.Controllers.CatalogController;
import client.Controllers.MainController;
import common.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
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
  public boolean flagCart;
  public boolean flagReports;
  public static boolean flag;
  public Catalog catalog;
  public Cart cart;
  public MonthlyReportList monthly_reports;
  public OrderLis monthly_reports;
  public User foundUser;


  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat. 
   **/
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
      client = new ChatClient(loginID, host, port, this);
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
	  System.out.println("getting data");
	  if(data instanceof Catalog) {
		  System.out.println("got catalog");
		  catalog=(Catalog)data;
		  flagCatalog=true;
		  System.out.println("set flag");
		  
	  }
	  else if (data instanceof Cart) {
		  System.out.println("got cart");
		  cart = (Cart) data;
		  System.out.println(cart);
		  flagCart = true;
		  System.out.println("set flag");
	  }
	  else if (data instanceof MonthlyReportList) {
		  System.out.println("got reports data");
		  monthly_reports = (MonthlyReportList) data;
		  System.out.println("item list contains: "+ monthly_reports.getItemList());
	  }
	  else if (data instanceof UserRequest && data.toString().equals("#findUser")) {
		  System.out.println("user found");
		  foundUser = (User) ((UserRequest)data).get_request_args().get(0);
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
  }
  
  @Override
	public void start(Stage primaryStage) throws IOException {
	  MyThread thread=new MyThread();
	  Parameters params = getParameters();
	  List<String> args = params.getRaw();
	  
	  //connect to server
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
	  
	  chat.client.handleMessageFromClientUI("#getStores");
	  // wait for response
	  chat.client.flagServerAns = false;
	  do {
		  try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  System.out.println("asdf");
	  }
	  while(!chat.client.flagServerAns);

	  //get data for catalog
	  chat.client.handleMessageFromClientUI("#getCatalog 0");
	  // wait for response
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


	  // get critical initializtaion data 
	  


	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/Main.fxml"));     
	  Parent root = (Parent)fxmlLoader.load(); 
				
	  
					  
	  MainController controller = fxmlLoader.<MainController>getController();
	  controller.setCatalog(chat.catalog.getList());
	  controller.setUpdateCatalog(chat.catalog.getList());
	  chat.flagCatalog=false;
	  
	  thread.start();
	  
	  controller.setClient(chat);
	  Scene scene = new Scene(root); 		
	  primaryStage.setScene(scene);    	
	  primaryStage.show(); 
	
		
	
	}


}
//End of ConsoleChat class
