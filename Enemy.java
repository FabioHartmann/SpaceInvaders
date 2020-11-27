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

            // Inimigo é destruido apenas por contato com canhao ou shot de um canhao
            if ( colidindoChar instanceof Canhao ||
                (colidindoChar instanceof Shot &&
                ((Shot)colidindoChar).getOwner() instanceof Canhao)) {
                AudioManager.getInstance().play(AssetsManager.getInstance().getSound("explosion1.mp3"));
                deactivate();
                Game.getInstance().onEnemyKilled(this);
            }
        }
        if (getY() + getAltura() > Params.GAME_HEIGHT) {
            Game.getInstance().onEnemyReachEnd(this);
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#00ff00"));
        graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
    }
}
