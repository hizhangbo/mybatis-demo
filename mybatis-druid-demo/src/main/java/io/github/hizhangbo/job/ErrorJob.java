package io.github.hizhangbo.job;

import io.github.hizhangbo.entity.User;
import io.github.hizhangbo.mapper.UserAnnoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Bob
 * @since 2021/2/21 13:03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorJob {
    private final UserAnnoMapper userAnnoMapper;

    @Scheduled(cron = "* * * * * ?")
    public void error() {
        List<User> users = userAnnoMapper.getAll();
        log.error(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " => " + users);
    }
}
