package com.yw.zidingyi;

import kd.bos.bill.IBillWebApiPlugin;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.api.ApiResult;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;

import java.util.HashMap;
import java.util.Map;

public class Inquire implements IBillWebApiPlugin {
    @Override
    public ApiResult doCustomService(Map<String, Object> params) {
        Map<String,Object> map=new HashMap<>();
        if (params.get("data") == null) {
            return ApiResult.fail("提示：data参数为空，请录入data");
        }

        if (params.get("tableName") == null) {
            return ApiResult.fail("提示：tableName参数为空，请录入表名");
        }
        String tableName=params.get("tableName").toString();
        if (params.get("select") == null) {
            return ApiResult.fail("提示：select参数为空，请录入select");
        }
        String select=params.get("select").toString();
        String filter="";
        if (params.get("filter")!=null){
            filter=params.get("filter").toString();
        }

        QFilter[]  qFilters=new QFilter[1];
        if(!StringUtils.isEmpty(filter)){
            qFilters[0]=QFilter.of(filter);
        }
        DynamicObject[] kded_test003s = BusinessDataServiceHelper.load(tableName, select, qFilters);
        map.put("id",kded_test003s);

        return ApiResult.success(map);
    }
}
