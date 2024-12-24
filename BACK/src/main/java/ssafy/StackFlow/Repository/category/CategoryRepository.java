package ssafy.StackFlow.Repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCateCode(String cateCode);
    List<Category> findByCateGroup_GroupCode(String groupCode);
    boolean existsByCateCode(String cateCode);
}
