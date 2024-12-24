package ssafy.StackFlow.Controller.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Repository.product.ColorRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/color")
public class ColorController {

    private final ColorRepository colorRepository;

    @GetMapping
    @ResponseBody
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addColor(@RequestParam String colorCode, @RequestParam String colorName) {
        if (colorRepository.findByColorCode(colorCode).isPresent()) {
            return ResponseEntity.badRequest().body("중복된 색상 코드가 존재합니다.");
        }

        Color color = new Color();
        color.setColorCode(colorCode);
        color.setColorName(colorName);

        colorRepository.save(color);
        return ResponseEntity.ok("색상이 성공적으로 추가되었습니다.");
    }

}
