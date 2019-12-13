package testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.HashUtil;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.HashUtil.getHash;

public class GETCharactersTestCase02 {

    @Parameters({"baseUrl", "characterEP", "apiKey", "apiKeyPrivate"})
    @Test
    public void getCharacterSuccess(String baseUrl, String characterEP, String apiKey, String apiKeyPrivate){
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        String timestampsStr = timeStamp.toString();

        String authStr = timestampsStr + apiKeyPrivate + apiKey;
        String hash = getHash(authStr);

        Response response = given().
                baseUri(baseUrl).
                basePath(characterEP).
                param("ts", timestampsStr).
                param("apikey", apiKey).
                param("hash", hash).
                when().
                get();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Parameters({"baseUrl", "characterEP", "apiKey", "apiKeyPrivate"})
    @Test
    public void getCharacterSuccessRestAssured(String baseUrl, String characterEP, String apiKey, String apiKeyPrivate){
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        String timestampsStr = timeStamp.toString();

        String authStr = timestampsStr + apiKeyPrivate + apiKey;
        String hash = getHash(authStr);

        given().
                baseUri(baseUrl).
                basePath(characterEP).
                param("ts", timestampsStr).
                param("apikey", apiKey).
                param("hash", hash).
                when().
                get().
                then().
                assertThat().body(matchesJsonSchemaInClasspath("GET_v1_public_characters_schema.json"));
    }

}
