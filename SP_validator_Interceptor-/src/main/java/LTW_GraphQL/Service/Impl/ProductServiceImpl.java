package LTW_GraphQL.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import LTW_GraphQL.Entity.Product;
import LTW_GraphQL.Entity.User;
import LTW_GraphQL.Model.ProductInput;
import LTW_GraphQL.Repository.ProductRepository;
import LTW_GraphQL.Repository.UserRepository;
import LTW_GraphQL.Service.ProductService;
@Service 
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;


    private UserRepository userRepository;


    @Override
	public List<Product> getProductsSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

   
    @Override
	public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

  
    @Override
	public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

  
    @Override
	public Product createProduct(ProductInput productInput) {
        User user = userRepository.findById(productInput.getUserid())
               .orElseThrow(() -> new RuntimeException("User not found for product creation"));

        Product product = new Product();
        product.setTitle(productInput.getTitle());
        product.setQuantity(productInput.getQuantity());
        product.setDesc(productInput.getDesc());
        product.setPrice(productInput.getPrice());
        product.setUser(user);
        return productRepository.save(product);
    }

 
    @Override
	public Product updateProduct(Long id, ProductInput productInput) {
        Product existingProduct = productRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        User user = userRepository.findById(productInput.getUserid())
               .orElseThrow(() -> new RuntimeException("User not found for product update"));

        existingProduct.setTitle(productInput.getTitle());
        existingProduct.setQuantity(productInput.getQuantity());
        existingProduct.setDesc(productInput.getDesc());
        existingProduct.setPrice(productInput.getPrice());
        existingProduct.setUser(user);
        return productRepository.save(existingProduct);
    }

   
    @Override
	public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}