package com.br.pi.FishAndChips.Category;

public class CategoryDto {

    private String nome;

    public CategoryDto fromEntity(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setNome(category.getName());
        return dto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
