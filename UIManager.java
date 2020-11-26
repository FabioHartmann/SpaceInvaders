import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UIManager {
    private static UIManager instance;

    public static UIManager getInstance() {
        if (instance == null) instance = new UIManager();
        return instance;
    }

    private StackPane gameStackPane;
    private BorderPane borderPane;
    private BorderPane header;
    private BorderPane gameOverPane;
    private Text scoreTxt;
    private Text lifesTxt;
    private Text gameOverText;
    private ObservableList<Score> scores;

    private UIManager() {
        scores = FXCollections.observableList(new ArrayList<>());

        borderPane = new BorderPane();

        header = new BorderPane();
        header.setPadding(new Insets(0, 30, 0, 30));
        scoreTxt = new Text(0, 10, "Score: 0");
        scoreTxt.setFont(Font.font(30));
        lifesTxt = new Text(0, 10, "Lifes: 0");
        lifesTxt.setFont(Font.font(30));
        header.setLeft(scoreTxt);
        header.setRight(lifesTxt);
        borderPane.setTop(header);

        gameStackPane = new StackPane();

        gameOverPane = new BorderPane();
        gameOverText = new Text(0, 0, "Game Over");
        gameOverText.setFill(Paint.valueOf("ff0000"));
        gameOverText.setFont(Font.font(48));
        gameOverText.setTextAlignment(TextAlignment.CENTER);

        TableView<Score> tableView = new TableView<>();
        TableColumn<Score, Integer> c1 = new TableColumn<>("Seus últimos scores:");
        c1.setCellValueFactory(
                new PropertyValueFactory<>("score"));
        c1.setMinWidth(315);

        tableView.getColumns().add(c1);
        tableView.setItems(scores);

        tableView.setEditable(false);

        gameOverPane.setTop(gameOverText);
        gameOverPane.setCenter(tableView);
        gameOverPane.setVisible(false);
        BorderPane.setAlignment(gameOverPane.getTop(), Pos.CENTER);
        gameOverPane.setMaxWidth(320);
        gameOverPane.setMaxHeight(300);

        gameStackPane.getChildren().add(gameOverPane);
        borderPane.setCenter(gameStackPane);
    }

    public void setScores(List<Score> scores) {
        this.scores.setAll(scores);
    }

    public void update() {
        scoreTxt.setText("Score: " + Game.getInstance().getScore());
        lifesTxt.setText("Lifes: " + Game.getInstance().getLifes());
    }

    public void setGameOverVisible(boolean visible) {
        gameOverPane.setVisible(visible);
    }

    public void setRoot(Group root) {
        root.getChildren().add(borderPane);
    }

    public void setCanvas(Canvas canvas) {
        gameStackPane.getChildren().add(0, canvas);
    }
}
