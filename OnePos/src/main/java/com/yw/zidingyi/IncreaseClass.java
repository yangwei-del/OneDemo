package com.yw.zidingyi;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.api.ApiResult;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;

import java.util.HashMap;
import java.util.Map;

public class IncreaseClass implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        Map<String,Object> map=new HashMap<>();



        String tableName=params.get("tableName").toString();
        String amountfield = params.get("amountfield").toString();
        DynamicObject dynamicObject = BusinessDataServiceHelper.newDynamicObject(tableName);
        dynamicObject.set("amountfield",amountfield);

        Object[] results= SaveServiceHelper.save(new DynamicObject[]{dynamicObject});
        if(params.get("id")!=null){
            DynamicObject dynamicObject1 = BusinessDataServiceHelper.loadSingle(params.get("id").toString(), tableName);
            dynamicObject1.set("amountfield",amountfield);

            Object[] save = SaveServiceHelper.save(new DynamicObject[]{dynamicObject1});
            map.put("result",save);
            return ApiResult.success(map);
        }
        map.put("result",results);
        return ApiResult.success(map);
    }
}
