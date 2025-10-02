package LTW_GraphQL.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "app_user") // Đổi tên bảng để tránh trùng từ khóa "USER"
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullname;
	private String email;
	private String password;
	private String phone;

	// Định nghĩa mối quan hệ nhiều-nhiều với Category
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_category", // Tên bảng trung gian
			joinColumns = @JoinColumn(name = "user_id"), // Khóa ngoại trỏ đến User
			inverseJoinColumns = @JoinColumn(name = "category_id") // Khóa ngoại trỏ đến Category
	)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Category> categories = new HashSet<>();
}