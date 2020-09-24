package com.yw.xiaodu;

import dm.jdbc.util.StringUtil;
import kd.bos.entity.datamodel.ListSelectedRow;
import kd.bos.form.FormShowParameter;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.mvc.list.ListView;
import kd.fi.bcm.formplugin.AbstractBaseListPlugin;

public class FenPeiZuZhi extends AbstractBaseListPlugin {
    //弹出组织列表,点击分配
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
            formShowParameter.setFormId("kded_tanchukuang");
            formShowParameter.getOpenStyle().setShowType(ShowType.Modal);
            StyleCss styleCss=new StyleCss();
            styleCss.setWidth("800px");
            styleCss.setHeight("600px");
            formShowParameter.setCustomParam("YWID",pkvalue);
            formShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
            this.getView().showForm(formShowParameter);
        }
    }
}
