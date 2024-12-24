package ssafy.StackFlow.Controller.admin;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Service.store.StoreService;
import ssafy.StackFlow.Service.product.AdminService;

@Controller
public class AdminController {
    @Autowired
    private StoreService storeService;

    @Autowired // 추가된 부분
    private AdminService adminService; // 추가된 부분

    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // admin.html을 반환
    }
    @GetMapping("/store")
    public String storePage() {
        return "store"; // store.html을 반환
    }

    // 추가된 부분: 카테고리 추가 처리
    @PostMapping("/admin/category/add")
    public String addCategory(@RequestParam String categorygroup,
                              @RequestParam String cateCode,
                              @RequestParam String cateName) {
        adminService.addCategory(categorygroup, cateCode, cateName);
        return "redirect:/product/create"; // 작업 완료 후 리다이렉트
    }

    @PostMapping("/admin/brand/add")
    public String addBrand(@RequestParam String brandCode, @RequestParam String brandName) {
        adminService.addBrand(brandCode, brandName);
        return "redirect:/product/create"; // 작업 완료 후 리다이렉트
    }

    @PostMapping("/admin/color/add")
    public String addColor(@RequestParam String colorCode, @RequestParam String colorName) {
        adminService.addColor(colorCode, colorName);
        return "redirect:/product/create"; // 작업 완료 후 리다이렉트
    }

    // @GetMapping("/admin/registerStore")
    // public String registerStorePage() {
    //     return "admin/registerStore"; // 매장 등록 페이지로 이동
    // }

    // @PostMapping("/admin/registerStore")
    // public String registerStore(@RequestParam String storeName, @RequestParam String location, Model model) {
    //     Store store = new Store();
    //     store.setStoreName(storeName);
    //     store.setLocation(location);

    //     // 매장 코드 생성
    //     String storeCode = storeService.generateStoreCode(location);
    //     store.setStoreCode(storeCode); // 생성된 매장 코드를 설정

    //     // 매장 저장
    //     storeService.saveStore(store);

    //     // storeCode가 String 타입인지 확인
    //     model.addAttribute("storeCode", storeCode); // 이 부분에서 오류가 발생할 수 있습니다.
    //     return "admin/storeRegistrationResult"; // 결과 페이지로 이동
    // }
}
