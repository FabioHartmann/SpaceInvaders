package src.elements.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import src.*;
import src.elements.Shot;

public class ShooterEnemy extends Enemy {
    private Timer shotTimer;

    public ShooterEnemy(int px, int py) {
        super(px, py, 3);
    }

    @Override
    public void start() {
        shotTimer = new Timer(0.6f, true);
        shotTimer.addHandler(loop -> {
            Game.getInstance().addChar(new Shot(getX()+16, getY()+getAltura(), 1, 0, 5, this));
        });
        super.start();
    }

    @Override
    public void Update(long currentTime, long deltaTime) throws Exception {
        super.Update(currentTime, deltaTime);
        setPosX(getX() + getSpeed());
        shotTimer.Update(deltaTime);
        if (getX() >= getLMaxH()){
            setPosX(getLMinH());
            setSpeed(Params.getInstance().nextInt(5)+1);
            setPosY(getY() + getAltura());
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        Image im = AssetsManager.getInstance().getImage("spaceship.gif");
        if (im == null) {
            graphicsContext.setFill(Paint.valueOf("#007700"));
            graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        } else {
            graphicsContext.drawImage(im, getX(), getY(), getLargura(), getAltura());
        }
        DrawLifes(graphicsContext, 0, -5);
    }
}
