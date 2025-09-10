package com.embabel.template.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.template.model.Movie;
import com.embabel.template.model.MovieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Profile;

@Agent(name = "movie-info-provider",
		description = "Provide movie information",
		version = "1.0.0",
		beanName = "movieInfoProviderAgent")
@Profile("!test")
class MovieInfoProviderAgent {

	private static final Logger log = LoggerFactory.getLogger(MovieInfoProviderAgent.class);

	@Action
	Movie.MovieBasicInfo getMovieBasicInfo(MovieRequest movieRequest, OperationContext context) {
		Movie.MovieBasicInfo movieBasicInfo = context.ai()
				.withDefaultLlm()
				.createObjectIfPossible("""
								Get Movie basic info from the given request: %s
								""".formatted(movieRequest.request()),
						Movie.MovieBasicInfo.class);
		log.info("Movie basic info:{}", movieBasicInfo);
		return movieBasicInfo;
	}

	@Action
	Movie.MovieDirector getMovieDirector(Movie.MovieBasicInfo movieBasicInfo, OperationContext context) {
		Movie.MovieDirector movieDirector = context.ai().withDefaultLlm()
				.createObjectIfPossible("""
								Get Movie director from the given movie name:%s
								""".formatted(movieBasicInfo.name()),
						Movie.MovieDirector.class);
		log.info("Movie director:{}", movieDirector);
		return movieDirector;
	}

	@Action
	Movie.MovieActors getMovieActors(Movie.MovieBasicInfo movieBasicInfo, OperationContext context) {
		Movie.MovieActors movieActors = context.ai().withDefaultLlm()
				.createObjectIfPossible("""
								Get Movie actors from the given movie name:%s
								""".formatted(movieBasicInfo.name()),
						Movie.MovieActors.class);
		log.info("Movie actors:{}", movieActors);
		return movieActors;
	}

	@AchievesGoal(description = "Provide movie information for the given movie name")
	@Action
	Movie getMovie(Movie.MovieBasicInfo movieBasicInfo, Movie.MovieDirector movieDirector,
			Movie.MovieActors movieActors) {
		return new Movie(movieBasicInfo, movieDirector, movieActors);
	}

}
