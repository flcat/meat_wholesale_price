package flcat.beef_wholesale_prices.mapper;

import flcat.beef_wholesale_prices.dto.BeefWholesalePrice;
import flcat.beef_wholesale_prices.dto.BeefWholesalePriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Entity와 DTO 간의 변환을 처리하는 MapStruct 매퍼 인터페이스
 *
 * @Mapper(componentModel = "spring"):
 *   - MapStruct가 Spring 컴포넌트로 구현체를 생성하도록 설정
 *   - 생성된 구현체는 스프링의 빈으로 자동 등록됨
 *
 * unmappedTargetPolicy = ReportingPolicy.IGNORE:
 *   - 매핑되지 않는 필드가 있어도 에러나 경고를 발생시키지 않음
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeefWholesalePriceMapper {

    /**
     * Entity를 DTO로 변환하는 메서드
     * Entity의 모든 필드를 DTO로 자동 매핑
     *
     * @param entity 변환할 Entity 객체
     * @return 변환된 DTO 객체
     */
    BeefWholesalePriceDto toDto(BeefWholesalePrice entity);

    /**
     * DTO를 Entity로 변환하는 메서드
     * DTO의 모든 필드를 Entity로 자동 매핑
     *
     * @param dto 변환할 DTO 객체
     * @return 변환된 Entity 객체
     */
    BeefWholesalePrice toEntity(BeefWholesalePriceDto dto);

    /**
     * DTO의 데이터로 기존 Entity를 업데이트하는 메서드
     *
     * @Mapping(target = "id", ignore = true):
     *   - id 필드는 업데이트하지 않음 (기존 ID 유지)
     *
     * @MappingTarget:
     *   - 업데이트될 대상 객체를 지정
     *
     * @param dto 업데이트할 데이터가 담긴 DTO
     * @param entity 업데이트될 Entity
     */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BeefWholesalePriceDto dto, @MappingTarget BeefWholesalePrice entity);
}