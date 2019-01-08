package org.sparrow.service;

import org.sparrow.api.ILeoService;
import org.sparrow.common.annotation.Service;

/**
 * @ClassName LeoServiceImpl
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/1/7 16:30
 **/
@Service("leoService")
public class LeoServiceImpl implements ILeoService {
    @Override
    public String cool(String name) {
        return "coooooooooooooooooooooooool";
    }
}
