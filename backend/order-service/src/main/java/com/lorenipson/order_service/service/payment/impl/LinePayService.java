package com.lorenipson.order_service.service.payment.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorenipson.order_service.dto.form.LinePayForm;
import com.lorenipson.order_service.dto.form.LinePayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class LinePayService {

    @Value("${line.pay.channel.id}")
    private String channelId;

    @Value("${line.pay.channel.secret}")
    private String channelSecret;

    private final RestClient restClient = RestClient
            .builder()
            .baseUrl("https://sandbox-api-pay.line.me")
            .build();

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    public LinePayResponse requestOnlinePay(LinePayForm form) {

        try {
            String jsonBody = new ObjectMapper().writeValueAsString(form);
            String nonce = UUID.randomUUID().toString();
            String uri = "/v3/payments/request";
            String signature = generateSignature(channelSecret, channelSecret + uri + jsonBody + nonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-LINE-ChannelId", channelId);
            headers.add("X-LINE-Authorization-Nonce", nonce);
            headers.add("X-LINE-Authorization", signature);

            JsonNode response = restClient.post()
                    .uri(uri)
                    .headers(http -> http.setAll(headers.toSingleValueMap()))
                    .body(jsonBody)
                    .retrieve()
                    .body(JsonNode.class);

            if (response != null &&
                response.has("info") &&
                response.get("info").has("paymentUrl")) {
                JsonNode info = response.get("info");
                return new LinePayResponse(
                        info.get("transactionId").asText(),
                        info.get("paymentUrl").get("web").asText(),
                        info.get("paymentUrl").get("app").asText()
                );
            }

            return null;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * CREDIT: Manders-Ma (GitHub)
     */
    @SuppressWarnings("SpellCheckingInspection")
    private String generateSignature(String channelSecret, String data) {

        String signature;
        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(channelSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSHA256.init(secretKey);
            byte[] hmacByte = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            signature = Base64.getEncoder().encodeToString(hmacByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return signature;

    }

}
