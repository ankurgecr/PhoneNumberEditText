package info.ankurpandya.phonenumberedittext.demo.utils;

public class Helper {

    public static boolean isValid(CharSequence text) {
        return text != null && text.toString().trim().length() > 0;
    }
}
