package com.br.pi.FishAndChips.Product;

import com.br.pi.FishAndChips.Category.Category;
import com.br.pi.FishAndChips.Category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;

    private double price;

    private String description;

    private Category category;

    private  String image;

    private long id;

    public ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setImage(product.getImage());
        dto.setId(product.getId());
        return dto;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
