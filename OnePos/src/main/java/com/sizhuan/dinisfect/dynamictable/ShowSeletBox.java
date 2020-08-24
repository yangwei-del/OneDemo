package com.sizhuan.dinisfect.dynamictable;

import dm.jdbc.util.StringUtil;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.entity.OrmLocaleValue;
import kd.bos.entity.EntityMetadataCache;
import kd.bos.entity.MainEntityType;
import kd.bos.form.control.EntryGrid;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.bos.servicehelper.user.UserServiceHelper;

import java.util.EventObject;

/**
 * @author zenghuogang
 * @create 2020/8/20
 */
public class ShowSeletBox extends AbstractFormPlugin {

    @Override
    public void afterBindData(EventObject e) {
        super.afterBindData(e);
//        DynamicObjectCollection dynamicObjectCollection= this.getModel().getEntryEntity("kded_entryentity");
//        DynamicObject dynamicObject=dynamicObjectCollection.addNew();
//        dynamicObject.set("kded_textfield","213124");
//        dynamicObject.set("kded_textfield1","name");
//        dynamicObject.set("kded_textfield2","hhh");
//        dynamicObjectCollection.add(dynamicObject);
    }

    @Override
    public void afterCreateNewData(EventObject e) {
        DynamicObjectCollection dynamicObjectCollection= this.getModel().getEntryEntity("kded_entryentity");

        Long id = UserServiceHelper.getCurrentUserId();

        QFilter filter = QFilter.of("id = ? and entryentity.ispartjob = ?", id, false);

        DynamicObject user = QueryServiceHelper.queryOne("bos_user", "id, phone, entryentity.dpt", filter.toArray());

        Long dptId = user.getLong("entryentity.dpt");
        //公司
        String companyid = isCompany(String.valueOf(dptId));

        DynamicObject[] dynamicObjects=BusinessDataServiceHelper.load("bos_org_structure","id,number,name,org",new QFilter[]{new QFilter("parent",QCP.equals,companyid)});
        for (int i = 0; i <dynamicObjects.length ; i++) {
            DynamicObject dynamicObject=dynamicObjectCollection.addNew();
            DynamicObject orgDynamic=dynamicObjects[i].getDynamicObject("org");
            dynamicObject.set("kded_textfield",orgDynamic.get("id"));
            dynamicObject.set("kded_textfield1",orgDynamic.get("number"));
            dynamicObject.set("kded_textfield2",orgDynamic.get("name"));
        }

    }

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("kded_toolbarap");
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String operkey=evt.getItemKey();
        if(StringUtil.equals(operkey,"kded_baritemap")){
            int index= this.getModel().getEntryCurrentRowIndex("kded_entryentity");
            DynamicObject dynamicObject= this.getModel().getEntryEntity("kded_entryentity").get(index);

            String pkvalue= this.getView().getFormShowParameter().getCustomParam("DPId");

            DynamicObject dpDynamic=BusinessDataServiceHelper.loadSingle(pkvalue,"kded_disinfect_progrom");
            DynamicObjectCollection dynamicObjectCollection=dpDynamic.getDynamicObjectCollection("kded_orgentryentity");
            for (int i = 0; i <dynamicObjectCollection.size() ; i++) {
                if(dynamicObjectCollection.get(i).getDynamicObject("kded_kded_orgfield").getString("id").equals(dynamicObject.get("kded_textfield"))){
                    this.getView().showTipNotification("方案已分配到该车间");
                    return;
                }
            }

            DynamicObject newDynamicObjec= dynamicObjectCollection.addNew();
            newDynamicObjec.set("kded_kded_orgfield",dynamicObject.get("kded_textfield"));
            SaveServiceHelper.save(new DynamicObject[]{dpDynamic});
            this.getView().showSuccessNotification("方案分配成功");
            this.getView().close();
        }

    }

    /**
     * 上级公司查询
     *
     * @param dptid
     * @return
     */
    private String isCompany(String dptid) {
        QFilter orgFilter = new QFilter("org", QCP.equals, dptid);
        String selectFields = "parent";
        QFilter[] filters = new QFilter[]{orgFilter};
        DynamicObjectCollection orgStruCol = QueryServiceHelper.query("bos_org_structure", selectFields, filters);
        MainEntityType e1 = EntityMetadataCache.getDataEntityType("bos_org");
        DynamicObject parentCompany = BusinessDataServiceHelper.loadSingle(orgStruCol.get(0).getLong("parent"), e1);
        String parentid = parentCompany.getString("id");
        if (parentid != null) {
            return parentid;
        }

        return null;
    }
}
