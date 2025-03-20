package org.example.userservice.config.security.old;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/check/**").access((authentication, context) -> {
//                            HttpServletRequest request = context.getRequest();
//                            String clientIp = request.getRemoteAddr();
//                            return clientIp.equals("192.168.1.100");
//                        })
//                        .requestMatchers("/h2-console/**").access((authentication, context) -> {
//                            HttpServletRequest request = context.getRequest();
//                            String clientIp = request.getRemoteAddr();
//                            return clientIp.equals("192.168.1.100");
//                        })
//                        .anyRequest().authenticated()
//                )
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // H2 Console 표시 허용
//                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화
//
//        return http.build();
//    }
//}
