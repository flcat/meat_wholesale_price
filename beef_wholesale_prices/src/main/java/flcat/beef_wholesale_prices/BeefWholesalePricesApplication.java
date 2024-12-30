package flcat.beef_wholesale_prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"flcat.beef_wholesale_prices", "flcat.beef_wholesale_prices.config"})
public class BeefWholesalePricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeefWholesalePricesApplication.class, args);
	}

}
