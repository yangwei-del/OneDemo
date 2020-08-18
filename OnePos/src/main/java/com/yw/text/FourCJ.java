package com.yw.text;

import kd.bos.list.plugin.AbstractTreeListPlugin;
import kd.bos.orm.query.QFilter;

import java.util.EventObject;

public class FourCJ extends AbstractTreeListPlugin {

    @Override
    public void initializeTree(EventObject e) {
        super.initializeTree(e);

        // 根节点是否显示
        this.getTreeModel().setRootVisable(true);

        if (this.getTreeModel().getGroupProp() != null){

            // 分组节点取数条件：
            // 只有单据有分组字段，分组节点由系统自动读取、构建时，才会用到这个条件
            this.getTreeModel().getTreeFilter().add(new QFilter("number", "like", "%abc%"));

            // 分组节点内容格式化
            this.getTreeModel().setTextFormat("名称{name}(编码{code})");

            // 分组节点取数级次 (暂未发现在何处使用到这个属性)
            this.getTreeModel().setDefaultQueryLevel(5);
        }
    }
}