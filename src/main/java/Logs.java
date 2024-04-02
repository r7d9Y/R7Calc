import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rand7Y9Z@gmail.com
 * @since 2024
 */

public class Logs {

    private static final String logFile = "logs/calcLogs.txt";
    private static final String resultsLog = "logs/resultsLog.txt";

    /**
     * manages log files for the application
     * checks and creates necessary directories and files
     * caps log file size if necessary
     */
    protected static void manageLogs() {
        try {
            if (!Files.exists(Paths.get(logFile.split("/")[0]))) {
                Files.createDirectory(Paths.get(logFile.split("/")[0]));
            }

            if (!Files.exists(Paths.get(logFile))) {
                Files.createFile(Paths.get(logFile));
                writeToLog("Log file has been created");
            }

            if (!Files.exists(Paths.get(resultsLog))) {
                Files.createFile(Paths.get(resultsLog));
                writeToLog("Results log file has been created");
            }

            capLogs();

        } catch (Exception ignored) {
        }


    }

    /**
     * writes a given log message to the log file
     * the log message is appended to the end of the log file
     * the current time is inserted infront of the log message
     *
     * @param log the log message to be written to the log file
     */
    protected static void writeToLog(String log) {
        try {
            BufferedWriter out = Files.newBufferedWriter(Paths.get(logFile), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            out.write(getCurrentTime() + log + System.lineSeparator());
            out.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * writes a given result to the results log file
     *
     * @param res the result to be written to the results log file
     */
    protected static void writeToResults(String res) {
        try {
            BufferedWriter out = Files.newBufferedWriter(Paths.get(resultsLog), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            out.write(res + System.lineSeparator());
            out.close();
            writeToLog("result has been successfully written to results log");
        } catch (Exception e) {
            writeToLog("result could not be written to results log -> " + e.getMessage());
        }
    }

    /**
     * gets the current time in the format [hh:mm:ss]
     *
     * @return a string representing the current time
     */
    private static String getCurrentTime() {
        LocalTime t = LocalTime.now();
        return String.format("[%2d:%02d:%02d] ", t.getHour(), t.getMinute(), t.getSecond());
    }

    /**
     * checks the size of the log file
     * if the size of the log file is greater than or equal to 10240 bytes, the log file is cut in half
     * the first half of the log file is deleted and the second half is kept
     */
    protected static void capLogs() throws IOException {
        long size = Files.size(Paths.get(logFile));

        if (size >= 51200) {
            byte[] content = Files.readAllBytes(Paths.get(logFile));
            Files.delete(Paths.get(logFile));
            Files.createFile(Paths.get(logFile));
            writeToLog("Log file has been cut in size");
            OutputStream out = Files.newOutputStream(Paths.get(logFile), StandardOpenOption.APPEND);
            out.write(content, content.length / 2, content.length / 2);
        }
    }

    /**
     * deletes and recreates the log file and the results log file
     */
    protected static void resetLogs() {
        try {
            Files.delete(Paths.get(logFile));
            Files.createFile(Paths.get(logFile));
            Files.delete(Paths.get(resultsLog));
            Files.createFile(Paths.get(resultsLog));
            writeToLog("Log files have been cleared");
        } catch (IOException e) {
            writeToLog("Log file could not be reset -> " + e.getMessage());
        }
    }

    /**
     * reads the log file and returns its content as a list of strings
     *
     * @return a list of strings representing the content of the log file
     */
    protected static List<String> readResultsAndErrors() throws IOException{
            return Files.readAllLines(Paths.get(resultsLog));
    }

    /**
     * reads the results log file and returns its content as a string
     * the method filters the content of the log file to only include lines that contain " = "
     * each line in the log file is appended to the returned string
     * if an IOException occurs during the reading of the log file an error message is written to the log file
     *
     * @return a string representing the content of the results log file
     */
    protected static String readResults(){
        StringBuilder ret = new StringBuilder();
        try {
            String[] s = readResultsAndErrors().stream().filter(e -> e.contains(" = ")).toArray(String[]::new);
            for (String value : s) ret.append(value).append(System.lineSeparator());
        }catch (IOException e){
            writeToLog("results log could not be read -> " + e.getMessage());
        }
        return ret.isEmpty() ? "There is no history of results to be shown" : ret.toString();
    }

    /**
     * reads the results log file and returns its content as a string
     * the method filters the content of the log file to exclude lines that contain " = "
     * each line in the log file is appended to the returned string
     * if an IOException occurs during the reading of the log file an error message is written to the log file
     *
     * @return a string representing the content of the results log file
     */
    protected static String readResultsErrors(){
        StringBuilder ret = new StringBuilder();
        try {
            String[] s = readResultsAndErrors().stream().filter(e -> !e.contains(" = ")).toArray(String[]::new);
            for (String value : s) ret.append(value).append(System.lineSeparator());
        }catch (IOException e){
            writeToLog("results log could not be read -> " + e.getMessage());
        }
        return ret.isEmpty() ? "There is no history of errors to be shown" : ret.toString();
    }


}