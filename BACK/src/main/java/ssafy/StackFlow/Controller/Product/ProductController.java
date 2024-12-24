package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ssafy.StackFlow.Domain.product.Product;
import ssafy.StackFlow.Service.product.ProductService;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Repository.category.CategoryRepository;
import ssafy.StackFlow.Repository.product.BrandRepository;
import ssafy.StackFlow.Repository.product.ColorRepository;
import ssafy.StackFlow.Repository.product.SizeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final BrandRepository brandRepository;

    // 상품 등록 페이지 호출
    @GetMapping("/create")
    public String createForm(Model model) {
        logger.info("상품 등록 페이지 호출");
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("colorList", colorRepository.findAll());
        model.addAttribute("sizeList", sizeRepository.findAll());
        model.addAttribute("brandList", brandRepository.findAll());
        return "product/productForm";
    }

    // 상품 등록 처리
    @PostMapping("/create")
    public String createProduct(
            @RequestParam String prodName,
            @RequestParam(defaultValue = "상세 정보 없음") String prodDetail,
            @RequestParam String categoryCode,
            @RequestParam String brandCode,
            @RequestParam String colorCode,
            @RequestParam String size,
            @RequestParam int stockPrice,
            @RequestParam int stockQuantity,
            @RequestParam int sellPrice,
            RedirectAttributes redirectAttributes) {
        try {
            logger.info("상품 등록 요청 - 이름: {}, 카테고리: {}, 브랜드: {}", prodName, categoryCode, brandCode);

            // 서비스 호출
            String prodCode = productService.create(
                    prodName, prodDetail, brandCode, categoryCode, colorCode, size, stockPrice, stockQuantity, sellPrice);

            // 성공 메시지 설정
            redirectAttributes.addFlashAttribute("successMessage", "상품 등록 성공!");
            redirectAttributes.addFlashAttribute("prodCode", prodCode);

            // 상품 목록 페이지로 이동
            return "redirect:/product/list";
        } catch (IllegalArgumentException e) {
            logger.error("상품 등록 실패 - 입력값 오류: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "입력값 오류: " + e.getMessage());
            return "redirect:/product/create";
        } catch (Exception e) {
            logger.error("상품 등록 실패 - 서버 오류: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "서버 오류가 발생했습니다.");
            return "redirect:/product/create";
        }
    }




    // 상품 목록 조회
    @GetMapping("/list")
    public String listProducts(Model model,
                               @RequestParam(required = false) String keyword,
                               @ModelAttribute("successMessage") String successMessage,
                               @ModelAttribute("prodCode") String prodCode) {
        try {
            // 키워드가 있을 경우 검색, 없으면 전체 조회
            List<Product> products = (keyword != null && !keyword.isEmpty())
                    ? productService.searchProducts(keyword)
                    : productService.findAllProducts();

            model.addAttribute("products", products);
            model.addAttribute("successMessage", successMessage);
            model.addAttribute("prodCode", prodCode);

            logger.info("상품 목록 조회 성공: {}개 상품 로드됨", products.size());
            return "product/productList";
        } catch (Exception e) {
            logger.error("상품 목록 조회 실패: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "상품 목록 조회 중 오류가 발생했습니다.");
            return "product/productList";
        }
    }


    // 상품 상세 조회
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        try {
            Product product = productService.findProductById(id);
            model.addAttribute("product", product);
            logger.info("상품 상세 조회 성공: {}", product.getProdName());
            return "product/productDetail";
        } catch (Exception e) {
            logger.error("상품 상세 조회 실패: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "상품 상세 정보를 불러올 수 없습니다.");
            return "product/productList";
        }
    }

    // 상품 수정 페이지 호출
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        try {
            Product product = productService.findProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("colors", productService.getAllColors());
            model.addAttribute("sizes", productService.getAllSizes());
            model.addAttribute("brands", productService.getAllBrands());
            logger.info("상품 수정 폼 호출 (ID: {})", id);
            return "product/productEditForm";
        } catch (Exception e) {
            logger.error("상품 수정 폼 호출 실패 (ID: {}): {}", id, e.getMessage(), e);
            model.addAttribute("errorMessage", "상품 수정 정보를 불러오는 중 오류가 발생했습니다.");
            return "product/productList";
        }
    }

    // 상품 수정 처리
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @RequestParam String prodName, @RequestParam String prodDetail,
                         @RequestParam String prodCode, @RequestParam String brandCode, @RequestParam String categoryCode,
                         @RequestParam String colorCode, @RequestParam String size, @RequestParam int stockPrice,
                         @RequestParam int stockQuantity, @RequestParam int sellPrice) {
        try {
            productService.update(id, prodName, prodDetail, prodCode, brandCode, categoryCode, colorCode, size, stockPrice, stockQuantity, sellPrice);
            logger.info("상품 수정 성공 (ID: {}): {}", id, prodName);
            return "redirect:/product/list";
        } catch (Exception e) {
            logger.error("상품 수정 실패 (ID: {}): {}", id, e.getMessage(), e);
            return "product/productEditForm";
        }
    }

    // 상품 삭제 처리
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            logger.info("상품 삭제 성공 (ID: {})", id);
            return "redirect:/product/list";
        } catch (Exception e) {
            logger.error("상품 삭제 실패 (ID: {}): {}", id, e.getMessage(), e);
            return "product/productList";
        }
    }

    // 카테고리 그룹에 따른 카테고리 조회 (AJAX)
    @GetMapping("/api/categories")
    @ResponseBody
    public List<ssafy.StackFlow.Domain.category.dto.CategoryDTO> getCategoriesByGroup(@RequestParam String groupCode) {
        List<Category> categories = categoryRepository.findByCateGroup_GroupCode(groupCode);

        // 엔터티 -> DTO 변환
        return categories.stream()
                .map(category -> new ssafy.StackFlow.Domain.category.dto.CategoryDTO(category.getCateCode(), category.getCateName()))
                .collect(Collectors.toList());
    }

}
