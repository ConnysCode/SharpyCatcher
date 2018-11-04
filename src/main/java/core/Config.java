package core;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Config {

    private static Properties properties;
    static File file = new File("config.properties");

    //Hardcoded Vars
    public static String pokecordID = "365975655608745985";
    public static List<String> BOT_ADMINS = Collections.singletonList("");
    public static String SPAM_CHANNEL = "";
    public static List<String> ACTIVE_GUILDS = Collections.singletonList("");
    public static List<String> SPAM_WORDS = Collections.singletonList("");

    //Initialisiert die Datei -> In der Main ganz am Anfang aufrufen!
    public static void init(){
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            loadConfig();
        } catch (IOException e) {
            System.out.println("Config File '" + file.getName() + "' (" + file.getAbsolutePath() + ") not found");
            fileNotFoundAction(file);
        }
    }

    public static void loadConfig() {
        try {
            BOT_ADMINS =  Arrays.asList(properties.getProperty("bot_admins").split(","));
            SPAM_CHANNEL =  properties.getProperty("spam_channel");
            SPAM_WORDS =  Arrays.asList(properties.getProperty("spam_words").split(","));
            ACTIVE_GUILDS =  Arrays.asList(properties.getProperty("active_guilds").split(","));

            for (int i = 0; i < ACTIVE_GUILDS.size(); i++) {
                if (!propExist(ACTIVE_GUILDS.get(i) + "_pokecord_prefix")){
                    addKey(ACTIVE_GUILDS.get(i) + "_pokecord_prefix", "p!");
                }
            }

        } catch (Exception e) {

        }
    }

    //Gibt zurück ob ein Key existiert
    public static boolean propExist(String key) {
        if (properties.getProperty(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    //Gibt die Value von einem Key als String zurück
    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    //Fügt einen Key hinzu
    public static void addKey(String hash, String name) {
        properties.put(hash, name);
        try {
            properties.store(new FileOutputStream(file), "SharpyCatcher config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Erstellt Datei, falls nicht vorhanden
    private static void fileNotFoundAction(File f){
        properties.put("bot_admins", "289077956976967680,289077956976967680");
        properties.put("spam_words", "Run man.. RUN,GOTTA GO FAST!,mans not hot...,what are those?");
        properties.put("spam_channel", "");
        properties.put("active_guilds", "");
        properties.put("token", "Insert Token Here");
        try {
            properties.store(new FileOutputStream(f), "SharpyCatcher config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
