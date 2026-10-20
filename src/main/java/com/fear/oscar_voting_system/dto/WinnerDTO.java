package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record WinnerDTO(@NotNull UUID nominationId) {}
