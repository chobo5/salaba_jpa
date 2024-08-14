package salaba.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    // 비밀번호 유효성 검사 정규식
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
