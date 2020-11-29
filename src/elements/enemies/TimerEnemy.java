package src.elements.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import src.AssetsManager;
import src.Game;
import src.Timer;

public class TimerEnemy extends Enemy {
    private int time;
    private Timer timer;

    public TimerEnemy(int px, int py, int secondsAlive) {
        super(px, py, 4);
        timer = new Timer(1f, true);
        timer.addHandler(loop -> {
            time--;
            if (time == 0) {
                deactivate();
                int toSpawn = (int)Math.ceil((double)getLifes() / getMaxLifes() * 9);
                int spawned = 0;
                loop:
                for(int y=0; y < 3; y++){
                    for(int x=0; x < 3; x++){
                        Game.getInstance().addChar(new Spaceship(getX() + x * 35 - 30, getY() + y * 35 - 30));
                        spawned++;
                        if (toSpawn == spawned) break loop;
                    }
                }
            }
        });
        time = secondsAlive;
    }

    @Override
    public void Update(long currentTime, long deltaTime) {
        super.Update(currentTime, deltaTime);
        timer.Update(deltaTime);
        setPosX(getX() + getDirH() * getSpeed());
        if (getX() >= getLMaxH()){
            setPosX(getLMinH());
            setPosY(getY() + getAltura());
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        Image im = AssetsManager.getInstance().getImage("timerenemy.gif");
        if (im == null) {
            graphicsContext.setFill(Paint.valueOf("#ff0000"));
            graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        } else {
            graphicsContext.drawImage(im, getX(), getY(), getLargura(), getAltura());
        }
        graphicsContext.setFill(Paint.valueOf("#ff0000"));
        graphicsContext.setFont(Font.font(20));
        graphicsContext.fillText(time + "s", getX(), getY());
        graphicsContext.setFill(Paint.valueOf("#00ff00"));
        DrawLifes(graphicsContext, 0, -20);
    }
}
