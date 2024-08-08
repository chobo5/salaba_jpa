package salaba.entity.rental;

public enum RentalReportCategory {
    INCORRECT("부정확하거나 틀린 정보가 있어요."),
    FRAUD("사기 입니다."),
    UNPLEASANT("불쾌합니다."),
    ETC("기타");

    RentalReportCategory(String text) {
        this.text = text;
    }

    private final String text;

    public String getText() {
        return text;
    }

}
