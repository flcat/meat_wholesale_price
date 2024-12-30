package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.BeefWholesalePriceDto;
import flcat.beef_wholesale_prices.service.BeefWholesalePriceService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 소고기 도매가격 API를 처리하는 컨트롤러
 * @Slf4j: 로깅을 위한 어노테이션
 * @RestController: JSON 형식으로 데이터를 반환하는 REST API 컨트롤러
 * @RequestMapping("/api/beef-prices"): 모든 API의 기본 경로를 /api/beef-prices로 설정
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/beef-prices")
public class BeefWholesalePriceController {

    // 비즈니스 로직을 처리하는 서비스 객체
    private final BeefWholesalePriceService beefWholesalePriceService;

    /**
     * 모든 도매가격 정보를 조회하는 API
     * HTTP Method: GET
     * 경로: /api/beef-prices
     *
     * @return 200 OK와 함께 도매가격 정보 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<BeefWholesalePriceDto>> getAllPrices() {
        log.info("Fetching all beef wholesale prices");
        return ResponseEntity.ok(beefWholesalePriceService.getAllPrices());
    }

    /**
     * ID로 특정 도매가격 정보를 조회하는 API
     * HTTP Method: GET
     * 경로: /api/beef-prices/{id}
     *
     * @param id 조회할 가격 정보의 ID
     * @return 200 OK와 함께 가격 정보 반환, 정보가 없으면 404 Not Found 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<BeefWholesalePriceDto> getPriceById(@PathVariable Long id) {
        log.info("Fetching beef wholesale price with id: {}", id);
        return beefWholesalePriceService.getPriceById(id)
            .map(ResponseEntity::ok)           // 데이터가 있으면 200 OK
            .orElse(ResponseEntity.notFound().build());  // 없으면 404 Not Found
    }

    /**
     * 새로운 도매가격 정보를 생성하는 API
     * HTTP Method: POST
     * 경로: /api/beef-prices
     *
     * @param priceDto 생성할 가격 정보 (@Valid: 요청 데이터 유효성 검증)
     * @return 201 Created 상태코드, 생성된 리소스의 URI, 생성된 가격 정보 반환
     */
    @PostMapping
    public ResponseEntity<BeefWholesalePriceDto> createPrice(
        @Valid @RequestBody BeefWholesalePriceDto priceDto) {
        log.info("Creating new beef wholesale price: {}", priceDto);
        BeefWholesalePriceDto createdPrice = beefWholesalePriceService.createPrice(priceDto);
        // 생성된 리소스의 URI를 Location 헤더에 포함시켜 반환
        return ResponseEntity
            .created(URI.create("/api/beef-prices/" + createdPrice.getId()))
            .body(createdPrice);
    }

    /**
     * 기존 도매가격 정보를 수정하는 API
     * HTTP Method: PUT
     * 경로: /api/beef-prices/{id}
     *
     * @param id 수정할 가격 정보의 ID
     * @param priceDto 새로운 가격 정보
     * @return 200 OK와 수정된 정보 반환, 정보가 없으면 404 Not Found 반환
     */
    @PutMapping("/{id}")
    public ResponseEntity<BeefWholesalePriceDto> updatePrice(
        @PathVariable Long id,
        @Valid @RequestBody BeefWholesalePriceDto priceDto) {
        log.info("Updating beef wholesale price with id: {}", id);
        return beefWholesalePriceService.updatePrice(id, priceDto)
            .map(ResponseEntity::ok)           // 데이터가 있으면 200 OK
            .orElse(ResponseEntity.notFound().build());  // 없으면 404 Not Found
    }

    /**
     * 도매가격 정보를 삭제하는 API
     * HTTP Method: DELETE
     * 경로: /api/beef-prices/{id}
     *
     * @param id 삭제할 가격 정보의 ID
     * @return 204 No Content 반환 (삭제 성공)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        log.info("Deleting beef wholesale price with id: {}", id);
        beefWholesalePriceService.deletePrice(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}