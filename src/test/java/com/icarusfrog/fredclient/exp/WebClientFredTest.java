package com.icarusfrog.fredclient.exp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icarusfrog.fredclient.configObj.FredConfig;
import com.icarusfrog.fredclient.domain.Categories;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

public class WebClientFredTest {
    private WebClient webClient;
    private FredConfig fredConfig;
    private ObjectMapper objectMapper;

    public WebClientFredTest(){
        fredConfig = new FredConfig();
        objectMapper = new ObjectMapper();
        webClient = WebClient.builder()
                .baseUrl(fredConfig.getBaseUri())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .build();
    }

    @Test
    public void testMono(){
        Mono<Integer> integerMono = Mono.just(5);
        integerMono.subscribe(System.out::println
        );

        StepVerifier.create(integerMono).expectNext(5).verifyComplete();
    }

    @Test
    public void testCategoryFetch(){
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("category_id", "125");
        urlParams.put("api_key", fredConfig.getApiKey());
        urlParams.put("file_type", "json");
        WebClient.RequestHeadersSpec<?> categoryReq = webClient.get().uri("/category?category_id={category_id}&api_key={api_key}&file_type={file_type}", urlParams);

        WebClient.ResponseSpec responseSpec = categoryReq.retrieve();
        Mono<Categories> response = responseSpec.bodyToMono(Categories.class);
        response.onErrorReturn(new Categories());

//        Categories categories = response.block();
//
//        categories.getCategories();

        response.subscribe(
                (Categories categories) -> {
                    System.out.println(categories.getCategories().size());
                },
                (error) -> {
                    if(error instanceof WebClientResponseException.BadRequest) {
                        WebClientResponseException.BadRequest br = (WebClientResponseException.BadRequest) error;
                        System.out.println(br.getResponseBodyAsString());
                        br.getMessage();
                    }
                    System.out.println("error");
                    error.getCause();
                }
        );

        StepVerifier.create(response).expectNext(new Categories()).expectNext(new Categories()).verifyComplete();
    }
}
