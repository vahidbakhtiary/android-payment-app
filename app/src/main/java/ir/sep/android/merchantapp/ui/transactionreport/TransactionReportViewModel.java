package ir.sep.android.merchantapp.ui.transactionreport;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.data.entities.RahseparRequest;
import ir.sep.android.merchantapp.data.entities.RahseparResponse;
import ir.sep.android.merchantapp.data.entities.ServiceColumn;
import ir.sep.android.merchantapp.data.entities.ShamsiDate;
import ir.sep.android.merchantapp.data.repository.TransactionReportRepository;
import saman.zamani.persiandate.PersianDate;

@ActivityRetainedScoped
public class TransactionReportViewModel extends ViewModel {

    private TransactionReportRepository repository;
    private MediatorLiveData<RahseparResponse> liveDataTransactionReport = new MediatorLiveData<>();
    private MediatorLiveData<RahseparResponse> liveDataTotallyReport = new MediatorLiveData<>();
    //other filters behavior
    private MutableLiveData<String> posconditioncode = new MutableLiveData<>();
    private MutableLiveData<String> prcodenum = new MutableLiveData<>();
    private MutableLiveData<String> shaparakstatus = new MutableLiveData<>();
    private MutableLiveData<String> settlestatus = new MutableLiveData<>();
    //for date behavior
    private ShamsiDate today = new ShamsiDate();
    private MediatorLiveData<ShamsiDate> startDate = new MediatorLiveData<>();
    private MutableLiveData<ShamsiDate> endDate = new MutableLiveData<>();
    //for adapter behavior (more button)
    private MutableLiveData<List<Rahsepar>> rahseparItemsInList = new MutableLiveData<>(null);
    private MutableLiveData<Rahsepar> expandedItemInList = new MutableLiveData<>(new Rahsepar());
    //for loading state
    private MutableLiveData<Boolean> isTransactionReportLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTotallyReportLoading = new MutableLiveData<>();


    @ViewModelInject
    @Inject
    public TransactionReportViewModel(TransactionReportRepository repository) {
        this.repository = repository;
        setupDefaultRequest();
    }

    public void setupDefaultRequest(){
        //Todo remove
//        today.setDay(01);
//        today.setMonth(03);
//        today.setYear(1399);


        posconditioncode.setValue("");
        prcodenum.setValue("");
        shaparakstatus.setValue("");
        settlestatus.setValue("");
        //set default start date
        setStartDate(today.getYear(),today.getMonth(),1);
        //set default end date
        setEndDate(today.getYear(),today.getMonth(),today.getMonthLength());
    }

    public void setparam(RahseparRequest request) {
        setIsTransactionReportLoading(true);
        LiveData<RahseparResponse> transactionReport = repository.getTransactionReport(request);
        liveDataTransactionReport.addSource(transactionReport, value -> {
            liveDataTransactionReport.removeSource(transactionReport);
            setExpandedItemInList(null);
            setIsTransactionReportLoading(false);
            liveDataTransactionReport.setValue(value);
        });
    }

    public void setparamTotally(RahseparRequest request) {
        setIsTotallyReportLoading(true);
        LiveData<RahseparResponse> totallyTransactionReport = repository.getTotallyTransactionReport(request);
        liveDataTotallyReport.addSource(totallyTransactionReport, value -> {
            liveDataTotallyReport.removeSource(totallyTransactionReport);
            setIsTotallyReportLoading(false);
            liveDataTotallyReport.setValue(value);
        });
    }

    public LiveData<RahseparResponse> getLiveDataTransactionReport() {
        return liveDataTransactionReport;
    }

    public LiveData<RahseparResponse> getLiveDataTotallyReport() {
        return liveDataTotallyReport;
    }

