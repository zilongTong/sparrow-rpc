package org.sparrow.service;

import org.sparrow.api.ITonService;
import org.sparrow.common.annotation.Service;

/**
 * @ClassName TonServiceImpl
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/1/7 16:32
 **/
@Service("tonService")
public class TonServiceImpl implements ITonService {
    @Override
    public String say() {
        return "leoooooooooooooooooooooo";
    }
}
