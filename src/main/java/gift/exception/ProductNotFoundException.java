package gift.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("NOT FOUND PRODUCT "+id);
    }
}
