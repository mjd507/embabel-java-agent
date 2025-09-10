package com.embabel.template.model;

import java.time.LocalDate;
import java.util.List;

public record Movie(MovieBasicInfo basicInfo, MovieDirector director, MovieActors actors) {

	public record MovieBasicInfo(String name, LocalDate releaseDate) {

	}

	public record MovieDirector(String name) {

	}

	public record MovieActors(List<String> actors) {

	}

}

