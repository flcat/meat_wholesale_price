package flcat.beef_wholesale_prices.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flcat.beef_wholesale_prices.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

/**
 * 가축 시세 정보를 KAMIS API로부터 조회하는 서비스 클래스
 * @Slf4j: 로깅을 위한 어노테이션
 * @Service: 스프링의 서비스 컴포넌트로 등록
 * @RequiredArgsConstructor: final 필드의 생성자를 자동으로 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiveStockApiService {

    // HTTP 요청을 보내기 위한 WebClient
    private final WebClient webClient;
    // 날짜 처리를 위한 유틸리티 클래스
    private final DateUtil dateUtil;
    // JSON 처리를 위한 ObjectMapper
    private final ObjectMapper objectMapper;

    // application.properties/yml에서 설정된 API 키 값
    @Value("${api.key}")
    private String apiKey;

    // API 인증 ID
    @Value("${api.cert.id}")
    private String apiCertId;

    /**
     * 특정 품목의 가축 시세 정보를 조회하는 메서드
     * @param itemCode 품목 코드 (411: 소, 412: 돼지, 413: 닭)
     * @param date 조회할 날짜
     * @return 시세 정보 리스트를 담은 Mono (비동기 처리를 위한 리액터 타입)
     */
    public Mono<List<Map<String, Object>>> getLiveStockInfo(String itemCode, LocalDate date) {
        return fetchKamisData(itemCode, date)
            .map(this::processKamisData);
    }

    /**
     * KAMIS API로부터 데이터를 가져오는 private 메서드
     * @param itemCode 품목 코드
     * @param date 조회 날짜
     * @return API 응답 데이터를 JsonNode 형태로 반환하는 Mono
     */
    private Mono<JsonNode> fetchKamisData(String itemCode, LocalDate date) {
        String itemName = getItemName(itemCode);
        return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .path("/service/price/xml.do")
                .queryParam("action", "dailySalesList")
                .queryParam("p_product_cls_code", "02")        // 축산물 코드
                .queryParam("p_country_code", "1101")         // 국가 코드
                .queryParam("p_regday", dateUtil.formatDate(date))
                .queryParam("p_item_category_code", "100")    // 카테고리 코드
                .queryParam("p_item_name", itemName)
                .queryParam("p_cert_key", apiKey)
                .queryParam("p_cert_id", apiCertId)
                .queryParam("p_returntype", "json")           // 응답 형식을 JSON으로 요청
                .build())
            .retrieve()
            .bodyToMono(String.class)                         // 응답 본문을 String으로 받음
            .flatMap(response -> {
                try {
                    // String을 JsonNode로 파싱
                    return Mono.just(objectMapper.readTree(response));
                } catch (JsonProcessingException e) {
                    return Mono.error(e);                     // 파싱 실패시 에러 반환
                }
            })
            // 응답 데이터 로깅
            .doOnNext(response -> log.info("Received KAMIS data: {}", response))
            // 에러 발생시 빈 JsonNode 반환
            .onErrorResume(e -> {
                log.error("Error fetching KAMIS data", e);
                return Mono.just(objectMapper.createObjectNode());
            });
    }

    /**
     * API 응답 데이터를 처리하여 필요한 형태로 변환하는 private 메서드
     * @param responseData API로부터 받은 JSON 응답 데이터
     * @return 가공된 시세 정보 리스트
     */
    private List<Map<String, Object>> processKamisData(JsonNode responseData) {
        List<Map<String, Object>> result = new ArrayList<>();
        JsonNode priceArray = responseData.path("price");
        if (priceArray.isArray()) {
            for (JsonNode item : priceArray) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("itemName", item.path("itemname").asText());  // 품목명
                itemData.put("kindName", item.path("kindname").asText());  // 품종명
                itemData.put("price", item.path("price").asText());        // 가격
                itemData.put("unit", item.path("unit").asText());          // 단위
                itemData.put("date", item.path("regday").asText());        // 날짜
                result.add(itemData);
            }
        }
        return result;
    }

    /**
     * 품목 코드를 품목명으로 변환하는 private 메서드
     * @param itemCode 품목 코드
     * @return 품목명
     * @throws IllegalArgumentException 유효하지 않은 품목 코드일 경우 발생
     */
    private String getItemName(String itemCode) {
        switch (itemCode) {
            case "411": return "소";
            case "412": return "돼지";
            case "413": return "닭";
            default: throw new IllegalArgumentException("Invalid item code: " + itemCode);
        }
    }
}