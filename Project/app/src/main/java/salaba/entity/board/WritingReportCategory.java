package salaba.entity.board;

public enum WritingReportCategory {
    SPAM("스팸홍보/도배글 입니다."),
    PORNOGRAPHY("음란물 입니다."),
    ILLEGAL_INFO("불법정보를 포함하고 있습니다."),
    HARMFUL("유해한 내용입니다.."),
    ABUSE("욕설/생명경시/혐오/차별적 표현입니다."),
    PRIVACY_INVASION("개인정보 노출을 포함하고 있습니다."),
    UNPLEASANT("불쾌한 표현이 있습니다."),
    ETC("기타");

    WritingReportCategory(String text) {
        this.text = text;
    }

    private final String text;

    public String getText() {
        return text;
    }

}
