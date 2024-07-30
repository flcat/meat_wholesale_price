package flcat.beef_wholesale_prices.service;

import flcat.beef_wholesale_prices.beef.Beef;
import flcat.beef_wholesale_prices.beef.BeefRepository;
import flcat.beef_wholesale_prices.dto.ChartData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChartService {
    private final BeefRepository beefRepository;

    public List<String> getAllGrades() {
        return beefRepository.findDistinctGrades().stream()
            .map(this::normalizeGrade)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }

    public List<String> getAllBrands() {
        return beefRepository.findDistinctBrands();
    }

    private String normalizeGrade(String grade) {
        if (grade == null || grade.trim().isEmpty()) {
            return null;
        }
        grade = grade.trim();

        // 한글 등급 처리
        if (grade.matches("^[가-힣]+$")) {
            return grade;
        }

        // 영문 등급 처리
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
    private String normalizePart(String part) {
        if (part.contains("앞다리") || part.contains("전각")) {
            return "앞다리/전각";
        }
        return part;
    }

    public List<ChartData> getChartDataByGrade(String grade) {
        String normalizedGrade = normalizeGrade(grade);
        if (normalizedGrade == null) {
            return new ArrayList<>(); // 등급 정보가 없는 경우 빈 리스트 반환
        }

        List<Beef> prices = beefRepository.findByGradeOrderByDateAndPart(normalizedGrade);
        Map<Date, Map<String, List<Beef>>> groupedPrices = prices.stream()
            .collect(Collectors.groupingBy(Beef::getDate,
                Collectors.groupingBy(beef -> normalizePart(beef.getPart()))));

        List<ChartData> chartDataList = new ArrayList<>();
        for (Map.Entry<Date, Map<String, List<Beef>>> dateEntry : groupedPrices.entrySet()) {
            Date date = dateEntry.getKey();
            for (Map.Entry<String, List<Beef>> partEntry : dateEntry.getValue().entrySet()) {
                String part = partEntry.getKey();
                List<Beef> partBeefs = partEntry.getValue();
                int minPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).min().orElse(0);
                int maxPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).max().orElse(0);
                double avgPrice = partBeefs.stream().mapToInt(Beef::getPriceKg).average().orElse(0);
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

    public List<ChartData> getAllChartData() {
        List<String> grades = getAllGrades();
        return grades.stream()
            .flatMap(grade -> getChartDataByGrade(grade).stream())
            .collect(Collectors.toList());
    }

    public List<ChartData> getAllPrices() {
        List<Beef> prices = beefRepository.findAll();
        return prices.stream()
            .map(this::convertBeefToChartData)
            .collect(Collectors.toList());
    }

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