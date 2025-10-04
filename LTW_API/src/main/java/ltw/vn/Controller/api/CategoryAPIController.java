package ltw.vn.Controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ltw.vn.Entity.Category;
import ltw.vn.Model.CategoryDTO;
import ltw.vn.Model.Response;
import ltw.vn.Service.CategoryService;

@RestController
@RequestMapping("/api/categories") // Sửa thành "/api/categories" cho chuẩn REST
public class CategoryAPIController {

    @Autowired
    private CategoryService categoryService;

    // GET: Lấy tất cả danh mục
    @GetMapping
    public ResponseEntity<Response> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(new Response(true, "Lấy danh sách danh mục thành công", categories));
    }

    // GET: Lấy thông tin một danh mục theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategoryById(@PathVariable("id") Long categoryId) {
        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isPresent()) {
            return ResponseEntity.ok(new Response(true, "Tìm thấy danh mục", category.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(false, "Không tìm thấy danh mục với id: " + categoryId, null));
    }

    // POST: Thêm mới một danh mục
    @PostMapping
    public ResponseEntity<Response> createCategory(@Valid @ModelAttribute CategoryDTO categoryDTO) {
        // Sử dụng @ModelAttribute vì request là multipart/form-data (có file upload)
        
        if (categoryService.existsByCategoryName(categoryDTO.getCategoryName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Tên danh mục đã tồn tại", null));
        }

        try {
            Category savedCategory = categoryService.save(categoryDTO);
            return new ResponseEntity<>(new Response(true, "Thêm danh mục thành công", savedCategory), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Lỗi khi lưu file: " + e.getMessage(), null));
        }
    }

    // PUT: Cập nhật thông tin danh mục
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") Long categoryId,
                                                   @Valid @ModelAttribute CategoryDTO categoryDTO) {
        if (!categoryService.findById(categoryId).isPresent()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(false, "Không tìm thấy danh mục để cập nhật", null));
        }
        try {
            Category updatedCategory = categoryService.update(categoryId, categoryDTO);
            return ResponseEntity.ok(new Response(true, "Cập nhật danh mục thành công", updatedCategory));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Lỗi khi cập nhật: " + e.getMessage(), null));
        }
    }

    // DELETE: Xóa một danh mục
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable("id") Long categoryId) {
        if (!categoryService.findById(categoryId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(false, "Không tìm thấy danh mục để xóa", null));
        }
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok(new Response(true, "Xóa danh mục thành công", null));
    }
}
