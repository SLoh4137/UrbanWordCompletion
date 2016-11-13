package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.WebCrawler;

@SuppressWarnings("restriction")

public class GUIRunner extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		int sceneWidth = 400, sceneHeight = 400;
		int verSpaceBetweenNodes = 8, horSpaceBetweenNodes = 8;
		int paneBorderTop = 20, paneBorderRight = 20;
		int paneBorderBottom = 20, paneBorderLeft = 20;
		
		FlowPane fieldsPane = new FlowPane();
		fieldsPane.setHgap(horSpaceBetweenNodes);
		fieldsPane.setVgap(verSpaceBetweenNodes);
		fieldsPane.setPadding(new Insets(paneBorderTop, paneBorderRight, 
			    paneBorderBottom, paneBorderLeft));
		
		Label introText = new Label("Welcome to the Urban Dictionary Word Completion"
				+ "\nThis program will find words similar to the word you enter"
				+ "\nPlease type a word then click find words");
		introText.setWrapText(true);
		
		TextField userField = new TextField();
		Button find = new Button("Find Words");
		
		TextArea results = new TextArea();
		results.setPrefSize(sceneWidth - paneBorderRight - paneBorderLeft, sceneWidth / 2);
		
		find.setOnAction(e -> {
			String word = userField.getText();
			results.setText(WebCrawler.getWords(word));
		});
		
		fieldsPane.getChildren().addAll(introText, userField,find, results);
		
		/* Display the stage */
		Scene scene = new Scene(fieldsPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Urban Dictionary Word Completion");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
