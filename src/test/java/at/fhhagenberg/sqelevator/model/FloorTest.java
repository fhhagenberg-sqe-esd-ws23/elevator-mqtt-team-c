package at.fhhagenberg.sqelevator.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class FloorTest {
  @Test
  void testGetFloorNumber() {
    Floor floor = new Floor(1);
    assertEquals(1, floor.getFloorNumber());
  }

  @Test
  void testGetUpButton() {
    Floor floor = new Floor(1);
    assertNotNull(floor.getUpButton());
  }

  @Test
  void testSetUpButtonPressed() {
    Floor floor = new Floor(1);
    floor.setUpButton(true);
    assertTrue(floor.getUpButton().isPressed());
  }

  @Test
  void testSetUpButtonNotPressed() {
    Floor floor = new Floor(1);
    floor.setUpButton(false);
    assertFalse(floor.getUpButton().isPressed());
  }

  @Test
  void testGetDownButton() {
    Floor floor = new Floor(1);
    assertNotNull(floor.getDownButton());
  }

  @Test
  void testSetDownButtonPressed() {
    Floor floor = new Floor(1);
    floor.setDownButton(true);
    assertTrue(floor.getDownButton().isPressed());
  }

  @Test
  void testSetDownButtonNotPressed() {
    Floor floor = new Floor(1);
    floor.setDownButton(false);
    assertFalse(floor.getDownButton().isPressed());
  }
}
