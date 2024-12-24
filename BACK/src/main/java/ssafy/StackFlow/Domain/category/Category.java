package ssafy.StackFlow.Domain.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String cateCode;

    @Column(nullable = false)
    private String cateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code", referencedColumnName = "groupCode", nullable = false)
    @JsonBackReference // 순환 참조 방지
    private CategoryGroup cateGroup;
}
