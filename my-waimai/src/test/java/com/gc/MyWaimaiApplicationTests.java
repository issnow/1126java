package com.gc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MyWaimaiApplicationTests {

  @Test
  void contextLoads() {
    LocalDate l = LocalDate.now();
    System.out.println(l);//2024-08-07
    LocalDateTime l2 = LocalDateTime.now();
    System.out.println(l2);//2024-08-07T01:07:31.800
    DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String strDate = d.format(l2);
    System.out.println(strDate);//2024/08/07 01:07:31
  }

}
