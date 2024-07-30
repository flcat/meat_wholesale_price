package flcat.beef_wholesale_prices.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeefWholesalePriceDto {
    private Long id;

    private String grade;
    private String part;
    private Date date;
    private int minPrice;
    private int maxPrice;
    private int avgPrice;
    private String minPriceBrand;
    private String maxPriceBrand;
}