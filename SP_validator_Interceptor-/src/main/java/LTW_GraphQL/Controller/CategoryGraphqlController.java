package LTW_GraphQL.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import LTW_GraphQL.Entity.Category;
import LTW_GraphQL.Model.CategoryInput;
import LTW_GraphQL.Service.CategoryService;

@Controller
public class CategoryGraphqlController {

    @Autowired
    private CategoryService categoryService;

    @QueryMapping
    public List<Category> categories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public Optional<Category> categoryById(@Argument Long id) {
        return categoryService.getCategoryById(id);
    }

    @MutationMapping
    public Category createCategory(@Argument("category") CategoryInput categoryInput) {
        return categoryService.createCategory(categoryInput);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument("category") CategoryInput categoryInput) {
        return categoryService.updateCategory(id, categoryInput);
    }

    @MutationMapping
    public boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}