package by.teachmeskills.rest.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {

    RequestSpecification rqSpec;

    public BaseApiClient() {
        rqSpec = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri("https://petstore.swagger.io")
                .log().ifValidationFails();
    }

    public Response post(String uri, Object body) {
        return given().spec(rqSpec)
                      .body(body)
                      .when()
                      .post(uri)
                      .then()
                      .log().ifValidationFails()
                      .extract()
                      .response();
    }

    public Response get(String uri, Map<String, ?> parameterNameValuePairs) {
        return given().spec(rqSpec)
                      .pathParams(parameterNameValuePairs)
                      .when()
                      .get(uri)
                      .then()
                      .log().ifValidationFails()
                      .extract()
                      .response();
    }

    public Response put(String uri, Object body) {
        return given().spec(rqSpec)
                      .body(body)
                      .when()
                      .put(uri)
                      .then()
                      .log().ifValidationFails()
                      .extract()
                      .response();
    }

    public Response delete(String uri, Map<String, ?> parameterNameValuePairs) {
        return given().spec(rqSpec)
                      .pathParams(parameterNameValuePairs)
                      .when()
                      .delete(uri)
                      .then()
                      .log().ifValidationFails()
                      .extract()
                      .response();
    }
}
