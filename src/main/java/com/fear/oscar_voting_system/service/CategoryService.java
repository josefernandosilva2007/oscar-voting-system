package com.fear.oscar_voting_system.service;
import com.fear.oscar_voting_system.dto.CategoryDTO;
import com.fear.oscar_voting_system.model.*;
import com.fear.oscar_voting_system.repository.*;
import com.fear.oscar_voting_system.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final NominationRepository nominationRepository;
    public CategoryModel saveCategory(CategoryDTO dto) {
        return categoryRepository.save(CategoryModel.builder().name(dto.name()).build());
    }
    @Transactional
    public void defineWinningNomination(UUID categoryId, UUID nominationId) {
        categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        NominationModel winner = nominationRepository.findById(nominationId)
            .orElseThrow(() -> new ResourceNotFoundException("Indicação não encontrada"));
        if (!winner.getCategory().getId().equals(categoryId))
            throw new BusinessException("A indicação não pertence à categoria");
        nominationRepository.findByCategory_IdAndYear(categoryId, winner.getYear())
            .forEach(n -> n.setIsWinner(n.getId().equals(nominationId)));
    }
    public List<CategoryModel> listAllCategories() { return categoryRepository.findAll(); }
}
