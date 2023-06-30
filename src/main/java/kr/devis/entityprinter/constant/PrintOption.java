package kr.devis.entityprinter.constant;

public enum PrintOption {
    NO_DATA_TYPE,    // 데이터 타입 미출력
    EXCEPT_COLUMN,   // 제외 컬럼 설정
    ALLOW_MULTILINE, // 멀티라인 적용
    DATETIME_FORMAT,  // 날짜 형식지정
    WITHOUT_FLOOR; //바닥 문자열 삭제
}
