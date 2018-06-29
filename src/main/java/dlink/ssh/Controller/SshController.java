package dlink.ssh.Controller;


import dlink.ssh.common.ShhCommand;
import dlink.ssh.common.ShhCommandQueue;
import dlink.ssh.common.CommandResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by 91680 on 2018.6.12.
 *
 *SSH服务接口
 */
@RestController
public class SshController {
    private static final int waitFrequency=30;
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SshController.class);
    @RequestMapping(value = "/sshCommand", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateBlockSizeValue(@RequestBody ShhCommand input){
        Map<String, String> map=new HashMap<String, String>();
        try {
            ShhCommandQueue.getShhCommandQueue().produce(input);
            //延时等待3秒钟
            int waits=0;
            while (CommandResults.consume(input.getTaskId())==null && waits<waitFrequency){
                Thread.sleep(100);
                waits++;
            }
            if(waits>=waitFrequency){
                map.put("code", "200");
            }else{
                Future fs= CommandResults.consume(input.getTaskId());
                map.put("codes", fs.get().toString());
                map.put("code", "100");
                CommandResults.remove(input.getTaskId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            map.put("code", "200");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView index(HttpServletResponse response,
                              HttpServletRequest request) throws MalformedURLException {
        return new ModelAndView("index");

    }
}