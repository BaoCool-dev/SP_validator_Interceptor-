package ltw.vn.Config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF cho REST API
            .authorizeHttpRequests(auth -> auth
                // ADMIN có quyền truy cập vào /admin/**
                .requestMatchers("/admin/**").hasRole("ADMIN") 
                // USER có quyền truy cập vào /user/** và trang chủ user
                .requestMatchers("/user", "/user/**").hasRole("USER") 
                // Các trang công khai/đăng nhập
                .requestMatchers("/login", "/register", "/public/**").permitAll() 
                // Các request khác phải được xác thực
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Định nghĩa trang login
                .permitAll()
                // Xử lý chuyển hướng sau khi đăng nhập thành công
                .successHandler(authenticationSuccessHandler()) 
            )
            .logout(logout -> logout
                .permitAll()
                .logoutSuccessUrl("/login?logout")
            )
            .exceptionHandling(exceptions -> exceptions
                // Xử lý lỗi truy cập bị từ chối (403 Forbidden)
                .accessDeniedPage("/login?access-denied") 
            ); 

        return http.build();
    }
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            protected String determineTargetUrl(HttpServletRequest request, 
                                                HttpServletResponse response, 
                                                Authentication authentication) {
                
                // Lấy danh sách roles của người dùng
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                
                // Kiểm tra role và trả về URL đích
                if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return "/admin"; // Chuyển đến trang chủ Admin
                } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                    return "/user"; // Chuyển đến trang chủ User
                } else {
                    // Mặc định hoặc quay về login nếu không có role hợp lệ
                    return "/login?error"; 
                }
            }
        };
    }
    @Bean
    public UserDetailsService userDetailsService() {
        // Đây là ví dụ đơn giản với InMemoryUserDetailsManager.
        // Trong thực tế, bạn sẽ dùng UserDetailsService để load từ DB
        
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("123456")) // Mật khẩu đã mã hóa
            .roles("USER") // Role USER -> Mặc định thành ROLE_USER
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123")) // Mật khẩu đã mã hóa
            .roles("ADMIN") // Role ADMIN -> Mặc định thành ROLE_ADMIN
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Phải mã hóa mật khẩu
    }
}