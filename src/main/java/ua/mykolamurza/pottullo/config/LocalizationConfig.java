package ua.mykolamurza.pottullo.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationConfig {
    private static Locale locale = Locale.ENGLISH;

    public static void setSystemLanguage(String language) {
        if (language.length() == 2) {
            setSystemLocale(new Locale(language));
        }
    }

    public static String getBundledText(String key) {
        return ResourceBundle.getBundle("messages", locale).getString(key);
    }

    private static void setSystemLocale(Locale locale) {
        LocalizationConfig.locale = locale;
    }
}
