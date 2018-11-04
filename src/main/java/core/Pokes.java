package core;

import java.io.*;
import java.util.Properties;

public class Pokes {
    private static Properties properties;
    static File file = new File("pokes.properties");

    //Initialisiert die Datei -> In der Main ganz am Anfang aufrufen!
    public static void init(){
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            //load();
        } catch (IOException e) {
            System.out.println("Pokémon Database '" + file.getName() + "' (" + file.getAbsolutePath() + ") not found");
            fileNotFoundAction(file);
        }
    }

    //Gibt zurück ob ein Key existiert
    @Deprecated
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
    @Deprecated
    public static void addKey(String hash, String name) {
        properties.put(hash, name);
        try {
            properties.store(new FileOutputStream(file), "SharpyCatcher Pokémon Database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Erstellt Datei, falls nicht vorhanden
    private static void fileNotFoundAction(File f){
        properties.put("initial", "initial");
        try {
            properties.store(new FileOutputStream(f), "SharpyCatcher Pokémon Database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
