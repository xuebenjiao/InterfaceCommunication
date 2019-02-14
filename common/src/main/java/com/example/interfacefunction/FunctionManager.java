package com.example.interfacefunction;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by xue on 2019/1/21.
 */

public class FunctionManager {
    public static final String TAG = FunctionManager.class.getSimpleName() + "-------->";
    public static FunctionManager interfaceManager = null;
    private static HashMap<String, FunctionNoParamNoResult> mFunctionNoParamNoResultHashMap = null;
    private static HashMap<String, FunctionWithParamOnly> mFunctionWithParamOnlyHashMap = null;
    private static HashMap<String, FunctionWithResultOnly> mFunctionWithResultOnlyHashMap = null;
    private static HashMap<String, FunctionWithParamAndResult> mFunctionWithParamAndResultHashMap = null;

    private FunctionManager() {
        mFunctionNoParamNoResultHashMap = new HashMap<>();
        mFunctionWithParamOnlyHashMap = new HashMap<>();
        mFunctionWithResultOnlyHashMap = new HashMap<>();
        mFunctionWithParamAndResultHashMap = new HashMap<>();
    }

    public static FunctionManager getInstance() {
        if (interfaceManager == null) {
            interfaceManager = new FunctionManager();
        }
        return interfaceManager;
    }

    public FunctionManager addFunction( FunctionNoParamNoResult functionNoParamNoResult) {
        mFunctionNoParamNoResultHashMap.put(functionNoParamNoResult.funcName, functionNoParamNoResult);
        return this;
    }

    public FunctionManager addFunction(FunctionWithParamOnly functionWithParamOnly) {
        mFunctionWithParamOnlyHashMap.put(functionWithParamOnly.funcName, functionWithParamOnly);
        return this;
    }

    public FunctionManager addFunction(FunctionWithResultOnly functionWithResultOnly) {
        mFunctionWithResultOnlyHashMap.put(functionWithResultOnly.funcName, functionWithResultOnly);
        return this;

    }

    public FunctionManager addFunction( FunctionWithParamAndResult functionWithParamAndResult) {
        mFunctionWithParamAndResultHashMap.put(functionWithParamAndResult.funcName, functionWithParamAndResult);
        return this;
    }

    /**
     * 无参无返回值
     * @param name
     * @throws NullPointerException
     */
    public void invokeNoAll(String name) throws NullPointerException {
        if (name == null && name.equals("")) {
            Log.e(TAG, "方法名为空！");
        } else {
            if (mFunctionNoParamNoResultHashMap != null) {
                FunctionNoParamNoResult functionNoParamNoResult = mFunctionNoParamNoResultHashMap.get(name);
                if (functionNoParamNoResult != null) {
                    functionNoParamNoResult.function();
                } else {
                    Log.e(TAG, "方法为空！");
                }
            } else {
                throw new NullPointerException("mFunctionNoParamNoResultHashMap can not be null ,please first init FunctionManager !");
            }

        }
    }

    /**
     * 执行有参无返回值
     * @param name 方法名
     * @param param 参数
     * @param <Param> 参数类型
     * @throws NullPointerException
     */
    public <Param> void invokeWithParamOnly(String name, Param param) throws NullPointerException {
        if (name == null && name.equals("")) {
            Log.e(TAG, "方法名为空！");
        } else {
            if (mFunctionWithParamOnlyHashMap != null) {
                FunctionWithParamOnly<Param> functionNoParamNoResult = mFunctionWithParamOnlyHashMap.get(name);
                if (functionNoParamNoResult != null) {
                    functionNoParamNoResult.function(param);
                } else {
                    Log.e(TAG, "方法为空！");
                }
            } else {
                throw new NullPointerException("mFunctionWithParamOnlyHashMap can not be null ,please first init FunctionManager !");
            }

        }
    }

    /**
     * 执行无参有返回值的方法
     * @param name 方法名
     * @param resultClass 返回值
     * @param <Result> 返回值类型
     * @return
     * @throws NullPointerException
     */
    public <Result> Result invokeWithResultOnly(String name, Class<Result> resultClass) throws NullPointerException {
        if (name == null && name.equals("")) {
            Log.e(TAG, "方法名为空！");
        } else {
            if (mFunctionWithResultOnlyHashMap != null) {
                FunctionWithResultOnly<Result> functionNoParamNoResult = mFunctionWithResultOnlyHashMap.get(name);
                if (functionNoParamNoResult != null) {
                    if(null !=resultClass) {
                        return resultClass.cast(functionNoParamNoResult.function());
                    }
                } else {
                    Log.e(TAG, "方法为空！");
                }
            } else {
                throw new NullPointerException("mFunctionWithResultOnlyHashMap can not be null ,please first init FunctionManager !");
            }
        }
        return null;
    }

    /**
     * 有参有返回值
     * @param name 方法名
     * @param resultClass 返回值
     * @param param 参数
     * @param <Result> 返回值类型
     * @param <Param>  参数类型
     * @return
     * @throws NullPointerException
     */
    public <Result,Param> Result invokeWithAll(String name,Class<Result> resultClass,Param param) throws NullPointerException {
        if (name == null && name.equals("")) {
            Log.e(TAG, "方法名为空！");
        } else {
            if (mFunctionWithParamAndResultHashMap != null) {
                FunctionWithParamAndResult functionWithAll = mFunctionWithParamAndResultHashMap.get(name);
                if (functionWithAll != null) {
                    if(null !=resultClass) {
                        return resultClass.cast(functionWithAll.function(param));
                    }
                } else {
                    Log.e(TAG, "方法为空！");
                }
            } else {
                throw new NullPointerException("mFunctionWithParamAndResultHashMap can not be null ,please first init FunctionManager !");
            }
        }
        return null;
    }
}
