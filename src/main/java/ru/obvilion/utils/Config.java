package ru.obvilion.utils;

import arc.util.Log;
import ru.obvilion.HubPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class Config {
    public final String filePath = "config.properties";
    public final File configFile = new File(HubPlugin.pluginDir, filePath);

    public void init() {
        if (!configFile.exists()) {
            HubPlugin.pluginDir.mkdir();

            generate();

            Log.info("The config file for ObvilionHub was successfully generated.");
            Log.info("Configure it in " + configFile.getPath());
        }
    }

    public void generate() {
        try {
            InputStream in = Config.class.getClassLoader().getResourceAsStream(filePath);
            OutputStream out = new FileOutputStream(this.configFile);

            int data;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String value) {
        final Properties prop = new Properties();
        String out = "CONFIG_ERROR";

        try {
            final FileInputStream fileInputStream = new FileInputStream(this.configFile);
            prop.load(fileInputStream);

            out = prop.getProperty(value);

            /* Changing the string encoding to UTF-8 */
            out = new String(
                 out.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    public ArrayList<String[]> getAll() {
        final Properties prop = new Properties();
        final ArrayList<String[]> out = new ArrayList<>();

        try {
            final FileInputStream fileInputStream = new FileInputStream(this.configFile);
            prop.load(fileInputStream);

            final Enumeration keySet = prop.keys();

            while(keySet.hasMoreElements()) {
                String keyName = (String) keySet.nextElement();
                String keyValue = prop.getProperty(keyName);

                /* Changing the string encoding to UTF-8 */
                keyValue = new String(
                    keyValue.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8
                );

                out.add(new String[] {
                     keyName, keyValue
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }
}
