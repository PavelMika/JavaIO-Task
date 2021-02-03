import java.io.*;
import java.util.ArrayList;

public class FileMain {

    public static void main(String[] args) {
        String directoryName = "E:/Конкурс на Onliner";
        File dir = new File(directoryName);
        String fileNameIn = ".\\src\\main\\java\\treeDirectory";
        try (FileWriter fileWriter = new FileWriter(fileNameIn);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            if (dir.exists() && dir.isDirectory()) {
                writeTreeDirectory(dir, bufferedWriter, 0);
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
        System.out.println("Количество папок - " + countOfFolders(listOfInfo(fileNameIn)));
        System.out.println("Количество файлов - " + countOfFiles(listOfInfo(fileNameIn)));
        System.out.println("Среднее количество файлов в папке - " + averageAmountOfFilesInFolders(listOfInfo(fileNameIn)));
        System.out.println("Средняя длина названия файла - " + averageLengthOfFileNames(listOfInfo(fileNameIn)));
    }

    private static void writeTreeDirectory(File directory, BufferedWriter bufferedWriter, int level) {
        try {
            File[] files = directory.listFiles();
            if (level == 0) bufferedWriter.write(directory.getName() + "\n");
            if (files != null) {
                for (File f : files) {
                    bufferedWriter.write("    | ");
                    if (f.isDirectory()) {
                        for (int i = 0; i < level; i++) {
                            bufferedWriter.write("--");
                        }
                        bufferedWriter.write("[" + f.getName() + "]" + "\n");
                        writeTreeDirectory(f, bufferedWriter, level + 1);
                    } else {
                        for (int i = 0; i < level; i++) {
                            bufferedWriter.write("   ");
                        }
                        bufferedWriter.write(f.getName() + "\n");
                    }
                }
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    private static ArrayList<String> listOfInfo(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
        return list;
    }

    private static int countOfFolders(ArrayList<String> list) {
        int count = 0;
        for (String text : list) {
            if (text.contains("[") && text.contains("]")) {
                count++;
            }
        }
        return count;
    }

    private static int countOfFiles(ArrayList<String> list) {
        int count = 0;
        for (String text : list) {
            if (text.contains(".")) {
                count++;
            }
        }
        return count;
    }

    private static double averageAmountOfFilesInFolders(ArrayList<String> list) {
        return (countOfFiles(list) / (double) countOfFolders(list));
    }

    private static double averageLengthOfFileNames(ArrayList<String> list) {
        String fileName1;
        int count = 0;
        for (String fileName : list) {
            if (fileName.contains(".")) {
                fileName1 = fileName.replaceAll(" ", "");
                count += fileName1.length() - 1;
            }
        }
        return count / (double) countOfFiles(list);
    }
}

