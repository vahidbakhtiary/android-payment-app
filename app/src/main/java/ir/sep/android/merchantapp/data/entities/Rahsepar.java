package ir.sep.android.merchantapp.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import ir.sep.android.merchantapp.utils.NumberHelper;

public class Rahsepar {
    @SerializedName("settlestatus")
    @Expose
    private int settlestatus;
    @SerializedName("cityname")
    @Expose
    private String cityname;
    @SerializedName("provincename")
    @Expose
    private String provincename;
    @SerializedName("posconditioncode")
    @Expose
    private int posconditioncode;
    @SerializedName("responsedesc")
    @Expose
    private String responsedesc;
    @SerializedName("shaparakstatus")
    @Expose
    private int shaparakstatus;
    @SerializedName("settlestatusdesc")
    @Expose
    private String settlestatusdesc;
    @SerializedName("settleibans")
    @Expose
    private Object settleibans;
    @SerializedName("shaparaksettleibans")
    @Expose
    private Object shaparaksettleibans;
    @SerializedName("sprcode")
    @Expose
    private int sprcode;
    @SerializedName("stracedt")
    @Expose
    private String stracedt;
    @SerializedName("shaparaksettledate")
    @Expose
    private String shaparaksettledate;
    @SerializedName("ptracedt")
    @Expose
    private String ptracedt;
    @SerializedName("ptraceno")
    @Expose
    private int ptraceno;
    @SerializedName("termid")
    @Expose
    private int termid;
    @SerializedName("pin_charegeserianno")
    @Expose
    private Object pinCharegeserianno;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("rrn")
    @Expose
    private long rrn;
    @SerializedName("partyid")
    @Expose
    private int partyid;
    @SerializedName("payid")
    @Expose
    private Object payid;
    @SerializedName("billid")
    @Expose
    private Object billid;
    @SerializedName("employerid")
    @Expose
    private int employerid;
    @SerializedName("bankid")
    @Expose
    private int bankid;
    @SerializedName("responsecode")
    @Expose
    private int responsecode;
    @SerializedName("straceno")
    @Expose
    private int straceno;
    @SerializedName("agentid")
    @Expose
    private int agentid;
    @SerializedName("cityid")
    @Expose
    private int cityid;
    @SerializedName("regcode")
    @Expose
    private Object regcode;
    @SerializedName("prcodenum")
    @Expose
    private int prcodenum;
    @SerializedName("groupid")
    @Expose
    private int groupid;
    @SerializedName("originalamount")
    @Expose
    private long originalamount;
    @SerializedName("affectiveamount")
    @Expose
    private double affectiveamount;
    @SerializedName("merchantname")
    @Expose
    private String merchantname;
    @SerializedName("poscondtion")
    @Expose
    private String poscondtion;
    @SerializedName("prcode")
    @Expose
    private String prcode;
    @SerializedName("shaparakstatusdesc")
    @Expose
    private String shaparakstatusdesc;

    private boolean isExpanded = false;
    private int positionInList;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public int getSettlestatus() {
        return settlestatus;
    }

