package ru.improve.abs.service.api.dto.graphql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterType {

    /**
    * тип фильтра, кототрый должен наложиться на поле
    * */
    FilterTypeEnum type();

    /**
    * имя поля, можно не заполнять, если имя колонки соответствует имени фильтра
    * */
    String fieldName() default "";

    /**
    * только при JOIN_ENTITY_ID_CONTAINS
    * имя колонки по которой будет происходить join
    * смотри документацию @JoinColumn
    * */
    String referencedColumnName() default "";

    /**
    * только при JOIN_ENTITY_ID_CONTAINS
    * имя поля join сущности в модели, к которой будет приклепляться сущность
    * смотри документацию @OneToMany
    * */
    String mappedBy() default "";
}
