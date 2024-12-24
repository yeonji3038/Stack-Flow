package ssafy.StackFlow.Repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.product.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandCode(String brandCode);
    boolean existsByBrandCode(String brandCode); // 중복 확인 메서드 추가
}
