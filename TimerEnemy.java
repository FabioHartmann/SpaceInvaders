import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class TimerEnemy extends Enemy {
    private long spawnTime;
    private int secondsAlive;

    public TimerEnemy(int px, int py, int secondsAlive) {
        super(px, py);
        spawnTime = System.currentTimeMillis();
        this.secondsAlive = secondsAlive;
    }

    @Override
    public void Update() {
        super.Update();
        setPosX(getX() + getDirH() * getSpeed());
        if (getX() >= getLMaxH()){
            setPosX(getLMinH());
            setPosY(getY() + getAltura());
        }
        if (System.currentTimeMillis() - spawnTime > secondsAlive * 1000) {
            deactivate();
            for(int y=0; y < 3; y++){
                for(int x=0; x < 3; x++){
                    Game.getInstance().addChar(new Spaceship(getX() + x * 35 - 30, getY() + y * 35 - 30));
                }
            }
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
        graphicsContext.fillText((secondsAlive - (System.currentTimeMillis() - spawnTime) / 1000) + "s", getX(), getY());
    }
}
