package LTW_GraphQL.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import LTW_GraphQL.Entity.Category;
import LTW_GraphQL.Model.CategoryInput;
import LTW_GraphQL.Repository.CategoryRepository;
import LTW_GraphQL.Service.CategoryService;

@Service 
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepository categoryRepository;


    @Override
	public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

   
    @Override
	public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    
    @Override
	public Category createCategory(CategoryInput categoryInput) {
        Category category = new Category();
        category.setName(categoryInput.getName());
        category.setImages(categoryInput.getImages());
        return categoryRepository.save(category);
    }

   
    @Override
	public Category updateCategory(Long id, CategoryInput categoryInput) {
        Category existingCategory = categoryRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        existingCategory.setName(categoryInput.getName());
        existingCategory.setImages(categoryInput.getImages());
        return categoryRepository.save(existingCategory);
    }

    
    @Override
	public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }
}