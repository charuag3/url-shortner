package com.url.shortner.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.url.shortner.data.repo.UrlRepo;
import com.url.shortner.entity.UrlEntity;

@Controller
public class UrlController {
	private static final String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
	@Autowired
	UrlRepo urlRepo;

	/**
	 * Welcome URL
	 * @return
	 */
	@GetMapping("/home")
	@ResponseBody
	public String sayHello() {
		return "This is home URL";
	}

	/**
	 * Creates a URL from longUrl in given UrlEntity. 
	 * @param urlEntity - should have only longUrl field in json
	 * @param request
	 * @return short url link
	 */
	@PostMapping("/createUrl")
	@ResponseBody
	public String createUrl(@RequestBody UrlEntity urlEntity,HttpServletRequest request) {
		String[] schemes = {"http", "https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		String longUrl = urlEntity.getLongUrl();
		if(longUrl!=null && urlValidator.isValid(longUrl)) {
			Long id =0L;
			Long maxId = urlRepo.findMaxId();
			if(maxId!=null) {
				id = maxId+1;
			}
			urlEntity.setShortUrl(base62Encode(id));
			urlEntity.setId(id);
			urlRepo.save(urlEntity);
			return "short url created: "+request.getRequestURL().toString().replace("createUrl", "")+urlEntity.getShortUrl();
		}else {
			logger.error("Bad request. Url is not valid");
			return "Bad request. Url is not valid";
		}
	}

	/**
	 * Redirects user to the longUrl associated with the short url
	 * @param shortUrl - short URL which need to be redirected
	 * @return redirection link to the longUrl. This will cause a page redirect
	 */
	@RequestMapping(value="/{shortUrl}")
	public String redirectShortUrl(@PathVariable("shortUrl") String shortUrl) {
		long id = decode(shortUrl);
		Optional<UrlEntity> urlEntity = urlRepo.findById(id);
		if(urlEntity.isPresent()) {
			return "redirect:"+urlEntity.get().getLongUrl();
		}else {
			return "error";
		}
	}

	/**
	 * encodes an ID to a short URL
	 * @param value
	 * @return
	 */
	private String base62Encode(long value) {
		StringBuilder sb = new StringBuilder();
		while (value != 0) {
			sb.append(base62.charAt((int)(value % 62)));
			value /= 62;
		}
		while (sb.length() < 6) {
			sb.append(0);
		}
		return sb.reverse().toString();
	}
	/**
	 * Decodes a shortened URL to id of longUrl.
	 * @param shortUrl
	 * @return id of longUrl
	 */
	private static long decode(String shortUrl) {
		String base62Encoded = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
		long decode = 0;
		for(int i = 0; i < base62Encoded.length(); i++) {
			decode = decode * 62 + base62.indexOf("" + base62Encoded.charAt(i));
		}
		return decode;
	}

}
