package kr.hanjari.backend.infrastructure.slack;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SlackWebhookSender {

  @Value("${slack.webhook-url}")
  private String webhookUrl;

  private final RestClient restClient = RestClient.create();

  public void sendMessage(String message) {
    Map<String, String> payload = Map.of("text", message);

    restClient.post()
        .uri(webhookUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .body(payload)
        .retrieve()
        .toBodilessEntity();
  }
}