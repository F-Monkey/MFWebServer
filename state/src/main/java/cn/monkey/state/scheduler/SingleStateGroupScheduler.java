package cn.monkey.state.scheduler;


import cn.monkey.commons.bean.Refreshable;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.scheduler.strategy.WaitingStrategy;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class SingleStateGroupScheduler extends EventLoopScheduler implements StateGroupScheduler, Refreshable {

    protected volatile StateGroup stateGroup;

    protected static final VarHandle STATE_GROUP_UPDATER;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            STATE_GROUP_UPDATER = lookup.findVarHandle(SingleStateGroupScheduler.class, "stateGroup", StateGroup.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public SingleStateGroupScheduler(long id, WaitingStrategy waitingStrategy, ThreadFactory threadFactory) {
        super(id, waitingStrategy, threadFactory);
    }

    @Override
    public boolean isEmpty() {
        return null == this.stateGroup;
    }

    @Override
    public int size() {
        return this.stateGroup == null ? 0 : 1;
    }

    @Override
    public boolean isFull() {
        return this.stateGroup != null;
    }

    @Override
    protected void execute() {
        if (null != this.stateGroup) {
            this.stateGroup.update();
        }
    }

    @Override
    public boolean tryAddStateGroup(StateGroup stateGroup) {
        return STATE_GROUP_UPDATER.compareAndSet(this, null, stateGroup);
    }

    @Override
    public void refresh() {
        if (this.stateGroup.canClose()) {
            this.stateGroup = null;
        }
    }
}
