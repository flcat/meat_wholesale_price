package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/charts")
    public String showCharts(Model model) {
        model.addAttribute("grades", chartService.getAllGrades());
        model.addAttribute("brands", chartService.getAllBrands());
        return "charts";  // This will render the charts.html template
    }
}
