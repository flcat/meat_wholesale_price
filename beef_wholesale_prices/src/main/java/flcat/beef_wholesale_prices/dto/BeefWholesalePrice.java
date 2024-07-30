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

@Entity
@Table(name = "meat_prices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeefWholesalePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private String brand;
    private String origin;
    private String part;
    private String grade;

    @Column(name = "price_kg")
    private int priceKg;
}