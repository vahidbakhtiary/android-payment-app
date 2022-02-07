package ir.sep.android.merchantapp.ui.login;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.repository.LoginRepository;

@ActivityRetainedScoped
public class LoginViewModel extends ViewModel {

    private LoginRepository repository;
    private LiveData<MerchantServiceEntity> liveDataRegistration;
    private LiveData<MerchantServiceEntity> liveDataValidateRegistrationPin;
    private MutableLiveData<String> pass = new MutableLiveData<>();
    public MutableLiveData<String> getPass() {
        return pass;
    }

    @ViewModelInject
    @Inject
    public LoginViewModel(LoginRepository repository) {
        this.repository = repository;
    }


    public void setPass(String pass) {
        this.pass.setValue(pass);
    }

    public LiveData<MerchantServiceEntity> getRegistrationLiveData(long terminal, String mobileNO, String cell_number) {
        this.liveDataRegistration = repository.getRegistrationPin(terminal,mobileNO,cell_number);
        return this.liveDataRegistration;
    }

    public LiveData<MerchantServiceEntity> saveAuthenticationParams(long terminal, String mobileNO, String appKey, String pin,String regKey) {

        this.liveDataValidateRegistrationPin = repository.validateRegistrationPin(terminal,mobileNO, appKey,pin,regKey);
        return this.liveDataValidateRegistrationPin;
    }
}
