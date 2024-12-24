package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.product.Brand;
import ssafy.StackFlow.Repository.product.BrandRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandRepository brandRepository;

    @GetMapping
    @ResponseBody
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBrand(@RequestParam String brandCode, @RequestParam String brandName) {
        if (brandRepository.existsByBrandCode(brandCode)) {
            return ResponseEntity.badRequest().body("중복된 브랜드 코드가 존재합니다.");
        }

        Brand brand = new Brand();
        brand.setBrandCode(brandCode);
        brand.setBrandName(brandName);

        brandRepository.save(brand);
        return ResponseEntity.ok("브랜드가 성공적으로 추가되었습니다.");
    }


}
