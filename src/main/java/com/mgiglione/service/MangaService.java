package com.mgiglione.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriUtils;

import com.github.Doomsdayrs.Jikan4java.types.Main.Manga.Manga;
import com.mgiglione.model.MangaResult;

@Service
public class MangaService {

    Logger logger = LoggerFactory.getLogger(MangaService.class);
    private static final String MANGA_SEARCH_URL="https://api.jikan.moe/v3/search/manga/";

    @Autowired
    RestTemplate restTemplate;
    
    public List<Manga> getMangasByTitle(String title) {
        
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        
        URIBuilder uriBuilder = null;
        URI uri = null;
		try {
			uriBuilder = new URIBuilder(MANGA_SEARCH_URL);
			uriBuilder.addParameter("q", title);
			uriBuilder.addParameter("page", "1");
			uri = uriBuilder.build();
			System.out.println(uriBuilder.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        //MangaResult forObject = restTemplate.getForObject(MANGA_SEARCH_URL+title, MangaResult.class);
        //return forObject.getResult();
        ResponseEntity<MangaResult> temp = restTemplate.exchange(uri, HttpMethod.GET, entity, MangaResult.class);
        System.out.println(temp.getBody().getResults().size());
        return temp.getBody().getResults();
    }

}
