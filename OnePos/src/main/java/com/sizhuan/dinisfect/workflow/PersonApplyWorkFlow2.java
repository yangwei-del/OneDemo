package com.sizhuan.dinisfect.workflow;

import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.bos.workflow.api.AgentExecution;
import kd.bos.workflow.component.approvalrecord.IApprovalRecordItem;
import kd.bos.workflow.engine.extitf.IWorkflowPlugin;

import java.util.List;

/**
 * @author zenghuogang
 * @create 2020/8/20
 */
public class PersonApplyWorkFlow2 implements IWorkflowPlugin {
    @Override
    public List<Long> calcUserIds(AgentExecution execution) {
        return null;
    }

    @Override
    public List<Long> calcUserIds(DynamicObject pme, String entityNumber, String businessKey) {
        return null;
    }

    @Override
    public boolean hasTrueCondition(AgentExecution execution) {
        return false;
    }

    @Override
    public void notify(AgentExecution execution) {

        DynamicObject dynamicObject= BusinessDataServiceHelper.loadSingle(execution.getBusinessKey(),"kded_person_apply");
        dynamicObject.set("billstatus","C");
        SaveServiceHelper.save(new DynamicObject[]{dynamicObject});
    }

    @Override
    public void notifyByWithdraw(AgentExecution execution) {
        System.out.println(execution.getBusinessKey());
    }

    @Override
    public IApprovalRecordItem formatFlowRecord(IApprovalRecordItem item) {
        return null;
    }

    @Override
    public String parseBillSubject(String subject, AgentExecution execution, String lang) {
        return null;
    }

    @Override
    public String parseBillSubject(AgentExecution execution, String lang) {
        return null;
    }

}
