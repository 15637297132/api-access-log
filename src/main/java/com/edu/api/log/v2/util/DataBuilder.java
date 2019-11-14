package com.edu.api.log.v2.util;


import com.alibaba.fastjson.JSON;
import java.lang.reflect.*;
import java.util.*;
/**
 * 数据构造工具
 * 注意：Model中不要嵌套相同的Model，也不能互相嵌套
 * @author Yangzhen
 * @Description
 * @date 2019-09-02 15:55
 **/
public class DataBuilder {

    public static final String SAMPLE_CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final Map<String, Class<?>> BASIC_DATA_TYPE_MAP = new HashMap<>();

    static {
        BASIC_DATA_TYPE_MAP.put("int", Integer.class);
        BASIC_DATA_TYPE_MAP.put("double", Double.class);
        BASIC_DATA_TYPE_MAP.put("long", Long.class);
        BASIC_DATA_TYPE_MAP.put("short", Short.class);
        BASIC_DATA_TYPE_MAP.put("byte", Byte.class);
        BASIC_DATA_TYPE_MAP.put("boolean", Boolean.class);
        BASIC_DATA_TYPE_MAP.put("char", Character.class);
        BASIC_DATA_TYPE_MAP.put("float", Float.class);
    }

    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(SAMPLE_CODE.length());
            sb.append(SAMPLE_CODE.charAt(number));
        }
        return sb.toString();
    }

    public static Date getNow() {
        return new Date();
    }

    /**
     * 随机数字，转换成对应的包装类型，对于Boolean类型的数据，总是返回false
     *
     * @param length
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getRandomNumber(int length, Class<?> clazz) throws Exception {

        if (length <= 0) {
            length = 1;
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(random.nextInt(10));
        }
        String str = sb.toString();
        if (clazz.isPrimitive()) {
            clazz = BASIC_DATA_TYPE_MAP.get(clazz.getName());
        }
        Constructor<?> constructor = clazz.getConstructor(String.class);
        Object result = constructor.newInstance(str);
        return (T) result;
    }

    /**
     * 构造数据，你的对象一定要有无参构造函数
     * 集合和Map的数据自己构建
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newDataByMethod(Class<?> clazz) {
        try {
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("set")) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> type = parameterTypes[0];
                    Object value = getDataByClass(type);
                    method.invoke(obj, value);
                }
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * 构造数据，你的对象一定要有无参构造函数
     * 集合和Map的数据不需要自己构建
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newDataByField(Class<?> clazz) {
        try {

            Object obj = clazz.newInstance();
            for (; !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    if (Modifier.isFinal(declaredField.getModifiers()) && Modifier.isStatic(declaredField.getModifiers())) {
                        continue;
                    }
                    Method method = clazz.getMethod("set" + firstToUpperCase(declaredField.getName()), declaredField.getType());
                    Object value = getDataByField(declaredField);
                    method.invoke(obj, value);
                }
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static <T> T getDataByField(Field field) throws Exception {
        Class<?> clazz = field.getType();
        if (isPrimitive(clazz)) {
            return getRandomNumber(0, clazz);
        } else if (Date.class.isAssignableFrom(clazz)) {
            return (T) getNow();
        } else if (String.class.isAssignableFrom(clazz)) {
            return (T) getRandomString(6);
        } else if (clazz.isInterface()) {
            if (List.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.emptyList();
                List<T> result = new ArrayList<>();
                genericDeal(field, result, false);
                return (T) result;
            } else if (Set.class.isAssignableFrom(clazz)) {
                Set<T> result = new HashSet<>();
                genericDeal(field, result, false);

                return (T) result;
            } else if (Map.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.EMPTY_MAP;
                HashMap result = new HashMap<>();
                genericDeal(field, result, true);
                return (T) result;
            }
            return null;
        } else {
            // Object
            return (T) clazz.newInstance();
        }
    }

    public static void genericDeal(Field field, Object obj, boolean flag) throws Exception {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            if (flag) {
                Class actualType1 = (Class) parameterizedType.getActualTypeArguments()[0];
                Class actualType2 = (Class) parameterizedType.getActualTypeArguments()[1];
                Map result = (Map) obj;
                result.put(getDataByClass(actualType1), getDataByClass(actualType2));
            } else {
                Class actualType = (Class) parameterizedType.getActualTypeArguments()[0];
                Collection result = (Collection) obj;
                result.add(getDataByClass(actualType));
            }
        }
    }


    /**
     * 适配数据类型，数组类型直接返回数组对象
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getDataByClass(Class<?> clazz) throws Exception {

        if (isPrimitive(clazz)) {
            return getRandomNumber(0, clazz);
        } else if (Date.class.isAssignableFrom(clazz)) {
            return (T) getNow();
        } else if (String.class.isAssignableFrom(clazz)) {
            return (T) getRandomString(6);
        } else if (clazz.isInterface()) {
            if (List.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.emptyList();
                return (T) new ArrayList<>();
            } else if (Set.class.isAssignableFrom(clazz)) {
                return (T) new HashSet<>();
            } else if (Map.class.isAssignableFrom(clazz)) {
                // 返回的集合不能添加数据
                // return (T) Collections.EMPTY_MAP;
                return (T) new HashMap<>();
            }
            return null;
        } else {
            // Object
            return (T) clazz.newInstance();
        }
    }

    /**
     * 判断基本数据类型和包装类型
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        try {
            // 判断基本数据类型
            if (clazz.isPrimitive()) {
                return true;
            }
            // 判断包装类型
            if (clazz.getField("TYPE") != null && ((Class) (clazz.getField("TYPE").get(null))).isPrimitive()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static String firstToUpperCase(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static void main(String[] args) throws Exception {
        Goods goods = newDataByField(Goods.class);
        System.out.println(JSON.toJSONString(goods));
        Fruit fruit = newDataByField(Fruit.class);
        System.out.println(JSON.toJSONString(fruit));
    }

    static class Goods {

        public Goods() {
        }

        private String name;
        private Integer count;
        private Double price;
        private List<String> ext;
        private Byte byt;
        private Boolean flag;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public List<String> getExt() {
            return ext;
        }

        public void setExt(List<String> ext) {
            this.ext = ext;
        }

        public Byte getByt() {
            return byt;
        }

        public void setByt(Byte byt) {
            this.byt = byt;
        }

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(Boolean flag) {
            this.flag = flag;
        }

    }

    static class Fruit extends Goods {
        private Date date;
        private Set<String> set;
        private Map<Integer, String> map;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Map<Integer, String> getMap() {
            return map;
        }

        public void setMap(Map<Integer, String> map) {
            this.map = map;
        }

        public Set<String> getSet() {
            return set;
        }

        public void setSet(Set<String> set) {
            this.set = set;
        }
    }
}

