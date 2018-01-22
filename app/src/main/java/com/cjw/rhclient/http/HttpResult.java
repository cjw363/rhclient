package com.cjw.rhclient.http;

/**
 * 大部分的Http服务可能都是这样设置，resultCode和resultMessage的内容相对比较稳定，
 * 而data的内容变化多端，72变都不一定够变的，有可能是个User对象，也有可能是个订单对象，还有可能是个订单列表。
 * 按照我们之前的用法，使用Gson转型需要我们在创建subscriber对象是指定返回值类型，如果我们对不同的返回值进行封装的话，
 * 那可能就要有上百个Entity了
 * Created by Administrator on 17-2-23.
 *
 * data类型不确定，写成泛型T
 *
 */
public class HttpResult<T> {
    public int code;
    public T result;
    public String message;
}
