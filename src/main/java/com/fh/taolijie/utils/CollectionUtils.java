package com.fh.taolijie.utils;

import com.fh.taolijie.exception.checked.ObjectGenerationException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by wanghongfei on 15-3-7.
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * 把一个DTO对象转换成对应的Entity对象
     */
    public static <ENTITY_TYPE, DTO_TYPE> ENTITY_TYPE dto2Entity(DTO_TYPE dto, Class<ENTITY_TYPE> entityClass) throws ObjectGenerationException {
        ENTITY_TYPE entity = null;
        
        Class dtoClass = dto.getClass();

        Field[] dtoFields = dtoClass.getDeclaredFields();
        Field[] entityFields = entityClass.getDeclaredFields();


        // construct entity instance
        try {
            entity = entityClass.newInstance();

            // 判断dto的父类是否也是Dto对象
            if (false == dto.getClass().getSuperclass().getName().equals("java.lang.Object")) {
                // 该dto父类也是DTO
                // 将dto对象的父对象本身field的值取出赋给entity
                Field[] superFields = dto.getClass().getSuperclass().getDeclaredFields();
                for (Field f : superFields) {
                    setField(dto, entity, f, entityFields);
                }
            }

            // 将dto对象本身field的值取出赋给entity
            for (Field f : dtoFields) {
                setField(dto, entity, f, entityFields);
            }


        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ObjectGenerationException("generate dto for " + dto.getClass().getName() + "failed");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ObjectGenerationException("generate dto for " + dto.getClass().getName() + "failed");
        }

        return entity;
    }

    public static <T> T entity2Dto() {
        return null;
    }

    /**
     * 将{@code valueField}域的值赋给{@code targetFields}中对应的Field
     * @param sourceObj
     * @param targetObj
     * @param valueField
     * @param targetFields
     */
    private static void setField(Object sourceObj, Object targetObj, Field valueField, Field[] targetFields) {
        valueField.setAccessible(true);

        for (Field target : targetFields) {
            if (target.getName().equals(valueField.getName())) {
                target.setAccessible(true);

                System.out.println("setting field -> dto:" + valueField.getName() + ", entity:" + target.getName());

                try {
                    target.set(targetObj, valueField.get(sourceObj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 将一种集合类型经过处理后转换成另一种集合类型
     * @param apply
     * @param <T> 要返回的最终结果的类型
     * @param <E> 要处理的类型
     * @return
     */
    public static <T, E> List<T> transformCollection(Iterable<E> collection, Class<T> resultType, Function<E, T> apply) {
        Iterator<E> it =  collection.iterator();

        List<T> resultList = new ArrayList<>();
        while (it.hasNext()) {
            T resultObj = apply.apply(it.next());
            resultList.add(resultObj);
        }

        return resultList;
    }

    /**
     * 将一个元素添加到集合类中.
     * @param collection
     * @param obj
     * @param <T>
     * @return 如果集合类为空，则返回一个包含了该元素的新集合
     */
    public static <T> List<T> addToCollection(Collection<T> collection, T obj) {
        List<T> list = null;

        if (null == collection) {
            list = new ArrayList<>();
            list.add(obj);
        } else {
            collection.add(obj);
        }

        return list;
    }

    /**
     * 从一个集合类中删除一个元素. 测试条件由{@code test} 参数指定
     * @param collection
     * @param test
     * @param <T>
     * @return 被从Collection中移除的实体引用
     */
    public static <T> T removeFromCollection(Iterable<T> collection, Predicate<T> test) {
        if (null == collection) {
            return null;
        }

        T target = null;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T obj = it.next();
            if (test.test(obj)) {
                target = obj;
                it.remove();

                break;
            }
        }

        return target;
    }

    /**
     * 在集合类的每一个元素上应用指定操作, 操作由{@code action}参数指定
     * @param collection
     * @param action
     * @param <T>
     */
    public static <T> void applyActionOnCollection(Iterable<T> collection, Consumer<T> action) {
        if (null == collection) {
            return;
        }

        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T obj = it.next();
            action.accept(obj);
        }
    }
}
