package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.ChartData;
import flcat.beef_wholesale_prices.service.ChartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 차트 데이터를 제공하는 REST API 컨트롤러
 *
 * @RestController: 모든 메서드의 반환값이 HTTP 응답 본문으로 자동 변환됨
 * @RequestMapping("/api/chartData"): 모든 엔드포인트의 기본 경로 설정
 */
@RestController
@RequestMapping("/api/chartData")
@RequiredArgsConstructor
public class ChartDataController {

    // 차트 데이터 처리를 위한 서비스 객체
    private final ChartService chartService;

    /**
     * 특정 등급의 차트 데이터를 조회하는 API
     * URL: /api/chartData/byGrade?grade={등급}
     *
     * @param grade 조회할 등급 (예: "1++", "1+", "1" 등)
     * @return 해당 등급의 차트 데이터 리스트
     *
     * 사용 예시: /api/chartData/byGrade?grade=1++
     * 응답 예시:
     * [
     *   {
     *     "grade": "1++",
     *     "date": "2024-03-15",
     *     "price": 50000,
     *     ...
     *   },
     *   ...
     * ]
     */
    @GetMapping("/byGrade")
    public List<ChartData> getChartDataByGrade(@RequestParam String grade) {
        return chartService.getChartDataByGrade(grade);
    }

    /**
     * 모든 등급의 차트 데이터를 조회하는 API
     * URL: /api/chartData/all
     *
     * @return 모든 등급의 차트 데이터 리스트
     *
     * 응답 예시:
     * [
     *   {
     *     "grade": "1++",
     *     "date": "2024-03-15",
     *     "price": 50000
     *   },
     *   {
     *     "grade": "1+",
     *     "date": "2024-03-15",
     *     "price": 48000
     *   },
     *   ...
     * ]
     */
    @GetMapping("/all")
    public List<ChartData> getAllChartData() {
        return chartService.getAllChartData();
    }

    /**
     * 모든 가격 정보를 조회하는 API
     * URL: /api/chartData/all_prices
     *
     * @return 모든 가격 정보 리스트
     *
     * getAllChartData()와의 차이점:
     * - 가공되지 않은 원본 가격 데이터 제공
     * - 통계 처리나 그룹화가 되지 않은 개별 가격 정보
     */
    @GetMapping("/all_prices")
    public List<ChartData> getAllPricesData() {
        return chartService.getAllPrices();
    }
}