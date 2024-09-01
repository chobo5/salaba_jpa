package salaba.util;

import lombok.Getter;

public class Regex {
    //하나 이상의 알파벳 대문자, 소문자, 특수문자, 숫자, 8자 이상
    public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String PASSWORD_ERROR = "비밀번호는 하나 이상의 알파벳 대문자, 소문자, 특수문자, 숫자, 8자 이상이여야 합니다.";
    //소문자, 대문자, 숫자, 언더스코어(_)만 포함, 4자에서 20자 사이의 길이
    public static final String NICKNAME = "^[a-zA-Z0-9_]{4,20}$";
    public static final String NICKNAME_ERROR = "닉네임은 소문자, 대문자, 숫자, 언더스코어(_)만 포함, 4자에서 20자 사이의 길이여야 합니다.";

    public static final String EMAIL_ERROR = "올바른 이메일 형식이 아닙니다.";
}
