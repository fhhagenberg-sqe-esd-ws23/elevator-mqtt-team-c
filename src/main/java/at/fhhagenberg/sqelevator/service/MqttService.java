package at.fhhagenberg.sqelevator.service;

public interface MqttService {
  void connect(String brokerUrl, int port);
  void disconnect();
  void publish(String topic, String payload);

  void publish(String topic, int payload);
}
