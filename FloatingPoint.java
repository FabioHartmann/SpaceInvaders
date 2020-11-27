import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class FloatingPoint extends BasicElement {

    private int points;
    private long spawnTime;
    private long lastBlinkTime;
    private boolean isVisible;

    public FloatingPoint(int px, int py, int points) {
        setPosX(px);
        setPosY(py);
        this.points = points;
        spawnTime = System.currentTimeMillis();
        lastBlinkTime = spawnTime;
        isVisible = true;
    }

    @Override
    public void start(){
        setDirH(0);
        setDirV(-1);
        setSpeed(2);
        setColidivel(false);
    }

    @Override
    public void Update() {
        setPosX(getX() + getSpeed() * getDirH());
        setPosY(getY() + getSpeed() * getDirV());
        if (System.currentTimeMillis() - spawnTime > 500) {
            deactivate();
        }
        if (System.currentTimeMillis() - lastBlinkTime > 100) {
            isVisible = !isVisible;
            lastBlinkTime = System.currentTimeMillis();
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext) {
        if (isVisible) {
            graphicsContext.setFill(Paint.valueOf("#ffffff"));
            graphicsContext.setFont(Font.font(20));
            graphicsContext.fillText("+ " + points, getX(), getY());
        }
    }
}
