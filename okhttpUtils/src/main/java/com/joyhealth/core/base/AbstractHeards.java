package com.joyhealth.core.base;

import java.util.HashMap;

/**
 * @author zhangjinqi
 * @explain heads 基础类，如需要添加heads，请在application中初始化用build模式，添加实现类，并实现其方法
 * @since 2020/5/8
 */
public abstract class AbstractHeards {
    public abstract HashMap<String,String> setHashMapHeads();  //设置heads

}
