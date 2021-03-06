package src;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import src.elements.Canhao;
import src.elements.Character;
import src.elements.FloatingPoint;
import src.elements.Particle;
import src.elements.enemies.Enemy;
import src.ui.UIManager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Handles the game lifecycle and behavior
 * @author Bernardo Copstein and Rafael Copstein
 */
public class Game {
    private static Game game = null;
    private Canhao canhao;
    private Timer particleSpawner;
    private List<Character> activeChars;
    private List<Upgrade> upgrades;
    private int score = 0;
    private boolean died = false;
    private long lastTimeDied = 0;
    private int wave = 0;
    private boolean paused = false;
    private Rank rank = Rank.createRank();
    
    private Game() {

    }
    
    public static Game getInstance(){
        if (game == null){
            game = new Game();
        }
        return(game);
    }

    public int getWave() {return wave;};
    
    public void addChar(Character c){
        activeChars.add(c);
        c.start();
    }
    
    public void eliminate(Character c){
        activeChars.remove(c);
    }

    private void onAllWaveKilled() {
        spawnWave();
    }

    public void onEnemyKilled(Enemy enemy) {
        if(!died){
            int pointsEarned = enemy.getMaxLifes() * 10;
            score += pointsEarned;
            if (getChars(Enemy.class).size() == 0) {
                onAllWaveKilled();
            }
            addChar(new FloatingPoint(enemy.getX(), enemy.getY(), pointsEarned));
        }
    }

    public void onEnemyReachEnd(Enemy enemy) throws Exception {
        if (died) return;
        eliminate(canhao);
        onDie();
    }

    public void onPlayerDamage() throws Exception {
        if (died) return;
        if (canhao.getLives() == 0) {
            onDie();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private void onDie() throws Exception {
        rank.changeRank(score);
        died = true;
        lastTimeDied = System.currentTimeMillis();
        canhao.deactivate();
        // Substituir pelos scores reais do player
        List<Score> scoreList = rank.getRanking().stream().map(it-> new Score(it)).collect(Collectors.toList());

        UIManager.getInstance().setScores(scoreList);

        UIManager.getInstance().setGameOverVisible(true);
    }

    public void Start() {
        // Inicializa o asset e audio manager
        AssetsManager.getInstance();
        // Carrega todos sons na memoria
        AudioManager.getInstance().setupPlayersOfAll(AssetsManager.getInstance().getSounds(), 5);

        // Repositório de personagens
        // Se utilizar LinkedList, dará problemas com o Update dos characters
        // CopyOnWrite é necessario
        activeChars = new CopyOnWriteArrayList<>();

        upgrades = new LinkedList<>();
        upgrades.add(new Upgrade("Upgrade de velocidade do canhao", 1, 500, (upgrade) -> {
            if (canhao != null) {
                canhao.setFireDelay(100);
            }
        }));
        upgrades.add(new Upgrade("Comprar 1 vida", 3, 250, (upgrade) -> {
            canhao.setLives(canhao.getLives() + 1);
        }));
        UIManager.getInstance().setUpgrades(upgrades);

        particleSpawner = new Timer(0.2f, true);
        particleSpawner.addHandler(loop -> {
            for (int i = 0; i < 5; i++) {
                addChar(new Particle((int)Math.floor(Math.random() * Params.GAME_WIDTH), (int)Math.floor(Math.random() * Params.GAME_HEIGHT), 0, 1, 5));
            }
        });

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

    private void resetUpgrades() {
        for(Upgrade upgrade : upgrades) upgrade.reset();
    }

    public void resetGame() {
        activeChars.clear();
        score = 0;
        wave = 0;

        died = false;

        // Adiciona o canhao
        canhao = new Canhao();
        addChar(canhao);

        resetUpgrades();

        spawnWave();
    }

    public void removeScore(int score) {
        this.score -= score;
    }

    public void Update(long currentTime, long deltaTime) throws Exception {
        if (paused) return;

        particleSpawner.Update(deltaTime);
        for(int i=0;i<activeChars.size();i++) {
            Character este = activeChars.get(i);
            este.resetColidindo();
        }
        for(int i=0;i<activeChars.size();i++){
            Character este = activeChars.get(i);
            if (!este.isColidivel()) continue;
            for(int j =0; j<activeChars.size();j++){
                Character outro = activeChars.get(j);
                if (!outro.isColidivel()) continue;
                if ( este != outro){
                    este.testaColisao(outro);
                }
            }
        }

        // Quando algum Character era removido da lista,
        // nao era realizado o Update do proximo
        // (LinkedList -> CopyOnWriteArrayList)
        // Nova implementacao:
        for(Character este : activeChars) {
            este.Update(currentTime, deltaTime);
        }
        // Implementacao antiga:
        /*for(int i=0;i<activeChars.size();i++){
            Character este = activeChars.get(i);
            este.Update(currentTime, deltaTime);
        }*/
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
