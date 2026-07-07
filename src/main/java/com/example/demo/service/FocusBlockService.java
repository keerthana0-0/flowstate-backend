package com.example.demo.service;

import com.example.demo.entity.FocusBlockEntity;
import com.example.demo.entity.FocusBlockEntity.BlockStatus;
import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FocusBlockRepository;
import com.example.demo.repository.FocusSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FocusBlockService {

    @Autowired
    private FocusBlockRepository focusBlockRepository;

    @Autowired
    private FocusSessionRepository focusSessionRepository;

    public FocusBlockEntity createFocusBlock(Long sessionId, FocusBlockEntity block) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Session not found with id : " + sessionId));

        if (block.getEndTime().isBefore(block.getStartTime())) {
            throw new BusinessValidationException("End Time cannot be before Start Time.");
        }

        block.setFocusSession(session);

        return focusBlockRepository.save(block);
    }

    public List<FocusBlockEntity> getAllFocusBlocks() {

        return focusBlockRepository.findAll();
    }

    public FocusBlockEntity getFocusBlockById(Long id) {

        return focusBlockRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Block not found with id : " + id));
    }

    public List<FocusBlockEntity> getBlocksBySession(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Session not found with id : " + sessionId));

        return focusBlockRepository.findByFocusSession(session);
    }

    public List<FocusBlockEntity> getBlocksByStatus(BlockStatus status) {

        return focusBlockRepository.findByStatus(status);
    }

    public FocusBlockEntity updateFocusBlock(Long id, FocusBlockEntity updatedBlock) {

        FocusBlockEntity existingBlock = focusBlockRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Block not found with id : " + id));

        if (updatedBlock.getEndTime().isBefore(updatedBlock.getStartTime())) {
            throw new BusinessValidationException("End Time cannot be before Start Time.");
        }

        existingBlock.setBlockName(updatedBlock.getBlockName());
        existingBlock.setStartTime(updatedBlock.getStartTime());
        existingBlock.setEndTime(updatedBlock.getEndTime());
        existingBlock.setStatus(updatedBlock.getStatus());
        existingBlock.setRemarks(updatedBlock.getRemarks());

        return focusBlockRepository.save(existingBlock);
    }

    public FocusBlockEntity completeFocusBlock(Long id) {

        FocusBlockEntity block = focusBlockRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Block not found with id : " + id));

        block.setStatus(BlockStatus.COMPLETED);

        return focusBlockRepository.save(block);
    }

    public void deleteFocusBlock(Long id) {

        FocusBlockEntity block = focusBlockRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Block not found with id : " + id));

        focusBlockRepository.delete(block);
    }

    public long countBlocks(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Session not found with id : " + sessionId));

        return focusBlockRepository.countByFocusSession(session);
    }

    public long countCompletedBlocks(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Focus Session not found with id : " + sessionId));

        return focusBlockRepository.countCompletedBlocks(session);
    }

}