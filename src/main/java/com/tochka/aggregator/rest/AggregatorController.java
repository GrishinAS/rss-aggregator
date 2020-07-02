package com.tochka.aggregator.rest;

import com.tochka.aggregator.model.ParsingRequest;
import com.tochka.aggregator.model.dao.RssCrudService;
import com.tochka.aggregator.model.dao.RssItem;
import com.tochka.aggregator.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AggregatorController {

  private final AggregatorService aggregatorService;
  private final RssCrudService rssCrudService;

  @Autowired
  public AggregatorController(AggregatorService aggregatorService, RssCrudService rssCrudService) {
    this.aggregatorService = aggregatorService;
    this.rssCrudService = rssCrudService;
  }

  @PostMapping("/parseAddress")
  public List<Integer> parseAddress(@RequestBody ParsingRequest request) {
    List<Integer> integers = new ArrayList<>();
    List<RssItem> aggregatedData = aggregatorService.getAggregatedData(request);
    for (RssItem item : aggregatedData) {
      integers.add(rssCrudService.create(item));
    }
    return integers;
  }
}
