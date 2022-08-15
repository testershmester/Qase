package by.teachmeskills.rest.clients;

import by.teachmeskills.rest.dto.Pet;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Map;

public class PetApiClient extends BaseApiClient {

    public static final String PET_URI = "/v2/pet";
    public static final String PET_URI_WITH_ID = PET_URI + "/{petId}";
    public static final String PET_ID = "petId";

    public Pet postPet(Pet pet) {
        Response response = post(PET_URI, pet);
        return response.then()
                       .statusCode(HttpStatus.SC_OK)
                       .extract()
                       .body()
                       .as(Pet.class);
    }

    public Pet getPet(long petId) {
        Response response = get(PET_URI_WITH_ID, Map.of(PET_ID, petId));
        return response.then()
                       .statusCode(HttpStatus.SC_OK)
                       .extract()
                       .body()
                       .as(Pet.class);
    }

    public Response getPetResponse(long petId) {
        return get(PET_URI_WITH_ID, Map.of(PET_ID, petId));
    }

    public Pet putPet(Pet pet) {
        Response response = put(PET_URI, pet);
        return response.then()
                       .statusCode(HttpStatus.SC_OK)
                       .extract()
                       .body()
                       .as(Pet.class);
    }

    public void deletePet(long petId) {
        Response response = delete(PET_URI_WITH_ID, Map.of(PET_ID, petId));
        response.then()
                .statusCode(HttpStatus.SC_OK);
    }
}