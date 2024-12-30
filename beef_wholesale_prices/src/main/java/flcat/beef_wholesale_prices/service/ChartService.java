package flcat.beef_wholesale_prices.service;

import flcat.beef_wholesale_prices.beef.Beef;
import flcat.beef_wholesale_prices.beef.BeefRepository;
import flcat.beef_wholesale_prices.dto.ChartData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 소고기 도매가격 차트 데이터를 처리하는 서비스 클래스
 * @Service: 스프링이 이 클래스를 서비스 역할을 하는 클래스로 인식하게 함
 * @Transactional(readOnly = true): 데이터를 읽기만 하고 수정하지 않는다는 것을 명시
 * @RequiredArgsConstructor: BeefRepository를 자동으로 주입(연결)해주는 어노테이션
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChartService {
    // 데이터베이스에서 소고기 정보를 가져오는 역할을 하는 객체
    private final BeefRepository beefRepository;

    /**
     * 데이터베이스에 저장된 모든 소고기 등급을 조회하는 메서드
     * 중복을 제거하고 정규화된(표준화된) 등급 목록을 반환
     */
    public List<String> getAllGrades() {
        return beefRepository.findDistinctGrades().stream()
            .map(this::normalizeGrade)  // 등급명을 표준화
            .filter(Objects::nonNull)    // null값 제거
            .distinct()                  // 중복 제거
            .collect(Collectors.toList());
    }

    /**
     * 데이터베이스에 저장된 모든 브랜드 목록을 조회하는 메서드
     */
    public List<String> getAllBrands() {
        return beefRepository.findDistinctBrands();
    }

    /**
     * 등급명을 표준화하는 private 메서드
     * 예: "CHOICE" -> "초이스", "YG/GF" -> "YGGF" 등으로 변환
     * @param grade 원본 등급명
     * @return 표준화된 등급명
     */
    private String normalizeGrade(String grade) {
        if (grade == null || grade.trim().isEmpty()) {
            return null;
        }
        grade = grade.trim();  // 앞뒤 공백 제거

        // 한글로 된 등급은 그대로 반환
        if (grade.matches("^[가-힣]+$")) {
            return grade;
        }

        // 영문 등급을 처리 (공백 제거 후 대문자로 변환)
        grade = grade.toUpperCase().replaceAll("\\s", "");
        switch (grade) {
            case "YG/GF":
            case "YGGF":
                return "YGGF";
            case "CHOICE":
                return "초이스";
            case "PRIME":
                return "프라임";
            default:
                return grade;
        }
    }

    /**
     * 부위명을 표준화하는 private 메서드
     * "앞다리"와 "전각"을 "앞다리/전각"으로 통일
     */
    private String normalizePart(String part) {
        if (part.contains("앞다리") || part.contains("전각")) {
            return "앞다리/전각";
        }
        return part;
    }

    /**
     * 특정 등급의 차트 데이터를 조회하는 메서드
     * 같은 날짜와 부위별로 최소가격, 최대가격, 평균가격을 계산
     * @param grade 조회할 등급
     * @return 차트에 표시할 데이터 목록
     */
    public List<ChartData> getChartDataByGrade(String grade) {
        String normalizedGrade = normalizeGrade(grade);
        if (normalizedGrade == null) {
            return new ArrayList<>();  // 등급이 null이면 빈 리스트 반환
        }

        // 해당 등급의 모든 가격 데이터를 날짜와 부위별로 그룹화
        List<Beef> prices = beefRepository.findByGradeOrderByDateAndPart(normalizedGrade);
        Map<Date, Map<String, List<Beef>>> groupedPrices = prices.stream()
            .collect(Collectors.groupingBy(Beef::getDate,
                Collectors.groupingBy(beef -> normalizePart(beef.getPart()))));

        List<ChartData> chartDataList = new ArrayList<>();
        // 그룹화된 데이터를 순회하면서 차트 데이터 생성
        for (Map.Entry<Date, Map<String, List<Beef>>> dateEntry : groupedPrices.entrySet()) {
            Date date = dateEntry.getKey();
            for (Map.Entry<String, List<Beef>> partEntry : dateEntry.getValue().entrySet()) {
                String part = partEntry.getKey();
                List<Beef> partBeefs = partEntry.getValue();

                // 최소가격, 최대가격, 평균가격 계산
                int minPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).min().orElse(0);
                int maxPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).max().orElse(0);
                double avgPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).average().orElse(0);

                // 최소/최대 가격을 가진 브랜드 찾기
                String minPriceBrand = partBeefs.stream()
                    .filter(beef -> beef.getPriceKg() == minPrice)
                    .findFirst()
                    .map(Beef::getBrand)
                    .orElse("");
                String maxPriceBrand = partBeefs.stream()
                    .filter(beef -> beef.getPriceKg() == maxPrice)
                    .findFirst()
                    .map(Beef::getBrand)
                    .orElse("");

                // 차트 데이터 객체 생성 및 리스트에 추가
                chartDataList.add(new ChartData(
                    normalizedGrade,
                    part,
                    date,
                    minPrice,
                    maxPrice,
                    (int) Math.round(avgPrice),
                    minPriceBrand,
                    maxPriceBrand
                ));
            }
        }
        return chartDataList;
    }

    /**
     * 모든 등급의 차트 데이터를 조회하는 메서드
     * 각 등급별로 getChartDataByGrade를 호출하여 결과를 합침
     */
    public List<ChartData> getAllChartData() {
        List<String> grades = getAllGrades();
        return grades.stream()
            .flatMap(grade -> getChartDataByGrade(grade).stream())
            .collect(Collectors.toList());
    }

    /**
     * 데이터베이스의 모든 가격 정보를 차트 데이터 형식으로 조회하는 메서드
     */
    public List<ChartData> getAllPrices() {
        List<Beef> prices = beefRepository.findAll();
        return prices.stream()
            .map(this::convertBeefToChartData)
            .collect(Collectors.toList());
    }

    /**
     * Beef 엔티티를 ChartData 객체로 변환하는 private 메서드
     * 단일 가격 정보이므로 최소/최대/평균 가격이 모두 동일
     */
    private ChartData convertBeefToChartData(Beef beef) {
        return new ChartData(
            beef.getGrade(),
            normalizePart(beef.getPart()),
            beef.getDate(),
            beef.getPriceKg(),
            beef.getPriceKg(),
            beef.getPriceKg(),
            beef.getBrand(),
            beef.getBrand()
        );
    }
}