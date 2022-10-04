package net.autourducode.springwebclient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.annotation.Resource;
import java.time.Duration;

@SpringBootTest
class SpringWebClientApplicationTests {
    @Resource
    private WebClient webClient;

    @Test
    public void postUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        String str = webClient.post()
                .uri("/users/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(utilisateur), Utilisateur.class)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.fixedDelay(5, Duration.ZERO))
                .block();
        System.out.println(str);
    }

    @Test
    public void getUtilisateurs() {
        String str = webClient.get()
                .uri("/users/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(str);
    }

}
