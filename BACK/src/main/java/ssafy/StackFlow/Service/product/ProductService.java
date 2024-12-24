package ssafy.StackFlow.Service.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.product.Brand;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Domain.product.Size;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.ProductRepo;
import ssafy.StackFlow.Repository.product.SizeRepository;
import ssafy.StackFlow.Repository.category.CategoryGroupRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepo productRepo;
    private final CategoryGroupRepository categoryGroupRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final BrandRepository brandRepository;

    /**
     * 전체 카테고리 그룹 리스트 반환
     */
    public List<CategoryGroup> getAllCategoryGroups() {
        return categoryGroupRepository.findAll();
    }

    /**
     * 전체 카테고리 리스트 반환
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 전체 색상 리스트 반환
     */
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    /**
     * 전체 브랜드 리스트 반환
     */
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * 전체 사이즈 리스트 반환
     */
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    /**
     * 모든 상품 리스트 조회
     */
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    /**
     * 상품 ID로 상품 조회
     */
    public Product findProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("상품(ID: {})을(를) 찾을 수 없습니다.", id);
                    return new IllegalArgumentException("상품(ID: " + id + ")을(를) 찾을 수 없습니다.");
                });
    }

    /**
     * 상품 코드에 포함된 키워드로 상품 검색
     */
    public List<Product> searchProducts(String keyword) {
        return productRepo.findByProdCodeContaining(keyword);
    }

    /**
     * 상품 등록
     */
    @Transactional
    public String create(String prodName, String prodDetail, String brandCode, String categoryCode,
                         String colorCode, String size, int stockPrice, int stockQuantity, int sellPrice) {
        if (prodName == null || prodName.trim().isEmpty()) {
            throw new IllegalArgumentException("상품명이 누락되었습니다.");
        }

        // 상품 코드 생성
        String prodCode = generateProductCode(categoryCode, colorCode, size);

        // 연관 엔터티 조회 및 검증
        Category category = findCategoryByCode(categoryCode);
        Brand brand = findBrandByCode(brandCode);
        Color color = findColorByCode(colorCode);
        Size productSize = findSizeByName(size);

        // 상품 엔터티 생성
        Product product = new Product();
        product.setProdName(prodName);
        product.setProdDetail(prodDetail);
        product.setProdCode(prodCode);
        product.setStockPrice(stockPrice);
        product.setSellPrice(sellPrice);
        product.setStockQuantity(stockQuantity);
        product.setProdCate(category);
        product.setBrandCode(brand);
        product.setColorCode(color);
        product.setSize(productSize);

        // DB에 저장
        Product savedProduct = productRepo.save(product);

        logger.info("상품 등록 성공: {}", savedProduct);
        return prodCode;
    }


    /**
     * 상품 수정
     */
    @Transactional
    public void update(Long id, String prodName, String prodDetail, String prodCode, String brandCode,
                       String categoryCode, String colorCode, String size, int stockPrice, int stockQuantity, int sellPrice) {
        try {
            // 기존 상품 조회
            Product product = findProductById(id);

            // 정보 업데이트
            product.setProdName(prodName);
            product.setProdDetail(prodDetail);
            product.setProdCode(prodCode);
            product.setBrandCode(findBrandByCode(brandCode));
            product.setProdCate(findCategoryByCode(categoryCode));
            product.setColorCode(findColorByCode(colorCode));
            product.setSize(findSizeByName(size));
            product.setStockPrice(stockPrice);
            product.setStockQuantity(stockQuantity);
            product.setSellPrice(sellPrice);

            // 데이터 저장
            productRepo.save(product);
            logger.info("상품 수정 성공: {}", prodName);
        } catch (Exception e) {
            logger.error("상품 수정 실패 (ID: {}): {}", id, e.getMessage(), e);
            throw new RuntimeException("상품 수정 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void delete(Long id) {
        try {
            productRepo.deleteById(id);
            logger.info("상품 삭제 성공. ID: {}", id);
        } catch (Exception e) {
            logger.error("상품 삭제 실패 (ID: {}): {}", id, e.getMessage(), e);
            throw new RuntimeException("상품 삭제 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 상품 코드 생성
     */
    private String generateProductCode(String categoryCode, String colorCode, String size) {
        if (categoryCode == null || colorCode == null || size == null ||
                categoryCode.isEmpty() || colorCode.isEmpty() || size.isEmpty()) {
            throw new IllegalArgumentException("상품 코드 생성에 필요한 값이 누락되었습니다.");
        }
        return String.format("%s-%s-%s-%d", categoryCode, colorCode, size, System.currentTimeMillis());
    }

    // === 공통 조회 메서드 ===

    private Category findCategoryByCode(String categoryCode) {
        return categoryRepository.findByCateCode(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 코드(" + categoryCode + ")를 찾을 수 없습니다."));
    }

    private Brand findBrandByCode(String brandCode) {
        return brandRepository.findByBrandCode(brandCode)
                .orElseThrow(() -> new IllegalArgumentException("브랜드 코드(" + brandCode + ")를 찾을 수 없습니다."));
    }

    private Color findColorByCode(String colorCode) {
        return colorRepository.findByColorCode(colorCode)
                .orElseThrow(() -> new IllegalArgumentException("색상 코드(" + colorCode + ")를 찾을 수 없습니다."));
    }

    private Size findSizeByName(String size) {
        return sizeRepository.findBySize(size)
                .orElseThrow(() -> new IllegalArgumentException("사이즈(" + size + ")를 찾을 수 없습니다."));
    }

    private CategoryGroup findCategoryGroupByCode(String groupCode) {
        return categoryGroupRepository.findByGroupCode(groupCode)
                .orElseThrow(() -> new IllegalArgumentException("카테고리 그룹 코드(" + groupCode + ")를 찾을 수 없습니다."));
    }
}
