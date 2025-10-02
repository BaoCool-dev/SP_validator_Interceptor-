package LTW_GraphQL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import LTW_GraphQL.Entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Lấy tất cả sản phẩm và sắp xếp theo giá tăng dần. Tên phương thức tuân theo
	 * quy ước của Spring Data JPA.
	 * 
	 * @return Danh sách sản phẩm đã được sắp xếp.
	 */
	List<Product> findAllByOrderByPriceAsc();

	/**
	 * Lấy tất cả sản phẩm thuộc về một danh mục cụ thể.
	 * 
	 * @param categoryId ID của danh mục cần lọc.
	 * @return Danh sách sản phẩm thuộc danh mục đó.
	 */
	@Query("SELECT p FROM Product p JOIN p.user u JOIN u.categories c WHERE c.id = :categoryId")
	List<Product> findByCategoryId(Long categoryId);
}