package LTW_GraphQL.Service;

import java.util.List;
import java.util.Optional;

import LTW_GraphQL.Entity.Category;
import LTW_GraphQL.Model.CategoryInput;

public interface CategoryService {

	boolean deleteCategory(Long id);

	Category updateCategory(Long id, CategoryInput categoryInput);

	Category createCategory(CategoryInput categoryInput);

	Optional<Category> getCategoryById(Long id);

	List<Category> getAllCategories();

}
