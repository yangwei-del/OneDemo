package com.file.record;

import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.list.ListFilterParameter;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QFilter;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.EventObject;

public class ShangJia extends AbstractTreePlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
    }

    @Override
    public void treeNodeClick(TreeNodeEvent event) {
        String currentId = (String) event.getNodeId();
        QFilter filters = QFilter.of("textfield1=?",currentId);
        ListShowParameter listShowParameters = new ListShowParameter();
        ListFilterParameter listFilterParameters = new ListFilterParameter();
        listShowParameters.setListFilterParameter(listFilterParameters);
        listFilterParameters. setFilter(filters);
        listShowParameters. setListFilterParameter(listFilterParameters);
        listShowParameters. setFormId("bos_list");
        listShowParameters.setBillFormId("bhr_dangandaimulu");
        listShowParameters.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameters.getOpenStyle().setTargetKey("flexpanelap1");
        listShowParameters.setAppId("bhr_dananxitong");
        StyleCss styleCsss=new StyleCss();
        styleCsss.setHeight("100%");
        styleCsss.setWidth("100%");
        listShowParameters.getOpenStyle().setInlineStyleCss(styleCsss);
        this.getView().showForm(listShowParameters);
    }
}
