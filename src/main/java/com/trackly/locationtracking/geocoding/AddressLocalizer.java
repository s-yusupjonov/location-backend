package com.trackly.locationtracking.geocoding;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

final class AddressLocalizer {

    private static final Map<String, String> STREET_TYPE_SUFFIXES = new LinkedHashMap<>();
    static {
        STREET_TYPE_SUFFIXES.put("улица", "ko'chasi");
        STREET_TYPE_SUFFIXES.put("проспект", "shoh ko'chasi");
        STREET_TYPE_SUFFIXES.put("бульвар", "bulvari");
        STREET_TYPE_SUFFIXES.put("переулок", "tor ko'chasi");
        STREET_TYPE_SUFFIXES.put("тупик", "tor ko'chasi");
        STREET_TYPE_SUFFIXES.put("проезд", "o'tish yo'li");
        STREET_TYPE_SUFFIXES.put("шоссе", "shosse");
        STREET_TYPE_SUFFIXES.put("площадь", "maydoni");
        STREET_TYPE_SUFFIXES.put("массив", "dahasi");
        STREET_TYPE_SUFFIXES.put("квартал", "kvartali");
        STREET_TYPE_SUFFIXES.put("махалля", "mahallasi");
    }

    private static final Map<String, String> KNOWN_PLACE_NAMES = new LinkedHashMap<>();
    static {
        KNOWN_PLACE_NAMES.put("ташкент", "Toshkent");
        KNOWN_PLACE_NAMES.put("самарканд", "Samarqand");
        KNOWN_PLACE_NAMES.put("бухара", "Buxoro");
        KNOWN_PLACE_NAMES.put("андижан", "Andijon");
        KNOWN_PLACE_NAMES.put("наманган", "Namangan");
        KNOWN_PLACE_NAMES.put("фергана", "Farg'ona");
        KNOWN_PLACE_NAMES.put("нукус", "Nukus");
        KNOWN_PLACE_NAMES.put("термез", "Termiz");
        KNOWN_PLACE_NAMES.put("джизак", "Jizzax");
        KNOWN_PLACE_NAMES.put("гулистан", "Guliston");
        KNOWN_PLACE_NAMES.put("навои", "Navoiy");
        KNOWN_PLACE_NAMES.put("карши", "Qarshi");
        KNOWN_PLACE_NAMES.put("ургенч", "Urganch");
        KNOWN_PLACE_NAMES.put("хива", "Xiva");
        KNOWN_PLACE_NAMES.put("коканд", "Qo'qon");
        KNOWN_PLACE_NAMES.put("маргилан", "Marg'ilon");
        KNOWN_PLACE_NAMES.put("чирчик", "Chirchiq");
        KNOWN_PLACE_NAMES.put("ангрен", "Angren");
    }

