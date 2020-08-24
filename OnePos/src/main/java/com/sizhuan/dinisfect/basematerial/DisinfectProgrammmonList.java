package com.sizhuan.dinisfect.basematerial;

import dm.jdbc.util.StringUtil;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.mvc.list.ListView;
import kd.fi.bcm.formplugin.AbstractBaseListPlugin;

/**
 * @author zenghuogang
 * @create 2020/8/20
 */
public class DisinfectProgrammmonList extends AbstractBaseListPlugin {
    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String operKey= evt.getItemKey();
        if(StringUtil.equals(operKey,"kded_baritemap")){
            ListView listView= (ListView) this.getView();
            ListSelectedRow listSelectedRow= listView.getCurrentSelectedRowInfo();
            String pkvalue=listSelectedRow.getPrimaryKeyValue().toString();
            if(pkvalue==null){
                return;
            }
            FormShowParameter formShowParameter=new FormShowParameter();
            formShowParameter.setFormId("kded_select_box");
            formShowParameter.getOpenStyle().setShowType(ShowType.Modal);
            StyleCss styleCss=new StyleCss();
            styleCss.setWidth("800px");
            styleCss.setHeight("600px");
            formShowParameter.setCustomParam("DPId",pkvalue);
            formShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
            this.getView().showForm(formShowParameter);
        }


    }


}
