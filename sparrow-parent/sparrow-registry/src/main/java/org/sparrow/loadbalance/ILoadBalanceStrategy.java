package org.sparrow.loadbalance;

/**
 * @ClassName ILoadBalanceStrategy
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/5/24 15:13
 **/
public interface ILoadBalanceStrategy<T> {
    String loadBalance(T var);
}
