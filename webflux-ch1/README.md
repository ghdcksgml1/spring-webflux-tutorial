# Chapter. 1

## 프로젝트 생성 후 서버 띄우기

<img width="1546" alt="스크린샷 2023-08-17 오후 3 03 58" src="https://github.com/ghdcksgml1/spring-webflux-tutorial/assets/79779676/5c410145-b26a-4372-b8a7-05163c6528bc">

## WebFluxConfigurationSupport

Spring WebMvc의 WebMvcConfigurationSupport의 역할을 WebFlux에서는 WebFluxConfigurationSupport가 해준다.

```java
@Configuration
@EnableWebFlux
public class AppConfig implements WebFluxConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // ...
    }
    
} 
```

위와 같이 WebMvc와 비슷한 방법으로 사용자가 원하는 커스텀 설정이 가능하다.