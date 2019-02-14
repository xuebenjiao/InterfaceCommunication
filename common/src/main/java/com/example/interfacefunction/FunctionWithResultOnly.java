package com.example.interfacefunction;

/**
 * Created by xue on 2019/1/21.
 */

public abstract class FunctionWithResultOnly<Result> extends  Function{
    public FunctionWithResultOnly(String name){
        super(name);
    }
   public abstract Result function();
}
