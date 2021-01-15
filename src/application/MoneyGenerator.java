package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;

public class MoneyGenerator {
	public int[] moneyArray = new int[] {
        	1, 5, 10, 15, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000
	};
	public List<Button> buttonList = new ArrayList<>();
	protected RestartGameHandler restartGameHandler;
	/**
	 * Button id prefix for all buttons
	 */
	private String buttonPrefixString = "liraBtn_";
	/**
	 * Offer money counter
	 */
	protected static int offerCounter = 0;
	/**
	 * Past offers stored
	 */
	public List<Integer> pastOffersAmounts = new ArrayList<Integer>();
	
	public MoneyGenerator() {
		init();
	}
	
	public void refreshState() {
		buttonList.clear();
		offerCounter = 0;
		pastOffersAmounts.clear();
	}
	
	public void init() {
		refreshState();
		
		for (int i = 0; i < 26; i++) {
    		Button btn = new Button(moneyArray[i] + " TL");
        	btn.setId(buttonPrefixString + moneyArray[i]);
        	btn.setAlignment(Pos.CENTER);
        	btn.setContentDisplay(ContentDisplay.CENTER);
        	btn.setFocusTraversable(false);
        	btn.setMnemonicParsing(false);
        	btn.setPrefHeight(30);
        	btn.setPrefWidth(150);
        	
        	buttonList.add(btn);
		}
	}
	
	public List<Button> moneySubList(int fromIndex, int toIndex) {
		return buttonList.subList(fromIndex, toIndex);
	}
	
	public void dropMoneyRandomly() {
		int randomIndex = getRandomButtonIndex();
		Button btn = (Button)buttonList.get(randomIndex);
		btn.setDisable(true);
		btn.setStyle("-fx-background-color: #f2b3ae;");
		buttonList.remove(randomIndex);
		
		// if user on the last two box, then user's box is going to open and finish the game
		if(buttonList.size() <= 2) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Final choice! If you want to open your case then click Yes\nIf you want to open other case then click No", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Final choice");
	    	alert.setHeaderText("");
			alert.showAndWait();

			int randomFinalMoneyIndex = getRandomButtonIndex();
			Button btnMoneyFinal = (Button)buttonList.get(randomFinalMoneyIndex);
			int amount = splitButtonIdFromString(btnMoneyFinal.getId().toString());
			buttonList.remove(randomFinalMoneyIndex);
			
			if (alert.getResult() == ButtonType.YES) {
				// User open his case
				Alert alertFinalWon = new Alert(AlertType.CONFIRMATION, "You opened your case. You won " + amount + " TL", ButtonType.OK);
				alertFinalWon.setTitle("Congratulations!");
				alertFinalWon.setHeaderText("");
				alertFinalWon.showAndWait();
			} else {
				// User open the other case
				Alert alertFinalWon = new Alert(AlertType.CONFIRMATION, "You opened the other case. You won " + amount + " TL", ButtonType.OK);
				alertFinalWon.setTitle("Congratulations!");
				alertFinalWon.setHeaderText("");
				alertFinalWon.showAndWait();
			}
			if(restartGameHandler != null) {
				restartGameHandler.restart();
			}
		} else {
			if(MoneyGenerator.offerCounter >= 5) {
				MoneyGenerator.offerCounter = 0;
				makeOffer();
			}else{
				MoneyGenerator.offerCounter++;
			}
		}
	}
	
	private int getRandomButtonIndex() {
		Random rand = new Random();
		int max = buttonList.size()-1;
		int min = 0;
		return rand.nextInt(max - min + 1) + min;
	}
	
	private int splitButtonIdFromString(String buttonStringId) {
		int amount = Integer.parseInt(buttonStringId.substring(buttonPrefixString.length()));
		 return amount;
	}
	
	private void makeOffer() {
		int totalAmount = 0;
		
		for (int i = 0; i < buttonList.size(); i++) {
			Button btn = (Button)buttonList.get(i);
			int buttonAmount = splitButtonIdFromString(btn.getId().toString());
			totalAmount += buttonAmount;
		}
		
		int amountOffered = totalAmount / buttonList.size();
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Banker's offer " + amountOffered + " TL\n Do you accept the deal?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Banker's offer");
    	alert.setHeaderText("");
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    Alert alertAccept = new Alert(AlertType.CONFIRMATION, "You accepted the deal. You won " + amountOffered + " TL\n,congratulations :)\nWould you like to play new one?", ButtonType.OK);
		    alertAccept.setTitle("Congratulations!");
		    alertAccept.setHeaderText("");
		    alertAccept.showAndWait();

			if (alertAccept.getResult() != null) {
			    // restart the game
				if(restartGameHandler != null) {
					restartGameHandler.restart();
				}
			}
		}else{
			// User declined the offer
			pastOffersAmounts.add(amountOffered);
		}
	}
	
	public void setOnRestartGame(RestartGameHandler handler) {
		restartGameHandler = handler;
	}	
}
