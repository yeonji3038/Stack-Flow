package ssafy.StackFlow.Domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String colorCode;

    @Column(nullable = false)
    private String colorName;

    @OneToMany(mappedBy = "colorCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return colorCode;
    }
}
