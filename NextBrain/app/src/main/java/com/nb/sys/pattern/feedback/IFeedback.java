package com.nb.sys.pattern.feedback;

/**
 * Created by wangyue.wy on 14-4-4.
 */
public interface IFeedback {

    /**
     *
     */
    public ISender getFrom();

    public IReceiver getTo();

    public IPathway getPathway();

}
