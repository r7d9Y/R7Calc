import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rand7Y9Z@gmail.com
 * @since 2024
 */

public class Config {
    private static String configFilePath = getConfigFilePath();

    /**
     * sets the path to the config file
     * @param path the path to the config file
     */
    protected static void setConfigFilePath(String path) {
        try {
            Files.delete(Path.of("config/configFilePath.txt"));
            Files.createFile(Path.of("config/configFilePath.txt"));
            Files.writeString(Path.of("config/configFilePath.txt"), path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            configFilePath = path;
        } catch (IOException e) {
            Logs.writeToLog("Couldn't set config file path -> " + e.getMessage());
        }
        manageConfig();
    }

    /**
     * resets the path to the config file to the default value
     */
    protected static void resetConfigFilePath() {
        try {
            Files.writeString(Path.of("config/configFilePath.txt"), "config/config.csv", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            configFilePath = "config/config.csv";
        } catch (IOException e) {
            Logs.writeToLog("Couldn't reset config file path -> " + e.getMessage());
        }
        manageConfig();
    }

    /**
     * gets the path to the config file from the file configFilePath.txt
     * defaults to "config/config.csv"
     * @return
     */
    protected static String getConfigFilePath() {
        try {
            if (!Files.exists(Path.of("config/configFilePath.txt"))) {
                Files.writeString(Path.of("config/configFilePath.txt"), "config/config.csv", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }

            return Files.readString(Path.of("config/configFilePath.txt"));
        } catch (IOException e) {
            Logs.writeToLog("Couldn't read or find config file -> " + e.getMessage());
            return "config/config.csv";
        }
    }

    /**
     * manages the config file
     * reads in the config file and applies the settings
     * if the config file is not found or can't be read, default values are applied
     */
    protected static void manageConfig() {


        try {
            readInConfigFile(readInCSV(configFilePath));
            Logs.writeToLog("Config file has been read in and applied successfully");
            Logs.writeToLog(String.format("%s %.1f / %.1f ready to start ", Config.getName(), Config.getGUIVersion(), Config.getCalculatorVersion()));

        } catch (IOException e) {
            Logs.writeToLog("Couldn't read or find config file- > " + e.getMessage());
            String[][] d = {
                    {"update time", "500"},
                    {"default for deg/rad", "1"},
                    {"background color", "75", "75", "75"},
                    {"background2 color", "128", "128", "128"},
                    {"background3 color", "150", "150", "150"},
                    {"accent color", "66", "99", "110"},
                    {"font color", "255", "255", "255"},
                    {"height&width", "550", "430", ""},
                    {"_", "", "", ""},
                    {"_", "", "", ""},
                    {"_", "", "", ""},
                    {"_", "", "", ""},
                    {"GUI Version", "0"},
                    {"Calculator Version", "0"},
                    {"Program name", "name"}
            };
            try {
                readInConfigFile(d);
                Logs.writeToLog("default values have been applied for configuration");
            } catch (IOException ex) {
                Logs.writeToLog("couldn't apply default values to configuration -> " + ex.getMessage());
            }

        }

    }

    private static int updateTime = 100;
    private static int isDeg = 1;
    private static int[] bgColor = new int[3];
    private static int[] bgColor2 = new int[3];
    private static int[] bgColor3 = new int[3];
    private static int[] acColor = new int[3];
    private static int[] fontColor = new int[3];
    private static int[] heightAndWidth = new int[2];
    private static double GUIVersion;
    private static double CalculatorVersion;
    private static String ProgramName;

    /**
     * this methode reads in the config file, namely config/config.cvs, for some specifications (e.g. colors,
     * update time of the GUI and Versions)
     */
    protected static void readInConfigFile(String[][] data) throws IOException {
        for (String[] s : data) {
            switch (s[0]) {
                case "update time" -> updateTime = Integer.parseInt(remove(s[1], ' '));
                case "default for deg/rad" -> isDeg = Integer.parseInt(s[1]) == 1 ? 1 : 0;
                case "background color" ->
                        bgColor = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])};
                case "background2 color" ->
                        bgColor2 = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])};
                case "background3 color" ->
                        bgColor3 = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])};
                case "accent color" ->
                        acColor = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])};
                case "font color" ->
                        fontColor = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])};
                case "height&width" -> heightAndWidth = new int[]{Integer.parseInt(s[1]), Integer.parseInt(s[2])};
                case "GUI Version" -> GUIVersion = Double.parseDouble(s[1]);
                case "Calculator Version" -> CalculatorVersion = Double.parseDouble(s[1]);
                case "Program name" -> ProgramName = s[1];
            }
        }
    }

    /**
     * reads a csv file and returns its data as a 2d string array
     *
     * @param filePath the path to the csv file
     * @return a 2d string array with the csv file data
     */
    protected static String[][] readInCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
            Logs.writeToLog("file has been read in successfully [.csv]");
        } catch (IOException e) {
            Logs.writeToLog("Error while trying to read in file [.csv]");
        }
        return data.toArray(new String[0][]);
    }


    /**
     * Removes all occurrences of a specified character from a string
     *
     * @param s The input string
     * @param c The character to be removed
     * @return The string with all occurrences of the character removed
     */
    protected static String remove(String s, char c) {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != c) r.append(s.charAt(i));
        }
        return r.toString();
    }

    /**
     * these are the getter methods for the config class they provide access to the following properties
     * - update time the time interval at which the gui updates
     * - degree mode a boolean indicating if the calculator is in degree mode
     * - background colors three different background colors used in the gui each represented as an array of rgb values
     * - accent color the accent color used in the gui represented as an array of rgb values
     * - font color the color of the font used in the gui represented as an array of rgb values
     * - gui version the version number of the gui
     * - calculator version the version number of the Cal.java program (the one that actually calculates the results)
     * - program name the name of the program
     * - height and width -> the height and width of the gui
     */
    protected static int getUpdateTime() {
        return updateTime;
    }

    protected static boolean getIsDeg() {
        return isDeg == 1;
    }

    //------------------------------------------------------------------------------------------------------------------

    protected static int[] getBgColor() {
        return bgColor;
    }

    protected static int[] getBgColor2() {
        return bgColor2;
    }

    protected static int[] getBgColor3() {
        return bgColor3;
    }

    protected static int[] getAcColor() {
        return acColor;
    }

    protected static int[] getFontColor() {
        return fontColor;
    }

    //------------------------------------------------------------------------------------------------------------------
    protected static int[] getHeightAndWidth() {
        return heightAndWidth;
    }

    protected static double getGUIVersion() {
        return GUIVersion;
    }

    protected static double getCalculatorVersion() {
        return CalculatorVersion;
    }

    protected static String getName() {
        return ProgramName;
    }


}