package flcat.beef_wholesale_prices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient 설정을 담당하는 Configuration 클래스
 *
 * @Configuration: 이 클래스가 Spring의 설정 클래스임을 나타냄
 * WebClient는 Spring에서 제공하는 비동기 HTTP 클라이언트로,
 * RestTemplate의 현대적인 대안으로 사용됨
 */
@Configuration
public class WebClientConfig {

    /**
     * application.properties/yml 파일에서 api.url 값을 주입받음
     * 예: api.url=https://api.example.com
     */
    @Value("${api.url}")
    private String apiUrl;

    /**
     * WebClient 빈을 생성하는 메서드
     *
     * 설정내용:
     * - 리다이렉트 자동 처리 활성화
     * - 기본 URL 설정
     *
     * @return 설정이 완료된 WebClient 인스턴스
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            // HttpClient 설정: 리다이렉트 자동 처리 활성화
            .clientConnector(new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
            ))
            // 모든 요청의 기본 URL 설정
            .baseUrl(apiUrl)
            .build();
    }
}