package com.sizhuan.dinisfect.basematerial;

import kd.bos.bill.OperationStatus;
import kd.bos.dataentity.OperateOption;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.utils.StringUtils;
import kd.bos.entity.datamodel.events.BizDataEventArgs;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.OperationServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.fi.bcm.formplugin.AbstractBaseFormPlugin;
import org.apache.flink.runtime.rest.handler.async.OperationKey;
import scala.tools.nsc.transform.SpecializeTypes;

import java.util.EventObject;

/**
 * @author zenghuogang
 * @create 2020/8/19
 * 消毒方案
 */
public class DisinfectProgrammon extends AbstractBaseFormPlugin {


    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("tbmain");
    }

    @Override
    public void createNewData(BizDataEventArgs e) {
        //this.getView().getFormShowParameter().setStatus(OperationStatus.ADDNEW);

    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String operKey=evt.getItemKey();
        if(StringUtils.equals(operKey,"kded_baritemap")){
            //获取消毒方案的id
            String dp= this.getModel().getValue("id").toString();
            //过滤条件
            QFilter qFilter=new QFilter("kded_basedatafield.id", QCP.equals,dp);
            //查询是否存在，如果存在就新增，否则执行修改
            DynamicObject disinfectRecord= BusinessDataServiceHelper.loadSingle("kded_disinfect_record","id",new QFilter[]{qFilter});
            if(disinfectRecord==null){
                DynamicObject dynamicObject= this.getModel().getDataEntity(true);
                try {
                    OperationServiceHelper.executeOperate("save","kded_disinfect_progrom",new DynamicObject[]{dynamicObject}, OperateOption.create());
                    this.getView().showSuccessNotification("方案发布成功");
                }catch (Exception e){
                    this.getView().showMessage(e.getMessage());
                }

                return;
            }
            DynamicObject dynamicObject= this.getModel().getDataEntity(true);
            DynamicObject newDynamic= BusinessDataServiceHelper.newDynamicObject("kded_disinfect_progrom");
            newDynamic.set("number",dynamicObject.get("number"));
            newDynamic.set("name",dynamicObject.get("name"));
            newDynamic.set("kded_integerfield",1);
            newDynamic.set("kded_billstatusfield","A");
            newDynamic.set("enable","A");
            newDynamic.set("kded_entryentity",dynamicObject.getDynamicObjectCollection("kded_entryentity"));
            Object[] results= SaveServiceHelper.save(new DynamicObject[]{newDynamic});

            dynamicObject.set("kded_integerfield",dynamicObject.getInt("kded_integerfield")+1);
            dynamicObject.set("kded_billstatusfield","B");
            OperationServiceHelper.executeOperate("save","kded_disinfect_progrom",new DynamicObject[]{dynamicObject}, OperateOption.create());
            this.getView().showSuccessNotification("方案发布成功");
        }
    }
}
