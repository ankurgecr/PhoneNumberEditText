package android.helper.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.helper.R;
import android.helper.entity.Country;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Ankur on 26-09-2017.
 */
public class CountryListForPhoneCodeAdapter extends ArrayAdapter<Country> {

    List<Country> countries;
    int defaultHintColor = -99;
    int defaultTextColor = -99;

    public CountryListForPhoneCodeAdapter(Context context, int resourceId, List<Country> objects) {
        super(context, resourceId, objects);
        countries = objects;
        if (defaultHintColor == -99) {
            defaultHintColor = ContextCompat.getColor(context, R.color.pnet_defaultHintTextColor);
        }
        if (defaultTextColor == -99) {
            defaultTextColor = ContextCompat.getColor(context, R.color.pnet_defaultTextColor);
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_coutry_phone_dropdown, parent, false);

        ImageView img_country_flag = row.findViewById(R.id.img_country_flag);
        TextView txt_country_title = row.findViewById(R.id.txt_country_title);
        TextView txt_country_code = row.findViewById(R.id.txt_country_code);
        View separator = row.findViewById(R.id.separator);

        txt_country_code.setTextColor(defaultHintColor);
        txt_country_code.setHintTextColor(defaultHintColor);

        txt_country_title.setText(countries.get(position).Nicename);
        txt_country_code.setText("+" + countries.get(position).Phonecode);
        String iso = countries.get(position).ISO;
        try {
            img_country_flag.setImageDrawable(getCountryImage(row.getContext(), iso));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (position < countries.size() - 1) {
            if (countries.get(position).index != countries.get(position + 1).index) {
                separator.setVisibility(View.VISIBLE);
            } else {
                separator.setVisibility(View.GONE);
            }
        } else {
            separator.setVisibility(View.GONE);
        }

        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_coutry_phone_dropdown_closed, parent, false);

        ImageView img_country_flag = row.findViewById(R.id.img_country_flag);
        TextView txt_country_code = row.findViewById(R.id.txt_country_code);

        txt_country_code.setTextColor(defaultTextColor);
        txt_country_code.setHintTextColor(defaultTextColor);
        txt_country_code.setText("+" + countries.get(position).Phonecode);
        String iso = countries.get(position).ISO;
        try {
            img_country_flag.setImageDrawable(getCountryImage(row.getContext(), iso));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public static Drawable getCountryImage(Context context, String code) {
        try {
            InputStream ims = context.getAssets().open("flags/" + code.toLowerCase() + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            return ContextCompat.getDrawable(context, R.drawable.flag_unknown);
        }
    }

    public void addCountries(List<Country> allCountries) {
        countries.addAll(allCountries);
        notifyDataSetChanged();
    }

    public int getDefaultHintColor() {
        return defaultHintColor;
    }

    public void setDefaultHintColor(int defaultHintColor) {
        this.defaultHintColor = defaultHintColor;
    }

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }
}