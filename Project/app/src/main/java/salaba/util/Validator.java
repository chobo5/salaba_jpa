package salaba.util;

import salaba.exception.ValidationException;

import java.util.regex.Pattern;

public class Validator {

    // 비밀번호 유효성 검사 정규식
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; //하나 이상의 알파벳 대문자, 소문자, 특수문자, 숫자, 8자 이상

    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9_]{4,20}$"; //소문자, 대문자, 숫자, 언더스코어(_)만 포함, 4자에서 20자 사이의 길이

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern nicknamePattern = Pattern.compile(NICKNAME_PATTERN);
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidPassword(String password) {
        if (password == null || !passwordPattern.matcher(password).matches()) {
            throw new ValidationException("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함한 8자 이상이여야 합니다.");
        }
        return true;
    }

    public static boolean isValidNickname(String nickname) {
        if (nickname == null || !nicknamePattern.matcher(nickname).matches()) {
            throw new ValidationException("닉네임은 소문자, 대문자, 숫자, 언더스코어(_)만 포함하며, 4자에서 20자 사이의 길이여야 합니다.");
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || !emailPattern.matcher(email).matches()) {
            throw new ValidationException("올바른 이메일 형식이 아닙니다.");
        }
        return true;
    }


}
