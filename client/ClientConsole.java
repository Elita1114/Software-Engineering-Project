package client;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
  public OrdersList orders;
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
	  else if (data instanceof OrdersList) {
		  System.out.println("got orders data");
		  orders = (OrdersList) data;
		  System.out.println("item list contains: "+ orders.getItemList());
	  }
	  else if (data instanceof UserRequest && data.toString().equals("#findUser")) {
		  System.out.println("user found");
		  foundUser = (User) ((UserRequest)data).get_request_args().get(0);
	  }
  }
  
  //Class methods ***************************************************
  
  public void send_mail(String to,  String subject,  String content)
  {
      // Mention the Sender's email address
      String from = "lilachJava@gmail.com";
      // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
      String host = "smtp.gmail.com";
      // Get system properties
      Properties properties = System.getProperties();
      // Setup mail server
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");
      // Get the Session object.// and pass username and password
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication("lilachJava@gmail.com", "javaFun2");
          }
      });
      // Used to debug SMTP issues
      session.setDebug(true);
      try {
          // Create a default MimeMessage object.
          MimeMessage message = new MimeMessage(session);
          // Set From: header field of the header.
          message.setFrom(new InternetAddress(from));
          // Set To: header field of the header.
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
          // Set Subject: header field
          message.setSubject(subject);
          // Now set the actual message
          message.setText(content);
          System.out.println("sending...");
          // Send message
          Transport.send(message);
          System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
          mex.printStackTrace();
      }
  }
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

	@Override
	public void stop() {
		ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(MainController.getClient().client.getLoggedUser());
    	UserRequest user_request = new UserRequest("#logout",  args);
    	MainController.getClient().client.handleMessageFromClientUI(user_request);
	}
  
}
//End of ConsoleChat class
