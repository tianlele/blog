package com.tianll.blog.web.scheduleTask;

import com.tianll.blog.model.mapper.StatisticMapper;
import com.tianll.blog.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tianll
 * @date 2024/3/23
 * 定时任务
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticMapper statisticMapper;

    @Autowired
    private MailUtils mailUtils;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Value(value = "${spring.mail.username}")
    private String mailTo;

    @Scheduled(cron = "0 0 12 1 * ?")
    public void sendMail() {
        long totalVisit = statisticMapper.getTotalVisit();
        long totalComment = statisticMapper.getTotalComment();
        StringBuffer content = new StringBuffer();
        content.append("博客访问量：" + totalVisit).append("<br/>");
        content.append("博客评论数：" + totalComment).append("<br/>");
        mailUtils.sendMail(mailTo, "个人博客流量统计", content.toString());
    }
}
