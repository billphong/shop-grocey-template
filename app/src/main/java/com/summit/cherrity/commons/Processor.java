package com.summit.cherrity.commons;

public class Processor {
    public static <T> T createInstance(Class clazz) throws InstantiationException, IllegalAccessException{
        T t = (T) clazz.newInstance();
        return t;
    }
}
