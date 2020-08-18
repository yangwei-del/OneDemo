package com.yw.text;

import kd.bos.form.FormShowParameter;
import kd.bos.form.events.PreOpenFormEventArgs;
import kd.bos.form.plugin.AbstractFormPlugin;
import kd.bos.servicehelper.model.PermissionStatus;

public class TwoCJ extends AbstractFormPlugin {
    @Override
    public void preOpenForm(PreOpenFormEventArgs e) {

        // 设置显示参数 - 是否触发TimerElapsed事件
        FormShowParameter showParameter = (FormShowParameter)e.getSource();
        showParameter.setListentimerElapsed(true);

        // 设置显示参数 - 界面标题
        showParameter.setCaption("hello world");
    }
}
