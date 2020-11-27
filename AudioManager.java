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
        if (medias == null) return;
        for(Media media : medias) {
            setupPlayers(media, count);
        }
    }

    public void setVolume(double volume) {
        try {
            for(Stack<MediaPlayer> mps : players.values()) {
                for(MediaPlayer mp : mps) {
                    try {
                        mp.setVolume(volume);
                    }
                    catch (Exception exc) {
                        System.out.println("Error on volume set mps " + exc.getMessage());
                    }
                }
            }
        } catch (Exception exc) {
            System.out.println("Error on volume set " + exc.getMessage());
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
            try {
                MediaPlayer mp = new MediaPlayer(media);
                mp.setVolume(0.2);
                mp.setAutoPlay(false);
                mps.push(mp);
                mp.setOnEndOfMedia(() -> mps.push(mp));

                players.put(media, mps);
            }
            catch (Exception exc) {
                System.out.println("Failed setup audio player");
            }
        }
    }

    public void play(Media media) {
        if (media == null) return;
        Stack<MediaPlayer> mps;
        if (!players.containsKey(media)) setupPlayers(media, 5);

        mps = players.get(media);

        if (mps == null || mps.isEmpty()) return;

        MediaPlayer mp = mps.pop();

        try {
            mp.seek(mp.getStartTime());
            mp.play();
        } catch (Exception exc) {
            System.out.println("Failed play audio");
            mps.push(mp);
        }
    }
}
