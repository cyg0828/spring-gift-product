package gift.dto;

public class ProductRequest {
    String name;
    String imageUrl;
    long price;

    public ProductRequest(String name, String imageUrl, long price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public long getPrice() {
        return price;
    }
}
