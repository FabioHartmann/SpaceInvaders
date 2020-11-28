package src;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AssetsManager {
    private static AssetsManager instance;

    public static AssetsManager getInstance() {
        if (instance == null) instance = new AssetsManager();
        return instance;
    }

    private String assetsPath = "assets/";
    private String imagesPath = "images/";
    private String soundsPath = "sounds/";
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
            URL url = getClass().getResource("/" + assetsPath + soundsPath);

            switch (url.getProtocol()) {
                case "jar": {
                    String assetSoundsPath = assetsPath + soundsPath;
                    for(String path : getPathsInsideJar(url, assetSoundsPath)) {
                        String filename = path.substring(assetSoundsPath.length());
                        try {
                            String externalURI = getClass().getResource("/" + path).toExternalForm();
                            Media media = new Media(externalURI);
                            sounds.put(filename, media);
                        }
                        catch (Exception exc) {
                            System.out.println("Failed load sound " + filename + " " + exc.getMessage());
                        }
                    }
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    JarFile file = connection.getJarFile();
                    break;
                }
                case "file": {
                    File dir = new File(url.getFile());
                    for (String filename : dir.list()) {
                        try {
                            String externalURI = getClass().getResource("/" + assetsPath + soundsPath + "/" + filename).toExternalForm();
                            Media media = new Media(externalURI);
                            sounds.put(filename, media);
                        } catch (Exception exc) {
                            System.out.println("Failed load sound " + filename + " " + exc.getMessage());
                        }
                    }
                    break;
                }
            }
        }
        catch (Exception exc) {
            System.out.println("Error on load sounds assets " + exc.getMessage());
        }
    }

    private void loadAllImages() {
        try {
            URL url = getClass().getResource("/" + assetsPath + imagesPath);

            switch (url.getProtocol()) {
                case "jar":
                    String assetImagesPath = assetsPath + imagesPath;
                    for(String path : getPathsInsideJar(url, assetImagesPath)) {
                        String filename = path.substring(assetImagesPath.length());
                        try {
                            Image img = new Image(path);
                            images.put(filename, img);
                        }
                        catch (Exception exc) {
                            System.out.println("Failed load image " + filename + " " + exc.getMessage());
                        }
                    }
                    break;
                case "file":
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
                    break;
            }
        }
        catch (Exception exc) {
            System.out.println("Error on load images assets " + exc.getMessage());
        }
    }

    private List<String> getPathsInsideJar(URL jar, String path) {
        List<String> paths = new LinkedList<>();
        try {
            JarURLConnection connection = (JarURLConnection) jar.openConnection();
            JarFile file = connection.getJarFile();
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (e.toString().startsWith(path)) {
                    if (e.toString().equals(path)) continue;
                    paths.add(e.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error on get paths inside jar file");
        }
        return paths;
    }
}
