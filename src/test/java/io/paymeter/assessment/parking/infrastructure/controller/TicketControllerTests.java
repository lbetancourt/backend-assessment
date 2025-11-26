package io.paymeter.assessment.parking.infrastructure.controller;

import io.paymeter.assessment.pricing.domain.PricingRepository;
import io.paymeter.assessment.pricing.infrastructure.postgres.dto.Pricing;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TicketControllerTests {

    @MockBean
    private PricingRepository pricingRepository;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testCalculatePrice_Success() {
        Pricing pricing = new Pricing();
        pricing.setParkingId("P000001");
        pricing.setFrom(LocalDateTime.parse("2025-11-25T06:28:13"));
        pricing.setTo(LocalDateTime.parse("2025-11-25T06:28:40"));
        pricing.setDuration(0);
        pricing.setPrice(BigDecimal.valueOf(0.00));
        Mockito.when(pricingRepository.get(Mockito.anyString())).thenReturn(pricing);

        String requestBody = """
                {
                    "parkingId": "P000001",
                    "from": "2023-09-15T10:00:00",
                    "to": "2023-09-15T14:00:00"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/tickets/calculate")
                .then()
                .statusCode(200)
                .body("parkingId", equalTo("P000001"))
                .body("duration", equalTo(240))
                .body("price", equalTo("1200EUR"));
    }

    @Test
    void testCalculatePrice_Invalid() {
        String requestBody = """
                {
                    "parkingId": "P000002",
                    "from": "2023-09-15T15:00:00",
                    "to": "2023-09-15T14:00:00"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/tickets/calculate")
                .then()
                .statusCode(400);
    }

    @Test
    void testCalculatePrice_NotFound() {
        String requestBody = """
                {
                    "parkingId": "P000003",
                    "from": "2023-09-15T10:00:00",
                    "to": "2023-09-15T14:00:00"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/tickets/calculate")
                .then()
                .statusCode(404);
    }
}
