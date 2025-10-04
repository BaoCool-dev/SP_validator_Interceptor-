package ltw.vn.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ltw.vn.Entity.User;

/**
 * Interceptor này kiểm soát quyền truy cập vào các trang.
 * Nó sẽ kiểm tra xem người dùng đã đăng nhập hay chưa và có đúng vai trò (role)
 * để truy cập một trang cụ thể hay không.
 */
@Component // Đánh dấu đây là một Spring Bean để có thể inject
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng đã đăng nhập từ session
        // Bạn cần đảm bảo đã lưu user vào session với key "currentUser" khi đăng nhập thành công
        User currentUser = (User) session.getAttribute("currentUser");

        // TRƯỜNG HỢP 1: CHƯA ĐĂNG NHẬP
        if (currentUser == null) {
            // Chuyển hướng về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Ngừng request
        }

        // TRƯỜNG HỢP 2: ĐÃ ĐĂNG NHẬP
        String requestURI = request.getRequestURI();

        // Kiểm tra quyền truy cập vào các trang quản trị "/admin/**"
        if (requestURI.startsWith(request.getContextPath() + "/admin")) {
            // Nếu không phải là role "ADMIN"
            if (!"ADMIN".equals(currentUser.getRole())) {
                // Chuyển hướng về trang chủ của người dùng
                response.sendRedirect(request.getContextPath() + "/");
                return false; // Ngừng request
            }
        }

        // Nếu tất cả các điều kiện đều thỏa mãn, cho phép request tiếp tục
        return true;
    }
}