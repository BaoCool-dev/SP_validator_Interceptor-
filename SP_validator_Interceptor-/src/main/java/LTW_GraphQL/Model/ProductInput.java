package LTW_GraphQL.Model;

import lombok.Data;

@Data
public class ProductInput {
    private String title;
    private int quantity;
    private String desc;
    private double price;
    private Long userid; // ID của User sở hữu sản phẩm
}