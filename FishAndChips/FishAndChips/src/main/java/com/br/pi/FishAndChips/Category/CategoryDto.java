package com.br.pi.FishAndChips.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String name;

    public CategoryDto fromEntity(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setName(category.getName());
        return dto;
    }


    @Override
    public String toString() {
        return name;
    }

}
