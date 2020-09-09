package com.sizhuan.dinisfect.task;

import kd.bos.context.RequestContext;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.exception.KDBizException;
import kd.bos.exception.KDException;
import kd.bos.message.api.EmailInfo;
import kd.bos.orm.query.QCP;
import kd.bos.orm.query.QFilter;
import kd.bos.schedule.executor.AbstractTask;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.message.MessageServiceHelper;
import kd.bos.servicehelper.operation.SaveServiceHelper;
import kd.bos.servicehelper.workflow.MessageCenterServiceHelper;
import kd.bos.workflow.engine.msg.info.MessageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zenghuogang
 * @create 2020/8/20
 */
public class DinisfectTaskTip extends AbstractTask {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void execute(RequestContext requestContext, Map<String, Object> map) throws KDException {
        String tablename="kded_person_apply";
        String selectProperties="id,billno,billstatus,kded_datefield,kded_userfield,kded_orgfield1";
        QFilter qFilter=new QFilter("billstatus", QCP.equals,"C");


        //当前时间去掉时分秒
        Date date=new Date();
        String cuurentTime= sdf.format(date);
        Date date1= null;
        try {
            date1= sdf.parse(cuurentTime);
        }catch (ParseException e){
            throw new KDBizException("时间转换异常");
        }


        DynamicObject[] dynamicObjects= BusinessDataServiceHelper.load(tablename,selectProperties,new QFilter[]{qFilter});
        for (int i = 0; i <dynamicObjects.length ; i++) {
            DynamicObject dynamicObject=dynamicObjects[i];
            Date applyTime=dynamicObject.getDate("kded_datefield");
            if(applyTime==null){
                continue;
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(applyTime);
            //增加一天
            calendar.add(Calendar.DATE,1);
            applyTime=calendar.getTime();
            //申请时间加一天大于当前时间热送短信
//            if(applyTime.compareTo(date1)>0){
//
//                continue;
//            }
            DynamicObject applyPerson= dynamicObject.getDynamicObject("kded_userfield");

            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setTitle("金蝶云苍穹");
            emailInfo.setContent("亲爱的棕熊工厂员工"+applyPerson.getString("name")+"，你所提交的"+dynamicObject.getDate("kded_datefield")+"时间进入"+dynamicObject.getDynamicObject("kded_orgfield1").getString("name")+"车间的申请单已超期并自动关闭，如还需进入车间，请重新发起申请！");
            emailInfo.setReceiver(new ArrayList<>());
            emailInfo.getReceiver().add( applyPerson.getString("email"));
            MessageServiceHelper.sendEmail(emailInfo);


            MessageInfo messageInfo=new MessageInfo();
            messageInfo.setType(MessageInfo.TYPE_ALARM);
            messageInfo.setSenderId(Long.valueOf("870950586781007872"));
            messageInfo.setSenderName("sender");
            messageInfo.setTitle("消息中心来信！");
            messageInfo.setContent("亲爱的棕熊工厂员工"+applyPerson.getString("name")+"，你所提交的"+dynamicObject.getDate("kded_datefield")+"时间进入"+dynamicObject.getDynamicObject("kded_orgfield1").getString("name")+"的申请单已超期并自动关闭，如还需进入车间，请重新发起申请！");
            List<Long> userIds=new ArrayList<>();
            userIds.add(applyPerson.getLong("id"));
            messageInfo.setUserIds(userIds);
            messageInfo.setEntityNumber("kded_person_apply");
            messageInfo.setOperation("view");
            messageInfo.setBizDataId(UUID.randomUUID().getLeastSignificantBits());
            messageInfo.setTag("重要,必读");
            Long result= MessageCenterServiceHelper.sendMessage(messageInfo);

            //作废
            dynamicObject.set("billstatus","G");
            SaveServiceHelper.save(new DynamicObject[]{dynamicObject});
        }

        //开始比较

    }
}
