package ir.sep.android.merchantapp.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ir.sep.android.merchantapp.Helper.LiveDataTestUtil;
import ir.sep.android.merchantapp.Helper.TestUtil;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class RollRepositoryTest {


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    RollRepository repository;

    @Mock
    MerchantSrvApiService apiService;

    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;


    MutableLiveData<MerchantServiceRollEntity> liveData;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        repository = new RollRepository(apiService);

        liveData=new MutableLiveData<>();
        liveData.setValue(new MerchantServiceRollEntity());

    }

    @Test
    public void getRollHistory_returnListWithHistory() throws InterruptedException {

        // Arrange
        MerchantServiceRollEntity merchantServiceRollEntities = TestUtil.TEST_MerchantServiceRollEntity_LIST.get(0);

        LiveDataTestUtil<MerchantServiceRollEntity> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<MerchantServiceRollEntity> returnedData = new MutableLiveData<>();
        returnedData.setValue(merchantServiceRollEntities);

        LiveData<MerchantServiceRollEntity> nnnn = null;

        when(repository.getRollHistory(TestUtil.merchant_GUID, TestUtil.appKey_GUID)).thenReturn(nnnn);

        // Act
        MerchantServiceRollEntity observedData = liveDataTestUtil.getValue(repository.getRollHistory(TestUtil.merchant_GUID, TestUtil.appKey_GUID));

        // Assert
        assertEquals(merchantServiceRollEntities, observedData);

    }

    @Test
    public void getRollHistory() {

        String merchant_GUID="sss";
        String appKey_GUID="sssdd";


        doThrow(new Exception()).when(apiService).PosPaperRollHis(merchant_GUID,appKey_GUID);
        LiveData<MerchantServiceRollEntity> rollHistory = repository.getRollHistory(merchant_GUID, appKey_GUID);

    }
}