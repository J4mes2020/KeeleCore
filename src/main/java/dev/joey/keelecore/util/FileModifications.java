package dev.joey.keelecore.util;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.joey.keelecore.KeeleCore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileModifications {

    private final KeeleCore keeleCore;

    public FileModifications(KeeleCore keeleCore) {
        this.keeleCore = keeleCore;
    }

    public static String getPlayerFile() {

        return "players.yml";

    }

    public void createCustomConfig(String file) {

        try {
            YamlDocument.create(new File(keeleCore.getDataFolder(), file), keeleCore.getResource(file));
        } catch (IOException ignored) {

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
