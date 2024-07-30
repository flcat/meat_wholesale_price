package flcat.beef_wholesale_prices.service;

import flcat.beef_wholesale_prices.beef.BeefWholesalePriceRepository;
import flcat.beef_wholesale_prices.dto.BeefWholesalePrice;
import flcat.beef_wholesale_prices.dto.BeefWholesalePriceDto;
import flcat.beef_wholesale_prices.mapper.BeefWholesalePriceMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeefWholesalePriceService {

    private final BeefWholesalePriceRepository beefWholesalePriceRepository;
    private final BeefWholesalePriceMapper beefWholesalePriceMapper;

    @Transactional(readOnly = true)
    public List<BeefWholesalePriceDto> getAllPrices() {
        return beefWholesalePriceRepository.findAll().stream()
            .map(beefWholesalePriceMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BeefWholesalePriceDto> getPriceById(Long id) {
        return beefWholesalePriceRepository.findById(id)
            .map(beefWholesalePriceMapper::toDto);
    }

    @Transactional
    public BeefWholesalePriceDto createPrice(BeefWholesalePriceDto priceDto) {
        BeefWholesalePrice price = beefWholesalePriceMapper.toEntity(priceDto);
        BeefWholesalePrice savedPrice = beefWholesalePriceRepository.save(price);
        return beefWholesalePriceMapper.toDto(savedPrice);
    }

    @Transactional
    public Optional<BeefWholesalePriceDto> updatePrice(Long id, BeefWholesalePriceDto priceDto) {
        return beefWholesalePriceRepository.findById(id)
            .map(existingPrice -> {
                beefWholesalePriceMapper.updateEntityFromDto(priceDto, existingPrice);
                BeefWholesalePrice updatedPrice = beefWholesalePriceRepository.save(existingPrice);
                return beefWholesalePriceMapper.toDto(updatedPrice);
            });
    }

    @Transactional
    public void deletePrice(Long id) {
        beefWholesalePriceRepository.deleteById(id);
    }
}