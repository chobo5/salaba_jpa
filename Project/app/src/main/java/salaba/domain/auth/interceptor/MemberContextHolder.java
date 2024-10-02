package salaba.domain.auth.interceptor;

public class MemberContextHolder {
    private static final ThreadLocal<Long> userContext = new ThreadLocal<>();

    public static void setMemberId(Long memberId) {
        userContext.set(memberId);
    }

    public static Long getMemberId() {
        if (userContext.get() == null) {
            return 0L;
        }
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }


}
