package com.file.record;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.tree.TreeNode;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.list.ListFilterParameter;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.EventObject;

public class flexmianban1 extends AbstractTreePlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
    }

    @Override
    public void treeNodeClick(TreeNodeEvent evt) {
        //拿到当前树节点ID
        String currentId = (String) evt.getNodeId();
        DynamicObjectCollection bhr_dangandaimulu = QueryServiceHelper.query("bhr_dangandaimulu", "number,name,textfield", null);
        QFilter filter = new QFilter("textfield", QCP.equals, currentId);
        ListShowParameter listShowParameter = new ListShowParameter();
        ListFilterParameter listFilterParameter = new ListFilterParameter();
        listShowParameter.setListFilterParameter(listFilterParameter);
        listFilterParameter. setFilter(filter);
        listShowParameter. setListFilterParameter(listFilterParameter);
        listShowParameter. setFormId("bos_list");
        listShowParameter.setBillFormId("bhr_dangandaimulu");
        listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameter.getOpenStyle().setTargetKey("flexmianban");
        listShowParameter.setAppId("bhr_dananxitong");
        StyleCss styleCss=new StyleCss();
        styleCss.setHeight("100%");
        styleCss.setWidth("100%");
        listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
        this.getView().showForm(listShowParameter);
    }
}