    public void setSettlestatus(int settlestatus) {
        this.settlestatus = settlestatus;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public int getPosconditioncode() {
        return posconditioncode;
    }

    public void setPosconditioncode(int posconditioncode) {
        this.posconditioncode = posconditioncode;
    }

    public String getResponsedesc() {
        return responsedesc;
    }

    public void setResponsedesc(String responsedesc) {
        this.responsedesc = responsedesc;
    }

    public int getShaparakstatus() {
        return shaparakstatus;
    }

    public void setShaparakstatus(int shaparakstatus) {
        this.shaparakstatus = shaparakstatus;
    }

    public String getSettlestatusdesc() {
        return Settlestatus.getDescription(settlestatus);
    }


    public void setSettlestatusdesc(String settlestatusdesc) {
        this.settlestatusdesc = settlestatusdesc;
    }

    public Object getSettleibans() {
        return settleibans;
    }

    public void setSettleibans(Object settleibans) {
        this.settleibans = settleibans;
    }

    public Object getShaparaksettleibans() {
        return shaparaksettleibans;
    }

    public void setShaparaksettleibans(Object shaparaksettleibans) {
        this.shaparaksettleibans = shaparaksettleibans;
    }

    public int getSprcode() {
        return sprcode;
    }

    public void setSprcode(int sprcode) {
        this.sprcode = sprcode;
    }

    public String getStracedt() {
        return stracedt;
    }

    public void setStracedt(String stracedt) {
        this.stracedt = stracedt;
    }

    public String getTransactionDateTime() {
        //"1399/01/17 17:01:12.000"
        String dateTime = stracedt.trim().toLowerCase();
        if (dateTime.isEmpty()) return "";

        String[] splitBySpace = dateTime.split("\\s+");

        String date = splitBySpace[0];
        String rawTime = splitBySpace[1].split("\\.")[0];
        String time = rawTime.substring(0, rawTime.length() - 3);

        return time + " " + date;
    }

    public String getShaparaksettledate() {
        return shaparaksettledate;
    }

    public void setShaparaksettledate(String shaparaksettledate) {
        this.shaparaksettledate = shaparaksettledate;
    }

    public String getPtracedt() {
        return ptracedt;
    }

    public void setPtracedt(String ptracedt) {
        this.ptracedt = ptracedt;
    }

    public int getPtraceno() {
        return ptraceno;
    }

    public void setPtraceno(int ptraceno) {
        this.ptraceno = ptraceno;
    }

    public int getTermid() {
        return termid;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public Object getPinCharegeserianno() {
        return pinCharegeserianno;
    }

    public void setPinCharegeserianno(Object pinCharegeserianno) {
        this.pinCharegeserianno = pinCharegeserianno;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public long getRrn() {
        return rrn;
    }

    public void setRrn(int rrn) {
        this.rrn = rrn;
    }

    public String getRrnInString() {
        return "شماره مرجع: " + rrn;
    }

    public int getPartyid() {
        return partyid;
    }

    public void setPartyid(int partyid) {
        this.partyid = partyid;
    }

    public Object getPayid() {
        return payid;
    }

    public void setPayid(Object payid) {
        this.payid = payid;
    }

    public Object getBillid() {
        return billid;
    }

    public void setBillid(Object billid) {
        this.billid = billid;
    }

    public int getEmployerid() {
        return employerid;
    }

    public void setEmployerid(int employerid) {
        this.employerid = employerid;
    }

    public int getBankid() {
        return bankid;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
    }

    public int getStraceno() {
        return straceno;
    }

    public void setStraceno(int straceno) {
        this.straceno = straceno;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public Object getRegcode() {
        return regcode;
    }

    public void setRegcode(Object regcode) {
        this.regcode = regcode;
    }

    public int getPrcodenum() {
        return prcodenum;
    }

    public void setPrcodenum(int prcodenum) {
        this.prcodenum = prcodenum;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getOriginalamount() {
        return new NumberHelper().sperateDigit(originalamount) ;
    }

    public void setOriginalamount(long originalamount) {
        this.originalamount = originalamount;
    }

    public double getAffectiveamount() {
        return affectiveamount;
    }

    public void setAffectiveamount(double affectiveamount) {
        this.affectiveamount = affectiveamount;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getPoscondtion() {
        return poscondtion;
    }

    public void setPoscondtion(String poscondtion) {
        this.poscondtion = poscondtion;
    }

    public String getPrcode() {
        return prcode;
    }

    public void setPrcode(String prcode) {
        this.prcode = prcode;
    }

    public String getShaparakstatusdesc() {
        return Shaparakstatus.getDescription(shaparakstatus);
    }

    public void setShaparakstatusdesc(String shaparakstatusdesc) {
        this.shaparakstatusdesc = shaparakstatusdesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rahsepar rahsepar = (Rahsepar) o;
        return rrn == rahsepar.rrn &&
                Objects.equals(settlestatus, rahsepar.settlestatus) &&
                Objects.equals(cityname, rahsepar.cityname) &&
                Objects.equals(provincename, rahsepar.provincename) &&
                Objects.equals(posconditioncode, rahsepar.posconditioncode) &&
                Objects.equals(responsedesc, rahsepar.responsedesc) &&
                Objects.equals(shaparakstatus, rahsepar.shaparakstatus) &&
                Objects.equals(settlestatusdesc, rahsepar.settlestatusdesc) &&
                Objects.equals(settleibans, rahsepar.settleibans) &&
                Objects.equals(shaparaksettleibans, rahsepar.shaparaksettleibans) &&
                Objects.equals(sprcode, rahsepar.sprcode) &&
                Objects.equals(stracedt, rahsepar.stracedt) &&
                Objects.equals(shaparaksettledate, rahsepar.shaparaksettledate) &&
                Objects.equals(ptracedt, rahsepar.ptracedt) &&
                Objects.equals(ptraceno, rahsepar.ptraceno) &&
                Objects.equals(termid, rahsepar.termid) &&
                Objects.equals(pinCharegeserianno, rahsepar.pinCharegeserianno) &&
                Objects.equals(pan, rahsepar.pan) &&
                Objects.equals(partyid, rahsepar.partyid) &&
                Objects.equals(payid, rahsepar.payid) &&
                Objects.equals(billid, rahsepar.billid) &&
                Objects.equals(employerid, rahsepar.employerid) &&
                Objects.equals(bankid, rahsepar.bankid) &&
                Objects.equals(responsecode, rahsepar.responsecode) &&
                Objects.equals(straceno, rahsepar.straceno) &&
                Objects.equals(agentid, rahsepar.agentid) &&
                Objects.equals(cityid, rahsepar.cityid) &&
                Objects.equals(regcode, rahsepar.regcode) &&
                Objects.equals(prcodenum, rahsepar.prcodenum) &&
                Objects.equals(groupid, rahsepar.groupid) &&
                Objects.equals(originalamount, rahsepar.originalamount) &&
                Objects.equals(affectiveamount, rahsepar.affectiveamount) &&
                Objects.equals(merchantname, rahsepar.merchantname) &&
                Objects.equals(poscondtion, rahsepar.poscondtion) &&
                Objects.equals(prcode, rahsepar.prcode) &&
                Objects.equals(shaparakstatusdesc, rahsepar.shaparakstatusdesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settlestatus, cityname, provincename, posconditioncode, responsedesc, shaparakstatus, settlestatusdesc, settleibans, shaparaksettleibans, sprcode, stracedt, shaparaksettledate, ptracedt, ptraceno, termid, pinCharegeserianno, pan, rrn, partyid, payid, billid, employerid, bankid, responsecode, straceno, agentid, cityid, regcode, prcodenum, groupid, originalamount, affectiveamount, merchantname, poscondtion, prcode, shaparakstatusdesc);
    }

    @Override
    public String toString() {
        return "Rahsepar{" +
                "settlestatus=" + settlestatus +
                ", cityname='" + cityname + '\'' +
                ", provincename='" + provincename + '\'' +
                ", posconditioncode=" + posconditioncode +
                ", responsedesc='" + responsedesc + '\'' +
                ", shaparakstatus=" + shaparakstatus +
                ", settlestatusdesc='" + settlestatusdesc + '\'' +
                ", settleibans=" + settleibans +
                ", shaparaksettleibans=" + shaparaksettleibans +
                ", sprcode=" + sprcode +
                ", stracedt='" + stracedt + '\'' +
                ", shaparaksettledate='" + shaparaksettledate + '\'' +
                ", ptracedt='" + ptracedt + '\'' +
                ", ptraceno=" + ptraceno +
                ", termid=" + termid +
                ", pinCharegeserianno=" + pinCharegeserianno +
                ", pan='" + pan + '\'' +
                ", rrn=" + rrn +
                ", partyid=" + partyid +
                ", payid=" + payid +
                ", billid=" + billid +
                ", employerid=" + employerid +
                ", bankid=" + bankid +
                ", responsecode=" + responsecode +
                ", straceno=" + straceno +
                ", agentid=" + agentid +
                ", cityid=" + cityid +
                ", regcode=" + regcode +
                ", prcodenum=" + prcodenum +
                ", groupid=" + groupid +
                ", originalamount=" + originalamount +
                ", affectiveamount=" + affectiveamount +
                ", merchantname='" + merchantname + '\'' +
                ", poscondtion='" + poscondtion + '\'' +
                ", prcode='" + prcode + '\'' +
                ", shaparakstatusdesc='" + shaparakstatusdesc + '\'' +
                ", isExpanded=" + isExpanded +
                ", positionInList=" + positionInList +
                '}';
    }


    //کد نوع تراکنش
    public enum Prcodenum {

        All(-1, "همه"),
        Sale(0, "خرید"),
        Bill(40, "قبض"),
        PaymentService(60, "سرویس پرداخت"),
        Balance(31, "مانده گیری"),
        Charge(30, "شارژ");


        private int id;
        private String description;

        Prcodenum(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public static String getIdByDescription(String description) {


            for (Prcodenum item : values()) {
                if (item.description.equals(description)) {
                    if (item.getId() == -1)
                        return "";
                    else
                        return String.valueOf(item.getId());
                }
            }

            return "";

        }
    }

    //کد وضعیت تسویه
    public enum Settlestatus {
        All(-1,"همه"),
        WaitingForSettle(1, "در انتظار تسویه"),
        NotPossibleForSettle(0, "غیر قابل تسویه"),
        Settled(2, "تسویه شده");


        private int id;
        private String description;

        Settlestatus(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public static String getDescription(int id) {
            String description = "";
            for (Settlestatus item : Settlestatus.values()) {
                if (id == item.id) description = item.description;
            }
            return description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public static String getIdByDescription(String description) {
            for (Settlestatus item : values()) {
                if (item.description.equals(description)) {
                    if (item.getId() == -1)
                        return "";
                    else
                        return String.valueOf(item.getId());
                }
            }

            return "";

        }
    }

    //کد وضعیت تراکنش
    public enum Shaparakstatus {
        All(-1,"همه"),
        Success(1, "موفق"),
        Faild(3, "ناموفق"),
        Proceccing(2, "درحال پردازش");

        private int id;
        private String description;

        Shaparakstatus(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public static String getDescription(int id) {
            String description = "";
            for (Shaparakstatus item : Shaparakstatus.values()) {
                if (id == item.id) description = item.description;
            }
            return description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public static String getIdByDescription(String description) {
            for (Shaparakstatus item : values()) {
                if (item.description.equals(description)) {
                    if (item.getId() == -1)
                        return "";
                    else
                        return String.valueOf(item.getId());
                }
            }

            return "";

        }
    }

    //کد نوع پایانه
    public enum Posconditioncode {
        All(-1,"همه"),
        Pos(14, "کارتخوان"),
        Mobile(5, "موبایل"),
        Internet(59, "اینترنت");

        private int id;
        private String description;

        Posconditioncode(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }


        public static String getIdByDescription(String description) {
            for (Posconditioncode item : values()) {
                if (item.description.equals(description)) {
                    if (item.getId() == -1)
                        return "";
                    else
                        return String.valueOf(item.getId());
                }
            }

            return "";

        }
    }


    public class TotallyTransaction {
        @SerializedName("purchase_count")
        @Expose
        private int purchaseCount;
        @SerializedName("service_count")
        @Expose
        private int serviceCount;
        @SerializedName("ud_count")
        @Expose
        private int udCount;
        @SerializedName("charge_count")
        @Expose
        private int chargeCount;
        @SerializedName("topupcharge_count")
        @Expose
        private int topupchargeCount;
        @SerializedName("bill_count")
        @Expose
        private int billCount;
        @SerializedName("reverse_count")
        @Expose
        private int reverseCount;
        @SerializedName("groupcharge_count")
        @Expose
        private int groupchargeCount;
        @SerializedName("balance_count")
        @Expose
        private int balanceCount;
        @SerializedName("faild_count")
        @Expose
        private int faildCount;
        @SerializedName("charge_sum")
        @Expose
        private int chargeSum;
        @SerializedName("topupcharge_sum")
        @Expose
        private int topupchargeSum;
        @SerializedName("groupcharge_sum")
        @Expose
        private int groupchargeSum;
        @SerializedName("merchantnumber")
        @Expose
        private String merchantnumber;
        @SerializedName("termid")
        @Expose
        private int termid;
        @SerializedName("ud_sum")
        @Expose
        private int udSum;
        @SerializedName("purchase_sum")
        @Expose
        private int purchaseSum;
        @SerializedName("service_sum")
        @Expose
        private int serviceSum;
        @SerializedName("bill_sum")
        @Expose
        private int billSum;

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public void setPurchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
        }

        public int getServiceCount() {
            return serviceCount;
        }

        public void setServiceCount(int serviceCount) {
            this.serviceCount = serviceCount;
        }

        public int getUdCount() {
            return udCount;
        }

        public void setUdCount(int udCount) {
            this.udCount = udCount;
        }

        public int getChargeCount() {
            return chargeCount;
        }

        public void setChargeCount(int chargeCount) {
            this.chargeCount = chargeCount;
        }

        public int getTopupchargeCount() {
            return topupchargeCount;
        }

        public void setTopupchargeCount(int topupchargeCount) {
            this.topupchargeCount = topupchargeCount;
        }

        public int getBillCount() {
            return billCount;
        }

        public void setBillCount(int billCount) {
            this.billCount = billCount;
        }

        public int getReverseCount() {
            return reverseCount;
        }

        public void setReverseCount(int reverseCount) {
            this.reverseCount = reverseCount;
        }

        public int getGroupchargeCount() {
            return groupchargeCount;
        }

        public void setGroupchargeCount(int groupchargeCount) {
            this.groupchargeCount = groupchargeCount;
        }

        public int getBalanceCount() {
            return balanceCount;
        }

        public void setBalanceCount(int balanceCount) {
            this.balanceCount = balanceCount;
        }

        public int getFaildCount() {
            return faildCount;
        }

        public void setFaildCount(int faildCount) {
            this.faildCount = faildCount;
        }

        public int getChargeSum() {
            return chargeSum;
        }

        public void setChargeSum(int chargeSum) {
            this.chargeSum = chargeSum;
        }

        public int getTopupchargeSum() {
            return topupchargeSum;
        }

        public void setTopupchargeSum(int topupchargeSum) {
            this.topupchargeSum = topupchargeSum;
        }

        public int getGroupchargeSum() {

            return groupchargeSum;
        }

        public void setGroupchargeSum(int groupchargeSum) {
            this.groupchargeSum = groupchargeSum;
        }

        public String getMerchantnumber() {
            return merchantnumber;
        }

        public void setMerchantnumber(String merchantnumber) {
            this.merchantnumber = merchantnumber;
        }

        public int getTermid() {
            return termid;
        }

        public void setTermid(int termid) {
            this.termid = termid;
        }

        public int getUdSum() {
            return udSum;
        }

        public void setUdSum(int udSum) {
            this.udSum = udSum;
        }

        public int getPurchaseSum() {
            return purchaseSum;
        }

        public void setPurchaseSum(int purchaseSum) {
            this.purchaseSum = purchaseSum;
        }

        public int getServiceSum() {
            return serviceSum;
        }

        public void setServiceSum(int serviceSum) {
            this.serviceSum = serviceSum;
        }

        public int getBillSum() {
            return billSum;
        }

        public void setBillSum(int billSum) {
            this.billSum = billSum;
        }
    }
}
