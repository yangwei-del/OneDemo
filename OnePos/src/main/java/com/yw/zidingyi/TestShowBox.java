package com.yw.zidingyi;

import dm.jdbc.util.StringUtil;
import kd.bos.entity.datamodel.events.BizDataEventArgs;
import kd.bos.form.control.Toolbar;
import kd.bos.form.control.events.BeforeItemClickEvent;
import kd.bos.form.control.events.ItemClickEvent;
import kd.bos.form.events.MessageBoxClosedEvent;
import kd.bos.form.plugin.AbstractFormPlugin;

import java.util.EventObject;

/**
 * @author zenghuogang
 * @create 2020/9/1
 */
public class TestShowBox extends AbstractFormPlugin {

    /**
     * 有些特殊的事件需要在这里注释才能生效
     * @param e
     */
    @Override
    public void registerListener(EventObject e) {
        super.registerListener(e);
        //如果是动态表单，工具是不会注册的
        Toolbar toolbar= this.getView().getControl("kded_toolbarap");
        toolbar.addItemClickListener(this);
    }

    /**
     * 界面创建数据包的时候
     * @param e
     */
    @Override
    public void createNewData(BizDataEventArgs e) {
        //this.getView().showMessage("createNewData");
    }

    /**
     * 界面创建数据包之后执行，DynamicObject对象修改一下
     * @param e
     */
    @Override
    public void afterCreateNewData(EventObject e) {
        this.getView().showMessage("afterCreateNewData");
    }

    /**
     * 前端界面和后台数据绑定之前执行
     * @param e
     */
    @Override
    public void beforeBindData(EventObject e) {
        super.beforeBindData(e);
        this.getView().showMessage("beforeBindData");
    }

    /**
     * 前端界面和后台数据包绑定之后执行
     * @param e
     */
    @Override
    public void afterBindData(EventObject e) {
        super.afterBindData(e);
        this.getView().showMessage("afterBindData");
    }

    /**
     * 工具栏按钮单机之前执行
     * @param evt
     */
    @Override
    public void beforeItemClick(BeforeItemClickEvent evt) {
        //this.getView().showMessage("beforeItemClick");
        //工具栏按钮单机之前执行，可取消后面的操作
        //evt.setCancel(true);
    }

    /**
     * 工具单机的时候执行
     * @param evt
     */
    @Override
    public void itemClick(ItemClickEvent evt) {
        super.itemClick(evt);
        String operKkey=evt.getItemKey();
        if(StringUtil.equals("kded_baritemap",operKkey)){
            this.getView().showSuccessNotification("我是成功提示");
        }else if(StringUtil.equals("kded_baritemap1",operKkey)){
            this.getView().showErrorNotification("我是错误的提示");
        }else if(StringUtil.equals("kded_baritemap2",operKkey)){
            this.getView().showTipNotification("我是警告提示");
        }
    }

    /**
     * 消息盒子关闭的时候执行
     * @param e
     */
    @Override
    public void messageBoxClosed(MessageBoxClosedEvent e) {
        super.messageBoxClosed(e);
        this.getView().showMessage("messageBoxClosed");
    }
}
