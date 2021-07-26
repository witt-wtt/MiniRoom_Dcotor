package com.joyhealth.core.base;

import java.util.HashMap;

/**
 * @author zhangjinqi
 * @explain heads 基础类，如需要添加heads，请在application中初始化用build模式，添加实现类，并实现其方法
 * @since 2020/5/8
 */
public abstract class AbstractHttpParameters {
    public abstract HashMap<String,String> setHashParameters();  //设置其他请求参数

}
