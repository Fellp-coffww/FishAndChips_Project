package com.br.pi.FishAndChips.Category;

public class CategoryDto {

    private String name;

    public CategoryDto fromEntity(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setNome(category.getName());
        return dto;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
