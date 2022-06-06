package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static ArrayList<String> listOfgames = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        GameProgress progressFirst = new GameProgress(1, 1, 1, 1.1);
        GameProgress progressSecond = new GameProgress(2, 2, 2, 2.2);
        GameProgress progressThird = new GameProgress(3, 3, 3, 3.3);

        File game1 = new File("C://Games//savegames//game1.dat");
        File game2 = new File("C://Games//savegames//game2.dat");
        File game3 = new File("C://Games//savegames//game3.dat");

        saveGame(game1.getAbsolutePath(), progressFirst);
        saveGame(game2.getAbsolutePath(), progressSecond);
        saveGame(game3.getAbsolutePath(), progressThird);

        zipFiles("C://Games//savegames//gameZip.zip", listOfgames);

        game1.delete();
        game2.delete();
        game3.delete();

    }

    static void saveGame(String directory, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(directory);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        listOfgames.add(directory);
    }

    static void zipFiles(String directory, ArrayList<String> listOfgames) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(directory))) {
            for (int i = 0; i < listOfgames.size(); i++) {
                FileInputStream fis = new FileInputStream(listOfgames.get(i));
                    ZipEntry entry = new ZipEntry("game" + (i + 1));
                        zout.putNextEntry(entry);
                            byte[] buffer = new byte[fis.available()];
                            fis.read(buffer);
                        zout.write(buffer);
                        zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

