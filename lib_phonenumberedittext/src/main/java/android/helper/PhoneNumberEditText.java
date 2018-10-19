package android.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.helper.adapters.CountryListForPhoneCodeAdapter;
import android.helper.entity.Country;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur on 08-10-2017.
 */
//android.helper.PhoneNumberEditText
public class PhoneNumberEditText extends LinearLayout {

    public static final String TAG = "PhoneNumberEditText";

    private static String defaultCountry = "US";
    LayoutInflater mInflater;
    View holderView;
    EditText input_phone;
    Spinner spinner_countries;
    TextInputLayout input_country_phone_container;
    TextView txt_code_hint;
    CountryListForPhoneCodeAdapter adapter;
    List<View> compulsoryFields = new ArrayList<>();
    List<View> properFields = new ArrayList<>();
    View touch_intersepter_layout;
    View container;

    public PhoneNumberEditText(Context context) {
        super(context);
    }

    public PhoneNumberEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PhoneNumberEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        parseAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
        parseAttributes(attrs);
    }

    public static String getDefaultCountry() {
        return defaultCountry;
    }

    public static void setDefaultCountry(String defaultCountry) {
        if (defaultCountry != null && defaultCountry.length() > 0) {
            PhoneNumberEditText.defaultCountry = defaultCountry;
        }
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        removeAllViewsInLayout();
        holderView = mInflater.inflate(R.layout.layout_country_phone_edit_text, this, true);
        input_phone = holderView.findViewById(R.id.input_country_phone);
        container = holderView.findViewById(R.id.container);
        touch_intersepter_layout = holderView.findViewById(R.id.touch_intersepter_layout);
        spinner_countries = holderView.findViewById(R.id.input_country_code);
        input_country_phone_container = holderView.findViewById(R.id.input_country_phone_container);
        txt_code_hint = holderView.findViewById(R.id.txt_code_hint);

        touch_intersepter_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //block all touches
            }
        });
        touch_intersepter_layout.setVisibility(GONE);

        adapter = new CountryListForPhoneCodeAdapter(getContext(), R.layout.item_coutry_phone_dropdown, Country.allCountries);
        adapter.setDropDownViewResource(R.layout.item_coutry_phone_dropdown);
        spinner_countries.setAdapter(adapter);

        compulsoryFields.add(input_phone);
        compulsoryFields.add(spinner_countries);

        properFields.add(input_phone);

        if (Country.allCountries == null || Country.allCountries.isEmpty()) {
            loadCountries();
        } else {
            //adapter.addCountries(Country.allCountries);
            setDefaultCountry();
        }
    }

    public static void initCountries(final Context context) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    String countryResponseString = loadJSONFromAsset(context);
                    JSONObject responseJson = new JSONObject(countryResponseString);
                    String Status = responseJson.getString("Status");
                    String Message = responseJson.getString("Message");
                    if ("Success".equals(Status)) {
                        Gson gson = new Gson();
                        List<Country> countries = new ArrayList<Country>();
                        JSONArray dataList = responseJson.getJSONObject("Data").getJSONArray("list");
                        for (int i = 0; i < dataList.length(); i++) {
                            Country country = gson.fromJson(dataList.getJSONObject(i).toString(), Country.class);
                            countries.add(country);
                        }
                        Country.initCountries(countries);
                    } else {
                        showToast(context, Message);
                        Log.d(TAG, "initCountries error: [" + Message + "] --");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void loadCountries() {

        try {
            String countryResponseString = loadJSONFromAsset(getContext());
            JSONObject responseJson = new JSONObject(countryResponseString);
            String Status = responseJson.getString("Status");
            String Message = responseJson.getString("Message");
            if ("Success".equals(Status)) {
                Gson gson = new Gson();
                List<Country> countries = new ArrayList<Country>();
                JSONArray dataList = responseJson.getJSONObject("Data").getJSONArray("list");
                for (int i = 0; i < dataList.length(); i++) {
                    Country country = gson.fromJson(dataList.getJSONObject(i).toString(), Country.class);
                    countries.add(country);
                }
                Country.initCountries(countries);

                adapter.addCountries(Country.allCountries);
                setDefaultCountry();

            } else {
                showToast(getContext(), Message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        */
    }

    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getPhoneNumber() {
        if (isValid(input_phone.getText())) {
            return ((Country) spinner_countries.getSelectedItem()).getPhonecodePrint() + input_phone.getText().toString();
        } else {
            return "";
        }
    }

    public String getOnlyPhoneNumberWithoutCode() {
        if (isValid(input_phone.getText())) {
            return input_phone.getText().toString();
        } else {
            return "";
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        updateDefaultCountry(phoneNumber);
        if (phoneNumber != null && phoneNumber.length() > 4) {
            String phoneString = phoneNumber.substring(4, phoneNumber.length());
            input_phone.setText(phoneString);
        } else {
            input_phone.setText(phoneNumber);
        }
    }

    private void updateDefaultCountry(String phoneNumber) {
        if (phoneNumber != null) {
            Country selected_country = Country.getCountryFromMobileNumber(phoneNumber);
            if (selected_country != null) {
                CountryListForPhoneCodeAdapter adapter = (CountryListForPhoneCodeAdapter) spinner_countries.getAdapter();
                if (adapter != null) {
                    int index = adapter.getPosition(selected_country);
                    if (index >= 0) {
                        spinner_countries.setSelection(index, true);
                    } else {
                        setDefaultCountry();
                    }
                } else {
                    setDefaultCountry();
                }
            } else {
                setDefaultCountry();
            }
        } else {
            setDefaultCountry();
        }
    }

    private void setDefaultCountry() {
        Country selected_country = Country.getCountryFromLocale(defaultCountry);
        if (selected_country != null) {
            CountryListForPhoneCodeAdapter adapter = (CountryListForPhoneCodeAdapter) spinner_countries.getAdapter();
            if (adapter != null) {
                int index = adapter.getPosition(selected_country);
                if (index >= 0) {
                    spinner_countries.setSelection(index, true);
                }
            }
        }
    }

    public void replaceCountries(List<Country> countryList) {
        adapter.clear();
        addCountries(countryList);
    }

    public void addCountries(List<Country> countryList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter.addAll(countryList);
        } else {
            for (Country country : countryList) {
                adapter.add(country);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PhoneNumberEditText, 0, 0);
        //TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PhoneNumberEditText);
        try {
            CharSequence hintText = a.getString(R.styleable.PhoneNumberEditText_phoneHint);
            if (hintText != null) {
                if (input_country_phone_container != null) {
                    input_country_phone_container.setHint(hintText);
                } else {
                    input_phone.setHint(hintText);
                }
            }

            String spinnerPrompt = a.getString(R.styleable.PhoneNumberEditText_phoneSpinnerPrompt);
            if (spinnerPrompt != null) {
                spinner_countries.setPrompt(spinnerPrompt);
            }

            String codeHintText = a.getString(R.styleable.PhoneNumberEditText_codeHint);
            if (codeHintText != null) {
                txt_code_hint.setText(codeHintText);
            }

            int textColor = a.getColor(R.styleable.PhoneNumberEditText_textColor, ContextCompat.getColor(getContext(), R.color.pnet_defaultTextColor));

            input_phone.setTextColor(textColor);
            adapter.setDefaultTextColor(textColor);

            adapter.notifyDataSetChanged();

            int textColorHint = a.getColor(R.styleable.PhoneNumberEditText_textColorHint, ContextCompat.getColor(getContext(), R.color.pnet_defaultHintTextColor));

            input_phone.setHintTextColor(textColorHint);
            txt_code_hint.setTextColor(textColorHint);

            //input_country_phone_container.;
            setUpperHintColor(input_country_phone_container, textColorHint);
            //adapter.setDefaultHintColor(textColorHint);
            adapter.notifyDataSetChanged();
            //input_country_phone_container.setHin(textColorHint);

            int backgroundResId = a.getResourceId(R.styleable.PhoneNumberEditText_android_background, R.drawable.cpet_bg_edit_text);
            container.setBackgroundResource(backgroundResId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    private void setUpperHintColor(TextInputLayout input_country_phone_container, int color) {
        try {
            Field field = input_country_phone_container.getClass().getDeclaredField("mFocusedTextColor");
            field.setAccessible(true);
            int[][] states = new int[][]{
                    new int[]{}
            };
            int[] colors = new int[]{
                    color
            };
            ColorStateList myList = new ColorStateList(states, colors);
            field.set(input_country_phone_container, myList);

            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(input_country_phone_container, myList);

            Method method = input_country_phone_container.getClass().getDeclaredMethod("updateLabelState", boolean.class);
            method.setAccessible(true);
            method.invoke(input_country_phone_container, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        for (View view : compulsoryFields) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                CharSequence text = editText.getText();
                if (text == null || text.length() == 0) {
                    setError(editText);
                    return false;
                } else if (editText.getInputType() == InputType.TYPE_CLASS_NUMBER) {
                    try {
                        Double.parseDouble(text.toString());
                    } catch (NumberFormatException e) {
                        setError(editText);
                        return false;
                    }
                } else if (editText.getInputType() == InputType.TYPE_CLASS_PHONE) {
                    if (!android.util.Patterns.PHONE.matcher(editText.getText()).matches()) {
                        setError(editText);
                        return false;
                    }
                }
            } else if (view instanceof Spinner) {
                Spinner spinner = (Spinner) view;
                if (spinner.getSelectedItem() == null) {
                    CharSequence text = spinner.getPrompt();
                    if (text != null) {
                        showToast(view.getContext(), "Please select " + text);
                    } else {
                        showToast(view.getContext(), "Invalid");
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isProper() {
        for (View view : compulsoryFields) {
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                CharSequence text = editText.getText();
                if (text == null || text.length() == 0)
                    continue;
                if (text.toString().matches("[0]+")) {
                    setError(editText);
                    return false;
                } else if ((editText.getInputType() & InputType.TYPE_CLASS_NUMBER) != 0) {
                    try {
                        Double.parseDouble(text.toString());
                    } catch (NumberFormatException e) {
                        setError(editText);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setEditable(boolean editable) {
        if (editable) {
            touch_intersepter_layout.setVisibility(GONE);
            container.setBackgroundResource(R.drawable.cpet_bg_edit_text);
            input_phone.setFocusable(false);
            input_phone.setFocusableInTouchMode(false);
            spinner_countries.setClickable(true);
        } else {
            touch_intersepter_layout.setVisibility(VISIBLE);
            container.setBackgroundResource(R.drawable.cpet_bg_edit_text_trans);
            input_phone.setFocusable(true);
            input_phone.setFocusableInTouchMode(true);
            spinner_countries.setClickable(false);
        }

    }

    //////////////////// Static fields ////////////////////

    public void setMaxLength(int length) {
        input_phone.setFilters(
                new InputFilter[]{
                        new InputFilter.LengthFilter(length)
                }
        );
    }

    private static void setError(EditText editText) {
        editText.requestFocus();
        CharSequence hintText = editText.getHint();
        String errorMessage = "Invalid";
        if (hintText != null && hintText.length() > 0) {
            errorMessage += " " + hintText + "!";
        } else {
            errorMessage += "!";
        }
        editText.setError(errorMessage);
    }

    private static void showToast(Context context, CharSequence message) {
        try {
            if (BuildConfig.DEBUG) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ignore) {

        }
    }

    private static boolean isValid(CharSequence string) {
        return string != null && isValid(string.toString());
    }

    private static boolean isValid(String string) {
        return string != null && string.trim().length() > 0;
    }
}
