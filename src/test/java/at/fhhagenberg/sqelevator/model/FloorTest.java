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
    assertNotNull(floor.upButton.get());
  }

  @Test
  void testSetUpButtonPressed() {
    Floor floor = new Floor(1);
    floor.upButton.set(true);
    assertTrue(floor.upButton.get());
  }

  @Test
  void testSetUpButtonNotPressed() {
    Floor floor = new Floor(1);
    floor.upButton.set(false);
    assertFalse(floor.upButton.get());
  }

  @Test
  void testGetDownButton() {
    Floor floor = new Floor(1);
    assertNotNull(floor.downButton.get());
  }

  @Test
  void testSetDownButtonPressed() {
    Floor floor = new Floor(1);
    floor.downButton.set(true);
    assertTrue(floor.downButton.get());
  }

  @Test
  void testSetDownButtonNotPressed() {
    Floor floor = new Floor(1);
    floor.downButton.set(false);
    assertFalse(floor.downButton.get());
  }
}
