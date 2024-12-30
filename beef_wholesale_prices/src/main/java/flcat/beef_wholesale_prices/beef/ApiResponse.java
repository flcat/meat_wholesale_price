package flcat.beef_wholesale_prices.beef;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ApiResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private List<Livestock> data;
}