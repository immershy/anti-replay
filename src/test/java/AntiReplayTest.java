import com.cmcc.arp.RequestPlayer;
import com.cmcc.arp.configuration.AntiReplayConfiguration;
import com.cmcc.arp.filter.AntiReplayFilter;
import com.cmcc.arp.handler.InMemoryWindowRequestStore;
import com.cmcc.arp.handler.RequestStore;
import com.cmcc.arp.handler.ServletErrorHandler;
import com.cmcc.arp.util.Config;
import com.cmcc.arp.util.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AntiReplayConfiguration.class})
public class AntiReplayTest {

    @Autowired
    private RequestPlayer requestPlayer;

    @Autowired
    private AntiReplayFilter antiReplayFilter;



    @Test
    public void testReplay() {
        requestPlayer.play("aaa");
        System.out.println(requestPlayer.hasPlayed("aaa"));
        System.out.println(requestPlayer.hasPlayed("bbb"));
    }


    /**
     * 测试签名错误
     *
     * @throws Exception
     */
    @Test
    public void testInvalidSign() throws Exception {
        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("bparam", "aaa");
        request.addParameter("cparam", "bbb");
        request.addParameter("aparam", "ccc");

        MockHttpServletResponse response = new MockHttpServletResponse();

        antiReplayFilter.doFilter(request, response, mockFilterChain);

        // 签名错误
        String respString = response.getContentAsString();
        assertTrue(respString.contains(ServletErrorHandler.INVALID_SIGN));
        System.out.println(respString);
    }

    /**
     * ok
     *
     * @throws Exception
     */
    @Test
    public void testFilter() throws Exception {

        String path = "/path/to/resource";

        MockFilterChain mockFilterChain = new MockFilterChain();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI(path);

        request.addParameter("bparam", "aaa");
        request.addParameter("cparam", "bbb");
        request.addParameter("aparam", "ccc");

        String strToBeEncoded = "aparamcccbparamaaacparambbb" + path;
        String sign = MD5.sign(strToBeEncoded, Config.private_key, Config.input_charset);

        request.addParameter("sign", sign);


        MockHttpServletResponse response = new MockHttpServletResponse();

        antiReplayFilter.doFilter(request, response, mockFilterChain);

        // 签名正确
        String respString = response.getContentAsString();
        System.out.println("签名正确:" + respString);

        // 重复请求
        MockFilterChain mockFilterChain1 = new MockFilterChain();
        antiReplayFilter.doFilter(request, response, mockFilterChain1);
        respString = response.getContentAsString();
        System.out.println("重复请求:" + respString);
        //assertTrue(respString.contains(ServletErrorHandler.REPLAY_ERROR));

        Thread.sleep(2000);
        MockFilterChain mockFilterChain2 = new MockFilterChain();
        antiReplayFilter.doFilter(request, response, mockFilterChain2);
        respString = response.getContentAsString();
        System.out.println("重复请求:" + respString);

    }
}
