package com.nb.sys.pattern.chain;

/**
 * 职责链-处理者
 * Created by wangyue.wy on 14-4-4.
 */
public abstract class IChainHandler {

    protected IChainHandler superior;

    /**
     *
     * @param superior 上级处理者
     */
    IChainHandler(IChainHandler superior) {
        this.superior = superior;
    }

    /**
     * 存在上级吗
     *
     * @return true if has an superior.
     */
    public boolean hasSuperior() {
        return (null != superior);
    }

    public static final String No_Superior = "No Superior";

    /**
     * @param param
     * @return true 已经处理 false 无法处理
     */
    abstract boolean handle(Object param);

    /**
     * 事务处理
     *
     * @param param
     */
    public void judge(Object param) {
        if (handle(param)) {
            return;
        } else if (hasSuperior()) {
            superior.handle(param);
        } else {
            throw new ChainHandleException(No_Superior);
        }
    }

}