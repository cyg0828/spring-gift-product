package gift.service;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResponse> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public ProductResponse addProduct(ProductRequest productRequest) {
        return productRepository.addProduct(productRequest);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        notFoundException(id);
        return productRepository.updateProduct(id, productRequest);
    }

    public void deletProduct(Long id) {
        notFoundException(id);
        productRepository.deleteProduct(id);
    }

    public void notFoundException(Long id){
        if(!productRepository.existById(id)){
            throw new ProductNotFoundException(id);
        }
    }

    public Product getOneProduct(Long id) {
        return productRepository.getOneProduct(id);
    }
}
