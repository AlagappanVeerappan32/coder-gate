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
    void testBranchConvertEntityToDTO()
    {
        BranchEntity branchEntity = new BranchEntity();
        BranchId branchId = new BranchId();
        branchId.setRepositoryId(4321);
        branchId.setBranchUrl("https://getBranch");
        branchEntity.setBranchId(branchId);
        RepositoryDTO actual = branchService.convertEntityToDto(branchEntity);
        assertNotNull(actual);
        assertEquals(branchId.getRepositoryId(), actual.getId());
        assertEquals(branchId.getBranchUrl(), actual.getBranchesUrl());

    }

    @Test
    void testBranchConvertEntityToDTONullTRepositoryDTO() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setBranchId(new BranchId());
        RepositoryDTO actual = branchService.convertEntityToDto(branchEntity);
        assertNotNull(actual);
    }

    @Test
    void testBranchConvertEntityToDTONullTValues() {
        BranchEntity branchEntity = null;
        RepositoryDTO actual = branchService.convertEntityToDto(branchEntity);
        assertNull(actual);
    }

    @Test
    public void testConvertDTOToEntity() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(4321);
        repositoryDTO.setBranchesUrl("https://getBranch");

        BranchEntity actual = branchService.convertDTOToEntity(repositoryDTO);

        assertNotNull(actual);
        assertNotNull(actual.getBranchId());

    }
    @Test
     void testBranchConvertDTOToEntity() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(4321);
        repositoryDTO.setBranchesUrl("https://getBranch");

        BranchEntity actual = branchService.convertDTOToEntity(repositoryDTO);

        assertEquals(repositoryDTO.getBranchesUrl(),actual.getBranchId().getBranchUrl()) ;
    }

    @Test
    public void testConvertDTOToEntityNullBranchURL() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(4321);
        repositoryDTO.setBranchesUrl(null);

        BranchEntity branchEntity = branchService.convertDTOToEntity(repositoryDTO);

        assertNull(branchEntity.getBranchId());

    }

    @Test
    public void testConvertDTOToEntityNWheNullUrlAndId() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setBranchesUrl(null);
        repositoryDTO.setId(null);

        BranchEntity actual = branchService.convertDTOToEntity(repositoryDTO);

        assertNotNull(actual);
        assertNull(actual.getBranchId());

    }


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