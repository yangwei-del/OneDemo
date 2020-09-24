package com.yw.xiaodu;

import dm.jdbc.util.StringUtil;
import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.bill.BillOperationStatus;
import kd.bos.bill.BillShowParameter;
import kd.bos.bill.OperationStatus;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.EntityMetadataCache;
import kd.bos.entity.MainEntityType;
import kd.bos.entity.datamodel.events.PropertyChangedArgs;
import kd.bos.form.ClientProperties;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.control.Image;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.field.BasedataEdit;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.user.UserServiceHelper;

import java.util.*;

/* 人员申请单 */
public class PersonShenQing extends AbstractBillPlugIn implements BeforeF7SelectListener {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        BasedataEdit field = getView().getControl("kded_orgfield1");
        field.addBeforeF7SelectListener(this);
    }

    /* 上级公司查询 */
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

    @Override
    public void beforeF7Select(BeforeF7SelectEvent e) {
        DynamicObjectCollection dynamicObjectCollection= this.getModel().getEntryEntity("kded_entryentity");
        Long id = UserServiceHelper.getCurrentUserId();
        QFilter filter = QFilter.of("id = ? and entryentity.ispartjob = ?", id, false);
        DynamicObject user = QueryServiceHelper.queryOne("bos_user", "id, phone, entryentity.dpt", filter.toArray());
        Long dptId = user.getLong("entryentity.dpt");
        //公司
        String companyid = isCompany(String.valueOf(dptId));
        List<Long> adIds = new ArrayList<>();
        DynamicObject[] dynamicObjects=BusinessDataServiceHelper.load("bos_org_structure","id,number,name,org",new QFilter[]{new QFilter("parent",QCP.equals,companyid)});
        for (int i = 0; i <dynamicObjects.length ; i++) {
            DynamicObject orgDynamic=dynamicObjects[i].getDynamicObject("org");
            adIds.add(orgDynamic.getLong("id"));
        }
        QFilter qFilter = new QFilter("id", QCP.in, adIds);
        ListShowParameter showParameter = (ListShowParameter) e.getFormShowParameter();
        showParameter.getListFilterParameter().setFilter(qFilter);

    }

    @Override
    public void propertyChanged(PropertyChangedArgs e) {
        String propertiesName=e.getProperty().getName();
        if(StringUtil.equals(propertiesName,"kded_datefield")){
            Date currentData=new Date();
            Date selectData=(Date) this.getModel().getValue("kded_datefield");
            if(selectData==null){
                return;
            }
            if (currentData.compareTo(selectData) > 0) {
                this.getView().showTipNotification("选择时间只能大于当前时间哦");
                this.getModel().setValue("kded_datefield",null);
                return;
            }
        }
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String operKey=evt.getItemKey();
        if(StringUtil.equals(operKey,"kded_baritemap")){
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put("kded_userfield",this.getModel().getValue("kded_userfield"));
            paramMap.put("kded_orgfield1",this.getModel().getValue("kded_orgfield1"));
            paramMap.put("kded_datefield",this.getModel().getValue("kded_datefield"));

            BillShowParameter billShowParameter=new BillShowParameter();
            billShowParameter.setFormId("kded_jiludan");
//            billShowParameter.setAppId("kded_xiaoduxitong");
            billShowParameter.setStatus(OperationStatus.ADDNEW);
            billShowParameter.setBillStatus(BillOperationStatus.ADDNEW);

            billShowParameter.getOpenStyle().setShowType(ShowType.MainNewTabPage);
            billShowParameter.setCustomParams(paramMap);
            this.getView().showForm(billShowParameter);
        }
    }
    @Override
    public void afterCreateNewData(EventObject e) {
        Long id= UserServiceHelper.getCurrentUserId();
        this.getModel().setValue("kded_userfield",id);
        QFilter filter = QFilter.of("id = ? and entryentity.ispartjob = ?", id, false);
        DynamicObject user = QueryServiceHelper.queryOne("bos_user", "id, phone, entryentity.dpt", filter.toArray());
        Long dptId = user.getLong("entryentity.dpt");
        // 部门
        getModel().setValue("kded_orgfield", dptId);
        //公司
        String companyid = isCompany(String.valueOf(dptId));
        getModel().setValue("org", companyid);
        //头像设置
        DynamicObject dynamicObject = (DynamicObject) this.getModel().getValue("creator");
        getModel().setValue("kded_userfield", dynamicObject);
        String imageUrl = dynamicObject.getString("picturefield");
        Image image = this.getView().getControl("kded_imageap");
        image.setUrl(dynamicObject.getString("picturefield"));
        image.setTips("hello");
    }
    @Override
    public void afterBindData(EventObject e) {
        // TODO Auto-generated method stub
        super.afterBindData(e);
        //数据状态
        String billstatus = (String) this.getModel().getValue("billstatus");
        switch (billstatus){
            case "A":
                fontColor("orange");
                break;
            case "B":
                fontColor("orange");
                break;
            case "C":
                fontColor("green");
                break;
            case "F":
                fontColor("red");
                break;
        }
    }

    /**
     * 改变数据状态字体颜色
     * @param color
     */
    private  void fontColor(String color){
        Map<String,Object> data = new HashMap<>();
        data.put(ClientProperties.ForeColor,color);
        getView().updateControlMetadata("billstatus",data);
    }

}
