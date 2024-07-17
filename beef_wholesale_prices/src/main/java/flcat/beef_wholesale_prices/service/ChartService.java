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
        return beefRepository.findDistinctGrades();
    }

    public List<String> getAllBrands() {
        return beefRepository.findDistinctBrands();
    }

    private String normalizePart(String part) {
        if (part.contains("앞다리") || part.contains("전각")) {
            return "앞다리/전각";
        }
        return part;
    }

    public List<ChartData> getChartDataByGrade(String grade) {
        List<Beef> prices = beefRepository.findByGradeOrderByDateAndPart(grade);
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
                    grade,
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
}