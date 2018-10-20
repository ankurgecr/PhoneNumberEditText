PhoneNumberEditText
    ===========

    Getting started
    ---------------

    ![PhoneNumberEditText Demo 1](https://i.imgur.com/q2kBgR3.gifv)
    ![PhoneNumberEditText Demo 2](https://i.imgur.com/w24JbR5.gif)

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
    ```
<android.helper.PhoneNumberEditText
    android:id="@+id/edt_phone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
    ```

    for more options
    ```
<android.helper.PhoneNumberEditText
android:id="@+id/edt_phone"
android:layout_width="match_parent"
android:layout_height="wrap_content"/>
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
    First 4 characters of String represents the Country code.
    For example: US = 0001, India = 0091, Bhutan = 0975
    and rest of the characters represents the phone number.

    for formatting contact number to show users in UI:
    ```
    String formattedNumber = PhoneNumberEditText.getPrintableMobileNumber(
    "00019876543210"
    ); //returns - "+1 (987) 654321"
    ```

    Demo
    --------
    Check the demo app for more details.
