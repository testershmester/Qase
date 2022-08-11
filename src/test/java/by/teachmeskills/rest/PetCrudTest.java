package by.teachmeskills.rest;

import by.teachmeskills.rest.dto.Category;
import by.teachmeskills.rest.dto.Pet;
import by.teachmeskills.rest.dto.Status;
import by.teachmeskills.rest.dto.Tag;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PetCrudTest {

    @Test
    public void createPetTest() {
        Pet expectedPet = Pet.builder()
                             .name("Pet Test Name")
                             .category(Category.builder()
                                               .name("Test Category")
                                               .build())
                             .photoUrls(List.of("Test Urls 1", "Test Urls 2"))
                             .tags(List.of(Tag.builder()
                                              .name("Test Tag")
                                              .build()))
                             .status(Status.available)
                             .build();

        //POST PET /pet
        Pet postActualPet = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(expectedPet).
                                    log().ifValidationFails().
                            when().
                                    post("https://petstore.swagger.io/v2/pet").
                            then().
                                    statusCode(200).
                                    log().ifValidationFails().
                            extract().
                                    body().as(Pet.class);

        assertPet(expectedPet, postActualPet);

        //GET PET /pet/{petId}
        Pet getActualPet = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    log().ifValidationFails().
                                    pathParams("petId", postActualPet.getId()).
                            when().
                                    get("https://petstore.swagger.io/v2/pet/{petId}").
                            then().
                                    statusCode(200).
                            extract().
                                    body().as(Pet.class);
        assertPet(expectedPet, getActualPet);
    }

    @Test
    public void updatePetTest() {
        Pet expectedPet = Pet.builder()
                             .name("Pet Test Name 1")
                             .category(Category.builder()
                                               .name("Test Category 1")
                                               .build())
                             .photoUrls(List.of("Test Urls 1 1", "Test Urls 2 1"))
                             .tags(List.of(Tag.builder()
                                              .name("Test Tag 1")
                                              .build()))
                             .status(Status.available)
                             .build();

        //POST PET /pet (create pet for test)
        Pet postActualPet = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(expectedPet).
                                    log().ifValidationFails().
                            when().
                                    post("https://petstore.swagger.io/v2/pet").
                            then().
                                    statusCode(200).
                                    log().ifValidationFails().
                            extract().
                                    body().as(Pet.class);

        postActualPet.setName("Test Name Upd 1")
                     .setCategory(Category.builder()
                                          .name("Test Category 1 Upd")
                                          .build())
                     .setPhotoUrls(List.of("Test Urls 1 Upd"));

        //PUT PET /pet (update created pet with new data)
        Pet putActualPet = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(postActualPet).
                                    log().ifValidationFails().
                            when().
                                    put("https://petstore.swagger.io/v2/pet").
                            then().
                                    statusCode(200).
                                    log().ifValidationFails().
                            extract().
                                    body().as(Pet.class);

        //GET PET /pet/{petId}
        Pet getUpdActualPet = given().
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    log().ifValidationFails().
                                    pathParams("petId", postActualPet.getId()).
                            when().
                                    get("https://petstore.swagger.io/v2/pet/{petId}").
                            then().
                                    statusCode(200).
                            extract().
                                    body().as(Pet.class);

        assertPet(getUpdActualPet, putActualPet);
    }

    private void assertPet(Pet expectedPet, Pet postActualPet) {
        assertThat(postActualPet).as("The pet in response doesn't match expected pet")
                                 .usingRecursiveComparison()
                                 .ignoringFields("id")
                                 .isEqualTo(expectedPet);
        assertThat(postActualPet.getId()).as("The \"id\" is not generated")
                                         .isNotNull()
                                         .isNotEqualTo(0);
    }
}
