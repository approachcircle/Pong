package net.approachcircle.game.backend;

import com.badlogic.gdx.Input;

import java.util.Locale;

public class LayoutConverter {
    public static String convert(int keycode, boolean shiftHeld, boolean altGrHeld) {
        // a UK layout backslash is unknown to a US layout
        if (keycode == Input.Keys.UNKNOWN && !isUSLayout()) {
            if (shiftHeld) {
                return "|";
            }
            return "\\";
        }
        // if a key name contains lowercase letters by default, it must be a special key
        String nameOfKey = Input.Keys.toString(keycode);
        System.out.println(nameOfKey);
        for (Character c : nameOfKey.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return "";
            }
        }
        if (keycode == Input.Keys.NUM_3) {
            if (shiftHeld) {
                if (!isUSLayout()) {
                    return "£";
                } else {
                    return "#";
                }
            }
        }
        if (keycode == Input.Keys.NUM_2) {
            if (shiftHeld) {
                if (!isUSLayout()) {
                    return "\"";
                } else {
                    return "@";
                }
            }
        }
        if (keycode == Input.Keys.APOSTROPHE) {
            if (shiftHeld) {
                if (!isUSLayout()) {
                    return "@";
                } else {
                    return "\"";
                }
            }
        }
        // probably never going to be used but may as well include all UK layout keys
        // excluding accented keys
        if (keycode == Input.Keys.GRAVE) {
            if (shiftHeld) {
                if (!isUSLayout()) {
                    return "¬";
                } else {
                    return "~";
                }
            }
            if (altGrHeld && !isUSLayout()) {
                return "¦";
            }
        }
        if (keycode == Input.Keys.NUM_1 && shiftHeld) {
            return "!";
        }
        if (keycode == Input.Keys.NUM_4 && shiftHeld) {
            return "$";
            // euro doesn't display correctly for some reason (alt-gradient + 4)
        }
        if (keycode == Input.Keys.NUM_5 && shiftHeld) {
            return "%";
        }
        if (keycode == Input.Keys.NUM_6 && shiftHeld) {
            return "^";
        }
        if (keycode == Input.Keys.NUM_7 && shiftHeld) {
            return "&";
        }
        if (keycode == Input.Keys.NUM_8 && shiftHeld) {
            return "*";
        }
        if (keycode == Input.Keys.NUM_9 && shiftHeld) {
            return "(";
        }
        if (keycode == Input.Keys.NUM_0 && shiftHeld) {
            return ")";
        }
        if (keycode == Input.Keys.MINUS && shiftHeld) {
            return "_";
        }
        if (keycode == Input.Keys.EQUALS && shiftHeld) {
            return "+";
        }
        if (keycode == Input.Keys.LEFT_BRACKET && shiftHeld) {
            return "{";
        }
        if (keycode == Input.Keys.RIGHT_BRACKET && shiftHeld) {
            return "}";
        }
        if (keycode == Input.Keys.SEMICOLON && shiftHeld) {
            return ":";
        }
        if (keycode == Input.Keys.POUND && shiftHeld) {
            return "~";
        }
        if (keycode == Input.Keys.COMMA && shiftHeld) {
            return "<";
        }
        if (keycode == Input.Keys.PERIOD && shiftHeld) {
            return ">";
        }
        if (keycode == Input.Keys.SLASH && shiftHeld) {
            return "?";
        }
        if ((keycode >= 29 && keycode <= 56) || (keycode >= 7 && keycode <= 16)) {
            if (!shiftHeld) {
                return Input.Keys.toString(keycode).toLowerCase();
            } else {
                return Input.Keys.toString(keycode);
            }
        }
        // layout doesn't need to be converted if it is already US
        if (isUSLayout()) {
            if (!shiftHeld) {
                return Input.Keys.toString(keycode).toLowerCase();
            } else {
                return Input.Keys.toString(keycode);
            }
        }
        if (keycode == Input.Keys.BACKSLASH) {
            if (shiftHeld) {
                return "~";
            }
            return "#";
        }
        if (keycode == Input.Keys.SPACE) {
             return " ";
        }
        // fallback to whitespace for other keys
        // return "";
        if (!shiftHeld) {
            return Input.Keys.toString(keycode).toLowerCase();
        } else {
            return Input.Keys.toString(keycode);
        }
    }

    private static boolean isUSLayout() {
        return Locale.getDefault().getCountry().equalsIgnoreCase("US");
    }
}
