package ssafy.StackFlow.Domain.category.dto;

public class CategoryDTO {
    private String cateCode;
    private String cateName;

    public CategoryDTO(String cateCode, String cateName) {
        this.cateCode = cateCode;
        this.cateName = cateName;
    }

    // Getters and Setters
    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
