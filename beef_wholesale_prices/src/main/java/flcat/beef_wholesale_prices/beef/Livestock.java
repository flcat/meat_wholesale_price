package flcat.beef_wholesale_prices.beef;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 가축 가격 정보를 담는 DTO(Data Transfer Object) 클래스
 *
 * @Data: equals(), hashCode(), toString() 등의 메서드를 자동 생성
 * @Getter: 모든 필드의 getter 메서드 자동 생성
 * @Setter: 모든 필드의 setter 메서드 자동 생성
 *
 * 참고: @Data가 @Getter와 @Setter를 포함하고 있어서 중복된 어노테이션이 있음
 */
@Data
@Getter
@Setter
public class Livestock {
    /**
     * 품목명 (예: 소, 돼지, 닭)
     * JSON 필드명: "item_name"
     */
    @JsonProperty("item_name")
    private String itemName;

    /**
     * 품종명 (예: 한우, 육우, 토종닭)
     * JSON 필드명: "kind_name"
     */
    @JsonProperty("kind_name")
    private String kindName;

    /**
     * 거래 단위 (예: kg, 마리)
     * JSON 필드명: "unit"
     */
    @JsonProperty("unit")
    private String unit;

    /**
     * 1일차 날짜
     * JSON 필드명: "day1"
     */
    @JsonProperty("day1")
    private String day1;

    /**
     * 1일차 가격 등락률
     * JSON 필드명: "dpr1" (daily price rate)
     */
    @JsonProperty("dpr1")
    private String dpr1;

    /**
     * 2일차 날짜
     * JSON 필드명: "day2"
     */
    @JsonProperty("day2")
    private String day2;

    /**
     * 2일차 가격 등락률
     * JSON 필드명: "dpr2"
     */
    @JsonProperty("dpr2")
    private String dpr2;

    /**
     * 3일차 날짜
     * JSON 필드명: "day3"
     */
    @JsonProperty("day3")
    private String day3;

    /**
     * 3일차 가격 등락률
     * JSON 필드명: "dpr3"
     */
    @JsonProperty("dpr3")
    private String dpr3;

    /**
     * 4일차 날짜
     * JSON 필드명: "day4"
     */
    @JsonProperty("day4")
    private String day4;

    /**
     * 4일차 가격 등락률
     * JSON 필드명: "dpr4"
     */
    @JsonProperty("dpr4")
    private String dpr4;

    /**
     * 5일차 날짜
     * JSON 필드명: "day5"
     */
    @JsonProperty("day5")
    private String day5;

    /**
     * 5일차 가격 등락률
     * JSON 필드명: "dpr5"
     */
    @JsonProperty("dpr5")
    private String dpr5;

    /**
     * 6일차 날짜
     * JSON 필드명: "day6"
     */
    @JsonProperty("day6")
    private String day6;

    /**
     * 6일차 가격 등락률
     * JSON 필드명: "dpr6"
     */
    @JsonProperty("dpr6")
    private String dpr6;

    /**
     * 7일차 날짜
     * JSON 필드명: "day7"
     */
    @JsonProperty("day7")
    private String day7;

    /**
     * 7일차 가격 등락률
     * JSON 필드명: "dpr7"
     */
    @JsonProperty("dpr7")
    private String dpr7;
}