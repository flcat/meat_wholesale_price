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

/**
 * 소고기 도매가격 정보를 처리하는 서비스 클래스
 * @Slf4j: 로깅을 위한 어노테이션 (log.info(), log.error() 등을 사용할 수 있게 해줌)
 * @Service: 이 클래스를 스프링의 서비스 컴포넌트로 등록
 * @RequiredArgsConstructor: final로 선언된 필드의 생성자를 자동으로 만들어줌
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeefWholesalePriceService {

    // 데이터베이스 작업을 처리하는 레포지토리
    private final BeefWholesalePriceRepository beefWholesalePriceRepository;
    // Entity와 DTO 간의 변환을 처리하는 매퍼
    private final BeefWholesalePriceMapper beefWholesalePriceMapper;

    /**
     * 모든 도매가격 정보를 조회하는 메서드
     * @Transactional(readOnly = true): 읽기 전용 트랜잭션으로 설정 (성능 최적화)
     * @return 모든 도매가격 정보의 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<BeefWholesalePriceDto> getAllPrices() {
        return beefWholesalePriceRepository.findAll().stream()  // 모든 데이터 조회
            .map(beefWholesalePriceMapper::toDto)              // Entity를 DTO로 변환
            .collect(Collectors.toList());                      // 리스트로 수집
    }

    /**
     * ID로 특정 도매가격 정보를 조회하는 메서드
     * @param id 조회할 가격 정보의 ID
     * @return 찾은 가격 정보를 Optional로 감싸서 반환 (없으면 빈 Optional)
     */
    @Transactional(readOnly = true)
    public Optional<BeefWholesalePriceDto> getPriceById(Long id) {
        return beefWholesalePriceRepository.findById(id)  // ID로 데이터 조회
            .map(beefWholesalePriceMapper::toDto);       // 찾으면 DTO로 변환
    }

    /**
     * 새로운 도매가격 정보를 생성하는 메서드
     * @param priceDto 생성할 가격 정보
     * @return 저장된 가격 정보의 DTO
     */
    @Transactional  // 데이터를 변경하는 트랜잭션
    public BeefWholesalePriceDto createPrice(BeefWholesalePriceDto priceDto) {
        BeefWholesalePrice price = beefWholesalePriceMapper.toEntity(priceDto);  // DTO를 Entity로 변환
        BeefWholesalePrice savedPrice = beefWholesalePriceRepository.save(price); // 데이터베이스에 저장
        return beefWholesalePriceMapper.toDto(savedPrice);                        // 저장된 Entity를 DTO로 변환하여 반환
    }

    /**
     * 기존 도매가격 정보를 수정하는 메서드
     * @param id 수정할 가격 정보의 ID
     * @param priceDto 새로운 가격 정보
     * @return 수정된 가격 정보를 Optional로 감싸서 반환 (없으면 빈 Optional)
     */
    @Transactional
    public Optional<BeefWholesalePriceDto> updatePrice(Long id, BeefWholesalePriceDto priceDto) {
        return beefWholesalePriceRepository.findById(id)    // ID로 기존 데이터 조회
            .map(existingPrice -> {                         // 데이터가 존재하면
                beefWholesalePriceMapper.updateEntityFromDto(priceDto, existingPrice);  // DTO의 내용으로 Entity 업데이트
                BeefWholesalePrice updatedPrice = beefWholesalePriceRepository.save(existingPrice);  // 변경사항 저장
                return beefWholesalePriceMapper.toDto(updatedPrice);  // 업데이트된 Entity를 DTO로 변환하여 반환
            });
    }

    /**
     * 도매가격 정보를 삭제하는 메서드
     * @param id 삭제할 가격 정보의 ID
     */
    @Transactional
    public void deletePrice(Long id) {
        beefWholesalePriceRepository.deleteById(id);  // ID로 데이터 삭제
    }
}