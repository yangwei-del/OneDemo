package com.yw.zidingyi;

import kd.bos.algo.DataSet;
import kd.bos.algo.Row;
import kd.bos.db.DB;
import kd.bos.db.DBRoute;
import kd.bos.orm.ORM;
import kd.bos.orm.impl.ORMImpl;
import org.junit.Test;

public class ShiLi {

    @Test
    public void hashCodes() {
//        DB.query("t_dangan","name,code","123","11");

        String algoKey = getClass().getName() + ".query_resume";
        String sql = "select number, name from t_dangan where number=?";
        Object[] params = { 'C' };

        try (DataSet ds = DB.queryDataSet(algoKey, DBRoute.of("hr"), sql, params)) {
            while (ds.hasNext()) {
                Row row = ds.next();
                System.out.println("fnumber=" + row.get(0));
            }
        }

        ORM orm = ORM.create();
        ORM orm1 = new ORMImpl();
    }
}
