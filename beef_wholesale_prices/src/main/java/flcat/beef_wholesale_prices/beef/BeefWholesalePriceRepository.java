package flcat.beef_wholesale_prices.beef;

import flcat.beef_wholesale_prices.dto.BeefWholesalePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BeefWholesalePriceRepository extends JpaRepository<BeefWholesalePrice, Long> {
    List<BeefWholesalePrice> findByDateBetween(LocalDate startDate, LocalDate endDate);
}