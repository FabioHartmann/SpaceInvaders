import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.LinkedList;

/**
 * Handles the game lifecycle and behavior
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Game {
    private static Game game = null;
    private Canhao canhao;
    private List<Character> activeChars;
    private int score = 0;
    private boolean died = false;
    private long lastTimeDied = 0;
    
    private Game() {

    }
    
    public static Game getInstance(){
        if (game == null){
            game = new Game();
        }
        return(game);
    }
    
    public void addChar(Character c){
        activeChars.add(c);
        c.start();
    }
    
    public void eliminate(Character c){
        activeChars.remove(c);
    }

    public void onEnemyKilled() {
        score++;
        UIManager.getInstance().update();
    }

    public void onPlayerDamage() {
        UIManager.getInstance().update();
        if (canhao.getLives() == 0) {
            died = true;
            lastTimeDied = System.currentTimeMillis();
        }
    }

    public void Start() {
        AssetsManager.getInstance();
        AudioManager.getInstance().setupPlayersOfAll(AssetsManager.getInstance().getSounds(), 5);

        // Repositório de personagens
        activeChars = new LinkedList();
        
        resetGame();
    }

    public int getScore() {
        return score;
    }

    public int getLifes() {
        if (canhao == null) return 0;
        return canhao.getLives();
    }

    private void resetGame() {
        activeChars.clear();
        score = 0;

        died = false;

        // Adiciona o canhao
        canhao = new Canhao();
        addChar(canhao);

        for(int i=0; i<5; i++){
            addChar(new Ball(100+(i*10),60+i*35));
        }
        for(int i=5; i<10; i++){
            addChar(new Spaceship(100+(i*30),60+i*40));
        }

        UIManager.getInstance().update();
    }
    
    public void Update(long currentTime, long deltaTime) {
        if(died) {
            if (System.currentTimeMillis() - lastTimeDied > 3000) {
                resetGame();
                UIManager.getInstance().setGameOverVisible(false);
            } else {
                UIManager.getInstance().setGameOverVisible(true);
                canhao.deactivate();
            }
        }

        for(int i=0;i<activeChars.size();i++) {
            Character este = activeChars.get(i);
            este.resetColidindo();
        }
        for(int i=0;i<activeChars.size();i++){
            Character este = activeChars.get(i);
            for(int j =0; j<activeChars.size();j++){
                Character outro = activeChars.get(j);
                if ( este != outro){
                    este.testaColisao(outro);
                }
            }
        }

        for(int i=0;i<activeChars.size();i++){
            Character este = activeChars.get(i);
            este.Update();
        }
    }
    
    public void OnInput(KeyCode keyCode, boolean isPressed) {
        canhao.OnInput(keyCode, isPressed);
    }
    
    public void Draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf("000000"));
        graphicsContext.fillRect(0, 0, Params.GAME_WIDTH, Params.GAME_HEIGHT);
        for(Character c:activeChars){
            c.Draw(graphicsContext);
        }
    }
}
