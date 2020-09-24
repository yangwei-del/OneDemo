package com.yw.xiaodu;

import com.alibaba.fastjson.JSONObject;
import dm.jdbc.util.StringUtil;
import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.bill.BillShowParameter;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.datamodel.events.PropertyChangedArgs;
import kd.bos.entity.report.CellStyle;
import kd.bos.form.BindingContext;
import kd.bos.form.control.EntryGrid;
import kd.bos.form.control.Image;
import kd.bos.form.control.Toolbar;
import kd.bos.form.control.events.BeforeUploadEvent;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.control.events.UploadEvent;
import kd.bos.form.control.events.UploadListener;
import kd.bos.form.events.AfterDoOperationEventArgs;
import kd.bos.form.field.BasedataEdit;
import kd.bos.form.field.events.BeforeF7SelectEvent;
import kd.bos.form.field.events.BeforeF7SelectListener;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.attachment.AttachmentFieldServiceHelper;

import java.util.*;

/**
 * @author zenghuogang
 * @create 2020/8/20
 * 消毒记录单
 */
public class XiaoDuJiLu extends AbstractBillPlugIn implements UploadListener, BeforeF7SelectListener {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        Toolbar toolbar= this.getView().getControl("tbmain");
        toolbar.addUploadListener(this);
        BasedataEdit basematerial = getView().getControl("kded_basedatafield");
        basematerial.addBeforeF7SelectListener(this);
    }



    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        this.getModel().getEntryEntity("kded_entryentity");

    }

    @Override
    public void afterCreateNewData(EventObject e) {
        BillShowParameter billShowParameter=(BillShowParameter)this.getView().getFormShowParameter();
        Map<String,Object> paramMap=billShowParameter.getCustomParams();
        if(paramMap.size()<3){
            return;
        }
        this.getModel().setValue("creator",((JSONObject)paramMap.get("kded_userfield")).get("id"));
        this.getModel().setValue("kded_orgfield",((JSONObject)paramMap.get("kded_orgfield1")).get("id"));
        this.getModel().setValue("kded_datefield",paramMap.get("kded_datefield"));
    }

    @Override
    public void afterBindData(EventObject e) {
        super.afterBindData(e);
        DynamicObject dynamicObject = (DynamicObject) this.getModel().getValue("creator");
        if(dynamicObject==null){
            return;
        }
        Image image = this.getView().getControl("kded_imageap");
        image.setUrl(dynamicObject.getString("picturefield"));
        image.setTips("hello");
        DynamicObjectCollection dynamicObjectCollection= this.getModel().getEntryEntity("kded_entryentity");
        for (int i = 0; i < dynamicObjectCollection.size(); i++) {
            if(StringUtil.equals(dynamicObjectCollection.get(i).getString("kded_billstatusfield"),"B")){
                this.setColor(dynamicObjectCollection.get(i).getInt("seq")-1);
            }
        }
        DynamicObject dinisfectProgram=(DynamicObject) this.getModel().getValue("kded_basedatafield");
        if(dinisfectProgram==null){
            return;
        }
        //当前单据体
        DynamicObjectCollection currentDOC= this.getModel().getEntryEntity("kded_entryentity");
        //方案单据体
        DynamicObjectCollection disinDOC=  dinisfectProgram.getDynamicObjectCollection("kded_entryentity");
        for (int i = 0; i <disinDOC.size() ; i++) {
            DynamicObject dynamicObject3=disinDOC.get(i);
            DynamicObject dinisfectLevel=dynamicObject3.getDynamicObject("kded_kded_basedatafield");
            DynamicObjectCollection dynamicObjectCollection3=dynamicObject3.getDynamicObjectCollection("kded_subentryentity");
            for (int j = 0; j <dynamicObjectCollection3.size() ; j++) {
                DynamicObject dynamicObject1=dynamicObjectCollection3.get(j);
                DynamicObject dynamicObject2= currentDOC.addNew();
                dynamicObject2.set("kded_basedatafield1",dinisfectLevel);
                dynamicObject2.set("kded_basedatafield2",dynamicObject1.get("kded_kded_basedatafield1"));
                dynamicObject2.set("kded_billstatusfield","A");
                this.getModel().createNewEntryRow("kded_entryentity",i,dynamicObject2);
                this.getModel().setValue("kded_entryentity",currentDOC);
            }
        }

    }

    @Override
    public void beforeUpload(BeforeUploadEvent evt) {
        System.out.println(evt.getAttachInfos());
    }

    @Override
    public void upload(UploadEvent evt) {

        Image image = this.getView().getControl("kded_imageap");
        image.setUrl(evt.getUrls()[1].toString());
        // do nothing
    }

    @Override
    public void afterUpload(UploadEvent evt) {
        List<String> fileUrls = new ArrayList<>();

        // 从事件的传入参数中，读取已上传到文件服务器的文件地址(相对地址，未包括文件服务器站点地址)
        for(Object url : evt.getUrls()){
            fileUrls.add((String) ((Map<String,Object>)url).get("url"));
        }
        // 从文件服务器中，读取已上传的XLS文件
        for(String fileUrl : fileUrls){

        }
    }

    @Override
    public void beforeF7Select(BeforeF7SelectEvent e) {
        DynamicObject workshop=(DynamicObject) this.getModel().getValue("kded_orgfield");
        if(workshop==null){
            this.getView().showTipNotification("请先选择车间");
            return;
        }
        String workshopId=workshop.getString("id");
        DynamicObject[] dinisfectProgramm= BusinessDataServiceHelper.load("kded_xiaodufangan","id,kded_orgentryentity,kded_orgentryentity.kded_kded_orgfield,kded_orgentryentity.kded_kded_orgfield",new QFilter[]{});
        List<Long> ids=new ArrayList<>();
        for (int i = 0; i <dinisfectProgramm.length ; i++) {
            DynamicObject dynamicObject=dinisfectProgramm[i];
            DynamicObjectCollection dynamicObjectCollection = dynamicObject.getDynamicObjectCollection("kded_orgentryentity");
            if(dynamicObjectCollection==null||dynamicObjectCollection.size()==0){
                continue;
            }
            for (int j = 0; j <dynamicObjectCollection.size() ; j++) {
                DynamicObject dynamicObject1=dynamicObjectCollection.get(j);
                DynamicObject workDy= dynamicObject1.getDynamicObject("kded_kded_orgfield");
                if(StringUtil.equals(workshopId,workDy.getString("id"))){
                    ids.add(dynamicObject.getLong("id"));
                    break;
                }
            }

        }
        QFilter qFilter = new QFilter("id", QCP.in, ids);
        ListShowParameter listShowParameter=(ListShowParameter)e.getFormShowParameter();
        listShowParameter.getListFilterParameter().setFilter(qFilter);
    }

    @Override
    public void propertyChanged(PropertyChangedArgs e) {
        String fieldkey= e.getProperty().getName();
        if(StringUtil.equals(fieldkey,"kded_basedatafield")){
            this.getView().updateView();


        }
    }
    public void setColor(int j){
        EntryGrid entryGrid= this.getView().getControl("kded_entryentity");
        List<CellStyle> cellStyles=new ArrayList<>();
        CellStyle cellStyle=new CellStyle();
        cellStyle.setRow(j);
        cellStyle.setBackColor("red");
        cellStyle.setFieldKey("kded_billstatusfield");
        cellStyles.add(cellStyle);
        entryGrid.setCellStyle(cellStyles);
        entryGrid.getControls();
    }

    @Override
    public void afterDoOperation(AfterDoOperationEventArgs e) {
        super.afterDoOperation(e);
        String operkey=e.getOperateKey();
        if(StringUtil.equals("save",operkey)){
            Boolean result= e.getOperationResult().isSuccess();
            if(!result){
                return;
            }
            DynamicObjectCollection dynamicObjectCollection= this.getModel().getEntryEntity("kded_entryentity");
            for (int i = 0; i < dynamicObjectCollection.size(); i++) {
                if(StringUtil.equals(dynamicObjectCollection.get(i).getString("kded_billstatusfield"),"B")){
                    this.setColor(dynamicObjectCollection.get(i).getInt("seq")-1);
                }
            }

        }
    }

}
