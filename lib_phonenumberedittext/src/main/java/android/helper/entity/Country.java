package android.helper.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ankur on 23-09-2017.
 */
public class Country {

    public static List<Country> allCountries = new ArrayList<>();

    public static void initCountries(List<Country> countries) {
        allCountries.addAll(countries);
    }

    public String Name;
    public String Phonecode;
    public String Id;
    public String ISO3;
    public String ISO;
    public String Numcode;
    public String Nicename;
    public int index = 1;

    public static Country getCountryFromMobileNumber(String mobile) {
        if (mobile == null || mobile.trim().length() == 0) {
            return null;
        }
        String code = new String(mobile);
        if (code.length() > 4) {
            code = code.substring(0, 4);
        }

        int index = 0;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) != '0') {
                index = i;
                break;
            }
        }
        code = code.substring(index, code.length());

        for (Country country : allCountries) {
            if (country.Phonecode.equals(code)) {
                return country;
            }
        }

        return null;
    }

    public static Country getCountryFromLocale(String locale) {
        for (Country country : allCountries) {
            if (country.ISO.equals(locale)) {
                return country;
            }
        }
        return null;
    }

    public static Country getCountryFromID(String id) {
        for (Country country : allCountries) {
            if (country.Id.equals(id)) {
                return country;
            }
        }
        return null;
    }

    public static Country getFromCountryName(String name) {
        for (Country country : allCountries) {
            if (country.Nicename.toLowerCase().equals(name.toLowerCase())) {
                return country;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.Nicename;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return this.Id.equals(((Country) obj).Id);
        } catch (Exception e) {
            return super.equals(obj);
        }
    }

    public String getPhonecodePrint() {
        String result = Phonecode;
        while (result.length() < 4) {
            result = "0" + result;
        }
        return result;
    }

    public static void setFavoriteCountry(String countryCode) {
        if (countryCode == null)
            return;
        for (Country country : allCountries) {
            if (country.ISO.equals(countryCode) || countryCode.equals(country.ISO3) || country.Phonecode.equals(countryCode)) {
                country.index = 0;
            }
        }
        sortCountries();
    }

    public static void removeFavoriteCountry(String countryCode) {
        if (countryCode == null)
            return;
        for (Country country : allCountries) {
            if (country.ISO.equals(countryCode) || countryCode.equals(country.ISO3) || country.Phonecode.equals(countryCode)) {
                country.index = 1;
            }
        }
        sortCountries();
    }

    public static void sortCountries() {
        //java.util.Collections.sort(allCountries, Collator.getInstance());
        Collections.sort(allCountries, new Comparator<Country>() {
            @Override
            public int compare(Country country1, Country country2) {
                int diff;
                diff = country1.index - country2.index;
                if (diff == 0) {
                    diff = country1.Name.compareTo(country2.Name);
                }
                return diff;
            }
        });
    }

}
