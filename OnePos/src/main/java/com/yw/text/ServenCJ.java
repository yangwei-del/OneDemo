package com.yw.text;

import kd.bos.bill.AbstractBillPlugIn;
import kd.bos.entity.datamodel.events.PropertyChangedArgs;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ServenCJ extends AbstractBillPlugIn {

    //计算假期天数
    @Override
    public void propertyChanged(PropertyChangedArgs e) {
        String pname = e.getProperty().getName();
        //开始标识"fkaishi",结束标识"fjieshu",假期天数标识"days"
        if ("fkaishi".equals(pname) || "fjieshu".equals(pname)){
            if (this.getModel().getValue("fkaishi") != null && this.getModel().getValue("fjieshu") != null){
                Date start = (Date) this.getModel().getValue("fkaishi");
                Date end = (Date) this.getModel().getValue("fjieshu");
                Instant fkaishi = start.toInstant();
                Instant fjieshu = end.toInstant();
                Long days = Duration.between(fkaishi,fjieshu).toDays() + 1;
                this.getModel().setValue("fdays",days);
            }
        }
        super.propertyChanged(e);
    }
}
