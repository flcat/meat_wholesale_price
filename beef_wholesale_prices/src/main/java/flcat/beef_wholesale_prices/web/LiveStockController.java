package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.service.LiveStockApiService;
import java.time.LocalDate;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * 가축 시세 정보를 제공하는 REST API 컨트롤러
 * @Slf4j: 로깅을 위한 어노테이션
 * @RestController: REST API 컨트롤러임을 표시
 * @RequestMapping: 모든 API의 기본 경로를 /api/livestock으로 설정
 */
@Slf4j
@RestController
@RequestMapping("/api/livestock")
@RequiredArgsConstructor
public class LiveStockController {

    // 실제 가축 시세 정보를 조회하는 서비스
    private final LiveStockApiService liveStockApiService;

    /**
     * 가축 시세 정보를 조회하는 API
     * HTTP Method: GET
     * 경로: /api/livestock
     *
     * @param countyCode 지역 코드 (예: "1101" - 서울)
     * @param itemCode 품목 코드 (예: "411" - 소)
     * @param date 조회 날짜 (ISO 형식: YYYY-MM-DD)
     * @return 가축 시세 정보를 포함한 ResponseEntity
     *
     * 응답 상태:
     * - 200 OK: 정상적으로 데이터 조회
     * - 400 Bad Request: 지역 코드가 없는 경우
     * - 500 Internal Server Error: 서버 오류 발생
     */
    @GetMapping
    public Mono<ResponseEntity<List<Map<String, Object>>>> getLiveStockInfo(
        @RequestParam(required = false) String countyCode,
        @RequestParam String itemCode,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        // 지역 코드 유효성 검사
        if (countyCode == null || countyCode.isEmpty()) {
            return Mono.just(ResponseEntity.badRequest()
                .body(List.of(Map.of("error", "County code is required"))));
        }

        // 시세 정보 조회 및 에러 처리
        return liveStockApiService.getLiveStockInfo(itemCode, date)
            .map(ResponseEntity::ok)  // 성공시 200 OK와 함께 데이터 반환
            .onErrorResume(e -> {     // 에러 발생시 500 에러 반환
                log.error("Error fetching livestock info", e);
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            });
    }

    /**
     * 지역 코드와 품목 코드 정보를 제공하는 API
     * HTTP Method: GET
     * 경로: /api/livestock/codes
     *
     * @return 지역 코드와 품목 코드 매핑 정보를 포함한 ResponseEntity
     *
     * 반환 데이터 구조:
     * {
     *   "countyCode": {
     *     "서울": "1101",
     *     "부산": "2100",
     *     ...
     *   },
     *   "itemCode": {
     *     "소": "411",
     *     "돼지": "412",
     *     ...
     *   }
     * }
     */
    @GetMapping("/codes")
    public ResponseEntity<Map<String, Map<String, String>>> getCodes() {
        Map<String, Map<String, String>> codes = new HashMap<>();

        // 지역 코드 매핑 정보
        Map<String, String> countyCode = new HashMap<>();
        countyCode.put("서울", "1101");
        countyCode.put("부산", "2100");
        countyCode.put("대구", "2200");
        countyCode.put("인천", "2300");
        countyCode.put("광주", "2401");
        countyCode.put("대전", "2501");
        countyCode.put("울산", "2601");
        countyCode.put("세종", "2701");
        countyCode.put("경기", "4100");
        countyCode.put("강원", "4200");
        countyCode.put("충북", "4301");
        countyCode.put("충남", "4302");
        countyCode.put("전북", "4401");
        countyCode.put("전남", "4501");
        countyCode.put("경북", "4601");
        countyCode.put("경남", "4701");
        countyCode.put("제주", "5000");

        // 품목 코드 매핑 정보
        Map<String, String> itemCode = new HashMap<>();
        itemCode.put("소", "411");
        itemCode.put("돼지", "412");
        itemCode.put("닭", "413");
        // 필요에 따라 더 많은 품목 코드 추가 가능

        // 전체 코드 매핑 정보 구성
        codes.put("countyCode", countyCode);
        codes.put("itemCode", itemCode);

        return ResponseEntity.ok(codes);
    }
}