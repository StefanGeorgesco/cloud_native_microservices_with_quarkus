package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.BaseEntity;
import fr.stefangeorgesco.smartbar.backoffice.categories.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Article extends BaseEntity {

    private String name;
    private BigDecimal price;
    private String description;
    private String picture;
    @ManyToOne
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
