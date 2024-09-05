package salaba.interceptor;

import salaba.exception.CannotFindMemberException;

public class MemberContextHolder {
    private static final ThreadLocal<Long> userContext = new ThreadLocal<>();

    public static void setMemberId(Long memberId) {
        userContext.set(memberId);
    }

    public static Long getMemberId() {
        if (userContext.get() == null) {
            throw new CannotFindMemberException("회원정보를 찾을 수 없습니다.");
        }
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }


}
