package Controllers.Helpers;

import Controllers.Enum.Config;
/**
 * @author Kasun Eranda - 12216898
 * Application Storage base functions goes here
 */
public class Storage {
    public static String getPath(String path) {
        return Config.STORAGE_PATH.getValue() + "/" + path;
    }
}
