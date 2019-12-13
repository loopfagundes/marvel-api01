package testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.HashUtil.getHash;

public class GETCharactersTestCase {

    @Parameters({"apiKey", "privateApiKey", "baseUrl", "characterEP"})
    @Test
    public void getCharactersWithSuccess(String apiKey, String privateApiKey, String baseUrl, String characterEP) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timesTampStr = timestamp.toString();

        String md5Str = timesTampStr + privateApiKey + apiKey;

        String hash = getHash(md5Str);

                given().
                        baseUri(baseUrl).
                        basePath(characterEP).
                        param("ts", timesTampStr).
                        param("apiKey",apiKey).
                        param("hash",hash).
                        param("name","Thor").
                when().
                        get().
                then().
                        assertThat().body(matchesJsonSchemaInClasspath("GET_v1_public_characters_schema.json"));
    }
}