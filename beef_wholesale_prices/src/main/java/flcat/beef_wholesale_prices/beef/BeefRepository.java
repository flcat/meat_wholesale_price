package flcat.beef_wholesale_prices.beef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeefRepository extends JpaRepository<Beef, Long> {
    @Query("SELECT DISTINCT b.grade FROM Beef b ORDER BY b.grade")
    List<String> findDistinctGrades();

    @Query("SELECT DISTINCT b.brand FROM Beef b ORDER BY b.brand")
    List<String> findDistinctBrands();

    @Query("SELECT b FROM Beef b WHERE b.grade = :grade ORDER BY b.date, b.part")
    List<Beef> findByGradeOrderByDateAndPart(String grade);
}