package application;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class BoxGenerator {
	
	public Button userSelectedButtonNode = null;
	public List<Button> buttonList = new ArrayList<>();
	protected Label notificationLabel;
	protected OpenBoxHandler openBoxHandler;
	/**
	 * Button id prefix for all buttons in boxes area
	 */
	private String buttonPrefixString = "kutuBtn_";
	
	public BoxGenerator() {
		init();
	}
	
	public void refreshState() {
		buttonList.clear();
		userSelectedButtonNode = null;
	}
	
	public void init() {
		refreshState();
		
		for (int i = 0; i < 26; i++) {
    		Button btn = new Button("" + (i + 1));
    		
    		btn.setOnAction(e -> {
    			Button clickedButton = (Button)e.getSource();
    			String buttonIdString = clickedButton.getId().toString();
    			if(userSelectedButtonNode == null) {
    				userSelectedButtonNode = clickedButton;
    				clickedButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
    				clickedButton.setDisable(true);
    				notify("Your case's number is " + (splitButtonIdFromString(buttonIdString) + 1));
    			}else {
    				// User opens the box
    				clickedButton.setDisable(true);
    				if(openBoxHandler != null) {
    					openBoxHandler.open();
    				}
    				
    			}
    		});
    		
        	btn.setId(buttonPrefixString + i);
        	btn.setAlignment(Pos.CENTER);
        	btn.setContentDisplay(ContentDisplay.CENTER);
        	btn.setFocusTraversable(false);
        	btn.setMnemonicParsing(false);
        	btn.setPrefHeight(30);
        	btn.setPrefWidth(40);
        	
        	buttonList.add(btn);
		}
	}
	
	public HBox createRow(int fromIndex, int toIndex) {
		HBox hBox =	new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(22);
    	hBox.getChildren().addAll(buttonList.subList(fromIndex, toIndex));
		return hBox;
	}
	
	public void setNotificationLabel(Label label) {
		notificationLabel = label;
	}
	
	private void notify(String message) {
		notificationLabel.setText(message);
	}
	
	private int splitButtonIdFromString(String buttonStringId) {
		int id = Integer.parseInt(buttonStringId.substring(buttonPrefixString.length()));
		 return id;
	}
	
	public void setOnOpenBox(OpenBoxHandler handler) {
		openBoxHandler = handler;
	}
}
