package src.elements.enemies;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import src.AssetsManager;
import src.Game;
import src.Params;
import src.Utils;

import java.util.List;

public class GroupEnemy extends Enemy {
    public GroupEnemy(int px, int py) {
        super(px, py, 2);
    }

    @Override
    public void start() {
        setSpeed(1);
        super.start();
    }

    @Override
    public void Update(long currentTime, long deltaTime) throws Exception {
        super.Update(currentTime, deltaTime);
        List<GroupEnemy> ges = Game.getInstance().getChars(GroupEnemy.class);
        if (ges.size() > 0) {
            if (ges.get(0) == this) {
                Rectangle2D rect = Utils.getRectOfCharacterCollection(ges);
                if (rect != null) {
                    if (rect.getMaxX() > Params.GAME_WIDTH) {
                        for (GroupEnemy ge : ges) {
                            ge.setDirH(-1);
                            ge.setPosY(ge.getY() + ge.getAltura());
                        }
                    } else if (rect.getMinX() < 0) {
                        for (GroupEnemy ge : ges) {
                            ge.setDirH(1);
                            ge.setPosY(ge.getY() + ge.getAltura());
                        }
                    }
                }
            }
        }
        setPosX(getX() + getSpeed() * getDirH());

    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        Image im = AssetsManager.getInstance().getImage("groupenemy.gif");
        if (im == null) {
            graphicsContext.setFill(Paint.valueOf("#007777"));
            graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        } else {
            graphicsContext.drawImage(im, getX(), getY(), getLargura(), getAltura());
        }
        DrawLifes(graphicsContext, 0, -5);
    }
}
