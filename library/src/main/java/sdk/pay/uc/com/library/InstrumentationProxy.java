package sdk.pay.uc.com.library;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class InstrumentationProxy extends Instrumentation {

	 public static final String TAG = "InstrumentationProxy";

	 private boolean hasHook = false;
	 
	 // ActivityThread里面原始的Instrumentation对象,这里千万不能写成mInstrumentation,这样写
	 //抛出异常，已亲测试，所以这个地方就要注意了
	 public Instrumentation oldInstrumentation;
     
	 //通过构造函数来传递对象
	 public InstrumentationProxy(Instrumentation mInstrumentation) {
		 oldInstrumentation = mInstrumentation;
	 }


	 
	 @Override
	public void callActivityOnCreate(Activity activity, Bundle icicle) {
		// TODO Auto-generated method stub
		 Log.d(TAG, " callActivityOnCreate: activity" + activity.getIntent().getAction());
		 if (isMainActivity(activity) && !hasHook) {
			 changeActivityHasCalled(activity);
			 activity.startActivity(new Intent(activity, ProxyActivity.class));
			 activity.finish();
             hasHook = true;
		} else {
			super.callActivityOnCreate(activity, icicle);
		}
		 
	}

	private boolean isMainActivity(Activity activity) {
		return Intent.ACTION_MAIN.equals(activity.getIntent().getAction()) &&
		activity.getIntent().getCategories().contains(Intent.CATEGORY_LAUNCHER);

	}

	private void changeActivityHasCalled(Activity activity) {
		Field nameField;
		try {
			Class<? extends Activity> clazz = Activity.class;// 获取到对象对应的class对象
			nameField = clazz.getDeclaredField("mCalled");
			// 获取私有成员变量:name
			nameField.setAccessible(true);// 设置操作权限为true
			nameField.set(activity, true);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}