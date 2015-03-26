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

        // 先从cache中查找
        // 如果没找到，则cache这个Class的全部Fields
        List<Field> entityFields = getFieldsFromCache(entityClass.getName());
        if (null == entityFields) {
            entityFields = cacheFields(entityClass);
        }

        // 先从cache中查找
        // 如果没找到，则cache这个Class的全部Fields
        List<Field> dtoFields = getFieldsFromCache(dtoClass.getName());
        if (null == dtoFields) {
            dtoFields = cacheFields(dtoClass);
        }

        //Field[] dtoFields = dtoClass.getDeclaredFields();
        //Field[] entityFields = entityClass.getDeclaredFields();


        // construct entity instance
        try {
            entity = entityClass.newInstance();

            // 判断dto的父类是否也是Dto对象
            if (false == dto.getClass().getSuperclass().getName().equals("java.lang.Object")) {
                // 该dto父类也是DTO
                // 将dto对象的父对象本身field的值取出赋给entity

                List<Field> superFields = getFieldsFromCache(dtoClass.getSuperclass().getName());
                if (null == superFields) {
                    superFields = cacheFields(dtoClass.getSuperclass());
                }

                //Field[] superFields = dto.getClass().getSuperclass().getDeclaredFields();

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

    /**
     * 将entity转换成dto
     */
    public static <ENTITY_TYPE, DTO_TYPE> DTO_TYPE entity2Dto(ENTITY_TYPE entity, Class<DTO_TYPE> dtoClass) throws ObjectGenerationException{
        DTO_TYPE dto = null;

        // 先从cache中查找
        // 如果没找到，则cache这个Class的全部Fields
        List<Field> entityFields = getFieldsFromCache(entity.getClass().getName());
        if (null == entityFields) {
            entityFields = cacheFields(entity.getClass());
        }

        // 先从cache中查找
        // 如果没找到，则cache这个Class的全部Fields
        List<Field> dtoFields = getFieldsFromCache(dtoClass.getName());
        if (null == dtoFields) {
            dtoFields = cacheFields(dtoClass);
        }

/*        Field[] entityFields = entity.getClass().getDeclaredFields();
        Field[] dtoFields = dtoClass.getDeclaredFields();*/

        try {
            dto = dtoClass.newInstance();

            // 判断dto的父类是否也是DTO对象
            if (false == dtoClass.getSuperclass().getName().equals("java.lang.Object")) {
                // 先给父dto对象赋值
                List<Field> superFields = getFieldsFromCache(dtoClass.getName());
                if (null == superFields) {
                    superFields = cacheFields(dtoClass);
                }

                //Field[] superFileds = dtoClass.getSuperclass().getDeclaredFields();

                for (Field f : entityFields) {
                    setField(entity, dto, f, superFields);
                }
            }

            for (Field f : entityFields) {
                setField(entity, dto, f, dtoFields);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ObjectGenerationException("generate entity for " + entity.getClass().getName() + " failed");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ObjectGenerationException("generate entity for " + entity.getClass().getName() + " failed");

        }

        return dto;
    }

    private static List<Field> getFieldsFromCache(String className) {
        return FieldCache.cacheMap.get(className);
    }

    private static List<Field> cacheFields(Class clazz) {
        Field[] f = clazz.getDeclaredFields();
        List<Field> fields = Arrays.asList(f);

        FieldCache.cacheMap.put(clazz.getName(), fields);

        return fields;
    }

    /**
     * 将{@code valueField}域的值赋给{@code targetFields}中对应的Field
     * @param sourceObj
     * @param targetObj
     * @param valueField
     * @param targetFields
     */
    private static void setField(Object sourceObj, Object targetObj, Field valueField, List<Field> targetFields) {
        valueField.setAccessible(true);

        for (Field target : targetFields) {
            if (target.getName().equals(valueField.getName())) {
                target.setAccessible(true);

                //System.out.println("setting field -> dto:" + valueField.getName() + ", entity:" + target.getName());

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
