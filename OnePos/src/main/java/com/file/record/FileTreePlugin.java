package com.file.record;

import dm.jdbc.util.StringUtil;
import kd.bos.base.BaseShowParameter;
import kd.bos.bill.OperationStatus;
import kd.bos.entity.tree.TreeNode;
import kd.bos.form.CloseCallBack;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.events.ClosedCallBackEvent;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.operation.DeleteServiceHelper;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.EventObject;

public class FileTreePlugin extends AbstractTreePlugin {
    final static FileCollectionImpl fileCollection=new FileCollectionImpl();
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        this.addItemClickListeners("toolbarap");
        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
    }
    @Override
    public void itemClick(ItemClickEvent evt) {

        String operKey= evt.getItemKey();

        TreeView treeView = this.getView().getControl("treeviewap");
        treeView.addTreeNodeClickListener(this);
        String focusNodeId = treeView.getTreeState().getFocusNodeId();
        if (focusNodeId == null) {
            this.getView().showTipNotification("选择节点");
        }else if(StringUtil.equals(operKey,"baritemap2")){
            BaseShowParameter formShowParameter = fileCollection.formShowParameter();
            formShowParameter.setStatus(OperationStatus.ADDNEW);
            formShowParameter.setCustomParam("fa",focusNodeId);
            CloseCallBack closeCallBack=new CloseCallBack(this,"addnewID");
            formShowParameter.setCloseCallBack(closeCallBack);
            this.getView().showForm(formShowParameter);
            this.getView().showSuccessNotification("新增成功");
            this.getView().updateView();
        }else if(StringUtil.equals(operKey, "baritemap")) {
            BaseShowParameter formShowParameter = fileCollection.formShowParameter();
            //改为修改状态
            formShowParameter.setCustomParam("myId",focusNodeId);
            CloseCallBack closeCallBack=new CloseCallBack(this,"updateID");
            formShowParameter.setCloseCallBack(closeCallBack);
            this.getView().showSuccessNotification("修改成功");
            this.getView().showForm(formShowParameter);
        }else if(StringUtil.equals(operKey, "baritemap3")){
            DeleteServiceHelper.delete("bhr_jiedianliebiao",new QFilter[]{QFilter.of("number = ? or code = ?",focusNodeId,focusNodeId)});
            treeView.deleteNode(focusNodeId);
            this.getView().showSuccessNotification("删除成功");
//            treeView.deleteNode();
        }
    }

    @Override
    public void beforeBindData(EventObject e) {

        TreeView treeView = this.getView().getControl("treeviewap");
        for (TreeNode treeNode:fileCollection.addTreeNode("0")
        ) {
            fileCollection.addAllTreeNode(treeNode);
            //往当前节点添加节点
            treeView.addNode(treeNode);
        }
    }

    @Override
    public void closedCallBack(ClosedCallBackEvent closedCallBackEvent) {
        Object returnData = closedCallBackEvent.getReturnData();
        String actionId = closedCallBackEvent.getActionId();
        TreeNode node = fileCollection.queryTreeNode(returnData);

        TreeView treeView = this.getView().getControl("treeviewap");
        String focusNodeId = treeView.getTreeState().getFocusNodeId();
        if (actionId.equals("addnewID")) {
            treeView.addNode(node);
            treeView.showNode(node.getId());
        } else if (actionId.equals("updateID")) {
            treeView.deleteNode(focusNodeId);
            treeView.addNode(node);
            treeView.showNode(node.getId());
        }
    }
}
