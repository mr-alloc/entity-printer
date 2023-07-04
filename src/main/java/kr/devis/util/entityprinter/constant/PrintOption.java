package kr.devis.util.entityprinter.constant;

public enum PrintOption {

    /**
     * 데이터타입 미출력
     */
    NO_DATA_TYPE,

    /**
     * 제외 컬럼 설정
     */
    EXCEPT_COLUMN,

    /**
     * 멀티 라인 적용
     */
    ALLOW_MULTILINE,

    /**
     * 날짜 형식 지정
     */
    DATETIME_FORMAT,

    /**
     * 각 로우별 바닥 테두리 제거
     */
    WITHOUT_EACH_BORDER_BOTTOM;
}
