package edu.erau.hackathon;

import edu.erau.hackathon.player.SoundCloud;
import edu.erau.hackathon.player.Track;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
    // Define gui panes
    private Scene scene;
    private Stage parentStage = new Stage();
    private BorderPane borderPane = new BorderPane();
    private MenuBar menuBar = new MenuBar();
    private ListView songList = new ListView();
    private VBox vbox = new VBox();
    private VBox top = new VBox();
    private HBox controls = new HBox();
    private HBox functions = new HBox();
    private TextField search = new TextField();

    // Define menu items
    private Menu menuFile = new Menu("File");
    private MenuItem menuImport = new MenuItem("Import");
    private Menu menuHelp = new Menu("Help");
    // Define buttons
    private final Button previousButton, playButton, pauseButton, nextButton, searchButton, soundCloudButton;
    private final Image imagePrevious = new Image("file:img/previous.png");
    private final Image imagePause = new Image("file:img/pause.png");
    private final Image imagePlay = new Image("file:img/play.png");
    private final Image imageShuffle = new Image("file:img/shuffle.png");
    private final Image imageRepeat = new Image("file:img/repeat.png");
    private final Image imageNext = new Image("file:img/next.png");
    private final Image searchImage = new Image("file:img/search.png");
    private final Image soundCloudImage = new Image("file:img/soundcloud.png");

    public GUI() {
        // Create buttons
        previousButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        nextButton = new Button();
        searchButton = new Button();
        soundCloudButton = new Button();

        // Image for buttons
        previousButton.setGraphic(new ImageView(imagePrevious));
        pauseButton.setGraphic(new ImageView(imagePause));
        playButton.setGraphic(new ImageView(imagePlay));
        // shuffleButton.setGraphic(new ImageView(imageShuffle));
        // repeatButton.setGraphic(new ImageView(imageRepeat));
        nextButton.setGraphic(new ImageView(imageNext));
        searchButton.setGraphic(new ImageView(searchImage));
        soundCloudButton.setGraphic(new ImageView(soundCloudImage));

        // Add itmes to the menu items
        menuFile.getItems().add(menuImport);
        menuBar.getMenus().addAll(menuFile, menuHelp);
        // Set size of the window
        scene = new Scene(borderPane, 266,320);
        songList.setEditable(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.parentStage = primaryStage;

        search.setPrefSize(184,38);
        primaryStage.setResizable(false);

        // Get children for buttons
        controls.getChildren().addAll(playButton, pauseButton);
        functions.getChildren().addAll(search, searchButton, soundCloudButton);

        // Set location of panes
        borderPane.setTop(vbox);
        borderPane.setLeft(top);
        borderPane.setCenter(songList);
        top.getChildren().addAll(functions,songList,controls);

        controls.setAlignment(Pos.CENTER);

        searchButton.setOnAction(e -> {
            SoundBox.tracks = SoundCloud.getInstance().search(search.getText());
            System.out.println("Tracks: " + SoundBox.tracks.size());

            songList.getItems().clear();
            songList.getItems().add("Tracks: " + SoundBox.tracks.size());
            for (Track track : SoundBox.tracks) {
                songList.getItems().add(track.title());
            }
        });

        soundCloudButton.setOnAction(e -> getHostServices().showDocument("https://soundcloud.com"));

        playButton.setOnAction(e -> {
            int i = songList.getSelectionModel().getSelectedIndex()-1;
            SoundBox.playThread.track(SoundBox.tracks.get(i));
            SoundBox.playThread.start();
        });
        pauseButton.setOnAction(e -> SoundBox.playThread.pause());

        //Display scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Media PlayerThread");
        primaryStage.show();
    }

    private void showHelp(){
        final String helpText = "This program was created by Robby Duke, Ryan Newitt, Andy Artze, John Martinicic at Hack Riddle on 3/26/2016.";
        Label helpLabel = new Label();
        helpLabel.setText(helpText);

        StackPane pane = new StackPane();
        pane.getChildren().add(helpLabel);

        Scene help = new Scene(pane, 550,100);
        Stage helpWindow = new Stage();
        helpWindow.setScene(help);
        helpWindow.setTitle("About this program");
        helpWindow.setResizable(false);
        helpWindow.show();
    }

    @Override
    public void stop(){
        SoundBox.playThread.pause();
        System.exit(-1);
    }
}