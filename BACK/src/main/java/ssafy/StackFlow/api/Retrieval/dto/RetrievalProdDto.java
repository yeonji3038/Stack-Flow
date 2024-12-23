package ssafy.StackFlow.api.Retrieval.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Product;

@Data
public class RetrievalProdDto {
    private Long prod_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private int product_stock;

    public RetrievalProdDto(Product product) {
        prod_id = product.getId();
        product_code = product.getProdCode();
        product_name = product.getProdName();
        color_code = product.getColorCode().getColorCode();
        product_size = product.getSize().getSize();
        if (!product.getStoreProducts().isEmpty()) {
            product_stock = product.getStoreProducts().get(0).getStockQuantity();
        } else {
            product_stock = 0;
        }
    }
}
