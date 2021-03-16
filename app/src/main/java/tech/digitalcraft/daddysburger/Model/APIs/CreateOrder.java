package tech.digitalcraft.daddysburger.Model.APIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "orderTypeId",
        "tableId",
        "addressDetials",
        "paymentMehtod",
        "meals"
})
public class CreateOrder {

    @JsonProperty("orderTypeId")
    private Integer orderTypeId;
    @JsonProperty("tableId")
    private Integer tableId;
    @JsonProperty("addressDetials")
    private String addressDetials;
    @JsonProperty("paymentMehtod")
    private Integer paymentMehtod;
    @JsonProperty("meals")
    private List<Meals> meals = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public CreateOrder(Integer orderTypeId, Integer tableId, String addressDetials, Integer paymentMehtod, List<Meals> meals) {
        this.orderTypeId = orderTypeId;
        this.tableId = tableId;
        this.addressDetials = addressDetials;
        this.paymentMehtod = paymentMehtod;
        this.meals = meals;
    }

    @JsonProperty("orderTypeId")
    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    @JsonProperty("orderTypeId")
    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    @JsonProperty("tableId")
    public Integer getTableId() {
        return tableId;
    }

    @JsonProperty("tableId")
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    @JsonProperty("addressDetials")
    public String getAddressDetials() {
        return addressDetials;
    }

    @JsonProperty("addressDetials")
    public void setAddressDetials(String addressDetials) {
        this.addressDetials = addressDetials;
    }

    @JsonProperty("paymentMehtod")
    public Integer getPaymentMehtod() {
        return paymentMehtod;
    }

    @JsonProperty("paymentMehtod")
    public void setPaymentMehtod(Integer paymentMehtod) {
        this.paymentMehtod = paymentMehtod;
    }

    @JsonProperty("meals")
    public List<Meals> getMeals() {
        return meals;
    }

    @JsonProperty("meals")
    public void setMeals(List<Meals> meals) {
        this.meals = meals;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}