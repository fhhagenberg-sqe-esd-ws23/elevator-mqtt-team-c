package at.fhhagenberg.sqelevator;

public class Parser {
    public FunctionPointer pointer;
    Parser(){}
    void setX(int x){pointer.method(1, x);}

    int getInterval(){return 250;}
    // String getPlcAddress(return "";);
    // String getMqtt
}
//PLC="""
//interval=250[ms]
//mqtt_address=localhost
//mqtt_prefix=""
