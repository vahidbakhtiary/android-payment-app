package ir.sep.android.merchantapp.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantServiceBaseEntity {

    @SerializedName("AppKey")
    @Expose
    private String appKey;

    @SerializedName("MerchantKey")
    @Expose
    private String merchantKey;

    @SerializedName("MobileNO")
    @Expose
    private String  mobileNO;

    @SerializedName("Pin")
    @Expose
    private String pin;

    @SerializedName("TerminalNO")
    @Expose
    private long terminalNO;

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("RegKey")
    @Expose
    private String regKey;






    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getMobileNO() {
        return mobileNO;
    }

    public void setMobileNO(String mobileNO) {
        this.mobileNO = mobileNO;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getTerminalNO() {
        return terminalNO;
    }

    public void setTerminalNO(long terminalNO) {
        this.terminalNO = terminalNO;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public class Data {

        @SerializedName("merchantKey")
        @Expose
        private String merchantKey;
        @SerializedName("customerNO")
        @Expose
        private long customerNO;
        @SerializedName("termPMID")
        @Expose
        private Integer termPMID;
        @SerializedName("receiptID")
        @Expose
        private Integer receiptID;
        @SerializedName("terminalNO")
        @Expose
        private Integer terminalNO;
        @SerializedName("receiptCount")
        @Expose
        private Integer receiptCount;
        @SerializedName("receiptpDate")
        @Expose
        private Object receiptpDate;
        @SerializedName("oldReceiptpDate")
        @Expose
        private Object oldReceiptpDate;
        @SerializedName("trxCount")
        @Expose
        private Integer trxCount;
        @SerializedName("oldtrxCount")
        @Expose
        private Integer oldtrxCount;
        @SerializedName("receiptDays")
        @Expose
        private Integer receiptDays;
        @SerializedName("regKey")
        @Expose
        private String regKey;
        @SerializedName("merchantAppID")
        @Expose
        private Integer merchantAppID;
        @SerializedName("pw")
        @Expose
        private long pw;
        @SerializedName("receiptPaperList")
        @Expose
        private List<ReceiptPaperList> receiptPaperList = null;
        @SerializedName("requestPaperList")
        @Expose
        private List<RequestPaperList> requestPaperList = null;


        public String getMerchantKey() {
            return merchantKey;
        }

        public void setMerchantKey(String merchantKey) {
            this.merchantKey = merchantKey;
        }

        public long getCustomerNO() {
            return customerNO;
        }

        public void setCustomerNO(long customerNO) {
            this.customerNO = customerNO;
        }

        public Integer getTermPMID() {
            return termPMID;
        }

        public void setTermPMID(Integer termPMID) {
            this.termPMID = termPMID;
        }

        public Integer getReceiptID() {
            return receiptID;
        }

        public void setReceiptID(Integer receiptID) {
            this.receiptID = receiptID;
        }

        public Integer getTerminalNO() {
            return terminalNO;
        }

        public void setTerminalNO(Integer terminalNO) {
            this.terminalNO = terminalNO;
        }

        public Integer getReceiptCount() {
            return receiptCount;
        }

        public void setReceiptCount(Integer receiptCount) {
            this.receiptCount = receiptCount;
        }

        public Object getReceiptpDate() {
            return receiptpDate;
        }

        public void setReceiptpDate(Object receiptpDate) {
            this.receiptpDate = receiptpDate;
        }

        public Object getOldReceiptpDate() {
            return oldReceiptpDate;
        }

        public void setOldReceiptpDate(Object oldReceiptpDate) {
            this.oldReceiptpDate = oldReceiptpDate;
        }

        public Integer getTrxCount() {
            return trxCount;
        }

        public void setTrxCount(Integer trxCount) {
            this.trxCount = trxCount;
        }

        public Integer getOldtrxCount() {
            return oldtrxCount;
        }

        public void setOldtrxCount(Integer oldtrxCount) {
            this.oldtrxCount = oldtrxCount;
        }

        public Integer getReceiptDays() {
            return receiptDays;
        }

        public void setReceiptDays(Integer receiptDays) {
            this.receiptDays = receiptDays;
        }

        public String getRegKey() {
            return regKey;
        }

        public void setRegKey(String regKey) {
            this.regKey = regKey;
        }

        public Integer getMerchantAppID() {
            return merchantAppID;
        }

        public void setMerchantAppID(Integer merchantAppID) {
            this.merchantAppID = merchantAppID;
        }

        public long getPw() {
            return pw;
        }

        public void setPw(long pw) {
            this.pw = pw;
        }

        public List<ReceiptPaperList> getReceiptPaperList() {
            return receiptPaperList;
        }

        public void setReceiptPaperList(List<ReceiptPaperList> receiptPaperList) {
            this.receiptPaperList = receiptPaperList;
        }

        public List<RequestPaperList> getRequestPaperList() {
            return requestPaperList;
        }

        public void setRequestPaperList(List<RequestPaperList> requestPaperList) {
            this.requestPaperList = requestPaperList;
        }
    }


    public class ReceiptPaperList {

        @SerializedName("receiptID")
        @Expose
        private Integer receiptID;
        @SerializedName("customerNO")
        @Expose
        private Integer customerNO;
        @SerializedName("merchantNO")
        @Expose
        private Integer merchantNO;
        @SerializedName("terminalNO")
        @Expose
        private Integer terminalNO;
        @SerializedName("supporterID")
        @Expose
        private Integer supporterID;
        @SerializedName("courseID")
        @Expose
        private Integer courseID;
        @SerializedName("receiptCount")
        @Expose
        private Integer receiptCount;
        @SerializedName("receiptfDate")
        @Expose
        private String receiptfDate;
        @SerializedName("receiptpDate")
        @Expose
        private String receiptpDate;
        @SerializedName("pMpDate")
        @Expose
        private String pMpDate;
        @SerializedName("pMfDate")
        @Expose
        private String pMfDate;
        @SerializedName("trxCount")
        @Expose
        private Integer trxCount;
        @SerializedName("oldtrxCount")
        @Expose
        private Integer oldtrxCount;
        @SerializedName("senfCode")
        @Expose
        private String senfCode;
        @SerializedName("switchID")
        @Expose
        private Integer switchID;
        @SerializedName("bankID")
        @Expose
        private Integer bankID;
        @SerializedName("karfarmaID")
        @Expose
        private Integer karfarmaID;
        @SerializedName("termPMHISID")
        @Expose
        private Integer termPMHISID;
        @SerializedName("actAgentID")
        @Expose
        private Integer actAgentID;
        @SerializedName("supporterName")
        @Expose
        private String supporterName;

        public Integer getReceiptID() {
            return receiptID;
        }

        public void setReceiptID(Integer receiptID) {
            this.receiptID = receiptID;
        }

        public Integer getCustomerNO() {
            return customerNO;
        }

        public void setCustomerNO(Integer customerNO) {
            this.customerNO = customerNO;
        }

        public Integer getMerchantNO() {
            return merchantNO;
        }

        public void setMerchantNO(Integer merchantNO) {
            this.merchantNO = merchantNO;
        }

        public Integer getTerminalNO() {
            return terminalNO;
        }

        public void setTerminalNO(Integer terminalNO) {
            this.terminalNO = terminalNO;
        }

        public Integer getSupporterID() {
            return supporterID;
        }

        public void setSupporterID(Integer supporterID) {
            this.supporterID = supporterID;
        }

        public Integer getCourseID() {
            return courseID;
        }

        public void setCourseID(Integer courseID) {
            this.courseID = courseID;
        }

        public Integer getReceiptCount() {
            return receiptCount;
        }

        public void setReceiptCount(Integer receiptCount) {
            this.receiptCount = receiptCount;
        }

        public String getReceiptfDate() {
            return receiptfDate;
        }

        public void setReceiptfDate(String receiptfDate) {
            this.receiptfDate = receiptfDate;
        }

        public String getReceiptpDate() {
            return receiptpDate;
        }

        public void setReceiptpDate(String receiptpDate) {
            this.receiptpDate = receiptpDate;
        }

        public String getpMpDate() {
            return pMpDate;
        }

        public void setpMpDate(String pMpDate) {
            this.pMpDate = pMpDate;
        }

        public String getpMfDate() {
            return pMfDate;
        }

        public void setpMfDate(String pMfDate) {
            this.pMfDate = pMfDate;
        }

        public Integer getTrxCount() {
            return trxCount;
        }

        public void setTrxCount(Integer trxCount) {
            this.trxCount = trxCount;
        }

        public Integer getOldtrxCount() {
            return oldtrxCount;
        }

        public void setOldtrxCount(Integer oldtrxCount) {
            this.oldtrxCount = oldtrxCount;
        }

        public String getSenfCode() {
            return senfCode;
        }

        public void setSenfCode(String senfCode) {
            this.senfCode = senfCode;
        }

        public Integer getSwitchID() {
            return switchID;
        }

        public void setSwitchID(Integer switchID) {
            this.switchID = switchID;
        }

        public Integer getBankID() {
            return bankID;
        }

        public void setBankID(Integer bankID) {
            this.bankID = bankID;
        }

        public Integer getKarfarmaID() {
            return karfarmaID;
        }

        public void setKarfarmaID(Integer karfarmaID) {
            this.karfarmaID = karfarmaID;
        }

        public Integer getTermPMHISID() {
            return termPMHISID;
        }

        public void setTermPMHISID(Integer termPMHISID) {
            this.termPMHISID = termPMHISID;
        }

        public Integer getActAgentID() {
            return actAgentID;
        }

        public void setActAgentID(Integer actAgentID) {
            this.actAgentID = actAgentID;
        }

        public String getSupporterName() {
            return supporterName;
        }

        public void setSupporterName(String supporterName) {
            this.supporterName = supporterName;
        }
    }


    public class RequestPaperList {

        @SerializedName("termPMID")
        @Expose
        private Integer termPMID;
        @SerializedName("terminalNO")
        @Expose
        private Integer terminalNO;
        @SerializedName("instCreateDate")
        @Expose
        private String instCreateDate;
        @SerializedName("instCreatepDate")
        @Expose
        private String instCreatepDate;

        public Integer getTermPMID() {
            return termPMID;
        }

        public void setTermPMID(Integer termPMID) {
            this.termPMID = termPMID;
        }

        public Integer getTerminalNO() {
            return terminalNO;
        }

        public void setTerminalNO(Integer terminalNO) {
            this.terminalNO = terminalNO;
        }

        public String getInstCreateDate() {
            return instCreateDate;
        }

        public void setInstCreateDate(String instCreateDate) {
            this.instCreateDate = instCreateDate;
        }

        public String getInstCreatepDate() {
            return instCreatepDate;
        }

        public void setInstCreatepDate(String instCreatepDate) {
            this.instCreatepDate = instCreatepDate;
        }
    }
}
