package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigService {

    private static final String CONFIG = "config.cfg";

    public static String readFromConfig(String key) throws IOException {
        Properties prop = new Properties();
        InputStream is = new FileInputStream(CONFIG);
        prop.load(is);
        return prop.getProperty(key);
    }
}