package src.elements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import src.*;

/**
 * Represents the game Gun
 * @author Bernardo Copstein, Rafael Copstein
 */
public class Canhao extends BasicElement implements KeyboardCtrl {
    private boolean pressingLeft = false;
    private boolean pressingRight = false;
    private boolean firing = false;
    private int lives = 3;
    private int fireDelay = 300;
    private long lastFiredTime = System.currentTimeMillis();
    
    @Override
    public void start() {
        setLargAlt(32, 32);
        setSpeed(4);
        setPosY(Params.GAME_HEIGHT-getAltura());
        setLimV(Params.GAME_HEIGHT-getAltura(),Params.GAME_HEIGHT);
    }

    private void move(int xDelta, int yDelta) {
        setPosX(Math.min(Math.max(getLMinH(), getX() + xDelta), getLMaxH() - getLargura()));
        setPosY(Math.min(Math.max(getLMinV(), getY() + yDelta), getLMaxV() - getAltura()));
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setFireDelay(int fireDelay) {
        this.fireDelay = fireDelay;
    }

    @Override
    public void Update(long currentTime, long deltaTime) throws Exception {
        if(isColidindo()) {
            setLives(lives-1);
            AudioManager.getInstance().play(AssetsManager.getInstance().getSound("killed.mp3"));
            Game.getInstance().onPlayerDamage();
        }
        if (pressingLeft) move(-getSpeed(), 0);
        if (pressingRight) move(getSpeed(), 0);

        if (firing && System.currentTimeMillis() - lastFiredTime > fireDelay) {
            Game.getInstance().addChar(new Shot(getX() + 16, getY() - 16, -1, 0, 15, this));
            AudioManager.getInstance().play(AssetsManager.getInstance().getSound("shoot1.mp3"));
            lastFiredTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        switch (keyCode) {
            case LEFT:
                pressingLeft = isPressed;
                break;
            case RIGHT:
                pressingRight = isPressed;
                break;
            case SPACE:
                firing = isPressed;
                break;
        }
        //if (keyCode == KeyCode.UP) do nothing
        //if (keyCode == KeyCode.DOWN) do nothing
    }
    
    @Override
    public void Draw(GraphicsContext graphicsContext) {
        Image im = AssetsManager.getInstance().getImage("player.png");
        if (im == null) {
            graphicsContext.setFill(Paint.valueOf("#00ff00"));
            graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
        } else {
            graphicsContext.drawImage(im, getX(), getY(), getLargura(), getAltura());
        }
    }
}
