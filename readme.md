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

To get started with PhoneNumberEditText, you'll need to get
add the dependency to your project's build.gradle file:
```
dependencies {
    //other dependencies
    implementation "android.helper:phonenumberedittext:1.0.0"
}
```
Then to sync up your project and you are all set to use PhoneNumberEditText.

To add UI componenet in your Layout XML file
```xml
<android.helper.PhoneNumberEditText
    android:id="@+id/edt_phone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

for more options
```xml
<android.helper.PhoneNumberEditText
    android:id="@+id/edt_phone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/edit_bg"
    app:phoneHint="EditText hint"
    app:codeHint="Spinner hint"
    app:phoneSpinnerPrompt="Spinner prompt text"
    app:textColor="@android:color/black"
    app:textColorHint="@android:color/red"/>
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

> First 4 characters of String represents the Country code.
>> For example: US = "0001", India = "0091", Bhutan = "0975"

> and rest of the characters represents the phone number.

for formatting contact number to show users in UI:
```
String formattedNumber = PhoneNumberEditText.getPrintableMobileNumber(
    "00019876543210"
); //returns - "+1 (987) 654321"
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