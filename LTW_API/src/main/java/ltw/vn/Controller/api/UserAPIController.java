package ltw.vn.Controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ltw.vn.Entity.User;

@RestController
@RequestMapping("/api/auth") // URL liên quan đến xác thực
public class UserAPIController {

    // Bạn nên inject UserService ở đây để xử lý logic
    // @Autowired
    // private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User registrationDTO) {
        // Logic đăng ký người dùng sau khi validation thành công
        // Ví dụ: userService.register(registrationDTO);
        return ResponseEntity.ok("Đăng ký thành công!");
    }
}
