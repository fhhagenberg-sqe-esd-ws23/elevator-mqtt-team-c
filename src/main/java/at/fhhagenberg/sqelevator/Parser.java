package at.fhhagenberg.sqelevator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Parser {
    public static String defaultConfigFile = "./sqelevator.conf";
    public static int defaultInterval = 250;// ms
    public static String defaultMqttPrefix = "";
    public static String keyInterval="interval"; 
    public static String keyPLC="plc"; 
    public static String keyMqttAddress="mqtt_address"; 
    public static String keyMqttPrefix="mqtt_prefix"; 
    Properties appProps = new Properties();
    
    public String parse(String[] args) {
        String file = defaultConfigFile;
        if (args.length >= 2 && args[0].contentEquals("-config")) {
            file = args[1];
        }
        return file;
    }

    public void Parse(InputStream configFile) throws IOException,IllegalArgumentException{

        appProps.load(configFile);

        // interval=Integer.parseInt(appProps.getProperty("interval"));
        if (!appProps.containsKey(keyPLC))
            throw new IllegalArgumentException();
        if (!appProps.containsKey(keyMqttAddress))
            throw new IllegalArgumentException();
    }

    public int getInterval() {
        if (appProps.containsKey(keyInterval))
            return Integer.parseInt(appProps.getProperty(keyInterval));
        else
            return defaultInterval;
    }

    public String getPlcAddress() {
        return appProps.getProperty(keyPLC);
    }

    public String getMqttAddress() {
        return appProps.getProperty(keyMqttAddress);
    }

    public String getMqttPrefix() {
        if (appProps.containsKey(keyMqttPrefix))
            return appProps.getProperty(keyMqttPrefix);
        return defaultMqttPrefix;
    }
}
// plc=""
// interval=250[ms]
// mqtt_address=localhost
// mqtt_prefix=""
