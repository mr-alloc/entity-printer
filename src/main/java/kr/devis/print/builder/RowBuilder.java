package kr.devis.print.builder;

import kr.devis.print.PrintConfigurator;

/**
 * Row를 만들기 위한 빌더 인터페이스
 *
 * @param <I> 인덱스 타입
 */
public interface RowBuilder<I> {

    RowBuilder<I> proceed(Object target, Class<?> type);

    RowBuilder<I> config(PrintConfigurator<I> configurator);

    String build();
}
