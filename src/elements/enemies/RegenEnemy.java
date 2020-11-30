package src.elements.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import src.AssetsManager;
import src.Params;
import src.Timer;

public class RegenEnemy extends Enemy  {
    private Timer shotTimer;

    public RegenEnemy(int px, int py, int lifes) {
        super(px, py, lifes);
    }

    @Override
    public void start() {
        shotTimer = new Timer(0.6f, true);
        super.start();
    }

    @Override
    public void Update(long currentTime, long deltaTime) throws Exception {
        super.Update(currentTime, deltaTime);
        setPosX(getX() + getDirH() * getSpeed());
        shotTimer.Update(deltaTime);
        if (getX() >= getLMaxH()){
            setPosX(getLMinH());
            if(this.getLifes() < this.getMaxLifes()){
                super.setLifes(this.getMaxLifes());
            }
            setSpeed(Params.getInstance().nextInt(5)+1);
            setPosY(getY() + getAltura());
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        Image im = AssetsManager.getInstance().getImage("regen.gif");
        if (im == null) {
            graphicsContext.setFill(Paint.valueOf("#007777"));
            graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        } else {
            graphicsContext.drawImage(im, getX(), getY(), getLargura(), getAltura());
        }
        DrawLifes(graphicsContext, 0, -5);
    }
}
