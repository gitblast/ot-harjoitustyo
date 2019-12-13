/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhandreplayer.ui;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pokerhandreplayer.domain.Card;
import pokerhandreplayer.domain.GameState;
import pokerhandreplayer.domain.HandCreator;
import pokerhandreplayer.domain.Player;
import pokerhandreplayer.domain.Replay;

public class ReplayerUI extends Application {    
    public static final int WIDTH = 960;
    public static final int HEIGHT = 670;
    public static final int CARD_WIDTH = 180;
    public static final int CARD_HEIGHT = 120;
    public static final int POT_WIDTH = 120;
    public static final int POT_HEIGHT = 60;
    public static final int btnHeight = 30;
    public static final int MARGIN = 80;
    public static final int PADDING = 15;
    
    private Replay replay;  
    private Pane table;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Replayer");
        primaryStage.setScene(handSelector(primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene handSelector(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*txt)", "*.txt"));
        TextArea text = new TextArea();
        
        Label textLabel = new Label("Or paste hand history below:");
        Label fileLabel = new Label("Select hand history from file:");

        Button button = new Button("Select File");

        VBox vBox = new VBox(fileLabel, button, textLabel, text);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(7, 7, 7, 7));
        vBox.setSpacing(5);
        Scene scene = new Scene(vBox, 250, 200);
        
        button.setOnAction(e -> {
            File handHistory = fileChooser.showOpenDialog(primaryStage);
            if (handHistory != null) {
                this.table = new Pane();
                this.table.setTranslateY(20);
                this.replay = new HandCreator().createHand(handHistory);
                Scene replayScene = replayer(primaryStage);
                primaryStage.setScene(replayScene);                
                primaryStage.setWidth(WIDTH);
                primaryStage.setHeight(replayScene.getHeight() + btnHeight);
            } else {
                System.out.println("No file selected");
            }
            
        });
        
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            this.table = new Pane();
            this.table.setTranslateY(20);
            ArrayList<String> lines = new ArrayList<>(Arrays.stream(newValue.split("\n")).collect(Collectors.toList()));
            this.replay = new HandCreator().createHand(lines);
            Scene replayScene = replayer(primaryStage);
            primaryStage.setScene(replayScene);                
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(replayScene.getHeight() + btnHeight);
        });
        
        return scene;
    }
    
    public Scene replayer(Stage primaryStage) {
        GameState initialState = replay.getByIndex(0);
        setPlayers(initialState);
        Button prev = new Button("<");
        Button next = new Button(">");
        prev.setPadding(new Insets(btnHeight, btnHeight, btnHeight, btnHeight));
        next.setPadding(new Insets(btnHeight, btnHeight, btnHeight, btnHeight));
        
        Button addNew = new Button("Edit comment");
        Button saveComment = new Button("Save comment");
        
        String commentText = replay.getCurrent().getComment();
        Label comment = new Label(commentText.equals("") ? "No comments yet" : commentText);
        comment.setPadding(new Insets(0, PADDING * 5, 0, PADDING));
        comment.setMinWidth(WIDTH / 3 * 2);
        comment.setMaxWidth(WIDTH / 3 * 2);
        comment.setMaxHeight(btnHeight * 2 + 16);
        comment.setWrapText(true);
        
        TextArea edit = new TextArea();
        edit.setPadding(new Insets(0, PADDING * 5, 0, PADDING ));
        edit.setMinWidth(WIDTH / 3 * 2);
        edit.setMaxWidth(WIDTH / 3 * 2);
        edit.setMaxHeight(btnHeight * 2 + 16);
        edit.setWrapText(true);
        
        HBox buttonBox = new HBox(prev, next, comment, addNew);
        buttonBox.setPadding(new Insets(0, PADDING, 0, PADDING));
        buttonBox.setSpacing(5);
        
        Button back = new Button("Return");
        Button save = new Button("Save hand");
        
        addNew.setOnAction(e -> {
            buttonBox.getChildren().removeAll(comment, addNew);
            edit.setText(comment.textProperty().get());
            buttonBox.getChildren().addAll(edit, saveComment);
        });
        
        saveComment.setOnAction(e -> {
            buttonBox.getChildren().removeAll(edit, saveComment);
            replay.getCurrent().setComment(edit.textProperty().get());
            comment.setText(replay.getCurrent().getComment());
            buttonBox.getChildren().addAll(comment, addNew);
        });
        
        prev.setOnAction(e -> {
            setPlayers(replay.getPrev());            
            comment.setText(replay.getCurrent().getComment().equals("") ? "No comments yet" : replay.getCurrent().getComment());
        });
        
        next.setOnAction(e -> {
            setPlayers(replay.getNext());
            comment.setText(replay.getCurrent().getComment().equals("") ? "No comments yet" : replay.getCurrent().getComment());
        });
        
        save.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*txt)", "*.txt"));
            File file = fileChooser.showSaveDialog(primaryStage);
            
            String hh = replay.getAsStringWithComments();
            if (file != null) {
                if (!file.getName().contains(".")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                saveTextToFile(hh, file);
            }
        });
        
        back.setOnAction(e -> {
            primaryStage.setScene(handSelector(primaryStage));
            primaryStage.sizeToScene();
        });
        
        HBox topBar = new HBox(back, save);
        topBar.setSpacing(5);
        topBar.setPadding(new Insets(0, PADDING, 0, PADDING));
        
        VBox top = new VBox(topBar, table);
        BorderPane root = new BorderPane();
        top.setSpacing(20);
        root.setTop(topBar);
        root.setCenter(table);
        root.setBottom(buttonBox);        
        root.setPadding(new Insets(PADDING, 0, PADDING, 0));
        Scene replayer = new Scene(root, WIDTH, HEIGHT + btnHeight + MARGIN * 2);
        return replayer;
    }
    
    public void setPlayers(GameState state) { 
        StackPane player1 = playerLayout(state.getPlayers().get(0), 0, HEIGHT / 2 - CARD_HEIGHT / 2, CARD_WIDTH * 2 / 3, 0);
        StackPane player2 = playerLayout(state.getPlayers().get(1), WIDTH / 3 - CARD_WIDTH / 2, 0, 0, CARD_HEIGHT * 2 / 3);
        StackPane player3 = playerLayout(state.getPlayers().get(2), WIDTH * 2 / 3 - CARD_WIDTH / 2, 0, 0, CARD_HEIGHT * 2 / 3);
        StackPane player4 = playerLayout(state.getPlayers().get(3), WIDTH - CARD_WIDTH, HEIGHT / 2 - CARD_HEIGHT / 2, CARD_WIDTH * -2 / 3, 0);
        StackPane player5 = playerLayout(state.getPlayers().get(4), WIDTH * 2 / 3 - CARD_WIDTH / 2, HEIGHT - CARD_HEIGHT, 0, CARD_HEIGHT * -1);
        StackPane player6 = playerLayout(state.getPlayers().get(5), WIDTH / 3 - CARD_WIDTH / 2, HEIGHT - CARD_HEIGHT, 0, CARD_HEIGHT * -1);
        
        StackPane pot = potLayout(state.getPot());
        
        Rectangle bg = new Rectangle(WIDTH * 0.8, HEIGHT * 0.8);
        bg.setTranslateX(WIDTH * 0.1);
        bg.setTranslateY(HEIGHT * 0.1);
        bg.setFill(Color.GREEN);
        bg.setArcWidth(WIDTH / 3);
        bg.setArcHeight(HEIGHT / 2);
        
        table.getChildren().clear();
        table.getChildren().addAll(bg, pot, player1, player2, player3, player4, player5, player6);
    }
    
    public StackPane potLayout(int size) {
        Rectangle bg = new Rectangle(POT_WIDTH, POT_HEIGHT);
        bg.setFill(Color.ALICEBLUE);
        bg.setArcWidth(POT_WIDTH * 0.2);
        bg.setArcHeight(POT_HEIGHT * 0.2);
        
        Label pot = new Label("POT: " + size / 100 + " €");
        StackPane root = new StackPane(bg, pot);
        root.setTranslateX(WIDTH / 2 - POT_WIDTH / 2);
        root.setTranslateY(HEIGHT / 2 - POT_HEIGHT / 2);
        
        return root;
    }
    
    public StackPane playerLayout(Player player, double offX, double offY, double betX, double betY) {
        
        Rectangle bg = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        bg.setFill(Color.WHEAT);
        bg.setArcWidth(POT_WIDTH * 0.2);
        bg.setArcHeight(POT_HEIGHT * 0.2);
        
        Label nameTag = new Label(player.getName());
        Label stack = new Label(player.getStackSize()/100 + " €");
        StackPane action = actionLayout(player);
        
        Label bet = new Label(player.getBet() == 0 ? "" : player.getBet()/100 + " €");
        bet.setTextFill(Color.WHITE);
        bet.setFont(new Font("Arial", 20));
        bet.setTranslateX(betX);
        bet.setTranslateY(betY);        
        
        StackPane cards = getCardDisplay(player);
        
        VBox playerData = new VBox(nameTag, stack, action);
        playerData.setSpacing(7);
        playerData.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(cards, bg, playerData, bet);
        root.setTranslateX(offX);
        root.setTranslateY(offY);
        return root;
    }
    
    public StackPane getCardDisplay(Player player) {
        if (player.hasFolded()) {
            return new StackPane();
        }
        
        StackPane root = new StackPane();
        
        for (Card c : player.getCards()) {
            Image card = new Image("file:assets/" + c.getString() +".gif");
            ImageView cardView = new ImageView(card);
            cardView.setFitWidth(50);
            cardView.setFitHeight(70);
            
            double offset = CARD_WIDTH * -0.2 + root.getChildren().size() * CARD_WIDTH * 0.1;
            
            cardView.setTranslateX(offset);
            cardView.setTranslateY(CARD_HEIGHT * -0.55);
            root.getChildren().add(cardView);
        }
        
        return root;
    }
    
    public StackPane actionLayout(Player player) {
        Rectangle bg = new Rectangle(CARD_WIDTH * 0.9, CARD_HEIGHT * 0.4);
        bg.setArcWidth(bg.getWidth() * 0.1);
        bg.setArcHeight(bg.getHeight() * 0.1);
        Label text = new Label(player.getLabel());
        text.setTextFill(Color.ALICEBLUE);
        return new StackPane(bg, text);
    }
    
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (Exception ex) {
            System.out.println("Error saving: " + ex.getMessage());
        }
    }
}