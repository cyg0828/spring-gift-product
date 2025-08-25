package gift.dto;

public class ProductResponse {
    Long id;
    String name;
    String imageUrl;
    Long price;

    public ProductResponse(Long id, String name, String imageUrl, long price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public Long getPrice() {
        return price;
    }
}
