package com.sizhuan.dinisfect.bill;

import kd.bos.form.events.SetFilterEvent;
import kd.bos.list.plugin.AbstractListPlugin;

/**
 * @author zenghuogang
 * @create 2020/8/20
 */
public class PersonApplyList extends AbstractListPlugin {
    @Override
    public void setFilter(SetFilterEvent e) {
        e.setOrderBy("billstatus,kded_datefield desc");
    }
}
