package xpa.shadow.weather.model;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum Language {
    ENGLISH("en"),
    PORTUGUESE("pt");

    private final String lang;

    Language(String lang) {
        this.lang = lang;
    }

    public static Language getByLocale(Locale locale) {
        for (Language language : Language.values()) {
            if (language.lang.equals(locale.getLanguage())) {
                return language;
            }
        }

        return Language.ENGLISH;
    }
}
