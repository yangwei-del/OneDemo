package com.yw.text;

import com.alibaba.druid.util.StringUtils;
import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.form.IFormView;
import kd.bos.form.control.Button;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.BeforeItemClickEvent;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.control.events.TreeNodeCheckEvent;
import kd.bos.form.control.events.TreeNodeCheckListener;
import kd.bos.form.events.OnGetControlArgs;
import kd.bos.form.field.TextEdit;

import java.util.EventObject;
import java.util.List;


public class Onedemo extends AbstractBillPlugIn implements TreeNodeCheckListener {
    private final static String KEY_TREEVIEW1 = "treeviewap1";

    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        this.getView().showMessage("hello word!");
    }

    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        TreeView treeView = this.getView().getControl(KEY_TREEVIEW1);
        treeView.addTreeNodeCheckListener(this);
    }

    @Override
    public void beforeBindData(EventObject e) {
        super.beforeBindData(e);
        TreeView treeView = this.getView().getControl(KEY_TREEVIEW1);
        treeView.setMulti(true);	// 支持多选
    }

    @Override
    public void treeNodeCheck(TreeNodeCheckEvent arg0) {
        TreeView treeView = (TreeView) arg0.getSource();
        if (StringUtils.equals(treeView.getKey(), KEY_TREEVIEW1)) {
            List<String> selectNodeIds = treeView.getTreeState().getCheckedNodeIds();
            // TODO 在此添加业务逻辑
        }
    }

}
