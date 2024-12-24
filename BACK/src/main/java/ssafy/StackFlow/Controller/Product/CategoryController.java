package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Service.category.CategoryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/group/{groupCode}")
    public ResponseEntity<List<Category>> getCategoriesByGroupCode(@PathVariable String groupCode) {
        List<Category> categories = categoryService.findCategoriesByGroupCode(groupCode);

        if (categories.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>()); // 빈 배열 반환
        }
        return ResponseEntity.ok(categories); // 카테고리 리스트 반환
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(
            @RequestParam String cateCode,
            @RequestParam String cateName,
            @RequestParam String groupCode) {
        try {
            categoryService.addCategory(cateCode, cateName, groupCode);
            return ResponseEntity.ok("카테고리가 성공적으로 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("카테고리 저장 중 오류 발생: " + e.getMessage());
        }
    }
}

