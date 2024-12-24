package ssafy.StackFlow.Repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.category.CategoryGroup;

import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    Optional<CategoryGroup> findByGroupCode(String groupCode); // 기존 메서드
    Optional<CategoryGroup> findByGroupName(String groupName); // 추가된 메서드
}
