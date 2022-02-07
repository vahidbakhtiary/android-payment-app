package ir.sep.android.merchantapp.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import ir.sep.android.merchantapp.ui.transactionreport.TotallyTransactionReportFilterFragment;
import ir.sep.android.merchantapp.ui.transactionreport.TransactionReportFilterFragment;
import ir.sep.android.merchantapp.ui.transactionreport.TotallyTransactionReportFragment;
import ir.sep.android.merchantapp.ui.transactionreport.TransactionReportFragment;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class TransactionReportModule {

    @Provides
    public TotallyTransactionReportFragment provideTotallyTransactionReportFragment() {
        return new TotallyTransactionReportFragment();
    }

    @Provides
    public TransactionReportFragment provideTransactionReportFragment() {
        return new TransactionReportFragment();
    }
}