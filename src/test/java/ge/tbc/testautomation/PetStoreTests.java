package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.PetStoreApiSteps;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.invoker.JacksonObjectMapper;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class PetStoreTests {
    public ApiClient apiClient;
    public PetStoreApiSteps petStoreApiSteps = new PetStoreApiSteps();
    @BeforeSuite
    public void setUpAPIClient()
    {
        apiClient = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .setContentType(ContentType.JSON)
                        .setAccept(ContentType.JSON)
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .addFilter(new AllureRestAssured())
                        .setBaseUri("https://petstore3.swagger.io/api/v3")));
    }

    @Test
    public void postAnOrder()
    {
        petStoreApiSteps
                .generateOrderBody()
                .createOrder(apiClient);
    }
}
