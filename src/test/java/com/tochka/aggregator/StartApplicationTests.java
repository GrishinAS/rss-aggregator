package com.tochka.aggregator;

import com.tochka.aggregator.service.impl.AggregatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartApplicationTests {

  @Autowired
  private AggregatorServiceImpl aggregatorService;


}
