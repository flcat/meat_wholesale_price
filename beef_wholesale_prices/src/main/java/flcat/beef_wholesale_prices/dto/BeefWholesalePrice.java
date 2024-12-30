package flcat.beef_wholesale_prices.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 소고기 도매가격 정보를 담는 Entity 클래스
 *
 * @Entity: 이 클래스가 데이터베이스 테이블과 매핑됨을 나타냄
 * @Table: 매핑될 데이터베이스 테이블 이름을 지정
 * @Getter: 모든 필드의 getter 메소드를 자동 생성
 * @NoArgsConstructor: 매개변수 없는 기본 생성자를 자동 생성
 * @AllArgsConstructor: 모든 필드를 매개변수로 받는 생성자를 자동 생성
 * @Builder: 빌더 패턴을 사용할 수 있게 해주는 어노테이션
 */
@Entity
@Table(name = "meat_prices")  // 실제 데이터베이스 테이블 이름은 'meat_prices'
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeefWholesalePrice {

    /**
     * 가격 정보의 고유 식별자 (Primary Key)
     * @Id: 이 필드가 기본키임을 나타냄
     * @GeneratedValue: 기본키 값을 자동으로 생성
     * IDENTITY 전략: 데이터베이스의 AUTO_INCREMENT 기능을 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 가격 정보가 해당되는 날짜
     * DB 컬럼명도 'date'로 동일하게 매핑됨
     */
    private Date date;

    /**
     * 브랜드명 (예: 한우, 육우 등)
     * DB 컬럼명도 'brand'로 동일하게 매핑됨
     */
    private String brand;

    /**
     * 원산지 정보
     * DB 컬럼명도 'origin'으로 동일하게 매핑됨
     */
    private String origin;

    /**
     * 부위 정보 (예: 등심, 안심 등)
     * DB 컬럼명도 'part'로 동일하게 매핑됨
     */
    private String part;

    /**
     * 등급 정보 (예: 1++, 1+, 1 등)
     * DB 컬럼명도 'grade'로 동일하게 매핑됨
     */
    private String grade;

    /**
     * kg당 가격 정보 (원 단위)
     * @Column: 데이터베이스 컬럼명을 'price_kg'로 지정
     */
    @Column(name = "price_kg")
    private int priceKg;
}