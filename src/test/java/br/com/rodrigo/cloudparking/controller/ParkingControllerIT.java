package br.com.rodrigo.cloudparking.controller;

import br.com.rodrigo.cloudparking.dto.ParkingCreateDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerIT {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest () {
        RestAssured.port = randomPort;
    }

    @Test
    void whenFindAllThenCheckResult(){

        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .prettyPrint();

    }

    @Test
    void whenCreatedThenCheckIsCreated () {

        ParkingCreateDTO createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("WRT-555");
        createDTO.setModel("BRASILIA");
        createDTO.setState("SP");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking/create")
                .then()
                .extract()
                .response()
                .prettyPrint();

    }

}
