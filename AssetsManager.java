import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.net.URL;
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

    private String assetsPath = "/assets";
    private String imagesPath = "/images";
    private String soundsPath = "/sounds";
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
        try {
            URL url = getClass().getResource(assetsPath + soundsPath);
            File dir = new File(url.getFile());
            for (String filename : dir.list()) {
                try {
                    String externalURI = getClass().getResource(assetsPath + soundsPath + "/" + filename).toExternalForm();
                    Media media = new Media(externalURI);
                    sounds.put(filename, media);
                }
                catch (Exception exc) {
                    System.out.println("Failed load sound " + filename + " " + exc.getMessage());
                }
            }
        }
        catch (Exception exc) {
            System.out.println("Error on load sounds assets " + exc.getMessage());
        }
    }

    private void loadAllImages() {
        try {
            URL url = getClass().getResource(assetsPath + imagesPath);
            File dir = new File(url.getFile());
            for (String filename : dir.list()) {
                try {
                    Image img = new Image(assetsPath + imagesPath + "/" + filename);
                    images.put(filename, img);
                }
                catch (Exception exc) {
                    System.out.println("Failed load image " + filename + " " + exc.getMessage());
                }
            }
        }
        catch (Exception exc) {
            System.out.println("Error on load images assets " + exc.getMessage());
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
