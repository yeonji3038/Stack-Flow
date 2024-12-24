package ssafy.StackFlow.Service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryGroupService {

    private final CategoryGroupRepository categoryGroupRepository;

    public List<CategoryGroup> findAllCategoryGroups() {
        return categoryGroupRepository.findAll();
    }

    public CategoryGroup findCategoryGroupByCode(String groupCode) {
        return categoryGroupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 코드가 존재하지 않습니다."));
    }
}
