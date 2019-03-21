package com.gurug.education.data.model.response.lessonplan;

public class PedagoyStepRelatedInfo {

    private String name;
    private String description;
    private String category;
    private String MethodType = "";
    private String methodName;
    private String pedagogyStepName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMethodType() {
        return MethodType;
    }

    public void setMethodType(String methodType) {
        this.MethodType = methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPedagogyStepName() {
        return pedagogyStepName;
    }

    public void setPedagogyStepName(String pedagogyStepName) {
        this.pedagogyStepName = pedagogyStepName;
    }
}
