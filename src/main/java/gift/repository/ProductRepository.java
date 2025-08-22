package gift.repository;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ResultSet 한 행 -> Product
    private static final RowMapper<Product> PRODUCT_MAPPER = (rs, rn) ->
            new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image_url"), // DB 컬럼명 기준
                    rs.getLong("price")
            );

    /** 목록 조회 */
    public List<ProductResponse> getAllProducts() {
        String sql = "SELECT id, name, image_url, price FROM product ORDER BY id DESC";
        return jdbcTemplate.query(sql, (rs, rn) ->
                new ProductResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("image_url"),
                        rs.getLong("price")
                )
        );
    }

    /** 생성 (AUTO_INCREMENT 키 회수) */
    public ProductResponse addProduct(ProductRequest req) {
        String sql = "INSERT INTO product (name, image_url, price) VALUES (?, ?, ?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, req.getName());
            ps.setString(2, req.getImageUrl());
            ps.setLong(3, req.getPrice());
            return ps;
        }, kh);

        Number key = kh.getKey();
        if (key == null) throw new IllegalStateException("Failed to retrieve generated id");
        long id = key.longValue();
        return new ProductResponse(id, req.getName(), req.getImageUrl(), req.getPrice());
    }

    /** 수정 */
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, req.getName(), req.getImageUrl(), req.getPrice(), id);
        if (updated == 0) throw new ProductNotFoundException(id);
        return new ProductResponse(id, req.getName(), req.getImageUrl(), req.getPrice());
    }

    /** 삭제 */
    public void deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) throw new ProductNotFoundException(id);
    }

    /** 존재 여부 */
    public boolean existById(Long id) {
        String sql = "SELECT COUNT(*) FROM product WHERE id = ?";
        Long cnt = jdbcTemplate.queryForObject(sql, Long.class, id);
        return cnt != null && cnt > 0;
    }

    /** 단건 조회 */
    public Product getOneProduct(Long id) {
        String sql = "SELECT id, name, image_url, price FROM product WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, PRODUCT_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        }
    }
}
