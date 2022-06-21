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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class PayfortActivity extends AppCompatActivity {
    private FortCallBackManager fortCallback = null;
    String deviceId = "";
    String sdkToken = "";
    String merchant_identifier = "";
    String access_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payfort);

        fortCallback = FortCallback.Factory.create();
        // Generating deviceId
        deviceId = FortSdk.getDeviceId(PayfortActivity.this);
        Log.d("DeviceId ", deviceId);
        // prepare payment request
        FortRequest fortrequest = new FortRequest();
        fortrequest.setRequestMap(collectRequestMap(sdkToken));
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
            FortSdk.getInstance().registerCallback(PayfortActivity.this, fortrequest, FortSdk.ENVIRONMENT.TEST, 5, fortCallback, new FortInterfaces.OnTnxProcessed() {
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

    private String getSignature() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            String text = "REQUESTPHRASEaccess_code="+ access_code + "device_id=" + FortSdk.getDeviceId(PayfortActivity.this)
                    + "language=enmerchant_identifier="+merchant_identifier+"service_command=SDK_TOKENREQUESTPHRASE";
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed

        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest();
        String signature = String.format("%064x", new java.math.BigInteger(1, digest));
        return signature;
    }

    public static String random() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(40, secureRandom).toString(32);
    }
}