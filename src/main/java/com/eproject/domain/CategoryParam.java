package com.eproject.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CategoryParam implements Serializable {
    @ApiModelProperty("分类id")
    private Integer id;
    @ApiModelProperty("分类级别")
    private String categoryLevel;
    @ApiModelProperty("分类名")
    private String categoryName;

    private List<SecondLevelCategoryParam> secondLevelCategoryParams;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public List<SecondLevelCategoryParam> getSecondLevelCategoryParams() {
        return secondLevelCategoryParams;
    }

    public void setSecondLevelCategoryParams(List<SecondLevelCategoryParam> secondLevelCategoryParams) {
        this.secondLevelCategoryParams = secondLevelCategoryParams;
    }
}
