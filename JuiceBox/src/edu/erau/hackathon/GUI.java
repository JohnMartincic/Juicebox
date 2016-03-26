package edu.erau.hackathon;

import javafx.application.Application;
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
    private TableView songList = new TableView();
    private VBox vbox = new VBox();
    private BorderPane top = new BorderPane();
    private BorderPane lower = new BorderPane();
    private HBox controls = new HBox();
    private HBox functions = new HBox();
    private ListView related = new ListView();
    private StackPane previousSong = new StackPane();
    private StackPane nextSong = new StackPane();
    private TextField search = new TextField();

    // Define menu items
    private Menu menuFile = new Menu("File");
    private MenuItem menuImport = new MenuItem("Import");
    // Define buttons
    private final Button previousButton, playButton, pauseButton, shuffleButton, repeatButton, nextButton, signInButton, uploadButton, websiteButton;
    private final Image imagePrevious = new Image("file:img/previous.png");
    private final Image imagePlay = new Image("file:img/play.png");
    private final Image imagePause = new Image("file:img/pause.png");
    private final Image imageShuffle = new Image("file:img/shuffle.png");
    private final Image imageRepeat = new Image("file:img/repeat.png");
    private final Image imageNext = new Image("file:img/next.png");
    private final Image signInImage = new Image("file:img/profile.png");
    private final Image uploadImage = new Image("file:img/upload.png");
    private final Image imageWebsite = new Image("file:img/website.png");

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
        signInButton = new Button();
        uploadButton = new Button();
        websiteButton = new Button();

        test = new Label();
        // Image for buttons
        previousButton.setGraphic(new ImageView(imagePrevious));
        playButton.setGraphic(new ImageView(imagePlay));
        pauseButton.setGraphic(new ImageView(imagePause));
        shuffleButton.setGraphic(new ImageView(imageShuffle));
        repeatButton.setGraphic(new ImageView(imageRepeat));
        nextButton.setGraphic(new ImageView(imageNext));
        signInButton.setGraphic(new ImageView(signInImage));
        uploadButton.setGraphic(new ImageView(uploadImage));
        websiteButton.setGraphic(new ImageView(imageWebsite));

        test.setGraphic(new ImageView(imageTest));
        // Add itmes to the menu items
        menuFile.getItems().add(menuImport);
        menuBar.getMenus().add(menuFile);
        // Set size of the window
        scene = new Scene(borderPane, 500, 310);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.parentStage = primaryStage;

        search.setPrefSize(184, 38);

        // Get children for buttons
        controls.getChildren().addAll(previousButton, playButton, pauseButton, shuffleButton, repeatButton, nextButton);
        //previousSong.getChildren().add(previousButton);
        //nextSong.getChildren().add(nextButton);
        vbox.getChildren().add(menuBar);
        functions.getChildren().addAll(search, signInButton, uploadButton);

        // Set location of panes
        borderPane.setTop(vbox);
        borderPane.setCenter(top);
        borderPane.setRight(lower);

        top.setTop(functions);
        //top.setLeft(previousSong);
        top.setCenter(test);
        top.setBottom(controls);
        //top.setRight(nextSong);
        lower.setCenter(related);

        //controls.setAlignment(Pos.CENTER);
        //functions.setAlignment(Pos.TOP_CENTER);


        signInButton.setOnAction(e -> getHostServices().showDocument("https://soundcloud.com/login"));
        uploadButton.setOnAction(e -> getHostServices().showDocument("https://soundcloud.com/upload"));
        websiteButton.setOnAction(e -> getHostServices().showDocument("https://soundcloud.com"));
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
        primaryStage.setTitle("Media Player");
        primaryStage.show();
    }
}