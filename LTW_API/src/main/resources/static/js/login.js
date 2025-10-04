document.addEventListener('DOMContentLoaded', () => {
    // Lắng nghe sự kiện submit của form có id là 'loginForm'
    const loginForm = document.getElementById('loginForm');
    const messageDiv = document.getElementById('loginMessage');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // Ngăn chặn hành vi submit form mặc định

            // Lấy dữ liệu từ form
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // Thiết lập thông báo trạng thái
            messageDiv.textContent = 'Đang đăng nhập...';
            messageDiv.style.color = 'blue';

            try {
                // 1. Gửi yêu cầu POST đến API đăng nhập
                const response = await fetch('/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ username, password })
                });

                // 2. Xử lý phản hồi từ Server
                if (response.ok) {
                    // Nếu đăng nhập thành công (status 200-299)
                    messageDiv.textContent = 'Đăng nhập thành công! Đang chuyển hướng...';
                    messageDiv.style.color = 'green';
                    
                    // Xử lý chuyển hướng theo Roles (theo yêu cầu của bạn)
                    // Lưu ý: Trong Spring Security, việc chuyển hướng theo Role thường được 
                    // xử lý ở phía Server (AuthenticationSuccessHandler).
                    // Nếu Server trả về Role, ta có thể xử lý ở đây.
                    
                    // Ví dụ nếu Server trả về JSON chứa role: { token: "...", role: "ADMIN" }
                    const data = await response.json(); 
                    
                    if (data && data.role === 'ADMIN') {
                        // Chuyển hướng đến trang Admin
                        window.location.href = '/admin'; 
                    } else if (data && data.role === 'USER') {
                        // Chuyển hướng đến trang User
                        window.location.href = '/user'; 
                    } else {
                        // Chuyển hướng mặc định hoặc dựa vào logic Server
                        window.location.href = '/'; 
                    }

                } else if (response.status === 401 || response.status === 403) {
                    // Nếu lỗi xác thực (Unauthorized/Forbidden)
                    messageDiv.textContent = 'Lỗi đăng nhập: Tên người dùng hoặc mật khẩu không đúng.';
                    messageDiv.style.color = 'red';
                } else {
                    // Xử lý các lỗi khác (ví dụ: 500 Internal Server Error)
                    messageDiv.textContent = `Lỗi Server: ${response.statusText}`;
                    messageDiv.style.color = 'red';
                }

            } catch (error) {
                // Lỗi kết nối mạng
                console.error('Lỗi khi gửi yêu cầu:', error);
                messageDiv.textContent = 'Lỗi kết nối. Vui lòng thử lại sau.';
                messageDiv.style.color = 'red';
            }
        });
    }
});/**
 * 
 */