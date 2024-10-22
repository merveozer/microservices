//package com.ozer.microservices.product_service;
//
//import com.ozer.microservices.product_service.event.ProductPlacedEvent;
//import io.restassured.RestAssured;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.context.annotation.Import;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.testcontainers.containers.MongoDBContainer;
//
//import java.math.BigDecimal;
//
//import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@Import(TestcontainersConfiguration.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class ProductServiceApplicationTests {
//
//    @ServiceConnection
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
//	@MockBean
//	private KafkaTemplate<String, ProductPlacedEvent> kafkaTemplate;
//
//    static {
//        mongoDBContainer.start();
//    }
//
//    @LocalServerPort
//    private Integer port;
//
//    @BeforeEach
//    void setup() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = port;
//    }
//
//    @Test
//    void shouldCreateProduct() {
//		String requestBody = """
//				{
//				        "name": "iPhone 16",
//				        "description": "smartphone",
//				        "price": 1000
//				    }
//				""";
//		ProductPlacedEvent productPlacedEvent = new ProductPlacedEvent("pro", "desc", new BigDecimal(100));
//		verify(kafkaTemplate, times(1)).send(anyString(), eq("product_topic"), productPlacedEvent);
//		RestAssured.given()
//				.contentType("application/json")
//				.body(requestBody)
//				.when()
//				.post("/api/product")
//				.then()
//				.statusCode(201)
//				.body("id", Matchers.notNullValue())
//				.body("name", Matchers.equalTo("iPhone 16"))
//				.body("description", Matchers.equalTo("smartphone"))
//				.body("price", Matchers.equalTo(1000));
//
//    }
//
//}
