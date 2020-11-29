package src.ui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import src.Game;
import src.Score;

public class GameOverUI extends BorderPane {
    private Text title;
    private Button playAgainBtn;

    private float bounceAnimAux;

    public GameOverUI(ObservableList<Score> observableScores) {
        title = new Text(0, 0, "Game Over");
        title.setFill(Paint.valueOf("ff0000"));
        title.setFont(Font.font(48));
        title.setTextAlignment(TextAlignment.CENTER);

        TableView<Score> tableView = new TableView<>();
        TableColumn<Score, Integer> c1 = new TableColumn<>("Seus últimos scores:");
        c1.setCellValueFactory(
                new PropertyValueFactory<>("score"));
        c1.setPrefWidth(320);
        tableView.getColumns().add(c1);
        tableView.setItems(observableScores);
        tableView.setEditable(false);
        tableView.setFocusTraversable(false);
        tableView.setBackground(null);

        playAgainBtn = new Button("Jogar Novamente");
        playAgainBtn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        playAgainBtn.setTextFill(Paint.valueOf("#dddddd"));
        playAgainBtn.setPrefWidth(120);
        playAgainBtn.setPrefHeight(30);
        playAgainBtn.setFocusTraversable(false);
        playAgainBtn.setTranslateY(10);
        playAgainBtn.setOnMouseClicked(e -> {
            setVisible(false);
            Game.getInstance().resetGame();
        });

        setTop(title);
        setCenter(tableView);
        setBottom(playAgainBtn);
        setVisible(false);
        setBackground(null);
        BorderPane.setAlignment(getTop(), Pos.CENTER);
        BorderPane.setAlignment(getBottom(), Pos.CENTER);
        setMaxWidth(320);
        setMaxHeight(300);
    }

    public void Update(long currentNanoTime, long deltaTime) {
        playAgainBtn.setScaleX(1d + Math.abs(Math.sin(bounceAnimAux)) / 5d);
        bounceAnimAux += 0.05;
        if (bounceAnimAux > 3.14) bounceAnimAux = 0;
    }

}
