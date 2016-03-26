package edu.erau.hackathon;

import edu.erau.hackathon.player.SoundCloud;
import edu.erau.hackathon.player.Track;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private BorderPane top = new BorderPane();
    private HBox controls = new HBox();
    private HBox functions = new HBox();
    private TextField search = new TextField();

    // Define menu items
    private Menu menuFile = new Menu("File");
    private MenuItem menuImport = new MenuItem("Import");
    private Menu menuHelp = new Menu("Help");
    // Define buttons
    private final Button previousButton, playButton, pauseButton, shuffleButton, repeatButton, nextButton, searchButton, uploadButton;
    private final Image imagePrevious = new Image("file:img/previous.png");
    private final Image imagePause = new Image("file:img/pause.png");
    private final Image imagePlay = new Image("file:img/play.png");
    private final Image imageShuffle = new Image("file:img/shuffle.png");
    private final Image imageRepeat = new Image("file:img/repeat.png");
    private final Image imageNext = new Image("file:img/next.png");
    private final Image searchImage = new Image("file:img/search.png");
    private final Image uploadImage = new Image("file:img/upload.png");
    private final Label test;
    private final Image imageTest = null;//new Image("file:img/album.jpg");

    public GUI() {
        // Create buttons
        previousButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        shuffleButton = new Button();
        repeatButton = new Button();
        nextButton = new Button();
        searchButton = new Button();
        uploadButton = new Button();

        test = new Label();
        // Image for buttons
        previousButton.setGraphic(new ImageView(imagePrevious));
        pauseButton.setGraphic(new ImageView(imagePause));

        playButton.setGraphic(new ImageView(imagePlay));
        shuffleButton.setGraphic(new ImageView(imageShuffle));
        repeatButton.setGraphic(new ImageView(imageRepeat));
        nextButton.setGraphic(new ImageView(imageNext));
        searchButton.setGraphic(new ImageView(searchImage));
        uploadButton.setGraphic(new ImageView(uploadImage));

        test.setGraphic(new ImageView(imageTest));
        // Add itmes to the menu items
        menuFile.getItems().add(menuImport);
        menuBar.getMenus().addAll(menuFile, menuHelp);
        // Set size of the window
        scene = new Scene(borderPane, 550,320);

        songList.setEditable(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.parentStage = primaryStage;

        search.setPrefSize(184,38);
        primaryStage.setResizable(false);

        // Get children for buttons
        controls.getChildren().addAll(previousButton, playButton, pauseButton, shuffleButton, repeatButton, nextButton);
        //previousSong.getChildren().add(previousButton);
        //nextSong.getChildren().add(nextButton);
        vbox.getChildren().add(menuBar);
        functions.getChildren().addAll(search, searchButton, uploadButton);

        // Set location of panes
        borderPane.setTop(vbox);
        borderPane.setLeft(top);
        borderPane.setCenter(songList);

        top.setTop(functions);
        //top.setLeft(previousSong);
        top.setCenter(test);
        top.setBottom(controls);
        //top.setRight(nextSong);
        //lower.setRight(related);

        //controls.setAlignment(Pos.CENTER);
        //functions.setAlignment(Pos.TOP_CENTER);


        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = songList.getSelectionModel().getSelectedIndex()-1;
                SoundBox.playThread.track(SoundBox.tracks.get(i));
                SoundBox.playThread.start();
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SoundBox.tracks = SoundCloud.getInstance().search(search.getText());
                System.out.println("Tracks: " + SoundBox.tracks.size());

                songList.getItems().clear();
                songList.getItems().add("Tracks: " + SoundBox.tracks.size());
                for (Track track : SoundBox.tracks) {
                    songList.getItems().add(track.title());
                }
            }
        });
        uploadButton.setOnAction(e -> getHostServices().showDocument("https://soundcloud.com/upload"));
        menuHelp.setOnAction(e -> showHelp());
        /*
        * previousButton.setOnAction(e ->);
        * playButton.setOnAction(e -> );
        * pauseButton.setOnAction(e -> );
        * repeatButton.setOnAction(e ->);
        * shuffleButton.setOnAction(e ->);
        * nextButton.setOnAction(e ->);
        */


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