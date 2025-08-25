package gift.controller;

import gift.dto.ProductRequest;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/new")
    public String createForm() {
        return "adminform";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute ProductRequest form,
                         BindingResult bindingResult,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "adminform";
        }
        productService.addProduct(form);
        ra.addFlashAttribute("message", "상품이 등록되었습니다.");
        return "redirect:/api/admin";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product p = productService.getOneProduct(id);
        model.addAttribute("productRequest", new ProductRequest(p.getName(), p.getImageUrl(), p.getPrice()));
        model.addAttribute("id", p.getId());
        return "adminform";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute ProductRequest form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "adminform";
        }
        productService.updateProduct(id, form);
        ra.addFlashAttribute("message", "상품이 수정되었습니다.");
        return "redirect:/api/admin";
    }

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
        return "admindetail";
    }
}
