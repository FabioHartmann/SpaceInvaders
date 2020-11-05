import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Spaceship extends Ball {
    private int fireDelay = 600;
    private long lastFiredTime = System.currentTimeMillis();

    public Spaceship(int px, int py) {
        super(px, py);
    }

    public void Update(){
        if (jaColidiu()){
            deactivate();
        }else{
            setPosX(getX() + getDirH() * getSpeed());
            if (System.currentTimeMillis() - lastFiredTime > fireDelay) {
                Game.getInstance().addChar(new Shot(getX()-16, getY()+getAltura(), 1, 0, 5));
                lastFiredTime = System.currentTimeMillis();
            }
            if (getX() >= getLMaxH()){
                setPosX(getLMinH());
                setSpeed(Params.getInstance().nextInt(5)+1);
            }
        }

    }

    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.drawImage(AssetsManager.getInstance().getImage("spaceship.gif"), getX(), getY(), getLargura(), getAltura());
    }


}
