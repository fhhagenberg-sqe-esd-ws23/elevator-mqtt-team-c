package at.fhhagenberg.sqelevator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Parser {
    public static final String DEFAULT_CONFIG_FILE = "./sqelevator.conf";
    public static final int DEFAULT_INTERVAL = 250;// ms
    public static final String DEFAULT_MQTT_PREFIX = "";
    public static final String KEY_INTERVAL ="interval";
    public static final String KEY_PLC ="plc";
    public static final String KEY_MQTT_ADDRESS ="mqtt_address";
    public static final String KEY_MQTT_PREFIX ="mqtt_prefix";
    Properties appProps = new Properties();
    
    public String parseArguments(String[] args) {
        String file = DEFAULT_CONFIG_FILE;
        if (args.length >= 2 && args[0].contentEquals("-config")) {
            file = args[1];
        }
        return file;
    }

    public void parseFile(InputStream configFile) throws IOException,IllegalArgumentException{

        appProps.load(configFile);

        if (!appProps.containsKey(KEY_PLC))
            throw new IllegalArgumentException();
        if (!appProps.containsKey(KEY_MQTT_ADDRESS))
            throw new IllegalArgumentException();
    }

    public int getInterval() {
        if (appProps.containsKey(KEY_INTERVAL))
            return Integer.parseInt(appProps.getProperty(KEY_INTERVAL));
        else
            return DEFAULT_INTERVAL;
    }

    public String getPlcAddress() {
        return appProps.getProperty(KEY_PLC);
    }

    public String getMqttAddress() {
        return appProps.getProperty(KEY_MQTT_ADDRESS);
    }

    public String getMqttPrefix() {
        if (appProps.containsKey(KEY_MQTT_PREFIX))
            return appProps.getProperty(KEY_MQTT_PREFIX);
        return DEFAULT_MQTT_PREFIX;
    }
}
// PLC="""
// interval=250[ms]
// mqtt_address=localhost
// mqtt_prefix=""
