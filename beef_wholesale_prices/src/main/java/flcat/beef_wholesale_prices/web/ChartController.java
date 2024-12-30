package flcat.beef_wholesale_prices.web;

import flcat.beef_wholesale_prices.dto.ChartData;
import flcat.beef_wholesale_prices.service.ChartService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 차트 관련 요청을 처리하는 컨트롤러
 *
 * @Controller: 이 클래스가 Spring MVC의 컨트롤러임을 나타냄
 * (@RestController와 달리 View 반환이 가능)
 *
 * @RequiredArgsConstructor: final 필드에 대한 생성자를 자동으로 생성
 */
@Controller
@RequiredArgsConstructor
public class ChartController {

    // 차트 데이터 처리를 위한 서비스 객체
    private final ChartService chartService;

    /**
     * 차트 페이지를 보여주는 엔드포인트
     * URL: /charts
     *
     * @param model View에 전달할 데이터를 담는 Model 객체
     * @return "charts" 뷰 템플릿의 이름
     *
     * 템플릿에서 사용 가능한 데이터:
     * - ${grades}: 모든 등급 목록
     * - ${brands}: 모든 브랜드 목록
     */
    @GetMapping("/charts")
    public String showCharts(Model model) {
        // View에 등급과 브랜드 목록 전달
        model.addAttribute("grades", chartService.getAllGrades());
        model.addAttribute("brands", chartService.getAllBrands());
        return "charts";  // charts.html 또는 charts.jsp 등의 뷰 템플릿 반환
    }

    /**
     * 차트 데이터를 JSON 형식으로 제공하는 API 엔드포인트
     * URL: /api/chartData
     *
     * @ResponseBody: 반환값을 HTTP 응답 본문에 직접 작성
     * @return 등급별로 그룹화된 차트 데이터 Map
     *
     * 응답 형식 예시:
     * {
     *   "1++": [
     *     {"date": "2024-03-15", "price": 50000, ...},
     *     {"date": "2024-03-14", "price": 51000, ...}
     *   ],
     *   "1+": [
     *     {"date": "2024-03-15", "price": 48000, ...},
     *     ...
     *   ]
     * }
     */
    @GetMapping("/api/chartData")
    @ResponseBody
    public Map<String, List<ChartData>> getChartData() {
        // 전체 차트 데이터 조회
        List<ChartData> allData = chartService.getAllChartData();

        // 등급별로 데이터 그룹화
        return allData.stream()
            .collect(Collectors.groupingBy(ChartData::getGrade));
    }
}