public class ParticleSpawner {
    private long lastTimeSpawn;
    private int delay;
    private int particlesPerSpawn;

    public ParticleSpawner(int delay, int particlesPerSpawn) {
        lastTimeSpawn = System.currentTimeMillis();
        this.delay = delay;
        this.particlesPerSpawn = particlesPerSpawn;
    }

    public void Update() {
        if (System.currentTimeMillis() - lastTimeSpawn >= delay) {
            for (int i = 0; i < particlesPerSpawn; i++) {
                Particle particle = new Particle((int)Math.floor(Math.random() * Params.GAME_WIDTH), (int)Math.floor(Math.random() * Params.GAME_HEIGHT), 0, 1, 5);
                Game.getInstance().addChar(particle);
            }
            lastTimeSpawn = System.currentTimeMillis();
        }
    }
}
