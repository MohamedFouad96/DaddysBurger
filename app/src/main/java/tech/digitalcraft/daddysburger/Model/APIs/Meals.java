package tech.digitalcraft.daddysburger.Model.APIs;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "mealId",
        "quantity",
        "notes"
})
public class Meals {

    @JsonProperty("mealId")
    private Integer mealId;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("notes")
    private String notes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Meals(Integer mealId, Integer quantity, String notes) {
        this.mealId = mealId;
        this.quantity = quantity;
        this.notes = notes;
    }

    @JsonProperty("mealId")
    public Integer getMealId() {
        return mealId;
    }

    @JsonProperty("mealId")
    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
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