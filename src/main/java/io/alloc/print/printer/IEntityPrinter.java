package io.alloc.print.printer;


import io.alloc.print.PrintConfigurator;

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
     * 일반적인 {@link EntityPrinter#drawEntity}와 같지만 설정 값을 추가 할 수 있다.
     *
     * @param entity       출력될 객체 (Entity to be printed)
     * @param configurator 출력 설정 정보
     * @param <T>          entity의 타입
     * @return formatted data in table as string.
     */
    <T> String drawEntity(final T entity, final PrintConfigurator configurator);

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
     * @param <T>        entity의 타입
     * @return formatted collection data in table as string.
     */
    <T> String drawCollection(final Collection<? extends T> entities, final PrintConfigurator configured, Class<? extends T> clazz);

}
