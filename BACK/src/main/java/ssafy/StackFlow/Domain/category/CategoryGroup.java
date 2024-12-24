package ssafy.StackFlow.Domain.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CategoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_group_id")
    private Long id;

    @Column(name = "groupCode", nullable = false, unique = true)
    private String groupCode;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "cateGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 역방향 참조 관리
    private List<Category> categories = new ArrayList<>();
}
