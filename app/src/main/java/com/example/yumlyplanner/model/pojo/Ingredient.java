package com.example.yumlyplanner.model.pojo;

import com.example.yumlyplanner.homefragment.view.BindableItem;

public class Ingredient implements BindableItem {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;
    private  String thumbIngredient;

    public String getThumbIngredient() {
        return "www.themealdb.com/images/ingredients/"+strIngredient+".png";
    }

    public void setThumbIngredient(String thumbIngredient) {
        this.thumbIngredient = thumbIngredient;
    }

    // Constructor
    public Ingredient(String idIngredient, String strIngredient, String strDescription, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    // Getters and Setters
    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "idIngredient='" + idIngredient + '\'' +
                ", strIngredient='" + strIngredient + '\'' +
                ", strDescription='" + strDescription + '\'' +
                ", strType='" + strType + '\'' +
                '}';
    }

    @Override
    public String getTitle() {
        return getStrIngredient();
    }

    @Override
    public String getImageUrl() {
        return "https://www.themealdb.com/images/ingredients/" + strIngredient + ".png";
    }
}
