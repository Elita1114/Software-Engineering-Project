package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import common.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.TimerTask;

import javax.net.ssl.SSLException;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import common.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  

/**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT =5555;
  
  private Connection con = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  // DB details
  final private static String DB = "RjSNNgTF3H";
  final private static String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB + "?useSSL=false";
  final private static String USER = "RjSNNgTF3H";
  final private static String PASS = "F4l71Ldtkw";
  /**
   * The interface type variable. It allows the implementation of 
   * the display method in the client.
   */
  ChatIF serverUI;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

   /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * @param serverUI The interface type variable.
   */
  public EchoServer(int port, ChatIF serverUI) throws IOException
  {
    super(port);
    this.serverUI = serverUI;
  }

  
  public void schedule_periodic_tasks()
  {
	  System.out.println("scheduling periodic task");
	  MonthlyTimer t_1 = MonthlyTimer.schedule( new GenerateReports(this), 1, 10);
	  MonthlyTimer t_2 = MonthlyTimer.schedule( new BillClients(this), 1, 10);
	  
  }
  
  private class GenerateReports extends TimerTask {
	  
	  EchoServer server;
	  public GenerateReports(EchoServer server_) {
		  server = server_;
	  }
	  @Override
	  public void run() {
		  System.out.println("requesting to create report");
		  server.handleMessageFromServerUI("#createReport");
	  }
  }
  
  private class BillClients extends TimerTask {
	  
	  EchoServer server;
	  public BillClients(EchoServer server_) {
		  server = server_;
	  }
	  @Override
	  public void run() {
		  System.out.println("billing clients");
		  // server.handleMessageFromServerUI("#billclients");
	  }
  }
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient (Object request, ConnectionToClient client)
  {
	  if(request instanceof UserRequest) {
		  UserRequest user_request = (UserRequest) request;
		  
			  if(user_request.get_request_str().equalsIgnoreCase("#signup"))
			  {
				  try { 
					  User new_user = (User) user_request.get_request_args().get(0);
					  System.out.println("Adding user: ");
					  System.out.println(new_user);
					  
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
					  PreparedStatement checkusername = con.prepareStatement("SELECT * FROM `Users` WHERE `Username`=?");
				      checkusername.setString(1, new_user.username);
				      ResultSet rs = checkusername.executeQuery();
				      int cuser = rs.last() ? rs.getRow() : 0;
				      if(cuser == 0){
					      PreparedStatement insertuser = con.prepareStatement("INSERT INTO `Users`(`Username`, `Password`, `paymentdetails`, `store`, `phoneNumber`, `pay_method`, `ID`) VALUES (?,?,?,?,?,?,?)");
					      insertuser.setString(1, new_user.username); // User name
					      insertuser.setString(2, generate_md5_hash(new_user.password));  // Password
					      insertuser.setString(3, "new_user.paymentdetails"); // payment details
					      insertuser.setInt(4, 0); // store
					      insertuser.setString(5, "new_user.phonenumber"); 	// phoneNumber
					      insertuser.setInt(6, 0); // subscription
					      insertuser.setString(7, new_user.id);  // ID
					      insertuser.executeUpdate();
				      }
				  	} catch(Exception e) {
				  		System.out.println(e);
				  	}
			  }
			  else if(user_request.get_request_str().equalsIgnoreCase("#login"))
			  {
				  System.out.println("login");
				  try { 
					  User new_user = (User) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
					  PreparedStatement checkusername = con.prepareStatement("SELECT * FROM `Users` WHERE `Username`=? AND `Password`=?");
					  checkusername.setString(1, new_user.username);
					  checkusername.setString(2, generate_md5_hash(new_user.password));
				      ResultSet rs = checkusername.executeQuery();
				      int cuser = rs.last() ? rs.getRow() : 0;
				      System.out.println(cuser);
				      if(cuser == 1){
				    	  User loggedUser = new User(rs.getInt("uid"), rs.getString("Username"), rs.getString("Password"), rs.getString("ID"), rs.getString("paymentdetails"), rs.getInt("pay_method"), rs.getString("phonenumber"), rs.getInt("store"),Status.values()[(rs.getInt("status"))]);
				    	  client.sendToClient(loggedUser); 
				      }
				      else {
				    	  client.sendToClient("#wrongdetails");
				    	  return;
				      }
				  }catch(Exception e) {
					  System.out.println(e);
				  }
			  }else if(user_request.get_request_str().equalsIgnoreCase("#order"))
			  {
				  try { 
					  User user = (User) user_request.get_request_args().get(0);
					  Order order = (Order) user_request.get_request_args().get(1);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      ArrayList<Item> order_items = order.get_order_items();
				      float price = 0;
				      for(Item item: order_items)
				      {
				    	  if(item instanceof CatalogItem)
				    	  {
				    		  CatalogItem catalogitem = (CatalogItem) item;
				    		  price += catalogitem.getPrice();
				    	  }
				      }
				      
				      PreparedStatement insertorder = con.prepareStatement("INSERT INTO `Orders`(`orderID`,`userID`, `address`, `wantshipping`, `timeToTransport`, `letter`, `deliveryTime`, `reciever`, `recieverPhone`, `price`,`store`) VALUES (NULL,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				      insertorder.setInt(1, user.user_id); // User id
				      insertorder.setString(2, order.get_shipping_address());  // reciever
				      insertorder.setInt(3, order.want_shipping()?1:0);  // reciever
				      insertorder.setDate(4, order.get_requested_delivery_date()); // delivery date
				      insertorder.setString(5, order.get_letter()); // letter
				      insertorder.setDate(6, order.get_requested_delivery_date()); 	// phoneNumber
				      insertorder.setString(7, order.get_shipping_reciever()); // reciver
				      insertorder.setString(8, order.get_recievre_phone_number());  // ID
				      insertorder.setFloat(9, price);  // ID
				      insertorder.setInt(10, user.store);  // store
				      insertorder.executeUpdate();
				      ResultSet insertedorder = insertorder.getGeneratedKeys();
				      insertedorder.next();
				      PreparedStatement updateCart = con.prepareStatement("UPDATE `Cart` SET `orderID`=? WHERE `userID`=? AND `orderID`=NULL");
				      updateCart.setInt(1, user.user_id);
				      updateCart.setInt(2, insertedorder.getInt(1));
				      updateCart.executeUpdate();
			    	  client.sendToClient(order); 	
				  }catch(Exception e) {
					  System.out.println("a");
					  System.out.println(e);
				  }
			  }
			  
			  else if(user_request.get_request_str().equalsIgnoreCase("#addtocart"))
			  {
				  try { 
					  User user = (User) user_request.get_request_args().get(0);
					  CartItem citem = (CartItem) user_request.get_request_args().get(1);
					  con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
					  PreparedStatement addtocart = con.prepareStatement("INSERT INTO `Cart`(`userID`, `productID`, `quantity`, `name`, `price`, `description`) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				      
					  if(citem.getItem() instanceof CatalogItem)
					  {
						  addtocart.setInt(1, user.user_id); // User id
						  addtocart.setInt(2, ((CatalogItem)citem.getItem()).getId());  // id
						  addtocart.setInt(3, citem.getQty());  // qty
						  addtocart.setString(4, ((CatalogItem)citem.getItem()).getName());  // Name
						  addtocart.setFloat(5, ((CatalogItem)citem.getItem()).getPrice());  // Price
						  addtocart.setString(6, ((CatalogItem)citem.getItem()).getDescription());  // Description
						  addtocart.executeUpdate();
						  client.sendToClient("#addedtocart");
					  }
					  
				  }catch(Exception e) {
					  System.out.println("a");
					  System.out.println(e);
				  }
			  }
			  
			  else if(user_request.get_request_str().equalsIgnoreCase("#getcart"))
			  {
				  try { 
					  System.out.println("entered here");
					  User user = (User) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      Statement stmt=con.createStatement();  
				      System.out.println("The id is " + user.user_id);
				      PreparedStatement getcart = con.prepareStatement("select * from Cart WHERE `userID`=? AND `orderID` is NULL");
				      getcart.setInt(1, user.user_id);
				      ResultSet rs = getcart.executeQuery();
				      ArrayList<Item> itemList = new ArrayList<Item>();
				      
				      
				      while(rs.next()) { 
				    	  itemList.add(new Item(rs.getInt("productID"), rs.getString("name"), rs.getString("description")));
				      }
				      
				      con.close();  
				      Cart cart= new Cart(itemList);
				      System.out.println(cart);
				      client.sendToClient(cart);  
				  }catch(Exception e) {
					  System.out.println("a");
					  System.out.println(e);
				  }
			  }
			  else if(user_request.get_request_str().equalsIgnoreCase("#getReports"))
			  {
				  try { 
					  User user = (User) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      PreparedStatement getreports = con.prepareStatement("select * from `reports` WHERE `store`=?");
				      getreports.setInt(1, user.store);
				      ResultSet rs = getreports.executeQuery();
				      ArrayList<MonthlyReport> itemList = new ArrayList<MonthlyReport>();
				      while(rs.next()) { 
				    	  itemList.add(new MonthlyReport(rs.getInt("store"), rs.getString("date"), rs.getFloat("Revenue"), rs.getString("text"), rs.getInt("handledcomplaints"), rs.getInt("unhandledcomplaints")));
				      }
				      con.close();  
				      MonthlyReportList reportlist= new MonthlyReportList(itemList);
				      System.out.println(reportlist);
				      client.sendToClient(reportlist);  
				  }catch(Exception e) {
					  System.out.println("a");
					  System.out.println(e);
				  }
			  }
			  else if(user_request.get_request_str().equalsIgnoreCase("#updateComplaint")) {
				  try { 
					  int complaintID  = (Integer) user_request.get_request_args().get(0);
					  String reply  = (String) user_request.get_request_args().get(1);
					  double refund  = (Double) user_request.get_request_args().get(2);
					  
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      
				      PreparedStatement updateComplaint = con.prepareStatement("UPDATE `Complaint` SET `reply`=?, `refund` = ?, `status` = ? WHERE `complaint_num`=?");
				      updateComplaint.setString(1, reply);
				      updateComplaint.setDouble(2, refund);
				      updateComplaint.setBoolean(3, true);
				      updateComplaint.setInt(4, complaintID);
				      updateComplaint.executeUpdate();
				      
				      
					  client.sendToClient(new ReturnStatus("#updateComplaint", true));
					  con.close();
				  }catch(Exception e) {
					  System.out.println(e);
				  }
			  }
			  else if(user_request.get_request_str().equalsIgnoreCase("#addComplaint")) {
				  System.out.println("complaint");
				  try { 
					  Complaint complaint  = (Complaint) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      PreparedStatement addComplaint = con.prepareStatement("INSERT INTO `Complaint`(`description`, `Status`, `refund`, `user`, `title`,`reply`,`timer`,`store`) VALUES (?,?,?,?,?,?,?,?)");
				      addComplaint.setString(1, complaint.description);
				      addComplaint.setBoolean(2, complaint.status);
				      addComplaint.setDouble(3, complaint.refund);
				      addComplaint.setInt(4, complaint.userID);
				      addComplaint.setString(5, complaint.title);
				      addComplaint.setString(6, complaint.reply);
				      addComplaint.setInt(7, complaint.timer);
				      addComplaint.setInt(8, complaint.store);
				      addComplaint.executeUpdate();
					  client.sendToClient(new ReturnStatus("#addComplaint", true));
					  con.close();
					  
				  }catch(Exception e) {
					  System.out.println(e);
				  }
			  }else if(user_request.get_request_str().equalsIgnoreCase("#dropCatalog")) {
				  System.out.println("drop");
				  try { 
					  int idItem  = (int) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      PreparedStatement addComplaint = con.prepareStatement("UPDATE `Products` SET `inCatalog` = '0' WHERE `Products`.`id` = ?");
				      addComplaint.setInt(1, idItem);
				      addComplaint.executeUpdate();
					  client.sendToClient("#dropCatalog");
					  
				  }catch(Exception e) {
					  System.out.println(e);
				  }
			  }
			  else if(user_request.get_request_str().equalsIgnoreCase("#addCatalogItem")) {
				  System.out.println("add catalog item");
				  try { 
				      CatalogItem addedItem  = (CatalogItem) user_request.get_request_args().get(0);
				      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
				      PreparedStatement addCayalogItemSql = con.prepareStatement("INSERT INTO `Products`(`name`, `description`, `type`, `price`, `Color`, `Image Path`, `store`, `sale`) VALUES (?,?,?,?,?,?,?,?)");
				      addCayalogItemSql.setString(1, addedItem.getName());
				      addCayalogItemSql.setString(2, addedItem.getDescription());
				      addCayalogItemSql.setInt(3, addedItem.getType());
				      addCayalogItemSql.setDouble(4, addedItem.getPrice());
				      addCayalogItemSql.setString(5, addedItem.getColor());
				      addCayalogItemSql.setString(6, addedItem.getImagePath());
				      addCayalogItemSql.setInt(7, addedItem.getStore());
				      addCayalogItemSql.setDouble(8, addedItem.getSale());
				      
				      addCayalogItemSql.executeUpdate();
					  client.sendToClient("#addCatalogItem");
					  
				  }catch(Exception e) {
					  System.out.println(e);
				  }
			  }
		  
		  return;
			  }

	  
	  
	  if(request.toString().equalsIgnoreCase("#productslist"))
	  {
		  try {
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      ResultSet rs=stmt.executeQuery("select * from Products");  
		      client.sendToClient("ID\tNAME\tPrice");
		      while(rs.next())  
		    	  client.sendToClient(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getFloat(5));  
		      con.close();  
		  }catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
	  }
	  if(request.toString().startsWith("#changeprice "))
	  {
		  try {
			  if(request.toString().split(" ").length != 3)
			  {
				  System.out.println("usage: #changeprice [id] [price]");
				  return;
			  }
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      int id = Integer.parseInt(request.toString().split(" ")[1]);
		      float price = Float.parseFloat(request.toString().split(" ")[2]);
		      stmt.executeUpdate("UPDATE `Products` SET price='" + price + "' WHERE `id`='" + id + "'");  
		      con.close();
		      client.sendToClient("UPDATED Successfully");
		  }catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
	  }
    if (request.toString().startsWith("#login "))
    {
      if (client.getInfo("loginID") != null)
      {
        try
        {
          client.sendToClient("You are already logged in.");
        }
        catch (IOException e)
        {
        }
        return;
      }
      client.setInfo("loginID", request.toString().substring(7));
    }
    else if (request.toString().startsWith("#getCatalog"))
    {
    	System.out.println("1");
    	try {
    		  int type= Integer.parseInt(request.toString().split(" ")[1]);
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      ResultSet rs = null;
		      if(type==0) {
		    	  rs=stmt.executeQuery("select * from Products"); 
		      }
		      else {
		    	  PreparedStatement prep_stmt = con.prepareStatement("select * from Products WHERE type = ?");
		    	  prep_stmt.setInt(1, type);
		    	  rs = prep_stmt.executeQuery();
		      }
		      System.out.println("2");
		      ArrayList<CatalogItem> itemList = new ArrayList<CatalogItem>();
		      
		      System.out.println("3");
		      client.sendToClient("getting catalog");
		      while(rs.next()) {
		    	  itemList.add(new CatalogItem(rs.getString("name"),rs.getString("description"),rs.getString("Color"),rs.getFloat("price"),rs.getInt("id"),rs.getString("Image Path"),rs.getInt("type") , rs.getInt("store") , rs.getInt("sale")));
		    	  System.out.println("2");
		    	  System.out.println("getting item");
		      }
		      System.out.println("2");
		      con.close();  
		      Catalog catalog=new Catalog(itemList,true);
		      System.out.println("3");
		      client.sendToClient(catalog);  
		      System.out.println("after send catalog\n");
		      client.sendToClient("after send catalog\n");  
		      
		      
		  } catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
    }
    else if(request.toString().equalsIgnoreCase("#getComplaints")) {
    	try {
  		  
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      ResultSet rs = null;
		      rs=stmt.executeQuery("select * from Complaint"); 
		      
		      ArrayList<Complaint> itemList = new ArrayList<Complaint>();

		      while(rs.next()) { 
		    	  itemList.add(new Complaint(rs.getString(6),rs.getString(1),rs.getBoolean(2),rs.getDouble(3),rs.getInt(5),rs.getString(7),rs.getInt(4),rs.getInt(9),rs.getInt(8)));
		    	  System.out.println("getting item");
		      }
		      con.close();  
		      ComplaintsList complaints=new ComplaintsList(itemList);
		      client.sendToClient(complaints);  
		      
		      
		  } catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
    }
    else if(request.toString().equalsIgnoreCase("#isUrgent")) {
    	try {
    		  
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      ResultSet rs = null;
		      rs=stmt.executeQuery("select * from Complaint WHERE timer < 61 AND status = false"); 
		      
		      int cuser = rs.last() ? rs.getRow() : 0;
		      if(cuser==0)
		    	  client.sendToClient(new ReturnStatus("#isUrgent", false));
		      else
		    	  client.sendToClient(new ReturnStatus("#isUrgent", true));
		      con.close();  

		      
		      
		  } catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
    }
    else if(request.toString().equalsIgnoreCase("#getStores")) {
    	try {
  		  
		      con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
		      Statement stmt=con.createStatement();  
		      ResultSet rs = null;
		      rs=stmt.executeQuery("select * from Store"); 
		      ArrayList<Store> storesList = new ArrayList<Store>();
		      while(rs.next()) { 
		    	  storesList.add(new Store(rs.getInt("store_num"), rs.getString("name")));
		      }
		      con.close();  
		      StoresList stores=new StoresList(storesList);
		      client.sendToClient(stores);   
		  } catch(Exception e) {
			  System.out.println("a");
			  System.out.println(e);
		  }
		  return;
    }
    else 
    {
      if (client.getInfo("loginID") == null)
      {
        try
        {
          client.sendToClient("You need to login before you can chat.");
          client.close();
        }
        catch (IOException e) {}
        return;
      }
      System.out.println("Message received: " + request + " from \"" + 
        client.getInfo("loginID") + "\" " + client);
      this.sendToAllClients(client.getInfo("loginID") + "> " + request);
    }
  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI
   */
  public void handleMessageFromServerUI(String message)
  {
	System.out.println("entered2");
    if (message.charAt(0) == '#')
    {
      runCommand(message);
    }
    else
    {
      // send message to clients
      serverUI.display(message);
      this.sendToAllClients("SERVER MSG> " + message);
    }
  }

  /**
   * This method executes server commands.
   *
   * @param message String from the server console.
   */
  private void runCommand(String message)
  {
    // run commands
    // a series of if statements

    if (message.equalsIgnoreCase("#quit"))
    {
      quit();
    }
    else if (message.equalsIgnoreCase("#stop"))
    {
      stopListening();
    }
    else if (message.equalsIgnoreCase("#close"))
    {
      try
      {
        close();
      }
      catch(IOException e) {}
    }
    else if (message.toLowerCase().startsWith("#setport"))
    {
      if (getNumberOfClients() == 0 && !isListening())
      {
        // If there are no connected clients and we are not 
        // listening for new ones, assume server closed.
        // A more exact way to determine this was not obvious and
        // time was limited.
        int newPort = Integer.parseInt(message.substring(9));
        setPort(newPort);
        //error checking should be added
        serverUI.display
          ("Server port changed to " + getPort());
      }
      else
      {
        serverUI.display
          ("The server is not closed. Port cannot be changed.");
      }
    }
    else if (message.equalsIgnoreCase("#start"))
    {
      if (!isListening())
      {
        try
        {
          listen();
        }
        catch(Exception ex)
        {
          serverUI.display("Error - Could not listen for clients!");
        }
      }
      else
      {
        serverUI.display
          ("The server is already listening for clients.");
      }
    }
    else if (message.equalsIgnoreCase("#getport"))
    {
      serverUI.display("Currently port: " + Integer.toString(getPort()));
    }
    else if(message.equalsIgnoreCase("#updateTimer")) {
    	try {
    		  
		    con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);

		    String sql = "update Complaint set timer = timer - ? where status = ? AND timer > ?";
			PreparedStatement prep_stmt = con.prepareStatement(sql);
			prep_stmt.setInt(1, 10);
			prep_stmt.setBoolean(2, false);
			prep_stmt.setInt(3, 0);
			prep_stmt.executeUpdate();
		      
		    con.close();  
 
		  } catch(Exception e) {
			  System.out.println("b");
			  System.out.println(e);
		  }
		  return;
    }
    else if(message.equalsIgnoreCase("#createReport")) {
    	try {
    		  
		    con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/" + DB + "?useSSL=false", USER, PASS);
			Statement stmt = con.createStatement();
			PreparedStatement prep_stmt = null;
			ResultSet stores = stmt.executeQuery("SELECT `store_num` FROM `Store`");
			while(stores.next())
			{
				int storeid = stores.getInt("store_num");
				prep_stmt = con.prepareStatement("SELECT `productID`, SUM(`quantity`) AS quantity FROM Cart WHERE `orderID` IN (SELECT `orderID` FROM `Orders` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=?) GROUP BY `productID`");
				prep_stmt.setInt(1, storeid);
				ResultSet cartqtys = prep_stmt.executeQuery();
				String reportText = "Number of items bought from the catalog: \n";
				while(cartqtys.next())
				{
					prep_stmt = con.prepareStatement("SELECT `name` FROM `Products` WHERE `id`=?");
					prep_stmt.setInt(1, cartqtys.getInt("productID"));
					ResultSet nameofitem = prep_stmt.executeQuery();
					nameofitem.next();
					reportText += nameofitem.getString("name") + ": " + cartqtys.getInt("quantity") + "\n";		
				}
				reportText += "Number of custom items bought: \n";
				prep_stmt = con.prepareStatement("SELECT `type`,count(*) as Qty FROM `CustomItem` WHERE `orderID` IN (SELECT `orderID` FROM `Orders` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=?) GROUP BY `type`");
				prep_stmt.setInt(1, storeid);
				ResultSet customeitems = prep_stmt.executeQuery(); 
				while(customeitems.next())
				{
					reportText += customeitems.getInt("type") + ": " + customeitems.getInt("Qty") + "\n";	
				}
				reportText += "Which contains the following arguments: \n";
				prep_stmt = con.prepareStatement("SELECT SUM(`Daisy`) AS DaisyQty, SUM(`Orchid`) AS OrchidQty, SUM(`Iris`) AS IrisQty, SUM(`Rose`) AS RoseQty, SUM(`Lily`) AS LilyQty, SUM(`hydrangea`) AS hydrangeaQty FROM `CustomItem` WHERE `orderID` IN (SELECT `orderID` FROM `Orders` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=?)");
				prep_stmt.setInt(1, storeid);
				ResultSet customeitemsargs = prep_stmt.executeQuery(); 
				customeitemsargs.next();
				reportText += "Daisy: " + customeitemsargs.getInt("DaisyQty") + "\n";
				reportText += "Orchid: " + customeitemsargs.getInt("OrchidQty") + "\n";
				reportText += "Iris: " + customeitemsargs.getInt("IrisQty") + "\n";
				reportText += "Rose: " + customeitemsargs.getInt("RoseQty") + "\n";
				reportText += "Lily: " + customeitemsargs.getInt("LilyQty") + "\n";
				reportText += "hydrangea: " + customeitemsargs.getInt("hydrangeaQty") + "\n";
				reportText += "Total Money income: ";
				prep_stmt = con.prepareStatement("SELECT `customprice`+`cartprice` as TotalinCome FROM ((SELECT IF(SUM(`price`) is NULL,0, SUM(`price`)) as `cartprice` FROM `Cart` WHERE `orderID` IN (SELECT `orderID` FROM `Orders` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=?)) as cart JOIN (SELECT IF(SUM(`price`) is NULL,0, SUM(`price`)) as `customprice` FROM `CustomItem` WHERE `orderID` IN (SELECT `orderID` FROM `Orders` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=?)) as custom)");
				prep_stmt.setInt(1, storeid);
				prep_stmt.setInt(2, storeid);
				ResultSet totalincome = prep_stmt.executeQuery(); 
				totalincome.next();
				float totalincomef = totalincome.getFloat("TotalinCome");
				reportText += Float.toString(totalincomef) + "\n";
				reportText += "Complaints statistic: \n";
				prep_stmt = con.prepareStatement("SELECT `Status`,count(*) AS stat FROM `Complaint` WHERE `date`>(CURRENT_DATE() - INTERVAL 1 MONTH) AND `store`=? GROUP by `Status`");
				prep_stmt.setInt(1, storeid);
				ResultSet complaints = prep_stmt.executeQuery(); 
				int handledcomplaints=0, unhandledcomplaints=0;
				while(complaints.next())
				{
					if(complaints.getInt("Status") == 1)
					{
						handledcomplaints = complaints.getInt("stat");
						reportText += "handled" + ": " + Integer.toString(handledcomplaints) + "\n";	
					}
					else
					{
						unhandledcomplaints = complaints.getInt("stat");
						reportText += "unhandled" + ": " + Integer.toString(unhandledcomplaints) + "\n";	
					}
						
				}
				if(handledcomplaints == 0)
					reportText += "handled" + ": 0\n";
				if(unhandledcomplaints == 0)
					reportText += "unhandled" + ": 0\n";
				PreparedStatement insertuser = con.prepareStatement("INSERT INTO `reports`(`text`, `store`, `handledcomplaints`, `unhandledcomplaints`, `Revenue`) VALUES (?,?,?,?,?)");
				insertuser.setString(1, reportText); // reportText
				insertuser.setInt(2, storeid);  // storeid
				insertuser.setInt(3, handledcomplaints); // handledcomplaints
				insertuser.setInt(4, unhandledcomplaints); // unhandledcomplaints
				insertuser.setFloat(5, totalincomef); // totalincome
				insertuser.executeUpdate();
				System.out.println(reportText);
			}
		    con.close();  
 
		  } catch(Exception e) {
			  System.out.println("b");
			  System.out.println(e);
		  }
		  return;
    }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  /**
   * Run when new clients are connected. Implemented by Benjamin Bergman,
   * Oct 22, 2009.
   *
   * @param client the connection connected to the client
   */
  protected void clientConnected(ConnectionToClient client) 
  {
    // display on server and clients that the client has connected.
    String msg = "A Client has connected";
    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * Run when clients disconnect. Implemented by Benjamin Bergman,
   * Oct 22, 2009
   *
   * @param client the connection with the client
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client)
  {
    // display on server and clients when a user disconnects
    String msg = client.getInfo("loginID").toString() + " has disconnected";

    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * Run when a client suddenly disconnects. Implemented by Benjamin
   * Bergman, Oct 22, 2009
   *
   * @param client the client that raised the exception
   * @param Throwable the exception thrown
   */
  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception) 
  {
    String msg = client.getInfo("loginID").toString() + " has disconnected";

    System.out.println(msg);
    this.sendToAllClients(msg);
  }

  /**
   * This method terminates the server.
   */
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e)
    {
    }
    System.exit(0);
  }


  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
	System.out.println("asdf");
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
  
  public static String generate_md5_hash(String input)
	{
		try { 	  
          // Static getInstance method is called with hashing MD5 
          MessageDigest md = MessageDigest.getInstance("MD5"); 

          // digest() method is called to calculate message digest 
          //  of an input digest() return array of byte 
          byte[] messageDigest = md.digest(input.getBytes()); 

          // Convert byte array into signum representation 
          BigInteger no = new BigInteger(1, messageDigest); 

          // Convert message digest into hex value 
          String hashtext = no.toString(16); 
          while (hashtext.length() < 32) { 
              hashtext = "0" + hashtext; 
          } 
          return hashtext; 
      }  
      // For specifying wrong message digest algorithms 
      catch (NoSuchAlgorithmException e) { 
          throw new RuntimeException(e); 
      } 
		  
	}
}
//End of EchoServer class
