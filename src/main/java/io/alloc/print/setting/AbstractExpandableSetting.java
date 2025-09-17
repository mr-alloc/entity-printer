package io.alloc.print.setting;

import io.alloc.constant.PrintOption;
import io.alloc.print.PrintConfigurator;

/**
 * 테이블 내 저장되는 값에 대한 확장 설정의 추상화
 */
public abstract class AbstractExpandableSetting implements ExpandableSetting {

    /**
     * 특정 필드 활성화
     * @param fieldNames 활성화 할 필드명
     * @return 활성화가 적용된 설정
     */
    public abstract AbstractExpandableSetting activateFields(final String... fieldNames);

    /**
     * 특정 날짜 형식을 적용 (ISO 8601을 따름)
     * @param pattern ISO 8601 Date format
     * @return 날짜 형식이 적용된 설정
     */
    public abstract AbstractExpandableSetting dateformat(final String pattern);

    /**
     * 멀티라인 활성화(한개의 행을 내용의 길이만큼 확장)
     * @return 확장이 적용된 설정
     */
    public abstract AbstractExpandableSetting allowMultiLine();

    /**
     * 행의 하단 프레임을 제거 (UI 적 요소)
     * @return 하단 프레임이 제거된 설정
     */
    public abstract AbstractExpandableSetting withoutFloor();

    /**
     * 데이터 타입 미표기
     * @return 미표기가 적용된 설정
     */
    public abstract AbstractExpandableSetting excludeDataType();

    /**
     * 여러 옵션으로 한번에 확장
     * @param printOptions 확장에 적용할 옵션 목록 @PrintOption
     * @return 확장이 적용된 세팅
     */
    public abstract AbstractExpandableSetting expand(PrintOption... printOptions);

    /**
     * 문자열 이스케이프 처리
     * @return 이스케이프가 적용된 설정
     */
    public abstract AbstractExpandableSetting noEscape();

    /**
     * 줄임표 제거 (내용 전체 확장)
     * @return 내용 생략 기능(기본 30자)이 제거된 설정
     */
    public abstract AbstractExpandableSetting noEllipsis();

    /**
     * @return 적용된 설정 내용 정보
     */
    public abstract PrintConfigurator getConfig();
}
