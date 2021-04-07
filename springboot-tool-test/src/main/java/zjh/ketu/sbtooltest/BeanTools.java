package zjh.ketu.sbtooltest;

import lombok.Getter;
import net.sf.cglib.beans.BeanCopier;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean 工具类
 * 支持浅拷贝 和 深拷贝
 * deepcopy提供新的方法，不建议在不同类型对象之间进行deepcopy（这也不符合语义）
 *
 * @author darrenfu
 */
public class BeanTools {
    private static final Logger log = LoggerFactory.getLogger(BeanTools.class);
    /**
     * bean copier 缓存
     */
    private static ConcurrentHashMap<BeanCopierNode, BeanCopier> beanCopierCacheMap = new ConcurrentHashMap<>(32);

    /**
     * Objenesis cache
     */
    private static final Objenesis objenesis = new ObjenesisStd();


    /**
     * 实例化一个bean，懒得new再换行
     *
     * @param <T>         bean type
     * @param targetClass target bean
     * @return bean instance
     */
    @SuppressWarnings("unused")
    public static <T> T instance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("实例化:{}失败:", targetClass.getSimpleName(), e);
            return objenesis.newInstance(targetClass);
        }
    }

    /**
     * copy properties to other bean，方便一点是一点
     * 使用cglib beanCopier 性能较spring 的beanUtils 高5倍以上
     *
     * @param <T>         bean type
     * @param source      source bean
     * @param targetClass target class
     * @return target bean instance
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        T target = instance(targetClass);
        // copy bean
        beanCopier.copy(source, target, null);
        return target;
    }

    /**
     * copy properties and generate list, 方便两点是两点
     *
     * @param <T>           the type parameter
     * @param <E>           the type parameter
     * @param sourceObjList the source obj list
     * @param targetClass   the target class
     * @return the list
     */
    public static <T, E> List<T> copyBeans(List<E> sourceObjList, Class<T> targetClass) {
        if (sourceObjList == null || sourceObjList.size() < 1) {
            return Collections.EMPTY_LIST;
        }

        List<T> targetObjList = new ArrayList<>(sourceObjList.size());
        // 生成copier 循环中复用
        BeanCopier beanCopier = getBeanCopier(sourceObjList.get(0).getClass(), targetClass);

        for (E source : sourceObjList) {
            T target = instance(targetClass);
            // copy bean
            beanCopier.copy(source, target, null);
            targetObjList.add(target);
        }
        return targetObjList;
    }

    /**
     * 默认浅拷贝
     * 从缓存中获取 beanCopier, 性能提升3-4倍
     * 提升幅度优于使用HashBasedTable的性能提升
     *
     * @param sourceClz the source clz
     * @param targetClz the target clz
     * @return the bean copier
     */
    public static BeanCopier getBeanCopier(Class sourceClz, Class targetClz) {

        return getBeanCopier(sourceClz, targetClz, false);
    }

    /**
     * 从缓存中获取 beanCopier
     *
     * @param sourceClz    the source clz
     * @param targetClz    the target clz
     * @param useConverter the use converter 使用转换器则表明是深拷贝
     * @return the bean copier
     */
    public static BeanCopier getBeanCopier(Class sourceClz, Class targetClz, boolean useConverter) {
        BeanCopierNode copierNode = new BeanCopierNode(sourceClz, targetClz, useConverter);
        BeanCopier beanCopier = beanCopierCacheMap.get(copierNode);

        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClz, targetClz, useConverter);
            beanCopierCacheMap.putIfAbsent(copierNode, beanCopier);
        }
        return beanCopier;
    }


    /**
     * The type Bean copier node.
     */
    static class BeanCopierNode {

        @Getter
        private Class sourceClass;

        @Getter
        private Class targetClass;

        @Getter
        private boolean useConverter;

        /**
         * Instantiates a new Bean copier node.
         *
         * @param sourceClass the source class
         * @param targetClass the target class
         */
        BeanCopierNode(Class sourceClass, Class targetClass) {
            this(sourceClass, targetClass, false);
        }

        /**
         * Instantiates a new Bean copier node.
         *
         * @param sourceClass  the source class
         * @param targetClass  the target class
         * @param useConverter the use converter
         */
        BeanCopierNode(Class sourceClass, Class targetClass, boolean useConverter) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
            this.useConverter = useConverter;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BeanCopierNode that = (BeanCopierNode) o;
            return Objects.equals(sourceClass, that.sourceClass) &&
                    Objects.equals(targetClass, that.targetClass) &&
                    Objects.equals(useConverter, that.useConverter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceClass, targetClass, useConverter);
        }
    }

    /**
     * Class 工具类
     */
    private static class ClassUtils {

        private static final Map<Class<?>, Class<?>> primitiveMap = new HashMap<>();
        // 转换类 泛型属性 class映射关系缓存
        private static final ConcurrentHashMap<Class<?>, Map<String, Class<?>>> paramTypeMap = new ConcurrentHashMap<>(32);

        static {
            primitiveMap.put(String.class, String.class);
            primitiveMap.put(Boolean.class, boolean.class);
            primitiveMap.put(Byte.class, byte.class);
            primitiveMap.put(Character.class, char.class);
            primitiveMap.put(Double.class, double.class);
            primitiveMap.put(Float.class, float.class);
            primitiveMap.put(Integer.class, int.class);
            primitiveMap.put(Long.class, long.class);
            primitiveMap.put(Short.class, short.class);
        }

        /**
         * Is primitive boolean.
         *
         * @param clazz the clazz
         * @return boolean boolean
         * @description 判断基本类型
         * @see Boolean#TYPE java.lang.Boolean#TYPEjava.lang.Boolean#TYPEjava.lang.Boolean#TYPE
         * @see Character#TYPE java.lang.Character#TYPEjava.lang.Character#TYPEjava.lang.Character#TYPE
         * @see Byte#TYPE java.lang.Byte#TYPEjava.lang.Byte#TYPEjava.lang.Byte#TYPE
         * @see Short#TYPE java.lang.Short#TYPEjava.lang.Short#TYPEjava.lang.Short#TYPE
         * @see Integer#TYPE java.lang.Integer#TYPEjava.lang.Integer#TYPEjava.lang.Integer#TYPE
         * @see Long#TYPE java.lang.Long#TYPEjava.lang.Long#TYPEjava.lang.Long#TYPE
         * @see Float#TYPE java.lang.Float#TYPEjava.lang.Float#TYPEjava.lang.Float#TYPE
         * @see Double#TYPE java.lang.Double#TYPEjava.lang.Double#TYPEjava.lang.Double#TYPE
         * @see Boolean#TYPE java.lang.Boolean#TYPEjava.lang.Boolean#TYPEjava.lang.Boolean#TYPE
         */
        public static boolean isPrimitive(Class<?> clazz) {
            if (primitiveMap.containsKey(clazz)) {
                return true;
            }
            return clazz.isPrimitive();
        }


        /**
         * Is simple type equal boolean.
         *
         * @param sourceClz the source clz
         * @param targetClz the target clz
         * @return the boolean
         */
        public static boolean isSimpleTypeEqual(Class sourceClz, Class targetClz) {

            return true;
        }

        /**
         * Gets prop name.
         *
         * @param setterName the setter name
         * @return the prop name
         */
        public static String getPropName(String setterName) {
            char[] chars = setterName.toCharArray();
            chars[3] += 32;

            return String.valueOf(chars, 3, chars.length - 3);
        }


        /**
         * Gets element type.
         *
         * @param target     the target
         * @param methodName the method name
         * @return Class<> element type
         */
        public static Class<?> getElementType(Class<?> target, String methodName) {
            // 加入缓存，优化性能
            if (paramTypeMap.containsKey(target)
                    && paramTypeMap.get(target) != null
                    && paramTypeMap.get(target).containsKey(methodName)) {
                return paramTypeMap.get(target).get(methodName);
            } else {
                paramTypeMap.putIfAbsent(target, new HashMap<>(8));
            }

            Class<?> elementTypeClass = null;
            String fieldName = ClassUtils.getPropName(methodName.toString());
            try {
                Type type = target.getDeclaredField(fieldName).getGenericType();
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    for (Type actualType : parameterizedType.getActualTypeArguments()) {
                        if (actualType instanceof Class) {
                            Class actualClz = (Class) actualType;
                            if (paramTypeMap.get(target) != null) {
                                paramTypeMap.get(target).put(methodName, actualClz);
                            }
                            return actualClz;
                        }
                    }
                }
            } catch (NoSuchFieldException | SecurityException e) {
                throw new RuntimeException("get fieldName[" + fieldName + "] error", e);
            }
            return elementTypeClass;
        }
    }
}

