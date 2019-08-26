package com.url.shortner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="url_entity")
@Table(name="url_entity")
public class UrlEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique=true, nullable = false)
	long id;
	String shortUrl;
	String longUrl;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	@Override
	public String toString() {
		return "URLEntity [id=" + id + ", shortUrl=" + shortUrl + ", longUrl=" + longUrl + "]";
	}
}
