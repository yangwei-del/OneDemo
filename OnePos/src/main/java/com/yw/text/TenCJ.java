package com.yw.text;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.datamodel.ListSelectedRowCollection;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.mvc.list.ListView;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.operation.DeleteServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.fi.bcm.formplugin.AbstractBaseListPlugin;

import java.util.EventObject;

public class TenCJ  extends AbstractBaseListPlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners( "toolbarap");
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        String key = evt.getItemKey();
        if ("add".equals(key)){ //保存
            DynamicObject dynamicObject= this.getModel().getDataEntity(true);
            Object[] results= SaveServiceHelper.save(new DynamicObject[]{dynamicObject});
        }
        super.itemClick(evt);
    }
}
