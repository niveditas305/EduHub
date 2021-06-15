package com.unity3d.ads.test.legacy;

import android.os.Parcel;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unity3d.services.core.properties.ClientProperties;
import com.unity3d.services.core.webview.bridge.WebViewCallback;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WebViewCallbackTest {
	@BeforeClass
	public static void prepareTests () throws Exception {
		ClientProperties.setApplicationContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
	}

	@Test
	public void testWebViewCallbackWriteAndReadParcel () throws Exception {
		WebViewCallback callback = new WebViewCallback("TEST_CALLBACK_01", 1);
		Parcel parcel = Parcel.obtain();
		callback.writeToParcel(parcel, 0);
		parcel.setDataPosition(0);
		WebViewCallback callbackFromParcel = WebViewCallback.CREATOR.createFromParcel(parcel);
		WebViewCallback[] arrayWebViewCallback = WebViewCallback.CREATOR.newArray(10);

		assertEquals("The array should contain correct amount of elements. ", 10, arrayWebViewCallback.length);
		assertEquals("CallbackID should be the same. ", callback.getCallbackId(), callbackFromParcel.getCallbackId());
		assertEquals("Describe contents value was wrong. ", 45678, callback.describeContents());
	}
}
