package ge.tbc.testautomation.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.model.Order;

import java.time.OffsetDateTime;

import static pet.store.v3.invoker.ResponseSpecBuilders.shouldBeCode;
import static pet.store.v3.invoker.ResponseSpecBuilders.validatedWith;

public class PetStoreApiSteps {
    Order newOrderBody;
    Order createOrder;
     Response response;

    @Step("generate order body")
    public PetStoreApiSteps generateOrderBody()
    {
        newOrderBody = new Order()
                .id(11L)
                .petId(5L)
                .quantity(1)
                .shipDate(OffsetDateTime.parse("2024-05-05T00:00:00Z"))
                .status(Order.StatusEnum.APPROVED)
                .complete(true);
        return this;
    }

    @Step("send a post request to create order")
    public PetStoreApiSteps createOrder(ApiClient apiClient)
    {
       createOrder = apiClient
               .store()
               .placeOrder()
               .body(newOrderBody)
               .executeAs(response -> {
                  this.response = response;
                   validatedWith(shouldBeCode(200)).andThen(ResponseBody::print).apply(response);
                   return response;
               });
       return this;
    }
}
