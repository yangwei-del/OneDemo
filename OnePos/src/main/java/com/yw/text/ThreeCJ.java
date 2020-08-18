package com.yw.text;

import com.alibaba.druid.util.StringUtils;
import kd.bos.form.events.FilterContainerSearchClickArgs;
import kd.bos.list.plugin.AbstractListPlugin;

import java.util.List;
import java.util.Map;

public class ThreeCJ extends AbstractListPlugin {

    /**
     * 用户选择的数据状态过滤值
     */
    private String billStateFilterValue;

    /**
     * 用户在过滤条件面板，修改了过滤条件之后，触发此事件
     *
     * @remark 在此事件，获取用户设置的数据状态过滤值
     */
    @Override
    public void filterContainerSearchClick(FilterContainerSearchClickArgs args) {

        billStateFilterValue = "A";

        // 获取用户在过滤面板中设置的过滤条件
        Map<String, List<Map<String, List<Object>>>> filterValues = args.getSearchClickEvent().getFilterValues();
        List<Map<String, List<Object>>> customFiterList = filterValues.get("customfilter");
        if (customFiterList == null) return;

        // 逐项条件匹配，找出数据状态过滤条件
        for (int i = customFiterList.size() - 1; i >= 0; i--) {
            this.getView().showMessage("save");

            Map<String, List<Object>> customFiter = customFiterList.get(i);

            List<Object> fieldNames = customFiter.get("FieldName");
            if (fieldNames == null || fieldNames.isEmpty()) continue;

            if (StringUtils.equals("billstatus", (String) fieldNames.get(0))) {
                // 找到了数据状态过滤条件

                List<Object> values = customFiter.get("Value");
                if (values != null && !values.isEmpty()) {
                    billStateFilterValue = (String) values.get(0);
                }

                break;
            }
        }
    }
}