package ir.sep.android.merchantapp.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;

public class TestUtil {

    public static final String appKey_GUID ="bf46a091-9091-4f93-a215-f3fc250b4f00";
    public static final  String merchant_GUID="dc113095-d045-4d21-b63e-04ee3364371a";
    public static final long  TerminalNO =2019;

    public static final List<MerchantServiceRollEntity> TEST_MerchantServiceRollEntity_LIST = Collections.unmodifiableList(
            new ArrayList<MerchantServiceRollEntity>(){{
                add(new MerchantServiceRollEntity());
                add(new MerchantServiceRollEntity());
            }}
    );
}
