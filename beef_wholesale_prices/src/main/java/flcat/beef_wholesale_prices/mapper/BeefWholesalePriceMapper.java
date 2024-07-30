package flcat.beef_wholesale_prices.mapper;

import flcat.beef_wholesale_prices.dto.BeefWholesalePrice;
import flcat.beef_wholesale_prices.dto.BeefWholesalePriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BeefWholesalePriceMapper {
    BeefWholesalePriceDto toDto(BeefWholesalePrice entity);
    BeefWholesalePrice toEntity(BeefWholesalePriceDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(BeefWholesalePriceDto dto, @MappingTarget BeefWholesalePrice entity);
}