package com.yw.text;

import kd.bos.form.FormShowParameter;
import kd.bos.form.control.Button;
import kd.bos.form.control.Control;
import kd.bos.form.control.events.ClickListener;
import kd.bos.form.events.PreOpenFormEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.servicehelper.model.PermissionStatus;

import java.util.EventObject;

public class TwoCJ extends AbstractFormPlugin  implements ClickListener {

    @Override
    public void preOpenForm(PreOpenFormEventArgs e) {

        // 设置显示参数 - 是否触发TimerElapsed事件
        FormShowParameter showParameter = (FormShowParameter)e.getSource();
        showParameter.setListentimerElapsed(true);

        // 设置显示参数 - 界面标题
        showParameter.setCaption("人员申请单");
    }

        public void regiterListener(EventObject evt){
            // 1.注册监听
            Button btn = this.getView().getControl("按钮标识");
            btn.addClickListener(this);
        }

        public void click(EventObject evt) {
            // 2. 响应方法，do something...
            Control control = (Control) evt.getSource();
            String key = control.getKey();
            if ("按钮标识".equals(key)) {
                // do some cool thing...
                this.getView().showMessage("1111111111111111111");
            }
    }


}
