package src.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import src.Score;
import src.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private static UIManager instance;

    public static UIManager getInstance() {
        if (instance == null) instance = new UIManager();
        return instance;
    }

    private StackPane gameStackPane;
    private BorderPane borderPane;
    private HeaderUI headerUI;
    private ConfigUI configUI;
    private UpgradesUI upgradesUI;
    private ObservableList<Score> scores;
    private ObservableList<Upgrade> upgrades;
    private GameOverUI gameOverUI;

    private UIManager() {
        scores = FXCollections.observableList(new ArrayList<>());
        upgrades = FXCollections.observableList(new ArrayList<>());

        gameStackPane = new StackPane();
        borderPane = new BorderPane();

        configUI = new ConfigUI();
        upgradesUI = new UpgradesUI(upgrades);
        gameOverUI = new GameOverUI(scores);
        headerUI = new HeaderUI(configUI, upgradesUI);

        borderPane.setTop(headerUI);
        borderPane.setCenter(gameStackPane);
        gameStackPane.getChildren().add(gameOverUI);
        gameStackPane.getChildren().add(configUI);
        gameStackPane.getChildren().add(upgradesUI);
    }

    public void setScores(List<Score> scores) {
        this.scores.setAll(scores);
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades.setAll(upgrades);
    }

    public void Update(long currentNanoTime, long deltaTime) {
        headerUI.Update(currentNanoTime, deltaTime);
        gameOverUI.Update(currentNanoTime, deltaTime);
        upgradesUI.Update(currentNanoTime, deltaTime);
    }

    public void setGameOverVisible(boolean visible) {
        gameOverUI.setVisible(visible);
    }

    public void setRoot(Group root) {
        root.getChildren().add(borderPane);
    }

    public void setCanvas(Canvas canvas) {
        gameStackPane.getChildren().add(0, canvas);
    }
}
