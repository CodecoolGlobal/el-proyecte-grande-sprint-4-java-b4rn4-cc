package com.codecool.bankapp.datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Configuration {

    private static Properties props;

    public static void setupApp() {
        String configFileName = "application.properties";

        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String configPath = rootPath + configFileName;

        props = new Properties();

        try {
            props.load(new FileInputStream(configPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProps() {
        return props;
    }
}
