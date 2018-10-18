package uiClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import converter.ConverterClass;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
//import sun.misc.ClassLoaderUtil;

	public class UiClass extends Application {
		File file;
		TextArea    txtField;
	@Override
	public void start(Stage primaryStage) throws IOException, URISyntaxException {
		
		Scene homeScene = createFirstScene(primaryStage);  
		file = new File("C://tessdata/upload.gif");
		
		GridPane gridPane = createProfileGrid();
		addUIControls(gridPane);
		Scene profileScene= new Scene(gridPane, 800, 600);
  
		
		primaryStage.setScene(homeScene);   
		primaryStage.show();
		
		ChangeToProiflePage(primaryStage,profileScene);
		
	}

	public static void main(String[] args) {
	launch(args);
	}
	
	public GridPane createProfileGrid() {
		  
		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);
		
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);
		
		GridPane gridPane = new GridPane();
		    	 gridPane.setAlignment(Pos.TOP_CENTER);
		    	 gridPane.setPadding(new Insets(05, 40, 40, 40));
		    	 gridPane.setHgap(10);
		    	 gridPane.setVgap(10);
		    	 gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
		    
		return gridPane;
	}
	
	
	private void addUIControls(GridPane gridPane) throws FileNotFoundException {
		 Button      convertButton 	= new Button("Convert");;
		 Button      cancelButton	= new Button("Cancel");;
		 Button		 downloadButton	= new Button("Download");;
		Label headerLabel = new Label("Welcome To OCR converter");
        	  headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        	  gridPane.add(headerLabel, 0,0,2,1);
        	  GridPane.setHalignment(headerLabel, HPos.CENTER);
        	  GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Label nameLabel = new Label("Your Selected Picture is :");
        	  nameLabel.setWrapText(true);
        	  gridPane.add(nameLabel,0,1);

       
        Image image = new Image(new FileInputStream(file));
        ImageView imageView = new ImageView(image);
        		  imageView.setX(50); 
        		  imageView.setY(25); 
        		  imageView.setFitHeight(350); 
        		  imageView.setFitWidth(350); 
        		  gridPane.add(imageView, 1,1);
        		  
        		  imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
        			    @Override
        			    public void handle(MouseEvent mouseEvent) {
        			    	try {
        			    		File localFile = loadFile();
        			    		Image localImage =new Image(new FileInputStream(localFile));
        			    		if(localImage.isError())
        			    			throw new Exception();
        			    		UiClass.this.file = localFile;
        			    		imageView.setImage(localImage);
        			    		downloadButton.setVisible(false);
    							convertButton.setVisible(true);
							} catch (Exception e) {
								showAlert(AlertType.ERROR, imageView.getScene().getWindow(),
										"Invalid Image File", "ImageFile Seems Invalid Please Selcet valid Image File");
								
							}
        			    	
        			    }
        			});

        Label     textLabel = new Label("Converted Text:");
        	      textLabel.setWrapText(true);
        	      gridPane.add(textLabel, 0, 2);

      
        	      txtField = new TextArea();
      		      txtField.setPrefRowCount(15);
      		      txtField.setPrefColumnCount(100);
      		      txtField.setWrapText(true);
      		      txtField.setPrefWidth(150);
      		      txtField.setPrefHeight(40);
      		      txtField.setDisable(true);
        		  gridPane.add(txtField, 1,2, 2,4);

     

      			  convertButton.setMinWidth(50);
      			  convertButton.setPrefWidth(100);
      			  convertButton.setMinHeight(30);
      			  gridPane.add(convertButton,  0, 6, 2, 1);
      			  convertButton.setVisible(false);
      			  GridPane.setMargin(convertButton, new Insets(20, 0,20,0));
      			
      			  downloadButton.setMinWidth(50);
      			  downloadButton.setVisible(false);
      			  downloadButton.setMinHeight(30);
      			  downloadButton.setPrefWidth(100);
     			  gridPane.add(downloadButton, 0, 6, 2, 1);
     			  //GridPane.setHalignment(downloadButton, HPos.CENTER);
     			  GridPane.setMargin(downloadButton, new Insets(20, 0,20,0));
        
      			  cancelButton.setMinWidth(50);
      			  cancelButton.setMinWidth(50);
      			  cancelButton.setMinHeight(30);
      			  cancelButton.setPrefWidth(100);
      			  gridPane.add(cancelButton, 2,6);
      			  GridPane.setMargin(convertButton, new Insets(20, 0,20,0));
      			  

        convertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	boolean load =false;
            	String text1= new ConverterClass().convertImageToText(UiClass.this.file);
            	if(text1.equals("")) {
            		showAlert(AlertType.WARNING, convertButton.getScene().getWindow(), 
            				"Image not recognized", "This Image is Not Recognized Please Select Another Image.");
            	}else {
            		txtField.setText(text1);
            		txtField.setDisable(false);
            		convertButton.setVisible(false);
            		downloadButton.setVisible(true);
            	}
            	
             System.out.println(text1);
            }
        });
        
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	File localFile = downloadFile();
            	if(localFile != null) {
            		if(new ConverterClass().writeFile(localFile, txtField.getText())) {
            			showAlert(AlertType.INFORMATION, downloadButton.getScene().getWindow(), 
            					"File Saved", "Your File saved Successfully");
            		}else {
            			showAlert(AlertType.ERROR, downloadButton.getScene().getWindow(), 
            					"Erro in Save", "There was error while saving");
            		}
            	}
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			  @Override
			  public void handle(ActionEvent event) {
				  Stage stage = (Stage) cancelButton.getScene().getWindow();
				  stage.close();
			  }
		  });

    }

    private void showAlert(Alert.AlertType alertType, javafx.stage.Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    private Scene createFirstScene(Stage primaryStage) throws FileNotFoundException, URISyntaxException {
    	primaryStage.setTitle("Welcome to OCR");  
    	Image image = new Image(new FileInputStream("C://tessdata//giphy.gif"));  
    	/*Image image = new Image(this.getClass().getClassLoader()
                .getResourceAsStream("giphy.gif")); */ 
        ImageView imageView = new ImageView(image); 
        		  imageView.setX(0); 
        		  imageView.setY(0); 
        		  imageView.setFitHeight(500); 
        		  imageView.setFitWidth(500); 
        		  imageView.setPreserveRatio(true);   
        		  
        Group root = new Group(imageView);  
        return new Scene(root, 500, 500);
    }
    
    private void ChangeToProiflePage(Stage primaryStage,Scene profileScene) {
    	PauseTransition delay = new PauseTransition(Duration.seconds(5));
    	    			delay.setOnFinished( event -> {
								primaryStage.setScene(profileScene);  
    	    			});
    	    			delay.play();
    }
    
    private File loadFile() {
    	JFileChooser  chooser = new JFileChooser();
	                  chooser.setMultiSelectionEnabled(false);
	                  int returnVal = chooser.showOpenDialog(null);
	         	      if(returnVal == JFileChooser.APPROVE_OPTION) 
	         	    	 file = chooser.getSelectedFile();
	    return file;
    }
    
    private File downloadFile() {
    	JFrame parentFrame = new JFrame();
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Specify a file to save");   
    	int userSelection = fileChooser.showSaveDialog(parentFrame);
    	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    return  fileChooser.getSelectedFile();
    	}
	    return null;
    }

    private void showToolTip(MouseEvent event,String text) {
    	   Tooltip tp = new Tooltip(text);
    	   Node node = (Node) event.getSource();
           tp.show(node, event.getScreenX() + 50, event.getScreenY());
    }
	
	
	}



