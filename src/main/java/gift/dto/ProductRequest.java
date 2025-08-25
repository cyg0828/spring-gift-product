package gift.dto;

import gift.validation.NoKakao;
import jakarta.validation.constraints.*;

public class ProductRequest {
    @NotBlank(message = "상품명을 입력해주세요")
    @NoKakao
    @Pattern(regexp = "^[A-Za-z0-9가-힣 ()\\[\\]+\\-&_]+$", message = "상품명에 허용되지 않은 특수문자가 있습니다.")
    @Size(max = 15, message = "상품명을 15자 이내로 입력해주세요")
    String name;

    @NotBlank(message = "상품이미지를 입력해주세요")
    String imageUrl;

    @NotNull(message = "상품 가격을 입력해주세요")
    @Positive(message = "상품 가격은 0보다 커야합니다.")
    Long price;

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
    public Long getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setPrice(long price) {
        this.price = price;
    }
}
