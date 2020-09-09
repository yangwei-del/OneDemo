package com.file.record;

import kd.bos.base.BaseShowParameter;
import kd.bos.dataentity.entity.DynamicObject;
import kd.bos.dataentity.entity.DynamicObjectCollection;
import kd.bos.dataentity.entity.LocaleString;
import kd.bos.entity.tree.TreeNode;
import kd.bos.form.ShowType;
import kd.bos.form.StyleCss;
import kd.bos.form.control.events.TreeNodeEvent;
import kd.bos.form.field.ComboItem;
import kd.bos.list.ListFilterParameter;
import kd.bos.list.ListShowParameter;
import kd.bos.orm.query.QFilter;
import kd.bos.servicehelper.BusinessDataServiceHelper;
import kd.bos.servicehelper.QueryServiceHelper;
import kd.taxc.common.plugin.AbstractTreePlugin;

import java.util.ArrayList;
import java.util.List;

public class FileCollectionImpl extends AbstractTreePlugin {
    public void addAllTreeNode(TreeNode treeNode) {
        //拿到当前树节点ID
        String currentId = treeNode.getId();
        //查询内容
        String fiedls = "number,code,name";
        //结果排序方式
        String orderby = "code asc";
        //where后接的查询条件
        QFilter filter = QFilter.of("code=?", currentId);
        //封装成一个查询条件数组
        QFilter[] filters = new QFilter[]{filter};
        //查询操作放在try里面，报错不执行里面的
        DynamicObject[] bhr_jiedianliebiao = BusinessDataServiceHelper.load("bhr_jiedianliebiao", fiedls, filters, orderby);
        for (int i = 0; i < bhr_jiedianliebiao.length; i++) {
            DynamicObject dynamicObject = bhr_jiedianliebiao[i];
            //创建一个节点对象
            TreeNode node = new TreeNode();
            node.setText(dynamicObject.getString("name"));
            node.setParentid(currentId);
            node.setId(dynamicObject.getString("number"));
            //给参数节点添加子节点
            treeNode.addChild(node);
            //以当前节点作为参数自己调用自己实现迭代
            this.addAllTreeNode(node);
        }

    }

    public List<TreeNode> addTreeNode(String currentId) {
        //创建一个节点集合对象
        List<TreeNode> data = new ArrayList<>();
        //查询内容
        String fiedls = "number,code,name";
        //结果排序方式
        String orderby = "code asc";
        //where后接的查询条件
        QFilter filter = QFilter.of("code=?", currentId);
        //封装成一个查询条件数组
        QFilter[] filters = new QFilter[]{filter};
        //查询操作放在try里面，报错不执行里面的
        DynamicObject[] bhr_jiedianliebiao = BusinessDataServiceHelper.load("bhr_jiedianliebiao", fiedls, filters, orderby);
        for (int i = 0; i < bhr_jiedianliebiao.length; i++) {
            DynamicObject dynamicObject = bhr_jiedianliebiao[i];
            //创建一个节点对象
            TreeNode node = new TreeNode();
            node.setText(dynamicObject.getString("name"));
            node.setId(dynamicObject.getString("number"));
            data.add(node);
        }
        return data;
    }

    public TreeNode queryTreeNode(Object returnData) {
        //查询内容
        String fiedls = "number,code,name";
        //结果排序方式
        String orderby = "code asc";
        //where后接的查询条件
        QFilter filter = QFilter.of("number=?", returnData);
        //封装成一个查询条件数组
        QFilter[] filters = new QFilter[]{filter};
        //查询操作放在try里面，报错不执行里面的
        DynamicObject[] kded_adds = BusinessDataServiceHelper.load("bhr_jiedianliebiao", fiedls, filters, orderby);
        TreeNode node = new TreeNode();
        node.setText(kded_adds[0].getString("name"));
        node.setId(kded_adds[0].getString("number"));
        node.setParentid(kded_adds[0].getString("code"));
        return node;
    }

    public BaseShowParameter formShowParameter() {
        BaseShowParameter formShowParameter = new BaseShowParameter();
        formShowParameter.setFormId("bhr_jiedianliebiao");
        formShowParameter.getOpenStyle().setShowType(ShowType.Modal);
        StyleCss styleCss = new StyleCss();
        styleCss.setWidth("800px");
        styleCss.setHeight("600px");
        formShowParameter.getOpenStyle().setInlineStyleCss(styleCss);
//        formShowParameter.setAppId("bhr_shoujizhengbian");
        return formShowParameter;
    }

    public List<ComboItem> treeNodeClick() {
        List<ComboItem> data = new ArrayList<>();
        //查询内容
        String fiedls = "number,code,name";
        //结果排序方式
        String orderby = "code asc";
        //where后接的查询条件
        QFilter[] filter = new QFilter[]{};
        //封装成一个查询条件数组
        QFilter[] filters = new QFilter[]{};
        //查询操作放在try里面，报错不执行里面的
        DynamicObject[] bhr_jiedianliebiao = BusinessDataServiceHelper.load("bhr_jiedianliebiao ", fiedls, filters, orderby);
        for (int i = 0; i < bhr_jiedianliebiao.length; i++) {
            DynamicObject dynamicObject = bhr_jiedianliebiao[i];
            ComboItem comboItem1 = new ComboItem();
            LocaleString localeString = new LocaleString();
            localeString.setLocaleValue(dynamicObject.getString("name"));
            comboItem1.setCaption(localeString);
            comboItem1.setId(dynamicObject.getString("code"));
            comboItem1.setValue(dynamicObject.getString("number"));
            data.add(comboItem1);
        }
        return data;
    }

    //下拉
    public List<ComboItem> ComboItem() {
        DynamicObject[] objects = BusinessDataServiceHelper.load("bhr_xiala", "number,name", null);
        List<ComboItem> list = new ArrayList<>();
        for (DynamicObject data : objects) {
            ComboItem comboItem = new ComboItem();
            LocaleString localeString = new LocaleString();
            localeString.setLocaleValue(data.getString("name"));
            comboItem.setCaption(localeString);
            comboItem.setValue(data.getString("number"));
            list.add(comboItem);
        }
        return list;
    }
}