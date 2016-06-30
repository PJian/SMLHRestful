package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by peijia on 6/21/2016.
 */
public class ShellUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShellUtil.class);
    /**
     * 启动应用程序
     */
    public static boolean startApp() {

        Runtime rn = Runtime.getRuntime();
        Process p = null;
        String superMarketLH = System.getenv("SUPERMARKETLH_HOME");
        String locationCmd =superMarketLH+"SuperMarketLH.exe" ;
        try {
            p = rn.exec(locationCmd);
            LOGGER.info(String.format("SuperMarketLH.exe executed success! "));
            return true;
        } catch (Exception e) {
            System.out.println("Error exec!");
            LOGGER.info(locationCmd+" executed failed! ");
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static boolean stopApp(){
        String killCmd = "taskkill /f /im SuperMarketLH.exe";
        try {
            Process p = Runtime.getRuntime().exec(killCmd);
            LOGGER.info(String.format("SuperMarketLH.exe stop success! "));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info(String.format("SuperMarketLH.exe stop failed! "));
            return false;
        }

    }


}
