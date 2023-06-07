package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//
//        log.info("userDetailsService.....................");
//        log.info(user);
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.antMatchers("/swagger-ui/**").authenticated();
            auth.anyRequest().authenticated();  // 나머지 요청은 권한이 있으면 허용.
        }).formLogin(formLogin ->
                formLogin
//                        .loginPage("/sample/login")
//                        .usernameParameter("")
//                        .passwordParameter("")
//                        .loginProcessingUrl("")
//                        .failureUrl("")
                        .defaultSuccessUrl("/sample/all")
//                        .permitAll()
        ).logout(logout ->
                logout.logoutUrl("/sample/logout")
                        .logoutSuccessUrl("/")
        ).exceptionHandling(except ->
                except.accessDeniedPage("/sample/denied")
        );

        http.httpBasic();
        http.cors();
        http.csrf().disable();

        return http.build();
    }


    // swagger configuration
//    @Bean
//    public OpenAPI customAPI() {
//        Info info = new Info().
//                title("Spring boot")
//                .description("")
//                .version("");
//
//        // Security 스키마 설정
//        SecurityScheme bearerAuth = new SecurityScheme()
//                .name(HttpHeaders.AUTHORIZATION)
//                .scheme("basic")
//                .type(SecurityScheme.Type.HTTP)
//                .in(SecurityScheme.In.HEADER);
//
//        // Security 요청 설정
//        SecurityRequirement securityItem = new SecurityRequirement();
//        securityItem.addList("Authorization");
//
//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("Authorization", bearerAuth))
//                .addSecurityItem(securityItem)
//                .info(info);
//    }

}

