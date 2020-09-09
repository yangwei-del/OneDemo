package com.file.record;

import com.grapecity.documents.excel.Z;
import kd.bos.base.BaseShowParameter;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.datamodel.events.PropertyChangedArgs;
import kd.bos.entity.tree.TreeNode;
import kd.bos.form.CloseCallBack;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.container.Container;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.form.field.ComboEdit;
import kd.bos.form.field.ComboItem;
import kd.bos.list.ListFilterParameter;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.EventObject;
import java.util.List;

public class FileZhengBianPligin extends AbstractTreePlugin {
    final static FileCollectionImpl fileCollection=new FileCollectionImpl();
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("toolbarap1");
        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
    }

    @Override
    public void treeNodeClick(TreeNodeEvent evt) {
        TreeNode treeNode = new TreeNode();
        //拿到当前树节点ID
        String currentId = treeNode.getId();
        DynamicObjectCollection bhr_dangandaimulu = QueryServiceHelper.query("bhr_dangandaimulu", "textfield", null);
        QFilter filter = new QFilter("textfield","=",currentId);
        ListShowParameter listShowParameter = new ListShowParameter();
        ListFilterParameter listFilterParameter = new ListFilterParameter();
        listShowParameter.setListFilterParameter(listFilterParameter);
        listFilterParameter. setFilter(filter);
        listShowParameter. setListFilterParameter(listFilterParameter);
        listShowParameter. setFormId("bos_list");
        listShowParameter.setBillFormId("bhr_dangandaimulu");
        listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameter.getOpenStyle().setTargetKey("flexpanelap4");
        listShowParameter.setAppId("bhr_dananxitong");
        StyleCss styleCss=new StyleCss();
        styleCss.setHeight("100%");
        styleCss.setWidth("100%");
        listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
        this.getView().showForm(listShowParameter);
    }


    @Override
    public void afterCreateNewData(EventObject e) {
        List<ComboItem> list = fileCollection.ComboItem();
        ComboEdit edit = this.getView().getControl("combofield");
        edit.setComboItems(list);
    }

    @Override
    public void propertyChanged(PropertyChangedArgs e) {
        String row = (String) this.getModel().getValue("combofield");
        QFilter filter = QFilter.of("textfield=?","");
        ListShowParameter listShowParameter = new ListShowParameter();
        ListFilterParameter listFilterParameter = new ListFilterParameter();
        listShowParameter.setListFilterParameter(listFilterParameter);
        listFilterParameter. setFilter(filter);
        listShowParameter. setListFilterParameter(listFilterParameter);
        listShowParameter. setFormId("bos_list");
        listShowParameter.setBillFormId(row);
        listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameter.getOpenStyle().setTargetKey("flexpanelap4");
        listShowParameter.setAppId("bhr_dananxitong");
        StyleCss styleCss=new StyleCss();
        styleCss.setHeight("100%");
        styleCss.setWidth("100%");
        listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
        this.getView().showForm(listShowParameter);

        QFilter filters = QFilter.of("textfield != ?","");
        ListShowParameter listShowParameters = new ListShowParameter();
        ListFilterParameter listFilterParameters = new ListFilterParameter();
        listShowParameters.setListFilterParameter(listFilterParameters);
        listFilterParameters. setFilter(filters);
        listShowParameters. setListFilterParameter(listFilterParameters);
        listShowParameters. setFormId("bos_list");
        listShowParameters.setBillFormId(row);
        listShowParameters.getOpenStyle().setShowType(ShowType.InContainer);
        listShowParameters.getOpenStyle().setTargetKey("flexmianban");
        listShowParameters.setAppId("bhr_dananxitong");
        StyleCss styleCsss=new StyleCss();
        styleCsss.setHeight("100%");
        styleCsss.setWidth("100%");
        listShowParameter.getOpenStyle().setInlineStyleCss(styleCsss);
        this.getView().showForm(listShowParameters);

    }

    @Override
    public void itemClick(ItemClickEvent evt) {
        String keys = evt.getItemKey();
        if (keys.equals("zhenbian")) {
            DataClass dataClass = DataClass.getInstance();
            String data = dataClass.getData(this.getView().getEntityId());
            dataClass.setData("textfield","");
            TreeView treeView = this.getView().getControl("treeviewap");
            String focusNodeId = treeView.getTreeState().getFocusNodeId();
            Container container=this.getView().getControl("flexpanelap4");
            Object combofield = this.getModel().getValue("combofield");
            if(combofield==null) {
                this.getView().showTipNotification("请选择类型");
                return;
            }else if(focusNodeId==null){
            this.getView().showTipNotification("请选择节点");
            return;
            }else if(data.equals("")) {
                this.getView().showTipNotification("请选择行数据");
                return;
            }
            DynamicObject dynamicObject = BusinessDataServiceHelper.loadSingleFromCache(combofield.toString(), new QFilter[]{QFilter.of("id=?", data)});
            dynamicObject.set("textfield",focusNodeId);
            SaveServiceHelper.save(new DynamicObject[]{dynamicObject});





            TreeNode treeNode = new TreeNode();
            //拿到当前树节点ID
            String currentId = treeNode.getId();
            DynamicObjectCollection bhr_dangandaimulu = QueryServiceHelper.query("bhr_dangandaimulu", "textfield", null);
            QFilter filter = new QFilter("textfield","=",currentId);
            ListShowParameter listShowParameter = new ListShowParameter();
            ListFilterParameter listFilterParameter = new ListFilterParameter();
            listShowParameter.setListFilterParameter(listFilterParameter);
            listFilterParameter. setFilter(filter);
            listShowParameter. setListFilterParameter(listFilterParameter);
            listShowParameter. setFormId("bos_list");
            listShowParameter.setBillFormId("bhr_dangandaimulu");
            listShowParameter.getOpenStyle().setShowType(ShowType.InContainer);
            listShowParameter.getOpenStyle().setTargetKey("flexpanelap4");
            listShowParameter.setAppId("bhr_dananxitong");
            StyleCss styleCss=new StyleCss();
            styleCss.setHeight("100%");
            styleCss.setWidth("100%");
            listShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
            this.getView().showForm(listShowParameter);



            this.getView().showSuccessNotification("整编成功");

        }
    }

}
