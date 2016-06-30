package ws;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.glass.ui.SystemClipboard;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ShellUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

/**
 * Created by peijia on 6/21/2016.
 */
@Path("/supermarket")
public class SuperMarketLHWS {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuperMarketLHWS.class);
    @Path("/connect")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response connect(){
        return  Response.ok("OK").build();
    }
    @Path("/start")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response start(){

        boolean flag = ShellUtil.startApp();
        return flag? Response.ok("OK").build():Response.ok("Failed").build();
    }


    @Path("/stop")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response stop(){
        boolean flag = ShellUtil.stopApp();
        return flag? Response.ok("OK").build():Response.ok("Failed").build();
    }

    @Path("/file")
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response file(FormDataMultiPart form)
    {
        //获取文件流
        FormDataBodyPart filePart = form.getField("file");
        //获取表单的其他数据
        FormDataBodyPart fileNamePart = form.getField("fileName");
        InputStream fileInputStream = filePart.getValueAs(InputStream.class);
        String fileName = fileNamePart.getValueAs(String.class);
        return  saveFile(fileName,fileInputStream)?Response.ok("OK").build():Response.ok("Failed").build();
    }

    @Path("/upload")
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    public Response file1(@FormDataParam("file") InputStream fileInputStream,
                          @FormDataParam("file") FormDataContentDisposition disposition,
                          @FormDataParam("fileName") String fileName)
    {
        return  saveFile(fileName,fileInputStream)?Response.ok("OK").build():Response.ok("Failed").build();
    }

    private boolean saveFile(String fileName,InputStream inputStream){
        String superMarketLH = System.getenv("SUPERMARKETLH_HOME");
        String saveFileName = superMarketLH+ fileName;
        LOGGER.info("save file name:"+saveFileName);
        File file = new File(saveFileName);
        try {
            FileUtils.copyInputStreamToFile(inputStream,file);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("fail save failed:"+saveFileName);
            LOGGER.info(e.getMessage());
            return  false;
        }
        return true;
    }

    /**
     * 取得tomcat之前的路径
     * @return
     */
    private String getTomcatPathPrefix(String path){
        return path.substring(0,path.indexOf("tomcat"));
    }
}
