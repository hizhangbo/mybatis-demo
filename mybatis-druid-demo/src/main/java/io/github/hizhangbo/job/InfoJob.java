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

@Slf4j
@Component
@RequiredArgsConstructor
public class InfoJob {
    private final UserAnnoMapper userAnnoMapper;

    @Scheduled(cron = "* * * * * ?")
    public void info() {
//        List<User> users = userAnnoMapper.getAll();
        log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " => ");
    }
}
