package flcat.beef_wholesale_prices.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * 날짜 관련 유틸리티 기능을 제공하는 클래스
 *
 * @Component: 스프링 컴포넌트로 등록되어 다른 클래스에서 주입받아 사용 가능
 */
@Component
public class DateUtil {

    /**
     * 날짜를 "yyyy-MM-dd" 형식으로 변환하기 위한 포매터
     * private static final: 변경 불가능한 상수로 선언
     * 예: "2024-03-15" 형식
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 오늘 날짜와 일주일 전 날짜를 리스트로 반환하는 메서드
     *
     * 사용 예시:
     * 오늘이 2024-03-15라면,
     * [2024-03-15, 2024-03-08] 반환
     *
     * @return 오늘과 일주일 전 날짜가 담긴 리스트
     */
    public List<LocalDate> getRelevantDates() {
        LocalDate today = LocalDate.now();          // 오늘 날짜
        LocalDate weekAgo = today.minusWeeks(1);    // 1주일 전 날짜
        return Arrays.asList(today, weekAgo);       // 두 날짜를 리스트로 반환
    }

    /**
     * LocalDate 객체를 "yyyy-MM-dd" 형식의 문자열로 변환하는 메서드
     *
     * 사용 예시:
     * LocalDate date = LocalDate.of(2024, 3, 15);
     * String formatted = formatDate(date);  // "2024-03-15" 반환
     *
     * @param date 변환할 LocalDate 객체
     * @return "yyyy-MM-dd" 형식의 문자열
     */
    public String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}