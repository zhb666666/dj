package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdd.admin.service.IAccessRecordService;
import com.mdd.admin.service.IIndexService;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.entity.AccessRecord;
import com.mdd.common.entity.user.User;
import com.mdd.common.mapper.AccessRecordMapper;
import com.mdd.common.mapper.user.UserMapper;
import com.mdd.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 主页服务实现类
 */
@Service
public class IndexServiceImpl implements IIndexService {

    /**
     * 控制台数据
     *
     * @author fzr
     * @return Map<String, Object>
     */
    @Resource
    AccessRecordMapper accessRecordMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public Map<String, Object> console() {
        Map<String, Object> console = new LinkedHashMap<>();

        // 版本信息
        Map<String, Object> version = new LinkedHashMap<>();
        version.put("name", ConfigUtils.get("website", "name", "LikeAdmin-Java"));
        version.put("version", GlobalConfig.version);
        version.put("website", "www.likeadmin.cn");
        version.put("based", "Vue3.x、ElementUI、MySQL");
        Map<String, String> channel = new LinkedHashMap<>();
        channel.put("gitee", "https://gitee.com/likeadmin/likeadmin_java");
        channel.put("website", "https://www.likeadmin.cn");
        version.put("channel", channel);
        console.put("version", version);
        // 获取当前时间 并转为10位的时间戳
        long currentToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        // 在当前时间基础上加上一天减去 1秒
        long currentTomorrow = currentToday + 86400 - 1;
        QueryWrapper<AccessRecord> countTodayAccessRecordQuery = new QueryWrapper<AccessRecord>()
                .select("id")
                .between("create_time", currentToday, currentTomorrow);
        System.out.println("current:"+currentTomorrow);
        Long countTodayAccessRecord = accessRecordMapper.selectCount(countTodayAccessRecordQuery);

        QueryWrapper<AccessRecord> totalAccessRecordQuery = new QueryWrapper<AccessRecord>()
                .select("id");
        Long totalAccessRecord = accessRecordMapper.selectCount(totalAccessRecordQuery);

        Long totalUser = userMapper.selectCount(new QueryWrapper().select("id"));
        QueryWrapper<User> countTodayUserQuery = new QueryWrapper<User>()
                .select("id")
                .between("create_time", currentToday, currentTomorrow);
        Long countTodayUser = userMapper.selectCount(countTodayUserQuery);
        // 今日数据
        Map<String, Object> today = new LinkedHashMap<>();
        today.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        today.put("todayVisits", countTodayAccessRecord);   // 访问量(人)
        today.put("totalVisits", totalAccessRecord);  // 总访问量
        today.put("todaySales", 30);    // 销售额(元)
        today.put("totalSales", 65);    // 总销售额
        today.put("todayOrder", 12);    // 订单量(笔)
        today.put("totalOrder", 255);   // 总订单量
        today.put("todayUsers", countTodayUser);   // 新增用户
        today.put("totalUsers", totalUser);   // 总访用户
        console.put("today", today);

        // 访客图表
        ArrayList<Integer> visitorList = new ArrayList<>();
        List<String> visitorDates = TimeUtils.daysAgoDate(15);
        for (String date : visitorDates) {
            QueryWrapper<AccessRecord> countVisitorQuery = new QueryWrapper<AccessRecord>()
                    .select("id")
                    .between("create_time", TimeUtils.dateToTimestamp(date + " 00:00:00"), TimeUtils.dateToTimestamp(date + " 23:59:59"));
            Long countVisitor = accessRecordMapper.selectCount(countVisitorQuery);
            visitorList.add(Integer.parseInt(countVisitor.toString()));
        }
        Map<String, Object> visitor = new LinkedHashMap<>();
        visitor.put("date", visitorDates);
        visitor.put("list", visitorList);
        console.put("visitor", visitor);

        return console;
    }

    /**
     * 公共配置
     *
     * @author fzr
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> config() {
        Map<String, String> website   = ConfigUtils.get("website");
        String copyright = ConfigUtils.get("website", "copyright", "");

        String captchaStatus = YmlUtils.get("like.captcha.status");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("loginCaptcha", StringUtils.isNotNull(captchaStatus) && captchaStatus.equals("true"));
        map.put("webName", website.getOrDefault("name", ""));
        map.put("webLogo", UrlUtils.toAbsoluteUrl(website.getOrDefault("logo", "")));
        map.put("webFavicon", UrlUtils.toAbsoluteUrl(website.getOrDefault("favicon", "")));
        map.put("webBackdrop", UrlUtils.toAbsoluteUrl(website.getOrDefault("backdrop", "")));
        map.put("ossDomain", UrlUtils.domain());
        map.put("copyright", ListUtils.stringToListAsMapStr(copyright));

        return map;
    }

}
