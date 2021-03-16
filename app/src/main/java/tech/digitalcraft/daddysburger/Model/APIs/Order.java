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
        "id",
        "clientId",
        "date",
        "orderTypeId",
        "tableId",
        "paymentMethod",
        "addressDetails",
        "clientName",
        "meals"
})
public class Order {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("date")
    private String date;
    @JsonProperty("orderTypeId")
    private Integer orderTypeId;
    @JsonProperty("tableId")
    private Integer tableId;
    @JsonProperty("paymentMethod")
    private Integer paymentMethod;
    @JsonProperty("addressDetails")
    private String addressDetails;
    @JsonProperty("clientName")
    private String clientName;
    @JsonProperty("meals")
    private List<OrderMeals> meals = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientId")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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

    @JsonProperty("paymentMethod")
    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("paymentMethod")
    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("addressDetails")
    public String getAddressDetails() {
        return addressDetails;
    }

    @JsonProperty("addressDetails")
    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    @JsonProperty("clientName")
    public String getClientName() {
        return clientName;
    }

    @JsonProperty("clientName")
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @JsonProperty("meals")
    public List<OrderMeals> getMeals() {
        return meals;
    }

    @JsonProperty("meals")
    public void setMeals(List<OrderMeals> meals) {
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