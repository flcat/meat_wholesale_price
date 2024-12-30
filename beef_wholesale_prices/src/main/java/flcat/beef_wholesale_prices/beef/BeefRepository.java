package flcat.beef_wholesale_prices.beef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 소고기 가격 정보에 대한 데이터베이스 operations을 정의하는 Repository 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공받음
 *
 * JpaRepository<Beef, Long>:
 * - Beef: 다루는 엔티티 타입
 * - Long: 엔티티의 ID(기본키) 타입
 */
public interface BeefRepository extends JpaRepository<Beef, Long> {

    /**
     * 모든 소고기 등급을 중복 없이 조회하는 메서드
     *
     * @Query: JPQL(Java Persistence Query Language)로 작성된 커스텀 쿼리
     * DISTINCT: 중복 제거
     * ORDER BY: 등급 기준 오름차순 정렬
     *
     * 예시 결과: ["1++", "1+", "1", "2", "3"]
     *
     * @return 중복이 제거된 등급 목록
     */
    @Query("SELECT DISTINCT b.grade FROM Beef b ORDER BY b.grade")
    List<String> findDistinctGrades();

    /**
     * 모든 브랜드를 중복 없이 조회하는 메서드
     *
     * DISTINCT: 중복 제거
     * ORDER BY: 브랜드명 기준 오름차순 정렬
     *
     * 예시 결과: ["한우", "육우", "수입육"]
     *
     * @return 중복이 제거된 브랜드 목록
     */
    @Query("SELECT DISTINCT b.brand FROM Beef b ORDER BY b.brand")
    List<String> findDistinctBrands();

    /**
     * 특정 등급의 소고기 가격 정보를 날짜와 부위 기준으로 정렬하여 조회하는 메서드
     *
     * WHERE b.grade = :grade: 파라미터로 받은 등급과 일치하는 데이터만 선택
     * ORDER BY b.date, b.part: 날짜 순으로 정렬하고, 같은 날짜 내에서는 부위 기준으로 정렬
     *
     * @param grade 조회할 등급 (예: "1++", "1+")
     * @return 해당 등급의 가격 정보 목록 (날짜, 부위 기준 정렬)
     */
    @Query("SELECT b FROM Beef b WHERE b.grade = :grade ORDER BY b.date, b.part")
    List<Beef> findByGradeOrderByDateAndPart(String grade);
}