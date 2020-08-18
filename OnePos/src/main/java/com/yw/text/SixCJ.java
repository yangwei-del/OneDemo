package com.yw.text;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.entity.datamodel.IDataModel;
import kd.bos.form.control.Control;
import kd.bos.form.control.events.CellClickEvent;
import kd.bos.form.control.events.RowClickEvent;
import kd.fi.gl.constant.Voucher;

public class SixCJ extends AbstractBillPlugIn {
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
