PhoneNumberEditText
===========

A simple material EditText combined with Country code select Spinner.

Preview
---------------
<p>
    <img src="https://i.imgur.com/q2kBgR3.gif" width="45%" />
    <img src="https://i.imgur.com/w24JbR5.gif" width="45%" />
</p>

Getting started
---------------

[ ![latestVersion](https://api.bintray.com/packages/ankurgecr/phoneNumberEditText/phonenumberedittext/images/download.svg?version=1.0.4) ](https://bintray.com/ankurgecr/phoneNumberEditText/phonenumberedittext/1.0.4/link)

To get started with PhoneNumberEditText, you'll need to get
add the dependency to your project's build.gradle file:
```
dependencies {
    //other dependencies
    implementation "android.helper:phonenumberedittext:$latestVersion"
}
```
Then to sync up your project and you are all set to use `PhoneNumberEditText`.

To add UI componenet in your Layout XML file
```xml
<android.helper.PhoneNumberEditText
    android:id="@+id/edt_phone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

Customization
---------------
![Customized PhoneNumberEditText](https://i.imgur.com/qxN0teu.png)

for more customization use these attributes:
```xml
 <android.helper.PhoneNumberEditText
    android:id="@+id/edt_phone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#333333"
    android:hint="Phone number text title"
    android:label="Country list dialog title"
    android:title="Code title"
    android:textColor="#ffffff"
    android:textColorHint="#aaaaaa" />
```
Get and Set contact numbers
--------

```
import android.helper.PhoneNumberEditText;

PhoneNumberEditText edt_phone;

edt_phone = findViewById(R.id.edt_phone);

edt_phone.setPhoneNumber("00019876543210"); //Sets +1 (987) 6543210

String contact = edt_phone.getPhoneNumber(); //Returns 00019876543210
```

- First 4 characters of String represents the Country code.
- - For example: US = "0001", India = "0091", Bhutan = "0975"
- and rest of the characters represents the phone number.

for formatting contact number to show users in UI:
```
String formattedNumber = PhoneNumberEditText.getPrintableMobileNumber(
    "00019876543210"
); //returns - "+1 (987) 654321"
```

Dex Merge Error
--------
If you are getting dex merge error because of Android support version or Gson version conflicts, use `exclude` in gradle dependencies like below:  
```
implementation("android.helper:phonenumberedittext:$latestVersion") {
    exclude group: 'com.android.support', module: 'appcompat-v7'
    exclude group: 'com.android.support', module: 'design'
    exclude group: 'com.google.code.gson'
}
```  

Questions?
--------
Feel free to register github issues with a 'question' label

Demo
--------
Check the demo app for more details.

License
--------
Licensed under the MIT license. See [LICENSE](LICENSE.md).