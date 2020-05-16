package com.eproject.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO(第二级)
 */
public class SecondLevelCategoryParam implements Serializable {
    private Integer id;

    private Integer parentId;

    private String categoryLevel;

    private String categoryName;

    private List<ThirdLevelCategoryParam> thirdLevelCategoryVOS;

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

    public List<ThirdLevelCategoryParam> getThirdLevelCategoryParam() {
        return thirdLevelCategoryVOS;
    }

    public void setThirdLevelCategoryParam(List<ThirdLevelCategoryParam> thirdLevelCategoryVOS) {
        this.thirdLevelCategoryVOS = thirdLevelCategoryVOS;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
