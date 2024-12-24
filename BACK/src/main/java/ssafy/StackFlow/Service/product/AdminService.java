package ssafy.StackFlow.Service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.product.Brand;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;

    public void addCategory(String categorygroup, String cateCode, String cateName) {
        Category category = new Category();
        category.setCateCode(cateCode);
        category.setCateName(cateName);
        categoryRepository.save(category);
    }

    public void addBrand(String brandCode, String brandName) {
        Brand brand = new Brand();
        brand.setBrandCode(brandCode);
        brand.setBrandName(brandName); // brandDetail 대신 brandName 사용
        brandRepository.save(brand);
    }

    public void addColor(String colorCode, String colorName) {
        Color color = new Color();
        color.setColorCode(colorCode);
        color.setColorName(colorName);
        colorRepository.save(color);
    }
}
