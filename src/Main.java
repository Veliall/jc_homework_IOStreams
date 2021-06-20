import java.io.*;

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

}
