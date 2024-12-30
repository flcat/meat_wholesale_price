package flcat.beef_wholesale_prices.handler;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 애플리케이션 전역의 예외를 처리하는 핸들러 클래스
 * @Slf4j: 로깅을 위한 어노테이션
 * @ControllerAdvice: 모든 컨트롤러에서 발생하는 예외를 잡아서 처리
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 유효성 검증(Validation) 실패 시 발생하는 예외를 처리
     * 예: @Valid 어노테이션이 붙은 객체의 검증이 실패했을 때
     *
     * @param ex 발생한 예외 객체
     * @return 400 Bad Request 상태코드와 에러 메시지를 포함한 ResponseEntity
     *
     * 응답 예시:
     * {
     *   "message": "Validation Failed",
     *   "errors": [
     *     "가격은 0보다 커야 합니다",
     *     "날짜는 필수입니다"
     *   ]
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 모든 필드 에러 메시지를 리스트로 수집
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)  // 각 필드 에러의 기본 메시지 추출
            .collect(Collectors.toList());

        // 에러 내용 로깅
        log.error("Validation error: {}", errors);

        // 에러 응답 생성 및 반환
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Validation Failed", errors));
    }

    /**
     * 요청한 리소스를 찾을 수 없을 때 발생하는 예외를 처리
     * 예: 존재하지 않는 URL로 요청이 들어왔을 때
     *
     * @param ex 발생한 예외 객체
     * @return 404 Not Found 상태코드와 에러 메시지를 포함한 ResponseEntity
     *
     * 응답 예시:
     * {
     *   "message": "Resource not found",
     *   "errors": [
     *     "요청한 리소스를 찾을 수 없습니다: /api/unknown-path"
     *   ]
     * }
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        // 에러 응답 생성
        ErrorResponse error = new ErrorResponse(
            "Resource not found",           // 에러 메시지
            List.of(ex.getMessage())        // 상세 에러 내용
        );

        // 404 상태코드와 함께 에러 응답 반환
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}