    public RahseparRequest makeRequestParam(String termId) {
        RahseparRequest request = new RahseparRequest();
        request.setCustomerNumber(null);
        request.setMerchantNumber(null);
        request.setTerminalNumber(null);
        request.setExportTo(0);

        ServiceColumn column1 = new ServiceColumn("تاریخ تراکنش سوئیچ", 60, 32, startDate.getValue().toString() /*ignore warning*/, "stracedt", "STraceDt", true, "");
        ServiceColumn column2 = new ServiceColumn("تاریخ تراکنش سوئیچ", 60, 22, endDate.getValue().toString() /*ignore warning*/, "stracedt", "STraceDt", true, "");
        ServiceColumn column3 = new ServiceColumn("شماره ترمینال", 50, 0, termId, "termid", "TermId", true, "");
        ServiceColumn column4 = new ServiceColumn("کد نوع پایانه", 50, 0, Rahsepar.Posconditioncode.getIdByDescription(getPosconditioncode().getValue()), "posconditioncode", "PosConditionCode", false, "");
        ServiceColumn column5 = new ServiceColumn("کد نوع تراکنش", 50, 0, Rahsepar.Prcodenum.getIdByDescription(getPrcodenum().getValue()), "prcodenum", "PrCodeNum", false, "");
        ServiceColumn column6 = new ServiceColumn("کد وضعیت تراکنش", 50, 0, Rahsepar.Shaparakstatus.getIdByDescription(getShaparakstatus().getValue()), "shaparakstatus", "ShaparakStatus", false, "");
        ServiceColumn column7 = new ServiceColumn("کد وضعیت تسویه", 50, 0, Rahsepar.Settlestatus.getIdByDescription(getSettlestatus().getValue()), "settlestatus", "SettleStatus", false, "");

        List<ServiceColumn> serviceColumnList = new ArrayList<>();
        serviceColumnList.add(column1);
        serviceColumnList.add(column2);
        serviceColumnList.add(column3);
        serviceColumnList.add(column4);
        serviceColumnList.add(column5);
        serviceColumnList.add(column6);
        serviceColumnList.add(column7);

        request.setServiceColumns(serviceColumnList);
        request.setUserName(Const.RAHSEPAR_USER_NAME);
        request.setPassword(Const.RAHSEPAR_PASSWORD);
        request.setRepWebserviceKey(Const.RAHSEPAR_REPWEBSERVICE_KEY);
        request.setCustomerKey(Const.RAHSEPAR_CUSTOMER_KEY);
        request.setRepRelationKey(Const.RAHSEPAR_REPRELATION_KEY);
        request.setCanSplitFun(false);
        request.setOfsetCount(-1);
        request.setFetchCount(-1);

        return request;
    }

    public RahseparRequest makeTotallyRequestParam(String termId) {
        RahseparRequest request = new RahseparRequest();
        request.setCustomerNumber(null);
        request.setMerchantNumber(null);
        request.setTerminalNumber(null);
        request.setExportTo(0);


        ServiceColumn column1 = new ServiceColumn("تاریخ تراکنش", 60, 32, startDate.getValue().toString() /*ignore warning*/, "txndate", "TxnDate", true, "");
        ServiceColumn column2 = new ServiceColumn("تاریخ تراکنش", 60, 22, endDate.getValue().toString() /*ignore warning*/, "txndate", "TxnDate", true, "");
        ServiceColumn column3 = new ServiceColumn("شماره ترمینال", 50, 0, termId, "termid", "termid", true, "");

        List<ServiceColumn> serviceColumnList = new ArrayList<>();
        serviceColumnList.add(column1);
        serviceColumnList.add(column2);
        serviceColumnList.add(column3);

        request.setServiceColumns(serviceColumnList);
        request.setUserName("rahsepar");
        request.setPassword("1nUSJwZZjCRsTg6GWnx94jDhtM0=");
        request.setRepWebserviceKey("812ccce3-479a-45a0-a5d2-38543437d987");
        request.setCustomerKey("30250e1e-e56a-4d4f-ab3c-34a7a20be160");
        request.setRepRelationKey("2bf5c679-0946-44d5-a289-988aeb3df014");
        request.setCanSplitFun(false);
        request.setOfsetCount(-1);
        request.setFetchCount(-1);

        return request;
    }

