/**
 * @author Rand7Y9Z@gmail.com
 * @since 2024
 */

public class Main {

    public static void main(String[] args) {
        Logs.manageLogs();
        Config.manageConfig();

        try {
            new GUI();
        } catch (RuntimeException er) {
            Logs.writeToLog("could not start GUI -> " + er.getMessage());
        }
    }

    public static String calInput;
    public static boolean isDEG = Config.getIsDeg();
}
