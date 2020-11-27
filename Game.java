import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
    private int wave = 0;
    private boolean paused = false;
    
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

    private void onAllWaveKilled() {
        System.out.println("New wave " + wave);
        spawnWave();
    }

    public void onEnemyKilled() {
        score++;
        if (getChars(Enemy.class).size() == 0) {
            onAllWaveKilled();
        }
        UIManager.getInstance().update();
    }

    public void onEnemyReachEnd() {
        if (died) return;
        eliminate(canhao);
        onDie();
        UIManager.getInstance().update();
    }

    public void onPlayerDamage() {
        if (died) return;
        UIManager.getInstance().update();
        if (canhao.getLives() == 0) {
            onDie();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private void onDie() {
        died = true;
        lastTimeDied = System.currentTimeMillis();
        // Substituir pelos scores reais do player
        UIManager.getInstance().setScores(Arrays.asList(new Score[]{
                new Score(1),
                new Score(2),
                new Score(3),
                new Score(4),
                new Score(5),
                new Score(8),
                new Score(4444),
        }));
    }

    public void Start() {
        // Inicializa o asset e audio manager
        AssetsManager.getInstance();
        // Carrega todos sons na memoria
        AudioManager.getInstance().setupPlayersOfAll(AssetsManager.getInstance().getSounds(), 5);

        // Repositório de personagens
        activeChars = new LinkedList();

        // Inicia o jogo
        resetGame();
    }

    public <T> List<T> getChars(Class<T> clazz) {
        return activeChars.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public int getScore() {
        return score;
    }

    public int getLifes() {
        if (canhao == null) return 0;
        return canhao.getLives();
    }

    private void spawnWave() {
        List<Enemy> enemies = Waves.getWaveEnemies(wave);
        for (Enemy enemy : enemies) {
            addChar(enemy);
        }
        wave++;
    }

    private void resetGame() {
        activeChars.clear();
        score = 0;
        wave = 0;

        died = false;

        // Adiciona o canhao
        canhao = new Canhao();
        addChar(canhao);

        spawnWave();

        UIManager.getInstance().update();
    }

    public void Update(long currentTime, long deltaTime) {
        if (paused) return;
        if(died) {
            if (System.currentTimeMillis() - lastTimeDied > 5000) {
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
