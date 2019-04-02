package spi.jdk.demo;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/4/2.
 */
@SPI
public interface DubboService {
    void  sayHello();
}
