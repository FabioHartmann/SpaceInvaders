package src.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import src.Game;
import src.Upgrade;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class UpgradesUI extends VBox {
    private Text title;
    private FlowPane items;
    private Button quitBtn;
    private List<UpgradeItemUI> upgradeItemsUi;

    public UpgradesUI(ObservableList<Upgrade> upgrades) {
        title = new Text(0, 0, "Upgrades");
        title.setFill(Paint.valueOf("#dddd44"));
        title.setFont(Font.font(48));
        title.setTextAlignment(TextAlignment.CENTER);

        items = new FlowPane();
        items.setBackground(null);
        items.setMaxHeight(100);
        items.setAlignment(Pos.CENTER);
        items.setHgap(5);

        upgradeItemsUi = new CopyOnWriteArrayList<>();
        upgrades.addListener((ListChangeListener<Upgrade>) c -> {
            while (c.next()) {
                for(Upgrade upgrade : c.getRemoved()) {
                    UpgradeItemUI itemUI = upgradeItemsUi.stream()
                            .filter(item -> item.getUpgrade() == upgrade)
                            .map(Optional::ofNullable)
                            .findFirst()
                            .flatMap(Function.identity())
                            .orElse(null);
                    if (itemUI != null) {
                        upgradeItemsUi.remove(itemUI);
                        items.getChildren().remove(itemUI);
                    }
                }

                for(Upgrade upgrade : c.getAddedSubList()) {
                    UpgradeItemUI item = new UpgradeItemUI(upgrade);
                    upgradeItemsUi.add(item);
                    items.getChildren().add(item);
                }
            }
        });

        for(Upgrade upgrade : upgrades) {
            UpgradeItemUI item = new UpgradeItemUI(upgrade);
            upgradeItemsUi.add(item);
            items.getChildren().add(item);
        }

        quitBtn = new Button("Sair");
        quitBtn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        quitBtn.setTextFill(Paint.valueOf("#dddddd"));
        quitBtn.setPrefWidth(120);
        quitBtn.setPrefHeight(30);
        quitBtn.setFocusTraversable(false);
        quitBtn.setOnMouseClicked(e -> {
            setVisible(false);
            Game.getInstance().setPaused(false);
        });

        setVisible(false);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#111111dd"), null, null)));
        setMaxWidth(320);
        setMaxHeight(250);
        setAlignment(Pos.CENTER);
        setSpacing(10);

        getChildren().add(title);
        getChildren().add(items);
        getChildren().add(quitBtn);
    }

    public void Update(long currentNanoTime, long deltaTime) {
        for(UpgradeItemUI itemUI : upgradeItemsUi) {
            itemUI.Update(currentNanoTime, deltaTime);
        }
    }
}
