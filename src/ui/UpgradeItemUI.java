package src.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import src.Upgrade;

public class UpgradeItemUI extends Button {
    private Upgrade upgrade;

    public UpgradeItemUI(Upgrade upgrade) {
        this.upgrade = upgrade;

        setBackground(new Background(new BackgroundFill(Paint.valueOf("#232323"), null, null)));
        setTextFill(Paint.valueOf("#dddddd"));
        setFocusTraversable(false);
        setPrefWidth(150);
        setPrefHeight(150);
        setWrapText(true);
        setOnMouseClicked(e -> {
            upgrade.use();
        });
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public void Update(long currentNanoTime, long deltaTime) {
        setDisable(!upgrade.isUsable());
        setText(upgrade.getName() + "\n\n" + upgrade.getPrice() + " pontos\n" + upgrade.getUsedTimes() + "/" + upgrade.getMaxUses());
    }
}