    //for date behavior
    public LiveData<ShamsiDate> getStartDate() {
        return startDate;
    }

    public void setStartDate(int year, int month, int day) {
        this.startDate.setValue(new ShamsiDate(year, month, day));
    }

    public ShamsiDate getToday() {
        return today;
    }

    public ShamsiDate getMaxEndDate() {
        ShamsiDate startDateValue = startDate.getValue();
        if (startDateValue == null) return null;
        PersianDate maxDate = new PersianDate()
                .setShYear(startDateValue.getYear())
                .setShMonth(startDateValue.getMonth())
                .setShDay(startDateValue.getDay())
                .addDate(0, 0, 40);

        int i = maxDate.compareTo(new PersianDate());
        if (i == 1) {
            return getToday();
        } else if (i == 0) {
            return getToday();
        } else {
            return new ShamsiDate(maxDate.getShYear(), maxDate.getShMonth(), maxDate.getShDay());
        }
    }

    public ShamsiDate getMaxStartDate() {
        return getToday();
    }

    public LiveData<ShamsiDate> getEndDate() {
        return endDate;
    }

    public void setEndDate(int year, int month, int day) {
        this.endDate.setValue(new ShamsiDate(year, month, day));
    }

    public void resetEndDate() {
        ShamsiDate startDateValue = startDate.getValue();
        if (startDateValue == null) return;
        PersianDate maxPersianDate = new PersianDate()
                .setShYear(startDateValue.getYear())
                .setShMonth(startDateValue.getMonth())
                .setShDay(startDateValue.getDay())
                .addDate(0, 0, 20);
        int i = maxPersianDate.compareTo(new PersianDate());
        if (i == 1) {
            this.endDate.setValue(getToday());
        } else if (i == 0) {
            this.endDate.setValue(getToday());
        } else {
            this.endDate.setValue(new ShamsiDate(maxPersianDate.getShYear(), maxPersianDate.getShMonth(), maxPersianDate.getShDay()));
        }
    }

    //for adapter behavior (more button)
    public LiveData<List<Rahsepar>> getRahseparItemsInList() {
        return rahseparItemsInList;
    }

    public void setRahseparItemsInList(List<Rahsepar> rahseparItemsInList) {
        this.rahseparItemsInList.setValue(rahseparItemsInList);
    }

    public LiveData<Rahsepar> getExpandedItemInList() {
        return expandedItemInList;
    }

    public void setExpandedItemInList(Rahsepar item) {
        this.expandedItemInList.setValue(item);
    }

    //other filters getter & setters

    public MutableLiveData<String> getPosconditioncode() {
        return posconditioncode;
    }

    public void setPosconditioncode(String posconditioncode) {
        this.posconditioncode.setValue(posconditioncode);
    }

    public MutableLiveData<String> getPrcodenum() {
        return prcodenum;
    }

    public void setPrcodenum(String prcodenum) {
        this.prcodenum.setValue(prcodenum);
    }

    public MutableLiveData<String> getShaparakstatus() {
        return shaparakstatus;
    }

    public void setShaparakstatus(String shaparakstatus) {
        this.shaparakstatus.setValue(shaparakstatus);
    }

    public MutableLiveData<String> getSettlestatus() {
        return settlestatus;
    }

    public void setSettlestatus(String settlestatus) {
        this.settlestatus.setValue(settlestatus);
    }

    public LiveData<Boolean> getTransactionReportLoadingState() {
        return isTransactionReportLoading;
    }

    public void setIsTransactionReportLoading(boolean isTransactionReportLoading) {
        this.isTransactionReportLoading.setValue(isTransactionReportLoading);
    }

    public LiveData<Boolean> getTotallyReportLoadingState() {
        return isTotallyReportLoading;
    }

    public void setIsTotallyReportLoading(boolean isLoading) {
        this.isTotallyReportLoading.setValue(isLoading);
    }
}