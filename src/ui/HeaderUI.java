package src.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.Game;

public class HeaderUI extends GridPane {
    private Text scoreTxt;
    private Text lifesTxt;
    private Text fpsText;
    private Button configBtn;
    private Button upgradesBtn;
    private ConfigUI configUI;
    private UpgradesUI upgradesUI;

    private int fpsAux = 0;
    private long lastFpsTime = 0;

    public HeaderUI(ConfigUI configUI, UpgradesUI upgradesUI) {
        this.configUI = configUI;
        this.upgradesUI = upgradesUI;

        setBackground(new Background(new BackgroundFill(Paint.valueOf("#121212"), null, null)));
        setPadding(new Insets(0, 30, 0, 30));

        scoreTxt = new Text(0, 10, "Score: 0");
        scoreTxt.setFill(Paint.valueOf("#dddddd"));
        scoreTxt.setFont(Font.font(30));

        lifesTxt = new Text(0, 10, "Lifes: 0");
        lifesTxt.setFill(Paint.valueOf("#dddddd"));
        lifesTxt.setFont(Font.font(30));

        upgradesBtn = new Button("Upgrades");
        upgradesBtn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        upgradesBtn.setTextFill(Paint.valueOf("#dddddd"));
        upgradesBtn.setPrefWidth(120);
        upgradesBtn.setPrefHeight(30);
        upgradesBtn.setFocusTraversable(false);
        upgradesBtn.setOnMouseClicked(e -> {
            upgradesUI.setVisible(true);
            Game.getInstance().setPaused(true);
        });

        configBtn = new Button("Configuração");
        configBtn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        configBtn.setTextFill(Paint.valueOf("#dddddd"));
        configBtn.setPrefWidth(120);
        configBtn.setPrefHeight(30);
        configBtn.setFocusTraversable(false);
        configBtn.setOnMouseClicked(e -> {
            configUI.setVisible(true);
            Game.getInstance().setPaused(true);
        });

        fpsText = new Text("Fps: 0");
        fpsText.setFill(Paint.valueOf("#00ff00"));
        fpsText.setFont(Font.font(12));

        addColumn(0, fpsText);
        addColumn(1, lifesTxt);
        addColumn(2, scoreTxt);
        addColumn(3, upgradesBtn);
        addColumn(4, configBtn);
        getColumnConstraints().addAll(
                new ColumnConstraints(50, 50, 50),
                new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, true),
                new ColumnConstraints(-1, -1, -1, Priority.ALWAYS, HPos.CENTER, true),
                new ColumnConstraints(100, 100, 100),
                new ColumnConstraints(100, 100, 100)
        );
        setHgap(5);
    }

    public void Update(long currentNanoTime, long deltaTime) {
        scoreTxt.setText("Score: " + Game.getInstance().getScore());
        lifesTxt.setText("Lifes: " + Game.getInstance().getLifes());
        fpsAux++;
        if (currentNanoTime - lastFpsTime > 1000000000) {
            lastFpsTime = currentNanoTime;
            fpsText.setText("Fps: " + fpsAux);
            fpsAux = 0;
        }
    }
}
