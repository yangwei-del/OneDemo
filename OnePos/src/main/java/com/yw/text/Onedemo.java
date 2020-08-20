package com.yw.text;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.entity.datamodel.IDataModel;
import kd.bos.entity.operate.result.OperationResult;
import kd.bos.form.events.AfterDoOperationEventArgs;
import kd.bos.form.events.HyperLinkClickEvent;
import kd.fi.gl.constant.Voucher;


public class Onedemo extends AbstractBillPlugIn {

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
    public void afterDoOperation(AfterDoOperationEventArgs evt) {
        String name = evt.getOperateKey();
        OperationResult operationResult = evt.getOperationResult();
        if (name.equals("ftextfield5") && operationResult.isSuccess()) {
            this.getView().setVisible(false, "creator");
        }
    }

}
