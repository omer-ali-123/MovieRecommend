package org.example.film_pro;

public class Category {
    private String categoryName;
    private int categoryCount;

    public Category(String categoryName, int categoryCount) {
        this.categoryName = categoryName;
        this.categoryCount = categoryCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }
}
