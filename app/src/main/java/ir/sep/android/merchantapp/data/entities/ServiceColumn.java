package ir.sep.android.merchantapp.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class ServiceColumn {

    @SerializedName("filterTitle")
    @Expose
    private String filterTitle;
    @SerializedName("filterType")
    @Expose
    private Integer filterType;
    @SerializedName("conditionType")
    @Expose
    private Integer conditionType;
    @SerializedName("conditionValue")
    @Expose
    private String conditionValue;
    @SerializedName("columnKey")
    @Expose
    private String columnKey;
    @SerializedName("columnName")
    @Expose
    private String columnName;
    @SerializedName("isMandatory")
    @Expose
    private Boolean isMandatory;
    @SerializedName("FixedValue")
    @Expose
    private String fixedValue;


    public ServiceColumn(String filterTitle, Integer filterType, Integer conditionType, String conditionValue, String columnKey, String columnName, Boolean isMandatory, String fixedValue) {
        this.filterTitle = filterTitle;
        this.filterType = filterType;
        this.conditionType = conditionType;
        this.conditionValue = conditionValue;
        this.columnKey = columnKey;
        this.columnName = columnName;
        this.isMandatory = isMandatory;
        this.fixedValue = fixedValue;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getFixedValue() {
        return fixedValue;
    }

    public void setFixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

}
