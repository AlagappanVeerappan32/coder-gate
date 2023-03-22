package com.github.codergate.services;

import com.github.codergate.dto.push.RepositoryDTO;
import com.github.codergate.entities.BranchEntity;
import com.github.codergate.entities.BranchId;

import com.github.codergate.repositories.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class BranchServiceTest {
    @Mock
    BranchRepository branchRepositoryMock;
    @InjectMocks
    BranchService branchServiceMock;
    BranchService branchService=new BranchService();




    @Test
    void testAddTag() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(123);
        repositoryDTO.setBranchesUrl("https://getBranch");

        BranchEntity branchEntity = new BranchEntity();
        BranchId branchId =new BranchId();
        branchId.setRepositoryId(123);
        branchId.setBranchUrl("https://getBranch");
        branchEntity.setBranchId(branchId);

        when(branchRepositoryMock.save(Mockito.any(BranchEntity.class))).thenReturn(branchEntity);

        RepositoryDTO actual = branchServiceMock.addBranch(repositoryDTO);

        assertEquals(actual.getId(), repositoryDTO.getId());
        assertEquals(actual.getBranchesUrl(), repositoryDTO.getBranchesUrl());

    }

    @Test
    void testAddTagValueIsNullValues()
    {
        RepositoryDTO repositoryDTO=null;
        RepositoryDTO actual =branchService.addBranch(repositoryDTO);
        assertNull(actual);
    }











}