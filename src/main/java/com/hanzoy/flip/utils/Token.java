package com.hanzoy.flip.utils;

import java.lang.annotation.*;

/**
 * 用于标注类中哪些字段需要写入token中
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Token {
}
