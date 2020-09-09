package com.yw.zidingyi;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.entity.api.ApiResult;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.operation.DeleteServiceHelper;

import java.util.HashMap;
import java.util.Map;

public class DeleteClass implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        Map<String,Object> map=new HashMap<>();
        String tableName=params.get("tableName").toString();
        String id=params.get("id").toString();
        int delete = DeleteServiceHelper.delete(tableName, new QFilter[]{QFilter.of("id = ?", id)});
        map.put("delete",delete);
        return ApiResult.success(map);
    }
}
