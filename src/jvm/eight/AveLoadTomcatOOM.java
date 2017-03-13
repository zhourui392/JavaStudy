package jvm.eight;


import org.netbeans.lib.profiler.heap.HeapFactory;
import org.netbeans.modules.profiler.oql.engine.api.OQLEngine;
import org.netbeans.modules.profiler.oql.engine.api.OQLException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zhour on 2017/3/13.
 */
public class AveLoadTomcatOOM {
    public static final String dumpFilePath="C:\\Users\\zhour\\Desktop\\tomcat.hprof";
    public static void main(String[] args) throws IOException, OQLException {
        OQLEngine engine;
        final List<Double> creationTimes=new ArrayList<Double>(10000);
        engine=new OQLEngine(HeapFactory.createHeap(new File(dumpFilePath)));
        String query="select s.creationTime from org.apache.catalina.session.StandardSession s";
        engine.executeQuery(query, (Object obj)->{
            creationTimes.add((Double)obj);
            return false;
        });
        Collections.sort(creationTimes);
        int maxCount = 0;
        double maxCountTime = 0;
        for (int i = 0; i< creationTimes.size(); i++){
            int count = 0;
            for(int j = i; j < creationTimes.size(); j++){
                if (creationTimes.get(j) - creationTimes.get(i) <= 1000){
                    count++;
                }
            }
            if (count > maxCount){
                maxCount = count;
                maxCountTime = creationTimes.get(i);
            }
        }
        System.out.println("最大负载请求数："+maxCount+"次/秒,时间为："+new Date(Double.doubleToLongBits(maxCountTime)));
    }
}
