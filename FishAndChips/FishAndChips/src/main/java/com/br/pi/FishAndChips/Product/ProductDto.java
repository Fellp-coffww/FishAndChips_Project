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

    private String category;

    public ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory().getName());
        return dto;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
