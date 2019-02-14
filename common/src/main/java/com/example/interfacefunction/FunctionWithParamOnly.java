package com.example.interfacefunction;

/**
 * Created by xue on 2019/1/21.
 * 有参无返回值
 */

public abstract class FunctionWithParamOnly<Param>  extends  Function{
    public FunctionWithParamOnly(String name){
        super(name);
    }
    public abstract  void function(Param param);
}
