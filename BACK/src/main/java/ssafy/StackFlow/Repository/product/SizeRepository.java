package ssafy.StackFlow.Repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.product.Size;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySize(String size);
}
