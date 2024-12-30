package flcat.beef_wholesale_prices.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LiveStockPageController {

    @GetMapping("/")
    public String getLivestockPage() {
        return "livestock";  // This will return livestock.html
    }
}