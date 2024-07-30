package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.ChartData;
import flcat.beef_wholesale_prices.service.ChartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chartData")
@RequiredArgsConstructor
public class ChartDataController {

    private final ChartService chartService;

    @GetMapping("/byGrade")
    public List<ChartData> getChartDataByGrade(@RequestParam String grade) {
        return chartService.getChartDataByGrade(grade);
    }

    @GetMapping("/all")
    public List<ChartData> getAllChartData() {
        return chartService.getAllChartData();
    }

    @GetMapping("/all_prices")
    public List<ChartData> getAllPricesData() {
        return chartService.getAllPrices();
    }
}