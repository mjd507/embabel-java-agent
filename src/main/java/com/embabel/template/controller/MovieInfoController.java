package com.embabel.template.controller;

import com.embabel.agent.api.common.Ai;
import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.template.model.Movie;
import com.embabel.template.model.MovieRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieInfoController {

	private final AgentPlatform agentPlatform;

	private final Ai ai;

	public MovieInfoController(AgentPlatform agentPlatform, Ai ai) {
		this.agentPlatform = agentPlatform;
		this.ai = ai;
	}

	@PostMapping("movie/info")
	public Movie getMovieInfo(@RequestBody MovieRequest movieRequest) {
		AgentInvocation<Movie> invocation = AgentInvocation.create(agentPlatform, Movie.class);
		return invocation.invoke(movieRequest);
	}

	@PostMapping("movie/info2")
	public Movie getMovieInfo2(@RequestBody MovieRequest movieRequest) {
		return ai
				.withDefaultLlm()
				.createObjectIfPossible("""
								Create a Movie from the given name: %s
								""".formatted(movieRequest.request()),
						Movie.class);
	}

}
