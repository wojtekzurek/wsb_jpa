package com.jpacourse.service;

import com.jpacourse.dto.VisitTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    @Transactional
    public void testShouldGetVisitByPatientId()
    {
        //given
        Long targetPatientId = 3L;
        int targetVisitsCount = 2;

        //when
        List<VisitTO> visitsList = visitService.getVisitByPatientId(targetPatientId);

        //then
        assertThat(visitsList.size()).isEqualTo(targetVisitsCount);
        assertThat(visitsList.get(0).getLocalDateTime()).isEqualTo(
                LocalDateTime.of(2022, 11, 30, 11, 35, 0)
        );
        assertThat(visitsList.get(1).getLocalDateTime()).isEqualTo(
                LocalDateTime.of(2024, 12, 30, 11, 35, 0)
        );
    }
}
