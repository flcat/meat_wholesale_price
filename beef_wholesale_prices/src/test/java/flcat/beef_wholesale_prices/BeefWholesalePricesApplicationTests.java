package flcat.beef_wholesale_prices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "livestock.url='${livestock.url}'")
@TestPropertySource(properties = "livestock.key='${livestock.key}'")
class BeefWholesalePricesApplicationTests {

	@Test
	void contextLoads() {
	}

}
