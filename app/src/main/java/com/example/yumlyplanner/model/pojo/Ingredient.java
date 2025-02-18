package com.example.yumlyplanner.model.pojo;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;
    private  String thumbIngredient;

    public String getThumbIngredient() {
        return "www.themealdb.com/images/ingredients/"+thumbIngredient+".png";
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
}