    private static final Map<String, String> TRANSLITERATION = new LinkedHashMap<>();
    static {
        TRANSLITERATION.put("Щ", "Sh");
        TRANSLITERATION.put("щ", "sh");
        TRANSLITERATION.put("Ё", "Yo");
        TRANSLITERATION.put("ё", "yo");
        TRANSLITERATION.put("Ю", "Yu");
        TRANSLITERATION.put("ю", "yu");
        TRANSLITERATION.put("Я", "Ya");
        TRANSLITERATION.put("я", "ya");
        TRANSLITERATION.put("Ж", "J");
        TRANSLITERATION.put("ж", "j");
        TRANSLITERATION.put("Х", "X");
        TRANSLITERATION.put("х", "x");
        TRANSLITERATION.put("Ц", "S");
        TRANSLITERATION.put("ц", "s");
        TRANSLITERATION.put("Ч", "Ch");
        TRANSLITERATION.put("ч", "ch");
        TRANSLITERATION.put("Ш", "Sh");
        TRANSLITERATION.put("ш", "sh");
        TRANSLITERATION.put("Ъ", "'");
        TRANSLITERATION.put("ъ", "'");
        TRANSLITERATION.put("Ь", "");
        TRANSLITERATION.put("ь", "");
        TRANSLITERATION.put("Э", "E");
        TRANSLITERATION.put("э", "e");
        TRANSLITERATION.put("Ў", "O'");
        TRANSLITERATION.put("ў", "o'");
        TRANSLITERATION.put("Қ", "Q");
        TRANSLITERATION.put("қ", "q");
        TRANSLITERATION.put("Ғ", "G'");
        TRANSLITERATION.put("ғ", "g'");
        TRANSLITERATION.put("Ҳ", "H");
        TRANSLITERATION.put("ҳ", "h");
        TRANSLITERATION.put("А", "A"); TRANSLITERATION.put("а", "a");
        TRANSLITERATION.put("Б", "B"); TRANSLITERATION.put("б", "b");
        TRANSLITERATION.put("В", "V"); TRANSLITERATION.put("в", "v");
        TRANSLITERATION.put("Г", "G"); TRANSLITERATION.put("г", "g");
        TRANSLITERATION.put("Д", "D"); TRANSLITERATION.put("д", "d");
        TRANSLITERATION.put("Е", "E"); TRANSLITERATION.put("е", "e");
        TRANSLITERATION.put("З", "Z"); TRANSLITERATION.put("з", "z");
        TRANSLITERATION.put("И", "I"); TRANSLITERATION.put("и", "i");
        TRANSLITERATION.put("Й", "Y"); TRANSLITERATION.put("й", "y");
        TRANSLITERATION.put("К", "K"); TRANSLITERATION.put("к", "k");
        TRANSLITERATION.put("Л", "L"); TRANSLITERATION.put("л", "l");
        TRANSLITERATION.put("М", "M"); TRANSLITERATION.put("м", "m");
        TRANSLITERATION.put("Н", "N"); TRANSLITERATION.put("н", "n");
        TRANSLITERATION.put("О", "O"); TRANSLITERATION.put("о", "o");
        TRANSLITERATION.put("П", "P"); TRANSLITERATION.put("п", "p");
        TRANSLITERATION.put("Р", "R"); TRANSLITERATION.put("р", "r");
        TRANSLITERATION.put("С", "S"); TRANSLITERATION.put("с", "s");
        TRANSLITERATION.put("Т", "T"); TRANSLITERATION.put("т", "t");
        TRANSLITERATION.put("У", "U"); TRANSLITERATION.put("у", "u");
        TRANSLITERATION.put("Ф", "F"); TRANSLITERATION.put("ф", "f");
    }

    private static final Pattern CYRILLIC = Pattern.compile("[А-Яа-яЁёЎўҚқҒғҲҳ]");

    private AddressLocalizer() {
    }

    static String localizeStreet(String streetComponent) {
        String trimmed = streetComponent.trim();
        for (Map.Entry<String, String> entry : STREET_TYPE_SUFFIXES.entrySet()) {
            String prefix = entry.getKey();
            if (trimmed.toLowerCase(java.util.Locale.ROOT).startsWith(prefix)) {
                String remainder = trimmed.substring(prefix.length()).trim();
                String properName = transliterate(remainder);
                return properName.isBlank() ? entry.getValue() : properName + " " + entry.getValue();
            }
        }
        return transliterate(trimmed);
    }

    static String localizeLocality(String placeComponent) {
        String trimmed = placeComponent.trim();
        String known = KNOWN_PLACE_NAMES.get(trimmed.toLowerCase(java.util.Locale.ROOT));
        return known != null ? known : transliterate(trimmed);
    }

    static String transliterate(String text) {
        if (text == null || text.isBlank()) {
            return text == null ? "" : text;
        }
        if (!CYRILLIC.matcher(text).find()) {
            return text;
        }
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));
            result.append(TRANSLITERATION.getOrDefault(ch, ch));
        }
        return result.toString();
    }
}