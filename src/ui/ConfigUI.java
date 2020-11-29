package src.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.AudioManager;
import src.Game;

public class ConfigUI extends VBox {
    private Text configTitle;
    private Text configVolumeTxt;
    private Button configExit;
    private Slider volumeSlider;

    public ConfigUI() {
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#111111dd"), null, null)));
        configTitle = new Text("Configurações");
        configTitle.setFill(Paint.valueOf("#dddddd"));
        configTitle.setFont(Font.font(30));
        configVolumeTxt = new Text("Volume:");
        configVolumeTxt.setFill(Paint.valueOf("#dddddd"));
        configVolumeTxt.setFont(Font.font(24));
        volumeSlider = new Slider(0.0, 1.0, 0.2);
        volumeSlider.setFocusTraversable(false);
        volumeSlider.valueProperty().addListener(event -> {
            AudioManager.getInstance().setVolume(volumeSlider.getValue());
        });
        configExit = new Button("Sair");
        configExit.setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        configExit.setTextFill(Paint.valueOf("#dddddd"));
        configExit.setPrefWidth(120);
        configExit.setPrefHeight(30);
        configExit.setFocusTraversable(false);
        configExit.setOnMouseClicked(e -> {
            setVisible(false);
            Game.getInstance().setPaused(false);
        });
        setVisible(false);
        getChildren().add(configTitle);
        getChildren().add(configVolumeTxt);
        getChildren().add(volumeSlider);
        getChildren().add(configExit);
        setAlignment(Pos.TOP_CENTER);
        setSpacing(20);
        setMaxWidth(320);
        setMaxHeight(200);
        setPadding(new Insets(30, 30, 30, 30));
    }
}
