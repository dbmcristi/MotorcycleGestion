package myproject.motospring.gestion;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import myproject.motospring.gestion.dto.RaceDTO;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.json.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GestionApplicationTests {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldPersistRace() throws IOException {
        String url = "http://localhost:" + port + "/race/";
        String urlGet = "http://localhost:" + port + "/race/1";
        String urlGetAll = "http://localhost:" + port + "/race/all";
        String urlSearch = "http://localhost:" + port + "/race/search?engineSize=CM_125";
        given()
                .contentType(ContentType.JSON)
                .body("""
                          {
                             "name": "Raliu",
                              "engineCapacity": "CM_500"
                          }
                        """
                )
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Raliu"))
                .body("engineCapacity", equalTo("CM_500"));
        given()
                .contentType(ContentType.JSON)
                .body("""
                          {
                             "id": 1,
                             "name": "RaliuX",
                              "engineCapacity": "CM_125"
                          }
                        """
                )
                .when()
                .put(url)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("RaliuX"))
                .body("engineCapacity", equalTo("CM_125"));
        //get
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(urlGet)
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("RaliuX"))
                .body("engineCapacity", equalTo("CM_125"));

        //getaLL
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(urlGetAll)
                .then()
                .statusCode(200)
                .body("id", equalTo(List.of(Integer.valueOf("1"))))
                .body("name", equalTo(List.of("RaliuX")))
                .body("engineCapacity", equalTo(List.of("CM_125")));;
        //searchAll
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(urlSearch)
                .then()
                .statusCode(200)
                .body("id", equalTo(List.of(Integer.valueOf("1"))))
                .body("name", equalTo(List.of("RaliuX")))
                .body("engineCapacity", equalTo(List.of("CM_125")));
    }
}