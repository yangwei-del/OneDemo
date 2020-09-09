package com.yw.text;

import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.fi.bcm.formplugin.AbstractBaseListPlugin;

import java.util.EventObject;

public class NineCJ extends AbstractBaseListPlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners( "toolbarap");
    }
    //查询
    @Override
    public void afterCreateNewData(EventObject e) {
        DynamicObjectCollection bhr_xueshengxinxi = QueryServiceHelper.query("bhr_xueshengxinxi", "name,nianji,xuehao,jiguan,fsz,zhengzhi", null);
        System.out.println(bhr_xueshengxinxi);
        this.getModel().setValue("textfield",bhr_xueshengxinxi.get(0).get(0));
        this.getModel().setValue("textfield1",bhr_xueshengxinxi.get(0).get(1));
        this.getModel().setValue("textfield2",bhr_xueshengxinxi.get(0).get(2));
        this.getModel().setValue("textfield3",bhr_xueshengxinxi.get(0).get(3));
        this.getModel().setValue("fsz",bhr_xueshengxinxi.get(0).get(4));
        this.getModel().setValue("zhengzhi",bhr_xueshengxinxi.get(0).get(5));
    }
}
