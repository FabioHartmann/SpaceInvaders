import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class Enemy extends BasicElement {
    private int fireDelay = 600;
    private long lastFiredTime = System.currentTimeMillis();

    public Enemy(int px, int py) {
        setPosX(px);
        setPosY(py);
    }

    @Override
    public void start(){
        setDirH(1);
    }

    @Override
    public void Update(){
        if (isColidindo()){
            Character colidindoChar = getColidindoChar();
            // Se colidir com um enemy, ignora a colisao
            if (colidindoChar instanceof Enemy) return;
            // Se colidir com um shot de outro inimigo, ignora a colisao
            if (colidindoChar instanceof Shot) {
                Shot shot = (Shot)colidindoChar;
                if (shot.getOwner() instanceof Enemy) return;
            }
            AudioManager.getInstance().play(AssetsManager.getInstance().getSound("explosion1.mp3"));
            deactivate();
            Game.getInstance().onEnemyKilled();
        }
        if (getY() + getAltura() > Params.GAME_HEIGHT) {
            Game.getInstance().onEnemyReachEnd();
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#00ff00"));
        graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
    }
}
