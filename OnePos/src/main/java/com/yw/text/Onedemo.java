package com.yw.text;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.form.control.events.*;

import java.util.EventObject;


public class Onedemo extends AbstractBillPlugIn {

    /**
     * 控件点击事件触发，通过Key判断触发来源执行不同操作。
     */
    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
            this.getView().showMessage("save");
    }

}
