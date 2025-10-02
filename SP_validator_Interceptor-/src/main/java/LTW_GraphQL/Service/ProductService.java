package LTW_GraphQL.Service;

import java.util.List;
import java.util.Optional;

import LTW_GraphQL.Entity.Product;
import LTW_GraphQL.Model.ProductInput;

public interface ProductService {

	boolean deleteProduct(Long id);

	Product updateProduct(Long id, ProductInput productInput);

	Product createProduct(ProductInput productInput);

	Optional<Product> getProductById(Long id);

	List<Product> getProductsByCategoryId(Long categoryId);

	List<Product> getProductsSortedByPriceAsc();

}
