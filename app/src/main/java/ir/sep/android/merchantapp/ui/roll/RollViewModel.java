package ir.sep.android.merchantapp.ui.roll;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.data.repository.RollRepository;

public class RollViewModel extends ViewModel {

    private RollRepository repository;
    private LiveData<MerchantServiceEntity> liveDataSendRollRequest;
    private LiveData<MerchantServiceRollEntity> liveDataRollHistory;
    @ViewModelInject
    public RollViewModel(RollRepository repository) {
        this.repository = repository;
    }


    public LiveData<MerchantServiceEntity> sendRollRequest(String merchant_GUID, String appKey_GUID, long terminalNo) {
        this.liveDataSendRollRequest = repository.sendRollRequest(merchant_GUID,appKey_GUID,terminalNo);
        return this.liveDataSendRollRequest;
    }

    public LiveData<MerchantServiceRollEntity> getRollHistory(String merchant_GUID, String appKey_GUID) {
        this.liveDataRollHistory = repository.getRollHistory(merchant_GUID,appKey_GUID);
        return this.liveDataRollHistory;
       // return  new MutableLiveData<>();
    }
}
