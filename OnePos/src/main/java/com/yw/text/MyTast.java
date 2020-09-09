package com.yw.text;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.exception.KDException;
import kd.bos.schedule.api.MessageHandler;
import kd.bos.schedule.executor.AbstractTask;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;

import java.util.Map;

public class MyTast extends AbstractTask {

    @Override
    public void execute(RequestContext requestContext, Map<String, Object> map) throws KDException {
        for (int i = 0;i < 10;i++){
        DynamicObject dy = BusinessDataServiceHelper.newDynamicObject("bhr_cunkuandan");
            dy.set("creator","adminsyh");
            dy.set("amountfield","30000");
        Object[] results= SaveServiceHelper.save(new DynamicObject[]{dy});
        }
    }
}
