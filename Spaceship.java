import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class Spaceship extends Enemy {
    private int fireDelay = 600;
    private long lastFiredTime = System.currentTimeMillis();

    public Spaceship(int px, int py) {
        super(px, py);
    }

    @Override
    public void Update() {
        super.Update();
        setPosX(getX() + getDirH() * getSpeed());
        if (System.currentTimeMillis() - lastFiredTime > fireDelay) {
            Game.getInstance().addChar(new Shot(getX()+16, getY()+getAltura(), 1, 0, 5, this));
            lastFiredTime = System.currentTimeMillis();
        }
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
    }
}
