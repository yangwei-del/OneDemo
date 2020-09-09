package com.file.record;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.datamodel.ListSelectedRowCollection;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.list.events.ListRowClickEvent;
import kd.bos.list.plugin.AbstractListPlugin;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;

public class XuanZhong extends AbstractListPlugin {
    DataClass dataClass = DataClass.getInstance();
    @Override
    public void listRowClick(ListRowClickEvent evt) {
        ListSelectedRowCollection listSelectedRows = evt.getListSelectedRowCollection();
        dataClass.setData(this.getView().getParentView().getEntityId(),listSelectedRows.get(0).getPrimaryKeyValue().toString());
        this.getView().showSuccessNotification("你被选中了");
    }
}
