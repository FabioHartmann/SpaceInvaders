import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

/**
 * Represents a shot that crosses the screen from bottom to up and then dismiss
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Shot extends BasicElement{
    private Character owner;

    public Shot(int px,int py, int dirV, int dirH, int speed, Character owner){
        super(px,py);

        setDirV(dirV);
        setDirH(dirH);
        setSpeed(speed);
        setLargAlt(3, 10);
        this.owner = owner;
    }

    public Character getOwner() {
        return owner;
    }

    @Override
    public void start(){ }
            
    @Override
    public void testaColisao(Character outro){
        // Não verifica colisão de um tiro com outro tiro
        if (outro instanceof Shot){
            return;
        }

        super.testaColisao(outro);
    }
                
    @Override
    public void Update(){
        if (jaColidiu()){
            deactivate();
        }else{
            setPosY(getY() + getDirV() * getSpeed());
            // Se chegou na parte superior da tela ...
            if (getY() <= getLMinV()){
                // Desaparece
                deactivate();
            }
        }
    }

    @Override
    public void Draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(Paint.valueOf("#00FF00"));
        graphicsContext.fillRect(getX(), getY(), getLargura(), getAltura());
    }
}

