package at.fhhagenberg.sqelevator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.text.html.Option;


public class sqelevator {
    static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static void main(String[] args) {
        // Options options = new Options();
        // options.addOption("config",true,"configfile");
        // CommandLineParser parser = new DefaultParser();
        // CommandLine cmd = parser.parse(options, args);
        // Properties appProps = new Properties();
        // String file="./conf.prop";
        // if(args.length>=2&&args[0].contentEquals("-config"))
        // {
        //     file=args[1];
        // }
        // File configFile=new File(file);
        // if(!configFile.exists())
        // {
        //     System.err.print(configFile.getAbsolutePath()+" not found");
        //     System.exit(-1);
        // }
        // try {
        //     appProps.load(new FileInputStream(configFile));
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        //     System.exit(-1);
        // }
        Parser x=new Parser();
        x.pointer= (a,b)->System.out.println(a);
        x.setX(2);
	}
}
