package gift.controller;

import gift.dto.ProductRequest;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/api/admin")
@Controller
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String adminList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "adminlist";
    }

    /** 생성 폼 */
    @GetMapping("/new")
    public String createForm() {
        return "adminform"; // 비어있는 폼
    }

    /** 생성 처리 (POST /products) */
    @PostMapping
    public String create(@RequestParam String name,
                         @RequestParam(required = false) String imageUrl,
                         @RequestParam long price,
                         RedirectAttributes ra) {
        productService.addProduct(new ProductRequest(name, imageUrl, price));
        ra.addFlashAttribute("message", "상품이 등록되었습니다.");
        return "redirect:/api/admin";
    }

    /** 수정 폼 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var p = productService.getOneProduct(id);             // 없으면 404 던지도록 구현
        model.addAttribute("p", p);             // 폼에 초기값으로 사용
        return "adminform";
    }

    /** 수정 처리 (POST /products/{id}) — PUT 스푸핑 없이 간단히 */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam(required = false) String imageUrl,
                         @RequestParam long price,
                         RedirectAttributes ra) {
        productService.updateProduct(id, new gift.dto.ProductRequest(name, imageUrl, price));
        ra.addFlashAttribute("message", "상품이 수정되었습니다.");
        return "redirect:/api/admin";
    }

    /** 삭제 처리 (POST /products/{id}/delete) — DELETE 스푸핑 없이 간단히 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        productService.deletProduct(id);
        ra.addFlashAttribute("message", "상품이 삭제되었습니다.");
        return "redirect:/api/admin";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var p = productService.getOneProduct(id); // 없으면 서비스에서 404 예외 던지도록
        model.addAttribute("p", p);
        return "admindetail"; // templates/admindetail.html
    }
}
