//package com.innteam.aggregator.service.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.innteam.aggregator.model.AggregatedCamera;
//import com.innteam.aggregator.model.SourceData;
//import org.junit.Assert;
//import org.junit.Before;
//import org.mockito.Mock;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class AggregatorServiceImplTest {
//  private AggregatorServiceImpl aggregatorService;
//
//  @Mock
//  private RestClient restClient = mock(RestClient.class);
//
//  @Before
//  public void init() {
//    aggregatorService = new AggregatorServiceImpl(restClient);
//  }
//
//  @org.junit.Test
//  public void getAggregatedData() throws IOException {
//    URL resource = getClass().getClassLoader().getResource("static/example.json");
//    ObjectMapper objectMapper= new ObjectMapper();
//    Camera[] cameras = objectMapper.readValue(resource, Camera[].class);
//    when(restClient.getCameras()).thenReturn(Arrays.asList(cameras));
//    for(Camera c : cameras){
//      //when(restClient.getToken(c.getTokenDataUrl())).thenReturn(new ParsingRequest("mockedValue", 111));
//      when(restClient.getSource(c.getSourceDataUrl())).thenReturn(new SourceData("mockedValue", "mockedValue"));
//    }
//
//    List<AggregatedCamera> aggregatedData = aggregatorService.getAggregatedData();
//    Assert.assertTrue(aggregatedData.size() > 0);
//  }
//}