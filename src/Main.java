import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        String path = "Z://Games";
        String pathScr = "Z://Games/scr";
        String pathMain = "Z://Games/scr/main";
        String pathRes = "Z://Games/res";
        String pathTemp = "Z://Games/temp";

        StringBuilder str = new StringBuilder();
        str.append("Создание файлов игры:\n")
                .append("Создание директории scr: \n").append(newDir(path, "scr"))
                .append("\nСоздание директории res: \n").append(newDir(path, "res"))
                .append("\nСоздание директории saveGames: \n").append(newDir(path, "saveGames"))
                .append("\nСоздание директории temp: \n").append(newDir(path, "temp"))
                .append("\nСоздание директории main: \n").append(newDir(pathScr, "main"))
                .append("\nСоздание файла Main.java: \n").append(newFile(pathMain, "Main.java"))
                .append("\nСоздание файла Main.java: \n").append(newFile(pathMain, "Utils.java"))
                .append("\nСоздание директории test: \n").append(newDir(pathScr, "test"))
                .append("\nСоздание директории drawables: \n").append(newDir(pathRes, "drawables"))
                .append("\nСоздание директории vectors: \n").append(newDir(pathRes, "vectors"))
                .append("\nСоздание директории icons: \n").append(newDir(pathRes, "icons"))
                .append("\nСоздание файла temp.txt: \n").append(newFile(pathTemp, "temp.txt"));

        try (FileWriter writer = new FileWriter(pathTemp + "/temp.txt")) {
            writer.write(str.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameProgress gameProgress1 = new GameProgress(100, 1, 1, 100);
        GameProgress gameProgress2 = new GameProgress(200, 2, 2, 200);
        GameProgress gameProgress3 = new GameProgress(300, 3, 3, 300);

        String pathSaveGames = "Z://Games/saveGames/";
        String pathGP1 = pathSaveGames + "gameProgress1.dat";
        String pathGP2 = pathSaveGames + "gameProgress2.dat";
        String pathGP3 = pathSaveGames + "gameProgress3.dat";

        if (saveGame(pathGP1, gameProgress1)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }
        if (saveGame(pathGP2, gameProgress2)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }
        if (saveGame(pathGP3, gameProgress3)) {
            System.out.println("Игра сохранена");
        } else {
            System.out.println("Произошла ошибка при сохранении");
        }

        String pathSaveZip = pathSaveGames + "save.zip";
        if (zipFiles(pathSaveZip,
                pathGP1,
                pathGP2,
                pathGP3)) {
            System.out.println("Файлы сохранений заархивированы");
        } else {
            System.out.println("Произошла ошибка архивации");
        }

        openZip(pathSaveZip, pathSaveGames);
        System.out.println(openProgress(pathGP1));

    }

    private static boolean newFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        boolean newFileCreated = false;
        try {
            newFileCreated = file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileCreated;
    }

    private static boolean newDir(String dirPath, String newDirName) {
        File file = new File(dirPath, newDirName);
        boolean newDirCreated = false;
        try {
            newDirCreated = file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDirCreated;
    }

    public static boolean saveGame(String dirPath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(dirPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean zipFiles(String dirPath, String... args) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dirPath))) {
            for (String filePath : args) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(file.getName());
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                fis.close();
                zos.closeEntry();
            }
            deleteZipedFiles(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void deleteZipedFiles(String... args) {
        for (String filePath : args) {
            new File(filePath).delete();
        }
    }

    public static void openZip(String filePath, String outputDirPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(outputDirPath + "/" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }

}
