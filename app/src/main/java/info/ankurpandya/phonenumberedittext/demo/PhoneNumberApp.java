package info.ankurpandya.phonenumberedittext.demo;

import android.app.Application;
import android.helper.PhoneNumberEditText;

public class PhoneNumberApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PhoneNumberEditText.initCountries(this);
    }
}
