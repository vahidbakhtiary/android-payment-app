package ir.sep.android.merchantapp.ui.roll;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ir.sep.android.merchantapp.Helper.TestUtil;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.data.repository.RollRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class RollViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private RollViewModel viewModel;
    @Mock
    RollRepository repository;
    @Mock
    Observer<MerchantServiceRollEntity> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;


    MutableLiveData<MerchantServiceRollEntity> liveData;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        viewModel = new RollViewModel(repository);

        liveData=new MutableLiveData<>();
        liveData.setValue(new MerchantServiceRollEntity());
        when(repository.getRollHistory(TestUtil.merchant_GUID,TestUtil.appKey_GUID)).thenReturn(liveData);
        viewModel.getRollHistory(TestUtil.merchant_GUID,TestUtil.appKey_GUID).observeForever(observer);
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
        viewModel = null;
    }

    @Test
    public void getRollHistory_test_null() {
        when(repository.getRollHistory(TestUtil.merchant_GUID,TestUtil.appKey_GUID)).thenReturn(liveData);
        assertNotNull(viewModel.getRollHistory(TestUtil.merchant_GUID,TestUtil.appKey_GUID));
        assertTrue(viewModel.getRollHistory(TestUtil.merchant_GUID,TestUtil.appKey_GUID).hasObservers());
    }


}