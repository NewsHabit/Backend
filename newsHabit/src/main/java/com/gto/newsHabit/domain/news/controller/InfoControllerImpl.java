package com.gto.newsHabit.domain.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoControllerImpl implements InfoController {
	@GetMapping("/")
	public String newsHabit() {
		return "NewsHabit";
	}
}
