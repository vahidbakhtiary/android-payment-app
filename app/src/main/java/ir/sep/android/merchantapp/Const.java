package ir.sep.android.merchantapp;

public class Const {

    public static final String TAG = "MerchantApp";

    public static final String  BASE_URL =
            BuildConfig.DEBUG ?
                    /*"https://172.31.10.66:8080/" : */  "http://10.0.2.2:8080/":
                    "https://sepyar.sep.ir:8080/";
    public static final String  FAQ_BASEURL=BASE_URL +"home/FAQ";
    public static final String  SUPPORT_BASEUR=BASE_URL+ "Home/PosError";
    public static final String APP_BASEURL=BASE_URL + "sepyar.apk";



    public static final int WEBSERVICE_TIME_OUT = 40;
    //public static final String RAHSEPAR_BASEURL = "https://report.sep.ir/";
    public static final String RAHSEPAR_USER_NAME="rahsepar";
    public static final String RAHSEPAR_PASSWORD="1oyOCbTXkGUTZjg5mWBFVhMvjFs=";
    public static final String RAHSEPAR_REPWEBSERVICE_KEY="d0d73f68-f1cb-4899-bef5-f56e52a70ec4";
    public static final String RAHSEPAR_CUSTOMER_KEY="30250e1e-e56a-4d4f-ab3c-34a7a20be160";
    public static final String RAHSEPAR_REPRELATION_KEY="c7f71172-5b2f-441f-b0b1-571d98a1551a";
    public static final String RAHSEPAR_BASEURL_Mock_Server = "https://run.mocky.io/";
    //public static final String RAHSEPAR_BASEURL_MERCHANT = "http://91.240.181.113:9090/";
    //public static final String RAHSEPAR_BASEURL_MERCHANT = "http://91.240.181.113:9090/MerchantSrv/ver/";


    public static final int SMS_TRY_AGAIN=120000;

    public static final String SHARED_PREF_merchant_config="merchant_config";
    public static final String SHARED_PREF_APP_GUID_KEY="AppKeyGUID";
    public static final String SHARED_PREF_Merchant_GUID_KEY="MerchantKeyGUID";
    public static final String SHARED_PREF_CUSTOMER_NO_KEY="CustomerNO";
    public static final String SHARED_PREF_IS_AUTHORIZED_SMS_KEY="isAuthrizedSMS";
    public static final String SHARED_PREF_IS_AUTHORIZED_PASS_KEY="isAuthrizedPASS";
    public static final String SHARED_PREF_IS_AUTHORIZED_SESSION_KEY="isAuthrizedSESSION";
    public static final String SHARED_PREF_TERMINAL_NUMBER_KEY="TerminalNO";
    public static final String SHARED_PREF_MOBILE_NUMBER_KEY="MobileNO";
    public static final String SHARED_PREF_REGKEY_KEY="RegKey";
    public static final String SHARED_PREF_Merchant_APPID_KEY="MerchantAppId";

    public static final String CALL_SUPPORTER_PHONE="02184080";



}
