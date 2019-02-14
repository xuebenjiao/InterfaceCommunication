package com.example.library;
import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xue on 2019/1/23.
 */

public class EventBus {
    private Handler myHandler ;
    private static volatile  EventBus eventBus = null;
    private HashMap<Object,List<MethodManager>> cache ;
    private ExecutorService executors ;
    private EventBus(){
        cache = new HashMap<>();
        myHandler = new  Handler(Looper.getMainLooper());
        executors = Executors.newCachedThreadPool();
    }
    public static   EventBus  getDefault(){
        if(eventBus ==null){
            synchronized (EventBus.class){
                eventBus = new EventBus();
            }
        }
        return eventBus;
    }

    /**
     * 注册订阅
     * @param subscriber 订阅类
     */
    public void register(Object subscriber){
        List<MethodManager> list = cache.get(subscriber);
        if(null == list ){
            list = findAnnotationMethod(subscriber);
            cache.put(subscriber,list);
        }
    }

    private List<MethodManager> findAnnotationMethod(Object subscriber) {
        List<MethodManager> list = new ArrayList<MethodManager>();
      /*获取类类型*/
        Class<?> clazz = subscriber.getClass();
        /*获取类中方法*/
        while(clazz!=null){
            String className = clazz.getName();
            if(className.startsWith("java.")||className.startsWith("javax.")||className.startsWith("android.")){
                break;
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
            /*获取该Subscribe注解的方法*/
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {//判断注解方法是否为null
                    continue;
                }
           /*step 1 判断方法返回值是否是void*/
                method.getGenericReturnType();
                Type type = method.getGenericReturnType();
                //method.getReturnType();
                if (!type.toString().equals("void")) {
                    throw new RuntimeException(method.getName()+"方法返回必须是void;");
                }
            /*step 2 判断方法的参数是否是一个*/
            /*获取方法参数类型*/
                Class<?>[] paramType = method.getParameterTypes();
                if (paramType.length != 1) {
                    throw new RuntimeException(method.getName()+"方法参数只能有一个;");
                }
                    /*完全符合要求的、规则的方法，保存到方法对象中MethodManager(3个重要成员)*/
                MethodManager methodManager = new MethodManager(paramType[0],subscribe.threadMode(),method);
                list.add(methodManager);
            }
            /*不断循环找出父类含有订阅者（注解）的类，直到为空，比如AppActivity没有*/
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    /**
     * 发布事件
     * @param event
     */
    public void post(final Object event){
        Set<Object> set = cache.keySet();
        if(null != set){
            for(final Object subscriber:set){
                List<MethodManager> list = cache.get(subscriber);
                if(null != list) {
                    for (final MethodManager method : list) {
                        /*有可能多个方法的参数一样，从而都同时收到消息，通过SecondActivity发送的EventBusMessageEntity对象（参数)
                        * 匹配MainActivity中所有注解的方法符合要求的，都发送消息*/
                        if(method.getType().isAssignableFrom(event.getClass())){
                            /*calss1.isAssignableFrom(class2)判定此Class对象所表示的类或接口与指定的Class参数所表示的类或接口是否相同，
                            或是否是其超类或超接口*/
                            switch (method.getThreadMode()){
                                case MAIN:/*在主线程中执行*/
                                    if(Looper.myLooper()==Looper.getMainLooper()){
                                        executeMethod(method.getMethod(),subscriber,event);
                                    }
                                    else{
                                        myHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                executeMethod(method.getMethod(),subscriber,event);
                                            }
                                        });
                                    }
                                    break;
                                case ASYNC:
                                    executors.submit(new Runnable() {
                                        @Override
                                        public void run() {
                                            executeMethod(method.getMethod(), subscriber,event);
                                        }
                                    });
                                    break;
                                case POSTING:/*如果发布事件在主线程，则在主线程中执行，反之在*/
                                    executeMethod(method.getMethod(), subscriber,event);
                                    break;
                                case BACKGROUND:
                                    if(Looper.getMainLooper() == Looper.myLooper()){
                                        executors.submit(new Runnable() {
                                            @Override
                                            public void run() {
                                                executeMethod(method.getMethod(), subscriber,event);
                                            }
                                        });
                                    }
                                    else{
                                        executeMethod(method.getMethod(), subscriber,event);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 执行方法
     * @param method 订阅方法
     * @param subscriber 订阅者
     * @param event 参数
     */
    private void executeMethod(Method method,Object subscriber, Object event)  {
        try {
            method.invoke(subscriber,event);
        } catch (IllegalAccessException |InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
