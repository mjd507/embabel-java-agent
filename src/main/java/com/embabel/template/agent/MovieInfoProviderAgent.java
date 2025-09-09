package com.embabel.template.agent;

import java.time.LocalDate;
import java.util.List;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
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

	record MovieBasicInfo(String name, LocalDate releaseDate) {

	}

	record MovieDirector(String name) {

	}

	record MovieActors(List<String> actors) {

	}

	record Movie(MovieBasicInfo basicInfo, MovieDirector director, MovieActors actors) {

	}

	@AchievesGoal(description = "Provide movie information for the given movie name")
	@Action
	Movie getMovie(UserInput userInput, OperationContext context) {
		Movie movie = context.ai()
				.withDefaultLlm()
				.createObjectIfPossible("""
								Create a Movie from this user input, extracting their details: %s
								""".formatted(userInput.getContent()),
						Movie.class
				);
		log.info("Movie info: {}", movie);
		return movie;
	}

}
