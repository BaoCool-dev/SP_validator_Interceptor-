$(document).ready(function() {
    // Gọi hàm để tải danh mục khi trang được tải
    loadCategories();

    // Hàm lấy danh mục từ API
    function loadCategories() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8092/api/category', // Đảm bảo URL đúng
            dataType: 'json',
            success: function(response) {
                if (response.status === true) {
                    var categories = response.body;
                    var html = '';
                    // Duyệt qua từng danh mục và hiển thị thông tin
                    categories.forEach(function(category) {
                        // Kiểm tra và xây dựng đường dẫn hình ảnh đúng
                          var imageUrl = 'http://localhost:8092/images/' + category.icon;

                        html += '<li>';
                        html += '<strong>ID:</strong> ' + category.categoryId + '<br>'; // Hiển thị categoryId
                        html += '<strong>Category Name:</strong> ' + category.categoryName + '<br>'; // Hiển thị categoryName
                        html += '<strong>Icon:</strong> <img src="' + imageUrl + '" alt="Icon" style="width:50px;height:50px;" /><br>'; // Hiển thị icon
                        html += '</li>';
                    });
                    $('#categoryList').html(html); // Render danh mục lên giao diện
                } else {
                    $('#categoryList').html('Không có danh mục');
                }
            },
            error: function() {
                $('#categoryList').html('Lỗi khi tải dữ liệu');
            }
        });
    }
});
