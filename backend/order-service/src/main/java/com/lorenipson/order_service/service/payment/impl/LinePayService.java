package com.lorenipson.order_service.service.payment.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorenipson.order_service.dto.form.linepay.LinePayConfirmForm;
import com.lorenipson.order_service.dto.form.linepay.LinePayReqForm;
import com.lorenipson.order_service.dto.form.linepay.LinePayReqResponse;
import com.lorenipson.order_service.entity.Order;
import com.lorenipson.order_service.entity.OrderPayment;
import com.lorenipson.order_service.repository.OrderPaymentRepository;
import com.lorenipson.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LinePayService {

    @Value("${line.pay.channel.id}")
    private String channelId;

    @Value("${line.pay.channel.secret}")
    private String channelSecret;

    private final OrderRepository orderRepos;
    private final OrderPaymentRepository orderPaymentRepos;


    private final RestClient restClient = RestClient
            .builder()
            .baseUrl("https://sandbox-api-pay.line.me")
            .build();

    public LinePayService(OrderRepository orderRepos, OrderPaymentRepository orderPaymentRepos) {
        this.orderRepos = orderRepos;
        this.orderPaymentRepos = orderPaymentRepos;
    }

    public LinePayReqResponse requestOnlinePay(LinePayReqForm form) {

        try {
            String jsonBody = new ObjectMapper().writeValueAsString(form);
            String nonce = UUID.randomUUID().toString();
            String uri = "/v3/payments/request";
            HttpHeaders headers = createHttpHeader(uri, jsonBody, nonce);

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
                return new LinePayReqResponse(
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

    @Transactional
    public String confirmOnlinePay(Long orderId) {

        System.out.println("============================== LINE PAY CONFIRM ONLINE PAYMENT ========================");

        try {
            Order targetOrder = orderRepos.findById(orderId)
                    .orElseThrow(EntityNotFoundException::new);
            OrderPayment targetPayment = orderPaymentRepos.findByOrder(targetOrder)
                    .orElseThrow(EntityNotFoundException::new);
            LinePayConfirmForm form = new LinePayConfirmForm();
            form.setAmount(targetOrder.getTotalPrice().intValue());
            form.setCurrency("TWD");

            String jsonBody = new ObjectMapper().writeValueAsString(form);
            String nonce = UUID.randomUUID().toString();
            String uri = "/v3/payments/" + targetPayment.getTransactionId() + "/confirm";
            HttpHeaders headers = createHttpHeader(uri, jsonBody, nonce);

            JsonNode response = restClient.post()
                    .uri(uri)
                    .headers(http -> http.setAll(headers.toSingleValueMap()))
                    .body(jsonBody)
                    .retrieve()
                    .body(JsonNode.class);

            if (response != null && response.has("returnCode")) {
                String returnCode = response.get("returnCode").asText();
                System.out.println("=== RETURN CODE ===" + returnCode);
                if ("0000".equals(returnCode)) {
                    targetOrder.setIsPaid(true);
                    targetOrder.setPaymentStatus("已使用 LINE Pay 付款。");
                    targetPayment.setPaymentTime(LocalDateTime.now());
                    orderPaymentRepos.save(targetPayment);
                    orderRepos.save(targetOrder);
                    return "success";
                }
            }

            return "failed";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * CREDIT: Manders-Ma (GitHub)
     */
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

    /**
     * 包裝 HttpHeader。
     */
    private HttpHeaders createHttpHeader(String uri, String jsonBody, String nonce) {

        String signature = generateSignature(channelSecret, channelSecret + uri + jsonBody + nonce);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-LINE-ChannelId", channelId);
        headers.add("X-LINE-Authorization-Nonce", nonce);
        headers.add("X-LINE-Authorization", signature);
        return headers;

    }

}
