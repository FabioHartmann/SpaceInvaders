import javafx.scene.image.Image;
import javafx.scene.media.Media;

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

        loadAllImages();
        loadAllSounds();
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

    private void loadAllSounds() {
        Path[] paths = getPathsOf("./assets/sounds");
        if (paths == null) return;

        for(Path p : paths) {
            try {
                if (p == null || p.toUri() == null) continue;
                String fileName = p.getFileName().toString();
                String path = p.subpath(1, p.getNameCount()).normalize().toString();

                Media media = new Media(p.toUri().toString());

                sounds.put(fileName, media);
            }
            catch (Exception e) {
                System.out.println("Failed load sound " + p.toUri().toString());
            }
        }
    }

    private void loadAllImages() {
        Path[] paths = getPathsOf("./assets/images");
        if (paths == null) return;

        for(Path p : paths) {
            if (p == null || p.toUri() == null) continue;
            try {
                String fileName = p.getFileName().toString();
                String path = p.subpath(1, p.getNameCount()).normalize().toString();

                Image image = new Image(path);
                if (image.isError()) continue;

                images.put(fileName, image);
            }
            catch (Exception exc) {
                System.out.println("Failed load image " + p.toUri().toString());
            }
        }
    }

    private Path[] getPathsOf(String directoryName) {
        if (directoryName == null) return null;
        try {
            return Files.list(Paths.get(directoryName)).toArray(Path[]::new);
        }
            catch (Exception exc) {
            System.out.println("Failed get paths of " + directoryName);
        }
        return null;
    }
}
