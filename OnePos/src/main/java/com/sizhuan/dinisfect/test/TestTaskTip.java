package com.sizhuan.dinisfect.test;

import com.sizhuan.dinisfect.task.DinisfectTaskTip;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.events.PreOpenFormEventArgs;
import kd.fi.bcm.formplugin.AbstractBaseFormPlugin;

import java.util.EventObject;

/**
 * @author zenghuogang
 * @create 2020/8/20
 * 测试后台任务
 */
public class TestTaskTip extends AbstractBaseFormPlugin {

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("tbmain");
    }

    @Override
    public void preOpenForm(PreOpenFormEventArgs e) {
        super.preOpenForm(e);
        DinisfectTaskTip dinisfectTaskTip=new DinisfectTaskTip();
        dinisfectTaskTip.execute(null,null);
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        DinisfectTaskTip dinisfectTaskTip=new DinisfectTaskTip();
        dinisfectTaskTip.execute(null,null);
    }
}
