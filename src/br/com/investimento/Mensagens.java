package br.com.investimento;

import java.util.*;

public class Mensagens {
    private ResourceBundle bundle;

    public Mensagens(String idioma) {
        Locale locale;
        switch (idioma.toLowerCase()) {
        case "en": locale = Locale.forLanguageTag("en"); break;
        case "es": locale = Locale.forLanguageTag("es"); break;
        case "fr": locale = Locale.forLanguageTag("fr"); break;
        default: locale = Locale.forLanguageTag("pt");
    }

        bundle = ResourceBundle.getBundle("mensagens", locale);
    }

    public String get(String chave) {
        return bundle.getString(chave);
    }
}
