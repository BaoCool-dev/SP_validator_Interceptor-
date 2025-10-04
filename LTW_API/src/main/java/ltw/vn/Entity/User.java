package ltw.vn.Entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Class này đại diện cho thông tin người dùng, thường được dùng làm DTO
 * (Data Transfer Object) cho các form đăng ký, đăng nhập.
 * Các annotation validation đảm bảo dữ liệu đầu vào là hợp lệ.
 */
@Data
public class User {

    // Giả sử bạn sẽ có các trường này khi lấy từ Database
    private Long id;
    private String role; // Ví dụ: "ADMIN" hoặc "USER"

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 3, max = 50, message = "Tên người dùng phải có từ 3 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
             message = "Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm chữ cái và số")
    private String password;

    // Constructors, Getters, Setters được tự động tạo bởi Lombok @Data
}