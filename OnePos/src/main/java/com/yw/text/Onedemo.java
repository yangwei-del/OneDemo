package com.yw.text;

import com.alibaba.druid.util.StringUtils;
import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.datamodel.IDataModel;
import kd.bos.form.control.Control;
import kd.bos.form.control.TreeView;
import kd.bos.form.control.events.*;
import kd.bos.form.events.HyperLinkClickEvent;
import kd.fi.gl.constant.Voucher;

import java.util.EventObject;
import java.util.List;


public class Onedemo extends AbstractBillPlugIn implements TreeNodeCheckListener {
    private final static String KEY_TREEVIEW1 = "treeviewap1";

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

    /**
     * 按钮点击事件触发，通过Key判断触发来源执行不同操作。
     */
    public void click(EventObject evt) {
        super.click(evt);
        Control control = (Control) evt.getSource();
        String Key = control.getKey();
        if (Key.endsWith("btnOK")) {
            doOk(Key);
            return;
        }
    }

    private void doOk(String key) {
        System.out.println("11111111111111111111");
    }

    /**
     * 控件点击事件触发，通过Key判断触发来源执行不同操作。
     */
    public void itemClick(ItemClickEvent evt) {
        String key = evt.getItemKey();
        // 保存操作
        if ("save".equals(key)) {
            //do save
        } else if ("add".equals(key)) {
            //do add
        }
    }


    /*
     *超链接点击
     */
    public void hyperLinkClick(HyperLinkClickEvent evt) {
        String key = evt.getFieldName();
        if (Voucher.ACCT.equals(key)) {
            IDataModel model = this.getModel();
            //获取操作行索引
            int rowIndex = evt.getRowIndex();
            DynamicObject voucher = model.getDataEntity(true);
            //获取指定行单据体字段值
            long acctId = (long) model.getValue(Voucher.id_(Voucher.ACCT), rowIndex);
            long curId = (long) model.getValue(Voucher.id_(Voucher.CURRENCY), rowIndex);
            //do hyperLink operate
        }
    }


    /**
     * 分录点击事件
     */
    public void entryRowClick(RowClickEvent evt) {
        IDataModel model = this.getModel();
        Control ce = (Control) evt.getSource();
        int curIndex = evt.getRow();
        //获取分录标识
        String entryname = ce.getKey();
        //do sth  
    }

    /**
     * 分录双击事件
     */
    public void entryRowDoubleClick(RowClickEvent evt) {
        IDataModel model = this.getModel();
        Control ce = (Control) evt.getSource();
        int curIndex = evt.getRow();
        //获取分录标识
        String entryname = ce.getKey();
        //do sth  
    }

    /**
     * 单元格点击事件
     */
    public void cellClick(CellClickEvent evt) {
        String key = evt.getFieldKey();
        this.getView().setEnable(Voucher.EDESC.equals(key), "fillentry");
    }
}
