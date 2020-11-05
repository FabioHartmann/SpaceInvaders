import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Stack;

public class AudioManager {
    private static AudioManager instance;

    public static AudioManager getInstance() {
        if (instance == null) instance = new AudioManager();
        return instance;
    }

    private HashMap<Media, Stack<MediaPlayer>> players;

    private AudioManager() {
        players = new HashMap<>();
    }

    public void setupPlayersOfAll(Media[] medias, int count) {
        for(Media media : medias) {
            setupPlayers(media, count);
        }
    }

    public void setupPlayers(Media media, int count) {
        final Stack<MediaPlayer> mps;
        if (players.containsKey(media)) {
            mps = players.get(media);
        } else {
            mps = new Stack<>();
        }

        for(int i = 0; i < count; i++) {
            MediaPlayer mp = new MediaPlayer(media);
            mp.setVolume(0.2);
            mp.setAutoPlay(false);
            mps.push(mp);
            mp.setOnEndOfMedia(() -> mps.push(mp));
        }

        players.put(media, mps);
    }

    public void play(Media media) {
        if (media == null) return;
        Stack<MediaPlayer> mps;
        if (!players.containsKey(media)) setupPlayers(media, 5);

        mps = players.get(media);

        if (mps.isEmpty()) return;

        MediaPlayer mp = mps.pop();

        try {
            mp.seek(mp.getStartTime());
            mp.play();
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
            mps.push(mp);
        }
    }
}
