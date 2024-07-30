package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.ChartData;
import flcat.beef_wholesale_prices.service.ChartService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/charts")
    public String showCharts(Model model) {
        model.addAttribute("grades", chartService.getAllGrades());
        model.addAttribute("brands", chartService.getAllBrands());
        return "charts";
    }

    @GetMapping("/api/chartData")
    @ResponseBody
    public Map<String, List<ChartData>> getChartData() {
        List<ChartData> allData = chartService.getAllChartData();
        return allData.stream()
            .collect(Collectors.groupingBy(ChartData::getGrade));
    }
}
