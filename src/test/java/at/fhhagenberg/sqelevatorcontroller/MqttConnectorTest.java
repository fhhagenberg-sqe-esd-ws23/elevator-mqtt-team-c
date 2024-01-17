package at.fhhagenberg.sqelevatorcontroller;


import at.fhhagenberg.sqelevator.MqttTopicGenerator;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.property.Listener;
import at.fhhagenberg.sqelevator.service.MqttService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

// import at.fhhagenberg.sqelevator.model.Building;
import at.fhhagenberg.sqelevator.model.Direction;
import at.fhhagenberg.sqelevator.service.ElevatorService;
import at.fhhagenberg.sqelevator.update.IUpdater;
import at.fhhagenberg.sqelevator.update.impl.BuildingUpdater;
import sqelevator.IElevator;


@ExtendWith(MockitoExtension.class)
class MqttConnectorTest {

    @Mock
    MqttService s;
    @Mock
    Building b;
    @Test
    void ConnectTest() throws Exception
    {

        MqttConnector uut=new MqttConnector(s);
        uut.connect(b);
        s.connect();
        verify(s,times(1)).connect();
//        subscribe(MqttTopicGenerator.elPath('+', MqttTopicGenerator.flPath('+',MqttTopicGenerator.FLOOR_BTN)), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.elPath('+',MqttTopicGenerator.CURRENT_FLOOR)), any(Listener.class));
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.ELEVATORS), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.FLOORS), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_UP)), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.flPath('+',MqttTopicGenerator.BTN_DOWN)), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DIRECTION)), Mockito.any());
        verify(s,times(1)).subscribe(eq(MqttTopicGenerator.elPath('+', MqttTopicGenerator.DOOR_STATE)), Mockito.any());
        uut.setDirection(0, Direction.DOWN);
        uut.setTargetFloor(0, 0);
        uut.setElevatorButton(0, 0, false);
        uut.setUpButton(0, false);

    }


    @Test
    void setDirectionTest() throws Exception
    {
        MqttConnector uut=new MqttConnector(s);
        
        uut.setDirection(0, Direction.DOWN);
        verify(s,times(1)).publish(eq(MqttTopicGenerator.elPath(0, MqttTopicGenerator.DIRECTION)), eq(Direction.DOWN.toString()));
        uut.setTargetFloor(0, 0);
        verify(s,times(1)).publish(eq(MqttTopicGenerator.elPath(0, MqttTopicGenerator.TARGET_FLOOR)), eq("0"));
        uut.setElevatorButton(0, 0, false);
        verify(s,times(1)).publish(eq(MqttTopicGenerator.elPath(0, MqttTopicGenerator.flPath(0, MqttTopicGenerator.FLOOR_BTN))), eq(String.valueOf(false)));
        uut.setUpButton(0, false);
        verify(s,times(1)).publish(eq(MqttTopicGenerator.flPath(0, MqttTopicGenerator.BTN_UP)), eq(String.valueOf(false)));
        uut.setDownButton(0, false);
        verify(s,times(1)).publish(eq(MqttTopicGenerator.flPath(0, MqttTopicGenerator.BTN_DOWN)), eq(String.valueOf(false)));
        

    }
    
    
}
