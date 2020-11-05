import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class AssetsManager {
    private static AssetsManager instance;

    public static AssetsManager getInstance() {
        if (instance == null) instance = new AssetsManager();
        return instance;
    }

    private HashMap<String, Image> images;
    private HashMap<String, Media> sounds;

    private AssetsManager() {
        images = new HashMap<>();
        sounds = new HashMap<>();

        try {
            loadAllImages();
            loadAllSounds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(String fileName) {
        if (images.containsKey(fileName)) {
            return images.get(fileName);
        }
        return null;
    }

    public Media getSound(String fileName) {
        if (sounds.containsKey(fileName)) {
            return sounds.get(fileName);
        }
        return null;
    }

    public Media[] getSounds() {
        return sounds.values().toArray(new Media[0]);
    }

    private void loadAllSounds() throws IOException {
        Path[] paths = getPathsOf("./assets/sounds");

        for(Path p : paths) {
            String fileName = p.getFileName().toString();
            String path = p.subpath(1, p.getNameCount()).normalize().toString();

            try {
                Media media = new Media(p.toUri().toString());

                sounds.put(fileName, media);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAllImages() throws IOException {
        Path[] paths = getPathsOf("./assets/images");

        for(Path p : paths) {
            String fileName = p.getFileName().toString();
            String path = p.subpath(1, p.getNameCount()).normalize().toString();

            images.put(fileName, new Image(path));
        }
    }

    private Path[] getPathsOf(String directoryName) throws IOException {
        return Files.list(Paths.get(directoryName)).toArray(Path[]::new);
    }
}
