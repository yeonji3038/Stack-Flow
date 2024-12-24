package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;
import ssafy.StackFlow.Repository.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryGroupRepository categoryGroupRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findCategoriesByGroupCode(String groupCode) {
        return categoryRepository.findByCateGroup_GroupCode(groupCode);
    }

    @Transactional
    public void addCategory(String cateCode, String cateName, String groupCode) {
        if (categoryRepository.existsByCateCode(cateCode)) {
            throw new IllegalArgumentException("중복된 카테고리 코드가 존재합니다.");
        }

        CategoryGroup group = categoryGroupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 그룹 코드입니다."));

        Category category = new Category();
        category.setCateCode(cateCode);
        category.setCateName(cateName);
        category.setCateGroup(group);

        categoryRepository.save(category);
    }
}
