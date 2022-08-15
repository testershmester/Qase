package by.teachmeskills.rest;

import by.teachmeskills.rest.clients.PetApiClient;
import by.teachmeskills.rest.dto.Category;
import by.teachmeskills.rest.dto.Pet;
import by.teachmeskills.rest.dto.Status;
import by.teachmeskills.rest.dto.Tag;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class PetCrudTest {

    Faker faker = new Faker();
    PetApiClient petApiClient = new PetApiClient();

    @Test
    public void createPetTest() {
        Pet expectedPet = preparePet();

        Pet postActualPet = petApiClient.postPet(expectedPet);
        assertPet(expectedPet, postActualPet);

        Pet getActualPet = petApiClient.getPet(postActualPet.getId());
        assertPet(expectedPet, getActualPet);
    }

    @Test
    public void updatePetTest() {
        Pet expectedPet = preparePet();

        Pet postActualPet = petApiClient.postPet(expectedPet);
        postActualPet.setName(faker.animal().name())
                     .setCategory(Category.builder()
                                          .name("Test Category 1 Upd")
                                          .build())
                     .setPhotoUrls(List.of(faker.internet().url()));
        Pet putActualPet = petApiClient.putPet(postActualPet);
        Pet getUpdActualPet = petApiClient.getPet(postActualPet.getId());
        assertPet(getUpdActualPet, putActualPet);
    }

    @Test
    public void validateGetPetResponseAgainstSchemaTest() {
        Pet expectedPet = preparePet();
        Pet postActualPet = petApiClient.postPet(expectedPet);

        Response petResponse = petApiClient.getPetResponse(postActualPet.getId());
        petResponse.then().assertThat().body(matchesJsonSchemaInClasspath("pet-schema.json"));
    }

    @Test
    public void deletePetTest() {
        Pet expectedPet = preparePet();
        Pet postActualPet = petApiClient.postPet(expectedPet);

        petApiClient.deletePet(postActualPet.getId());

        Response deletedPetResponse = petApiClient.getPetResponse(postActualPet.getId());
        assertThat(deletedPetResponse.statusCode()).as("Status code is incorrect in case for request for deleted pet")
                                                   .isEqualTo(HttpStatus.SC_NOT_FOUND);
        assertThat(deletedPetResponse.body().jsonPath().getString("message")).as("Error message is incorrect")
                                                                             .isEqualTo("Pet not found");
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

    private Pet preparePet() {
        return Pet.builder()
                  .name(faker.animal().name())
                  .category(Category.builder()
                                    .name("Test Category")
                                    .build())
                  .photoUrls(List.of(faker.internet().url(), faker.internet().url()))
                  .tags(List.of(Tag.builder()
                                   .name("Test Tag")
                                   .build()))
                  .status(Status.available)
                  .build();
    }
}
