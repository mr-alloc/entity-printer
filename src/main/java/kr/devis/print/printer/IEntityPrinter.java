package kr.devis.print.printer;


import kr.devis.print.PrintConfigurator;

import java.util.Collection;

interface IEntityPrinter {

    /**
     * Print entity such as POJO, not collection
     * 컬렉션이 아닌 일반적인 자바 객체를 출력한다.
     *
     * @param entity 출력될 객체 (Entity to be printed)
     * @param <T>    type of entity
     * @return formatted data in table as string.
     */
    <T> String drawEntity(final T entity);

    /**
     * Looks like same as {@link #drawEntity(Object)} but it has configurator.
     * 일반적인 {@link kr.devis.print.printer.EntityPrinter#drawEntity}와 같지만 설정 값을 추가 할 수 있다.
     *
     * @param entity       출력될 객체 (Entity to be printed)
     * @param configurator 출력 설정 정보
     * @param <I>          entity의 인덱스 타입. Map같은 경우 String이 Key로 들어가므로 인덱스가 String으로 잡히지만, 일반 객체는 field 순서(Integer)로 잡힌다.
     * @param <T>          entity의 타입
     * @return formatted data in table as string.
     */
    <I, T> String drawEntity(final T entity, final PrintConfigurator<I> configurator);

    /**
     * @param entities collection of entities
     * @param clazz inner type class of collection
     * @param <T> inner type of collection
     * @return formatted collection data in table as string.
     */
    <T> String drawCollection(final Collection<? extends T> entities, Class<? extends T> clazz);

    /**
     * Print collection of entities
     * 컬렉션내 자바 객체들을 출력한다.
     *
     * @param entities   출력된 객체 목록
     * @param configured 출력 설정 정보
     * @param clazz      컬렉션 내 객체의 타입
     * @param <I>        entity의 인덱스 타입. Map같은 경우 String이 Key로 들어가므로 인덱스가 String으로 잡히지만, 일반 객체는 field 순서(Integer)로 잡힌다.
     * @param <T>        entity의 타입
     * @return formatted collection data in table as string.
     */
    <I, T> String drawCollection(final Collection<? extends T> entities, final PrintConfigurator<I> configured, Class<? extends T> clazz);

}
