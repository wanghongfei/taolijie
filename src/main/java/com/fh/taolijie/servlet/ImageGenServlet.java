package com.fh.taolijie.servlet;

import com.fh.taolijie.domain.job.JobPostModel;
import com.fh.taolijie.domain.sh.SHPostModel;
import com.fh.taolijie.service.job.JobPostService;
import com.fh.taolijie.service.sh.ShPostService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by whf on 7/9/15.
 */
public class ImageGenServlet extends HttpServlet implements ApplicationContextAware {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static ApplicationContext applicationContext;


    // 验证码图片的宽度。
    private int width = 180;

    // 验证码图片的高度。
    private int height = 20;

    // 验证码字符个数
    private int codeCount = 11;

    private int x = 0;

    // 字体高度
    private int fontHeight;

    private int codeY;

    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };


    public void init() throws ServletException {
        // 从web.xml中获取初始信息
        // 宽度
        String strWidth = this.getInitParameter("width");
        // 高度
        String strHeight = this.getInitParameter("height");
        // 字符个数
        String strCodeCount = this.getInitParameter("codeCount");
        // System.out.println("strWidth==" + strWidth + ", strHeight==" + strHeight
        // + ", strCodeCount==" + strCodeCount);
        // 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
        }
        x = width / (codeCount + 1);
        fontHeight = height;
        codeY = height;
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
        String job = req.getParameter("jobId");
        String sh = req.getParameter("shId");

        String phoneNumber = null;
        if (null != job) {
            phoneNumber = getJobPhone(Integer.valueOf(job));
        } else if (null != sh) {
            phoneNumber = getShNumber(Integer.valueOf(sh));
        } else {
            return;
        }



        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 将图像填充为白色
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("黑体", Font.BOLD, fontHeight-5);
        // 设置字体。
        g.setFont(font);

        // 随机产生450个干扰点，使图象中的认证码不易被其它程序探测到。
        g.setColor(getRandColor(120,200));
/*        for(int i=0;i<50;i++){
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            g.drawOval(x,y,0,0);
        }*/

        for (int ix = 0 ; ix < phoneNumber.length() ; ++ix) {
            g.drawString(phoneNumber.charAt(ix) + "", (ix + 1) * x - 7, codeY - 5);
        }


        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }

    /**
     * 产生随机颜色
     *
     * @param num1
     * @param num2
     * @return Color
     */
    public static Color getRandColor(int num1, int num2) {
        Random random = new Random();
        if (num1 > 255)
            num1 = 255;
        if (num2 > 255)
            num2 = 255;
        int r = num1 + random.nextInt(num2 - num1);
        int g = num1 + random.nextInt(num2 - num1);
        int b = num1 + random.nextInt(num2 - num1);
        return new Color(r, g, b);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    private String getJobPhone(Integer jobId) {
        JobPostService jobService = (JobPostService) applicationContext.getBean("defaultJobPostService");
        JobPostModel job = jobService.findJobPost(jobId);
        String phone = job.getContactPhone();

        return phone;
    }

    private String getShNumber(Integer shId) {
        ShPostService shService = (ShPostService) applicationContext.getBean("defaultShPostService");
        SHPostModel sh = shService.findPost(shId);
        return sh.getContactPhone();
    }
}
