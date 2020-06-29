package com.innteam.aggregator.rest;

import com.innteam.aggregator.model.ParsingRequest;
import com.innteam.aggregator.service.AggregatorService;
import com.innteam.aggregator.utils.RssHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class AggregatorController {

    private final AggregatorService aggregatorService;

    @Autowired
    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @PostMapping("/parseAddress") //https://news.yandex.ru/cyber_sport.rss
    public void parseAddress(@RequestBody ParsingRequest request) {
        try {
            SAXParserFactory sfactory = SAXParserFactory.newInstance();
            SAXParser saxParser = sfactory.newSAXParser();
            XMLReader xmlparser = saxParser.getXMLReader();
            //xmlparser.setContentHandler(new RssHandler());
            InputStream input = new URL("https://news.yandex.ru/cyber_sport.rss").openStream();
            saxParser.parse(input, new RssHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
