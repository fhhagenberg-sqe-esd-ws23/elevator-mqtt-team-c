package at.fhhagenberg.sqelevator.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class FloorTest {
    @Test
    public void testGetFloorNumber() {
        Floor floor = new Floor(1);
        assertEquals(1, floor.getFloorNumber());
    }

    @Test
    public void testGetUpButton() {
        Floor floor = new Floor(1);
        assertNotNull(floor.getUpButton());
    }

    @Test
    public void testSetUpButtonPressed() {
        Floor floor = new Floor(1);
        floor.setUpButton(true);
        assertTrue(floor.getUpButton().isPressed());
    }

    @Test
    public void testSetUpButtonNotPressed() {
        Floor floor = new Floor(1);
        floor.setUpButton(false);
        assertFalse(floor.getUpButton().isPressed());
    }

    @Test
    public void testGetDownButton() {
        Floor floor = new Floor(1);
        assertNotNull(floor.getDownButton());
    }

    @Test
    public void testSetDownButtonPressed() {
        Floor floor = new Floor(1);
        floor.setDownButton(true);
        assertTrue(floor.getDownButton().isPressed());
    }

    @Test
    public void testSetDownButtonNotPressed() {
        Floor floor = new Floor(1);
        floor.setDownButton(false);
        assertFalse(floor.getDownButton().isPressed());
    }
}
