package com.myquotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;
import com.payfort.sdk.android.dependancies.models.FortRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FortCallBackManager fortCallback = null;
    String deviceId = "", sdkToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fortCallback = FortCallback.Factory.create();
        // Generating deviceId
        deviceId = FortSdk.getDeviceId(MainActivity.this);
        Log.d("DeviceId ", deviceId);
        // prepare payment request
        FortRequest fortrequest = new FortRequest();
        fortrequest.setRequestMap(collectRequestMap("PASS_THE_GENERATED_SDK_TOKEN_HERE"));
        fortrequest.setShowResponsePage(true);

        callSdk(fortrequest);
    }

    private Map<String, Object> collectRequestMap(String sdkToken) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("command",
                "PURCHASE");
        requestMap.put("customer_email", "Sam@gmail.com");
        requestMap.put("currency",
                "SAR");
        requestMap.put("amount",
                "100");
        requestMap.put("language", "en");
        requestMap.put("merchant_reference", "ORD-0000007682");
        requestMap.put("customer_name", "Sam");
        requestMap.put("customer_ip", "172.150.16.10");
        requestMap.put("payment_option", "VISA");
        requestMap.put("eci",
                "ECOMMERCE");
        requestMap.put("order_description", "DESCRIPTION");
        requestMap.put("sdk_token", sdkToken);
        return requestMap;
    }

    private void callSdk(FortRequest fortrequest) {
        try {
            FortSdk.getInstance().registerCallback(MainActivity.this, fortrequest, FortSdk.ENVIRONMENT.TEST, 5, fortCallback, new FortInterfaces.OnTnxProcessed() {
                @Override
                public void onCancel(Map<String, Object> requestParamsMap,
                                     Map<String, Object> responseMap) {
                    Log.d("Cancelled ", responseMap.toString());
                }

                @Override
                public void onSuccess(Map<String, Object> requestParamsMap,
                                      Map<String, Object> fortResponseMap) {
                    Log.i("Success ", fortResponseMap.toString());
                }

                @Override
                public void onFailure(Map<String, Object> requestParamsMap,
                                      Map<String, Object> fortResponseMap) {
                    Log.e("Failure ", fortResponseMap.toString());
                }
            });
        } catch (Exception e) {
            Log.e("execute Payment", "call FortSdk", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode, resultCode, data);
    }
}