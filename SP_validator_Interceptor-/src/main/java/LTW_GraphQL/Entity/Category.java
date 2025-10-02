package LTW_GraphQL.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String images;

    // mappedBy="categories" chỉ ra rằng User entity là chủ sở hữu của mối quan hệ
    // và thông tin về bảng join được định nghĩa ở User.
    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude // Tránh vòng lặp vô hạn khi dùng Lombok
    @ToString.Exclude // Tránh vòng lặp vô hạn khi dùng Lombok
    private Set<User> users = new HashSet<>();
}