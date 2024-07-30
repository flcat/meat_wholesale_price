package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.BeefWholesalePriceDto;
import flcat.beef_wholesale_prices.service.BeefWholesalePriceService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beef-prices")
public class BeefWholesalePriceController {

    private final BeefWholesalePriceService beefWholesalePriceService;

    @GetMapping
    public ResponseEntity<List<BeefWholesalePriceDto>> getAllPrices() {
        log.info("Fetching all beef wholesale prices");
        return ResponseEntity.ok(beefWholesalePriceService.getAllPrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeefWholesalePriceDto> getPriceById(@PathVariable Long id) {
        log.info("Fetching beef wholesale price with id: {}", id);
        return beefWholesalePriceService.getPriceById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BeefWholesalePriceDto> createPrice(@Valid @RequestBody BeefWholesalePriceDto priceDto) {
        log.info("Creating new beef wholesale price: {}", priceDto);
        BeefWholesalePriceDto createdPrice = beefWholesalePriceService.createPrice(priceDto);
        return ResponseEntity.created(URI.create("/api/beef-prices/" + createdPrice.getId())).body(createdPrice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeefWholesalePriceDto> updatePrice(@PathVariable Long id, @Valid @RequestBody BeefWholesalePriceDto priceDto) {
        log.info("Updating beef wholesale price with id: {}", id);
        return beefWholesalePriceService.updatePrice(id, priceDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        log.info("Deleting beef wholesale price with id: {}", id);
        beefWholesalePriceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
