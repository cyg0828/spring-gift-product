package gift.validation;

import java.lang.annotation.*;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoKakaoValidator.class)
public @interface NoKakao {
    String message() default "상품명에 \"카카오\"를 사용할 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
