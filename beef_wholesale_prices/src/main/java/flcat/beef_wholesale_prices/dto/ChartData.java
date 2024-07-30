package flcat.beef_wholesale_prices.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartData {
    private String grade;
    private String part;
    private Date date;
    private int minPrice;
    private int maxPrice;
    private int avgPrice;
    private String minPriceBrand;
    private String maxPriceBrand;

}