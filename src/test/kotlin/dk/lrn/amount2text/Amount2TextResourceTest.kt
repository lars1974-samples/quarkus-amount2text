package dk.lrn.amount2text

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

/**
 * This is just to test endpoint exposing is working with mapping for happy path and error.
 */
@QuarkusTest
class Amount2TextResourceTest {

    @Test
    fun testHappyPath() {
        given()
            .`when`().contentType(ContentType.JSON).body( "{\"input\": 42.42 }").post("/")
            .then()
            .statusCode(200)
            .body(`is`("{\"output\":\"FORTY TWO DOLLARS AND FORTY TWO CENTS\"}"))
    }

    @Test
    fun testExceptionMapping() {
        given()
            .`when`().contentType(ContentType.JSON).body( "{\"input\": 4242.4242 }").post("/")
            .then()
            .statusCode(400)
            .body(`is`("{\"message\":\"Number must be a number in range 0..999999 and with maximum of two decimals\"}"))
    }

}