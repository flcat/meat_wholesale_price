package flcat.beef_wholesale_prices.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PriceMapController {

    @GetMapping("price_map")
    public String showPriceMap(){


        return "price_map";
    }
}
