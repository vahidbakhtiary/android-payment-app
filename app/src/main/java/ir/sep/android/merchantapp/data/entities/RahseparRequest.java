package ir.sep.android.merchantapp.data.entities;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RahseparRequest {

    @SerializedName("customerNumber")
    @Expose
    private Object customerNumber;
    @SerializedName("merchantNumber")
    @Expose
    private Object merchantNumber;
    @SerializedName("terminalNumber")
    @Expose
    private Object terminalNumber;
    @SerializedName("ExportTo")
    @Expose
    private Integer exportTo;
    @SerializedName("serviceColumns")
    @Expose
    private List<ServiceColumn> serviceColumns = null;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("RepWebserviceKey")
    @Expose
    private String repWebserviceKey;
    @SerializedName("CustomerKey")
    @Expose
    private String customerKey;
    @SerializedName("RepRelationKey")
    @Expose
    private String repRelationKey;
    @SerializedName("CanSplitFun")
    @Expose
    private Boolean canSplitFun;
    @SerializedName("OfsetCount")
    @Expose
    private Integer ofsetCount;
    @SerializedName("FetchCount")
    @Expose
    private Integer fetchCount;



    public Object getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Object customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Object getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(Object merchantNumber) {
        this.merchantNumber = merchantNumber;
    }

    public Object getTerminalNumber() {
        return terminalNumber;
    }

    public void setTerminalNumber(Object terminalNumber) {
        this.terminalNumber = terminalNumber;
    }

    public Integer getExportTo() {
        return exportTo;
    }

    public void setExportTo(Integer exportTo) {
        this.exportTo = exportTo;
    }

    public List<ServiceColumn> getServiceColumns() {
        return serviceColumns;
    }

    public void setServiceColumns(List<ServiceColumn> serviceColumns) {
        this.serviceColumns = serviceColumns;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepWebserviceKey() {
        return repWebserviceKey;
    }

    public void setRepWebserviceKey(String repWebserviceKey) {
        this.repWebserviceKey = repWebserviceKey;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getRepRelationKey() {
        return repRelationKey;
    }

    public void setRepRelationKey(String repRelationKey) {
        this.repRelationKey = repRelationKey;
    }

    public Boolean getCanSplitFun() {
        return canSplitFun;
    }

    public void setCanSplitFun(Boolean canSplitFun) {
        this.canSplitFun = canSplitFun;
    }

    public Integer getOfsetCount() {
        return ofsetCount;
    }

    public void setOfsetCount(Integer ofsetCount) {
        this.ofsetCount = ofsetCount;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
    }

}



