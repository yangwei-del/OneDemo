package com.file.record;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.tree.TreeNode;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.container.Container;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.list.ListFilterParameter;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.EventObject;

public class FileShangJiaPlugin extends AbstractTreePlugin {
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("toolbarap");
        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
    }

    @Override
    public void treeNodeClick(TreeNodeEvent evt) {
        TreeNode treeNode = new TreeNode();
        //拿到当前树节点ID
        String currentId = treeNode.getId();
        DynamicObjectCollection bhr_dangandaimulu = QueryServiceHelper.query("bhr_dangandaimulu", "textfield1", null);
        QFilter filter = new QFilter("textfield1","=",currentId);
        ListShowParameter listShowParameter = new ListShowParameter();
        ListFilterParameter listFilterParameter = new ListFilterParameter();
        listShowParameter.setListFilterParameter(listFilterParameter);
        listFilterParameter. setFilter(filter);
        listShowParameter. setListFilterParameter(listFilterParameter);
        listShowParameter. setFormId("bos_list");
        listShowParameter.setBillFormId("bhr_dangandaimulu");
        listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameter.getOpenStyle().setTargetKey("flexpanelap2");
        listShowParameter.setAppId("bhr_dananxitong");
        StyleCss styleCss=new StyleCss();
        styleCss.setHeight("100%");
        styleCss.setWidth("100%");
        listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
        this.getView().showForm(listShowParameter);
    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        String keys = evt.getItemKey();
        if (keys.equals("shangjia")) {
            DataClass dataClass = DataClass.getInstance();
            String data = dataClass.getData(this.getView().getEntityId());
            dataClass.setData("textfield1","");
            TreeView treeView = this.getView().getControl("treeviewap");
            String focusNodeId = treeView.getTreeState().getFocusNodeId();
            Container container=this.getView().getControl("flexpanelap2");
            if(focusNodeId==null){
                this.getView().showTipNotification("请选择节点");
                return;
            }else if(data.equals("")) {
                this.getView().showTipNotification("请选择行数据");
                return;
            }
            DynamicObject dynamicObject = BusinessDataServiceHelper.loadSingleFromCache("bhr_dangandaimulu", new QFilter[]{QFilter.of("id=?", data)});
            dynamicObject.set("textfield1",focusNodeId);
            SaveServiceHelper.save(new DynamicObject[]{dynamicObject});


            TreeNode treeNode = new TreeNode();
            //拿到当前树节点ID
            String currentId = treeNode.getId();
            DynamicObjectCollection bhr_dangandaimulu = QueryServiceHelper.query("bhr_dangandaimulu", "textfield1", null);
            QFilter filter = new QFilter("textfield1","=",currentId);
            ListShowParameter listShowParameter = new ListShowParameter();
            ListFilterParameter listFilterParameter = new ListFilterParameter();
            listShowParameter.setListFilterParameter(listFilterParameter);
            listFilterParameter. setFilter(filter);
            listShowParameter. setListFilterParameter(listFilterParameter);
            listShowParameter. setFormId("bos_list");
            listShowParameter.setBillFormId("bhr_dangandaimulu");
            listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
            listShowParameter.getOpenStyle().setTargetKey("flexpanelap2");
            listShowParameter.setAppId("bhr_dananxitong");
            StyleCss styleCss=new StyleCss();
            styleCss.setHeight("100%");
            styleCss.setWidth("100%");
            listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
            this.getView().showForm(listShowParameter);



            this.getView().showSuccessNotification("上架成功");
        }
    }
}