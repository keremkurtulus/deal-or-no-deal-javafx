package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class MainController {		
	
	private BoxGenerator boxGenerator = new BoxGenerator();
	private MoneyGenerator moneyGenerator = new MoneyGenerator();
	
	@FXML
    private VBox boxesVBox;

    @FXML
    private VBox vBoxLeft;
    
    @FXML
    private VBox vBoxRight;
    
    @FXML
    private Label headerLabel;
    
    
    @FXML
    void initialize() {
    	boxGenerator.setNotificationLabel(headerLabel);
    	// Set open box handler
    	boxGenerator.setOnOpenBox(() -> {
    		moneyGenerator.dropMoneyRandomly();
    	});
    	// Set on restart handler
    	moneyGenerator.setOnRestartGame(() -> {
    		boxGenerator.init();
    		moneyGenerator.init();
    		afterInit();
    	});
    	
    	// Initialize application state
        afterInit();
    }
    
    void afterInit() {
    	createLeftPart();
    	createRightPart();
    	createBoxesPart();
    	
    	headerLabel.setText("Please choose your case");
    }

    @FXML
    void onClickShowPastOffers(ActionEvent event) {
    	String pastOfferString = "";
    	for (int i = 0; i < moneyGenerator.pastOffersAmounts.size(); i++) {
			int pastOffer = moneyGenerator.pastOffersAmounts.get(i);
			pastOfferString += (i+1) + ". offer: " + pastOffer + " TL\n";
		}
    	Alert alert = new Alert(AlertType.INFORMATION, pastOfferString, ButtonType.OK);
    	alert.setTitle("Past Offers");
    	alert.setHeaderText("Your past offers");
		alert.showAndWait();
    }
    
    @FXML
    void onClickExitGame(ActionEvent event) {
    	Alert alert = new Alert(AlertType.WARNING, "Do you want to close the game?", ButtonType.YES, ButtonType.CANCEL);
    	alert.setHeaderText("");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
    }

    @FXML
    void onClickNewGame(ActionEvent event) {
    	Alert alert = new Alert(AlertType.WARNING, "Do you want to restart the game?", ButtonType.YES, ButtonType.CANCEL);
    	alert.setHeaderText("");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			boxGenerator.init();
			moneyGenerator.init();
			afterInit();
		}
    }

    @FXML
    void onClickShowGameInfo(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Developed by Kerem Kurtuluş\nStudent ID: 160308028", ButtonType.OK);
    	alert.setTitle("Game Info");
    	alert.setHeaderText("");
		alert.showAndWait();
    }

    /**
     * User's boxes are listed in this block
     */
    private void createBoxesPart() {
    	boxesVBox.getChildren().clear();
    	boxesVBox.getChildren().add(boxGenerator.createRow(0, 4));
    	boxesVBox.getChildren().add(boxGenerator.createRow(4, 8));
    	boxesVBox.getChildren().add(boxGenerator.createRow(8, 13));
    	boxesVBox.getChildren().add(boxGenerator.createRow(13, 18));
    	boxesVBox.getChildren().add(boxGenerator.createRow(18, 22));
    	boxesVBox.getChildren().add(boxGenerator.createRow(22, 26));		
	}

    /**
     * Left block which has money list
     */
	void createLeftPart() {
    	vBoxLeft.getChildren().clear();
    	vBoxLeft.getChildren().addAll(moneyGenerator.moneySubList(0, 13));
    	vBoxLeft.setSpacing(10);
    }
    
	/**
     * Right block which has money list
     */
    void createRightPart() {
    	vBoxRight.getChildren().clear();
    	vBoxRight.getChildren().addAll(moneyGenerator.moneySubList(13, 26));
    	vBoxRight.setSpacing(10);
    }
}
