package flcat.beef_wholesale_prices.beef;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 소고기 가격 정보를 담는 Entity 클래스
 *
 * @NoArgsConstructor(access = AccessLevel.PROTECTED):
 *   - JPA를 위한 기본 생성자를 protected로 생성
 *   - 외부에서 직접 new Beef()를 호출하는 것을 방지하여 객체 생성을 제한
 *
 * @Entity: JPA 엔티티임을 명시
 * @ToString: 객체의 문자열 표현을 자동 생성 (디버깅 등에 유용)
 * @Getter: 모든 필드의 getter 메서드 자동 생성
 * @Table: 매핑할 데이터베이스 테이블 지정
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Getter
@Table(name = "meat_prices")
public class Beef {
    /**
     * 가격 정보의 고유 식별자
     * AUTO_INCREMENT로 자동 생성되는 Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 가격 정보가 해당되는 날짜
     * 예: 2024-03-15
     */
    private Date date;

    /**
     * 브랜드/종류 정보
     * 예: 한우, 육우 등
     */
    private String brand;

    /**
     * 원산지 정보
     * 예: 국내산, 수입산 등
     */
    private String origin;

    /**
     * 부위 정보
     * 예: 등심, 안심, 채끝 등
     */
    private String part;

    /**
     * 등급 정보
     * 예: 1++, 1+, 1, 2, 3 등
     */
    private String grade;

    /**
     * kg당 가격 (원 단위)
     * 데이터베이스 컬럼명은 'price_kg'로 매핑
     */
    @Column(name = "price_kg")
    private int priceKg;
}