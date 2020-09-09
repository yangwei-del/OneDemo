package com.file.record;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.datamodel.events.BizDataEventArgs;
import kd.bos.form.events.BeforeClosedEvent;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.epm.eb.formplugin.AbstractBasePlugin;

import java.util.EventObject;
import java.util.Map;

public class IncreaseDate extends AbstractBasePlugin {

    @Override
    public void afterCreateNewData(EventObject e) {
        Map<String, Object> customParams = this.getView().getFormShowParameter().getCustomParams();
        if (customParams.get("fa")!=null){
            Object fa = customParams.get("fa");
            this.getModel().setValue("code",fa.toString());
        }

    }

    @Override
    public void beforeClosed(BeforeClosedEvent e) {
        this.getView().returnDataToParent(this.getModel().getValue("number"));

    }

    @Override
    public void createNewData(BizDataEventArgs e) {
        Map<String, Object> customParams = this.getView().getFormShowParameter().getCustomParams();
        if (customParams.get("myId")!=null){
            Object myId = customParams.get("myId");
            DynamicObject bhr_jiedianliebiao = BusinessDataServiceHelper.loadSingle("bhr_jiedianliebiao",
                    "number,code,name1,level", new QFilter[]{QFilter.of("number=?", myId)});
            Object id = bhr_jiedianliebiao.get("id");
            DynamicObject dynamicObject = BusinessDataServiceHelper.loadSingle(id, "bhr_jiedianliebiao");
            e.setDataEntity(dynamicObject);
        }
    }

}
