import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Particle extends BasicElement {

    private long spawnTime;

    public Particle(int px, int py, int dirV, int dirH, int speed) {
        setPosX(px);
        setPosY(py);
        setDirV(dirV);
        setDirH(dirH);
        setSpeed(speed);
        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void start(){
        setLargAlt(2, 2);
        setColidivel(false);
    }

    @Override
    public void Update() {
        setPosX(getX() + getSpeed() * getDirH());
        setPosY(getY() + getSpeed() * getDirV());
        if (System.currentTimeMillis() - spawnTime > 500) {
            deactivate();
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("#ffffff"));
        graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
    }
}
