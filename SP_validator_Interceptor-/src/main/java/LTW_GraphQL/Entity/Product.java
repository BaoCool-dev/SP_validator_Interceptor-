package LTW_GraphQL.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int quantity;

    @Column(name = "description") // Đổi tên cột để khớp với "desc" trong database
    private String desc;
    
    private double price;

    // Định nghĩa mối quan hệ nhiều-một với User
    // Nhiều sản phẩm có thể thuộc về một người dùng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid") // Tên cột khóa ngoại trong bảng Product
    private User user;
}