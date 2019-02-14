package com.example.interfacefunction;

/**
 * Created by xue on 2019/1/21.
 * 有参有返回值
 */

public abstract class FunctionWithParamAndResult<Param,Result> extends Function{
    public FunctionWithParamAndResult(String name){
        super(name);
    }
    public abstract Result function(Param param);
}
