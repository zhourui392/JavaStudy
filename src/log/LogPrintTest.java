package log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhour on 2017/1/7.
 */
public class LogPrintTest {
    private static final Logger logger = LoggerFactory.getLogger(LogPrintTest.class);
    public static void main(String[] args) {
        Exception e = new NullPointerException("空指针");
        logger.debug("Exception:"+e);
        logger.debug("Exception:"+e.getMessage());
        logger.debug("Exception:",e);
    }
}
