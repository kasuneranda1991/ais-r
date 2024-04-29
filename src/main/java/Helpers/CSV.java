package Helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;

/**
 * @author kasun eranda - 12216898
 *         Application CSV maniputation
 */
public class CSV {
    public static void store(String path, String data) throws IOException {
        write(path, data, false);
    }

    public static void setHeading(String path, String heading) throws IOException {
        write(path, heading, true);
    }

    public static void createFile(String filename) throws IOException {
        File file = new File("src/main/java/Storage/" + filename);
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }
    }

    private static void write(String path, String data, Boolean doOverwrite) throws IOException {
        FileWriter pw = new FileWriter(Storage.getPath(path), !doOverwrite);
        BufferedWriter bw = new BufferedWriter(pw);
        bw.write(data);
        bw.newLine();
        bw.close();
    }

    public static CsvReader<CsvRecord> read(String fileName) {
        // CsvReader<CsvRecord> csv;
        try {
            Path p = Paths.get(Storage.getPath(fileName));
            CsvReader<CsvRecord> csv = CsvReader.builder().ofCsvRecord(p);
            return csv;
            // for (final CsvRecord csvRecord : csv) {
            // // System.out.println(csvRecord.getFields());
            // System.out.println(csvRecord.getField(5));
            // }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static Boolean isFileExists(String path) throws IOException {
        return new File(Storage.getPath(path)).isFile();
    }

}
