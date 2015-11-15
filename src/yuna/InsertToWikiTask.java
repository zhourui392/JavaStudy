package yuna;

import java.util.concurrent.TimeUnit;

/**
 * Created by ruizhou on 15/10/24.
 */
public class InsertToWikiTask implements Runnable{

    private ExchangeEmailShallowDTO mail;

    public  InsertToWikiTask(ExchangeEmailShallowDTO mail){
        this.mail = mail;
    }

    @Override
    public void run() {
        System.out.print("插入数据到WiKi");
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
