package ir.sep.android.merchantapp.ui.supervisor;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.repository.SupervisorRepository;

public class SupervisorViewModel extends ViewModel {


    private SupervisorRepository repository;
    private LiveData<MerchantServiceEntity> liveData;

    @ViewModelInject
    public SupervisorViewModel(SupervisorRepository repository) {
        this.repository = repository;
    }


    public LiveData<MerchantServiceEntity> posResetPW(String merchant_GUID, String appKey_GUID, long terminalNo) {
        this.liveData = repository.posResetPW(merchant_GUID,appKey_GUID,terminalNo);
        return this.liveData;
    }


}
