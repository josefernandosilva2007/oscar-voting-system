package com.fear.oscar_voting_system.dto;

public record CeremonyProgressDTO(int year, long categories, long resultsPublished, boolean votingOpen,
                                  boolean ceremonyStarted) {
}
