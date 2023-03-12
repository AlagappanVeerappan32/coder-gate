package com.github.codergate.services;

import com.github.codergate.dto.push.RepositoryDTO;
import com.github.codergate.entities.BranchEntity;
import com.github.codergate.entities.BranchId;
import com.github.codergate.repositories.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

    @Autowired
    BranchRepository branchRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BranchService.class);

    /***
     * adds branch information during push event
     * @param repository RepositoryDTO object
     * @return RepositoryDTO object
     */
    public RepositoryDTO addBranch(RepositoryDTO repository) {
        RepositoryDTO repositoryDTO = null;
        BranchEntity branchEntity = convertDTOToEntity(repository);
        if(branchEntity!=null) {
            BranchEntity savedEntity = branchRepository.save(branchEntity);
            LOGGER.info("addBranch : The branch information is added {}", savedEntity);
            repositoryDTO = convertEntityToDto(savedEntity);
        }
        return repositoryDTO;
    }

    /***
     * converts RepositoryDTO to Branch entity
     * @param repositoryDTO RepositoryDTO object
     * @return Branch entity
     */
    private BranchEntity convertDTOToEntity(RepositoryDTO repositoryDTO) {
        BranchEntity branchEntity = null;
        if(repositoryDTO != null)
        {
            branchEntity = new BranchEntity();
            if(repositoryDTO.getTagsUrl() != null && repositoryDTO.getId() != null)
            {
                BranchId branchId = new BranchId(repositoryDTO.getId(), repositoryDTO.getBranchesUrl());
                branchEntity.setBranchId(branchId);
            }
            LOGGER.info("convertDTOToEntity : Repository DTO has been converted to Branch Entity {}", branchEntity);
        } else {
            LOGGER.warn("convertDTOToEntity : Repository branch value is null");
        }
        return branchEntity;
    }

    /***
     * converts Branch entity to RepositoryDTO
     * @param branch Branch entity
     * @return RepositoryDTO object
     */
    private RepositoryDTO convertEntityToDto(BranchEntity branch) {
        RepositoryDTO repositoryDTO = null;
        if(branch != null)
        {
            repositoryDTO = new RepositoryDTO();
            if(branch.getBranchId() != null)
            {
                BranchId branchIdObject = branch.getBranchId();
                repositoryDTO.setId(branchIdObject.getRepositoryId());
                repositoryDTO.setBranchesUrl(branchIdObject.getBranchUrl());
            }
            LOGGER.info("convertEntityToDto : Branch Entity has been converted to RepositoryDTO {}", repositoryDTO);
        } else {
            LOGGER.warn("convertEntityToDto : Branch Entity value is null");
        }
        return repositoryDTO;
    }
}