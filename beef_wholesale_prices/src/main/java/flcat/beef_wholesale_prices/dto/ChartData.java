package flcat.beef_wholesale_prices.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChartData {
    private String grade;
    private String part;
    private Date date;
    private int minPrice;
    private int maxPrice;
    private int avgPrice;
    private String minPriceBrand;
    private String maxPriceBrand;

    public ChartData(String grade, String part, Date date, int minPrice, int maxPrice, int avgPrice,
        String minPriceBrand, String maxPriceBrand) {
        this.grade = grade;
        this.part = part;
        this.date = date;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.avgPrice = avgPrice;
        this.minPriceBrand = minPriceBrand;
        this.maxPriceBrand = maxPriceBrand;
    }
}