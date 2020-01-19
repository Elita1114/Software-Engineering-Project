package client.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SelfMadeItemController {

    @FXML
    private Button btnFinished;
    @FXML
    private Button btnOrchidMinus;
    @FXML
    private TextField tvDaisy;
    @FXML
    private RadioButton radioGlass;
    @FXML
    private RadioButton radioPaper;
    @FXML
    private Button btnRoseMinus;
    @FXML
    private Button btnDaisyMinus;
    @FXML
    private Button btnIrisPlus;
    @FXML
    private RadioButton radioPlastic;
    @FXML
    private Button btnOrchidPlus;
    @FXML
    private Button btnDaisyPlus;
    @FXML
    private Button btnLilyPlus;
    @FXML
    private ToggleGroup group;
    @FXML
    private Button btnHydrangeaMinus;
    @FXML
    private TextField tvIris;
    @FXML
    private TextField tvRose;
    @FXML
    private Button btnRosePlus;
    @FXML
    private Button btnHydrangeaPlus;
    @FXML
    private Text tvPrice;
    @FXML
    private Button btnLilyMinus;
    @FXML
    private TextField tvOrchid;
    @FXML
    private Button btnCancel;
    @FXML
    private RadioButton radioVase;
    @FXML
    private Button btnIrisMinus;
    @FXML
    private TextField tvLily;
    @FXML
    private TextField tvHydrangea;
    
    
    
    @FXML
    private ImageView ivOrchid;
    @FXML
    private ImageView ivRose;
    @FXML
    private ImageView ivLily;
    @FXML
    private ImageView ivHydrangea;
    @FXML
    private ImageView ivIris;
    @FXML
    private ImageView ivDaisy;
    
    
    
    private float priceFlowers;
    private float priceContainer;
    
    public SelfMadeItemController(){
    	priceFlowers=0;
    	priceContainer=15;
    	/*
    	ivOrchid.setImage(new Image("images/orchid.jpg"));
    	System.out.println("after orchid");
    	ivRose.setImage(new Image("images/rose.jpg"));
    	ivLily.setImage(new Image("images/lily.jpg"));
    	ivHydrangea.setImage(new Image("images/hydrangea.jpg"));
    	ivIris.setImage(new Image("images/iris.jpg"));
    	ivDaisy.setImage(new Image("images/daisy.jpg"));
    	*/
    }
    
    
    
    //handles plus/minus press
    private void updateCount(TextField text, boolean add,float itemPrice) {
    	if(add) {
    		text.setText(String.valueOf(Integer.parseInt(text.getText().toString())+1));
    		priceFlowers+=itemPrice;
    	}
    	else if(Integer.parseInt(text.getText().toString())>0) {
    		text.setText(String.valueOf(Integer.parseInt(text.getText().toString())-1));
    		priceFlowers-=itemPrice;
    	}
    	
    	tvPrice.setText("Total Price: "+String.valueOf(priceFlowers+priceContainer)+"$");
    	
    }
    private void upateRadio(float containerPrice) {
    	priceContainer=containerPrice;
    	tvPrice.setText("Total Price: "+String.valueOf(priceFlowers+priceContainer)+"$");;
    }
    
    
    @FXML
    void finishedPressed(ActionEvent event) {

    }

    @FXML
    void daisyMinusPressed(ActionEvent event) {
    	updateCount(tvDaisy, false,5f);
    }

    @FXML
    void daisyPlusPressed(ActionEvent event) {
    	updateCount(tvDaisy, true,5f);
    }

    @FXML
    void irisPlusPressed(ActionEvent event) {
    	updateCount(tvIris, true,6f);
    }

    @FXML
    void irisMinusPressed(ActionEvent event) {
    	updateCount(tvIris, false,6f);
    }

    @FXML
    void lilyPlusPressed(ActionEvent event) {
    	updateCount(tvLily, true,8f);
    }

    @FXML
    void lilyMinusPressed(ActionEvent event) {
    	updateCount(tvLily, false,8f);
    }

    @FXML
    void orchidPlusPressed(ActionEvent event) {
    	updateCount(tvOrchid, true,50f);
    }

    @FXML
    void orchidMinusPressed(ActionEvent event) {
    	updateCount(tvOrchid, false,50f);
    }

    @FXML
    void rosePlusPressed(ActionEvent event) {
    	updateCount(tvRose, true,5f);
    }

    @FXML
    void roseMinusPressed(ActionEvent event) {
    	updateCount(tvRose, false,5f);
    }

    @FXML
    void hydrangeaPlusPressed(ActionEvent event) {
    	updateCount(tvHydrangea, true,6f);
    }

    @FXML
    void hydrangeaMinusPressed(ActionEvent event) {
    	updateCount(tvHydrangea, false,6f);
    }
    
    
    
    
    
    @FXML
    void glassPressed(ActionEvent event) {
    	upateRadio(35);
    }

    @FXML
    void paperPressed(ActionEvent event) {
    	upateRadio(15);
    }

    @FXML
    void plasticPressed(ActionEvent event) {
    	upateRadio(25);
    }

    @FXML
    void vasePressed(ActionEvent event) {
    	upateRadio(40);
    }
    
    
    

    @FXML
    void cancelPressed(ActionEvent event) {

    }

}
