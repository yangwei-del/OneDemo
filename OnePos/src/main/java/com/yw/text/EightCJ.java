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

public class EightCJ extends AbstractBaseListPlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners( "toolbarap");
    }


    
    //删除
    @Override
    public void itemClick(ItemClickEvent evt) {
        String key = evt.getItemKey();
        if ("delete".equals(key)) {
            ListView list = (ListView) this.getView();
            ListSelectedRowCollection selectedRows = list.getSelectedRows();
            String name ="bhr_xueshengxinxi";
            QFilter[] qFilters=new QFilter[]{};
            DeleteServiceHelper.delete(name,new QFilter[]{QFilter.of("id = ?","966668689841588224")});
            this.getView().showMessage("删除成功");
        }else if ("xiugai".equals(key)){ //修改
            this.getModel().setValue("name","2");
            this.getModel().setValue("nianji","2");
            this.getModel().setValue("xuehao","2");
            this.getModel().setValue("guanji","2");
            this.getModel().setValue("fsz","2");
            this.getModel().setValue("zhengzhi","2");
            DynamicObject dynamicObject= this.getModel().getDataEntity(true);
            Object[] results=SaveServiceHelper.save(new DynamicObject[]{dynamicObject});
        }
        super.itemClick(evt);
    }
}
