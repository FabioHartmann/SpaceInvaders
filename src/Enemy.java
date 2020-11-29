package src;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class Enemy extends BasicElement {
    private int lifes;
    private int maxLifes;

    public Enemy(int px, int py, int lifes) {
        setPosX(px);
        setPosY(py);
        this.maxLifes = lifes;
        this.lifes = lifes;
    }

    public int getMaxLifes() {
        return maxLifes;
    }

    public int getLifes() {
        return lifes;
    }

    @Override
    public void start(){
        setDirH(1);
    }

    @Override
    public void Update(long currentTime, long deltaTime){
        if (isColidindo()){
            Character colidindoChar = getColidindoChar();

            // Inimigo é destruido apenas por contato com canhao ou shot de um canhao
            if ( colidindoChar instanceof Canhao ||
                (colidindoChar instanceof Shot &&
                ((Shot)colidindoChar).getOwner() instanceof Canhao)) {
                lifes--;
                if (lifes <= 0) {
                    AudioManager.getInstance().play(AssetsManager.getInstance().getSound("explosion1.mp3"));
                    deactivate();
                    Game.getInstance().onEnemyKilled(this);
                }
            }
        }
        if (getY() + getAltura() > Params.GAME_HEIGHT) {
            Game.getInstance().onEnemyReachEnd(this);
        }
    }

    public void DrawLifes(GraphicsContext graphicsContext, int xOffset, int yOffset) {
        graphicsContext.setFill(Paint.valueOf("#00ff00"));
        for (int i = 0; i < getLifes(); i++ ){
            graphicsContext.fillRect(getX() + xOffset + getLargura() / (double)getMaxLifes() * i, getY() + yOffset, getLargura() / (double)getMaxLifes() - 2, 3);
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#00ff00"));
        graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        DrawLifes(graphicsContext, 0, -5);
    }
}
