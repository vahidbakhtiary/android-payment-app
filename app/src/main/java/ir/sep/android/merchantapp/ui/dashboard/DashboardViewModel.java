package ir.sep.android.merchantapp.ui.dashboard;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import ir.sep.android.merchantapp.data.entities.RahseparRequest;
import ir.sep.android.merchantapp.data.entities.RahseparResponse;
import ir.sep.android.merchantapp.data.entities.ServiceColumn;
import ir.sep.android.merchantapp.data.entities.ShamsiDate;
import ir.sep.android.merchantapp.data.repository.TransactionReportRepository;

@ActivityRetainedScoped
class DashboardViewModel extends ViewModel {

    private TransactionReportRepository repository;
    public ShamsiDate today = new ShamsiDate();
    private MediatorLiveData<RahseparResponse> totallyReport = new MediatorLiveData<>();
    private MutableLiveData<Boolean> isTotallyReportLoading = new MutableLiveData<>();
    private MediatorLiveData<ShamsiDate> todatDateMediatorLiveData = new MediatorLiveData<>();

    @ViewModelInject
    @Inject
    public DashboardViewModel(TransactionReportRepository repository) {
        this.repository = repository;
    }

    public RahseparRequest makeTotallyRequestParameters(String termId) {
        RahseparRequest request = new RahseparRequest();
        request.setCustomerNumber(null);
        request.setMerchantNumber(null);
        request.setTerminalNumber(null);
        request.setExportTo(0);

        ShamsiDate startDate = new ShamsiDate(today.getYear(), today.getMonth(), 1);
        ShamsiDate endDate = new ShamsiDate(today.getYear(), today.getMonth(), today.getMonthLength());

        ServiceColumn column1 = new ServiceColumn("تاریخ تراکنش", 60, 32, startDate.toString(), "txndate", "TxnDate", true, "");
        ServiceColumn column2 = new ServiceColumn("تاریخ تراکنش", 60, 22, endDate.toString(), "txndate", "TxnDate", true, "");
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

    public void sendRequest(RahseparRequest request) {
        setIsTotallyReportLoading(true);
        LiveData<RahseparResponse> totallyTransactionReport = repository.getTotallyTransactionReport(request);
        totallyReport.addSource(totallyTransactionReport, value -> {
            totallyReport.removeSource(totallyTransactionReport);
            setIsTotallyReportLoading(false);
            totallyReport.setValue(value);
        });
    }

    public LiveData<Boolean> getTotallyReportLoadingState() {
        return isTotallyReportLoading;
    }

    public void setIsTotallyReportLoading(boolean isLoading) {
        this.isTotallyReportLoading.setValue(isLoading);
    }

    public LiveData<RahseparResponse> getTotallyReport() {
        return totallyReport;
    }



    public void setTodayShamsiDate(ShamsiDate shamsiDate)
    {
        todatDateMediatorLiveData.setValue(shamsiDate);
    }
    public LiveData<ShamsiDate> getTodayShamsiDateLiveData()
    {
        return todatDateMediatorLiveData;
    }

    public float generateMaxForChar(float[] arr)
        {

            Arrays.sort(arr);
            float max = arr[arr.length-1];
            if (max==0)
                return 1;

            BigDecimal d = new BigDecimal(String.valueOf(max));
            BigDecimal d2 = d.setScale(0, BigDecimal.ROUND_HALF_UP);
            String newStr=d2.toString();
            int firstDigit=Integer.parseInt(String.valueOf(newStr.charAt(0)));
            firstDigit++;
            int lenght=newStr.length();


            String zero="";
            for (int i=0;i<lenght - 1;i++)
            {
                    zero+="0";
            }

            newStr =firstDigit + zero;

            return  Float.parseFloat(newStr) ;


        }
    public float generateMaxForChar(int[] arr)
    {

        Arrays.sort(arr);
        float max = arr[arr.length-1];

        if (max==0)
            return 1;

         return max + 10;


    }
}
