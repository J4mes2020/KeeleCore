package dev.joey.keelecore.util;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.joey.keelecore.KeeleCore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileModifications {

    private final KeeleCore keeleCore;
    YamlDocument playerConfig;

    public FileModifications(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
    }

    public String getPlayerFile() {

        return "players.yml";

    }

    public void createCustomPlayerConfig() {

        try {
            playerConfig = YamlDocument.create(new File(keeleCore.getDataFolder(), getPlayerFile()), keeleCore.getResource(getPlayerFile()));
        } catch (IOException ignored) {

        }

    }

    public YamlDocument getCustomPlayerConfig() {

        System.out.println(playerConfig);

        return playerConfig;

    }

    public void saveCustomPlayerConfig(String file) {

        try {
            playerConfig.save();
        }
        catch (IOException ignored) {

        }


    }


    public static boolean isInFile(String string, String file) {

        try {

            return Files.lines(Paths.get(file)).anyMatch(line -> line.contains(string));
        } catch (IOException exception) {

            return false;

        }

    }

}
