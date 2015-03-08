package com.fh.taolijie.utils;

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
     * @return
     */
    public static <T> boolean removeFromCollection(Iterable<T> collection, Predicate<T> test) {
        if (null == collection) {
            return false;
        }

        boolean result = false;

        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            T obj = it.next();
            if (test.test(obj)) {
                it.remove();
                result = true;

                break;
            }
        }

        return result;
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
