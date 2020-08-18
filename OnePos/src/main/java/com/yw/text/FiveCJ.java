package com.yw.text;

import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.entity.ExtendedDataEntity;
import kd.bos.entity.validate.AbstractValidator;

public class FiveCJ extends AbstractValidator {

    /**
     * 预计送货日期字段标识
     */
    public final static String KEY_DELIVERYDATE = "deliverydate";
    /**
     * 最迟送货日期字段标识
     */
    public final static String KEY_LASTDATE = "lastdate";

    /**
     * 返回校验器的主实体：系统将自动对此实体数据，逐行进行校验
     */
    @Override
    public String getEntityKey() {
        return this.entityKey;
    }

    /**
     * 给校验器传入上下文环境及单据数据包之后，调用此方法；
     *
     * @remark 自定义校验器，可以在此事件进行本地变量初始化：如确认需要校验的主实体
     */
    @Override
    public void initializeConfiguration() {
        super.initializeConfiguration();
        // 在此方法中，确认校验器检验的主实体：送货子单据体
        // 需要对送货子单据体行，逐行判断预计送货日期
        this.entityKey = "subentryentity";
    }

    @Override
    public void initialize(){
        super.initialize();
    }
    @Override
    public void validate() {
        //遍历单据校验
//        for (ExtendedDataEntity dataEntity : dataEntities) {
//            DynamicObjectCollection entryentity = (DynamicObjectCollection) dataEntity.getValue("entryentity");
//            //....
//            //if()
//            //addWarningMessage addFatalErrorMessage addMessage
//            addErrorMessage(dataEntity, "my error message");
//            return;
//            //else
//        }
    }
}