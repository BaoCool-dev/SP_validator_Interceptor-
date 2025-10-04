package ltw.vn.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ltw.vn.Interceptor.AuthInterceptor;

/**
 * Class cấu hình cho Spring MVC.
 * Dùng để đăng ký các Interceptor.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor; // Inject AuthInterceptor đã được tạo bởi Spring

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                // addPathPatterns: Áp dụng Interceptor cho tất cả các URL
                .addPathPatterns("/**")
                // excludePathPatterns: Loại trừ các URL không cần kiểm tra
                .excludePathPatterns(
                        "/login",           // Trang đăng nhập
                        "/register",        // Trang đăng ký
                        "/api/**",          // Các API công khai (nếu có, ví dụ API đăng nhập)
                        "/css/**",          // Thư mục chứa file CSS
                        "/js/**",           // Thư mục chứa file JavaScript
                        "/images/**"        // Thư mục chứa hình ảnh
                );
    }
}