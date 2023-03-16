package com.github.codergate.services;

import com.github.codergate.dto.installation.RepositoriesAddedDTO;
import com.github.codergate.entities.RepositoryEntity;
import com.github.codergate.repositories.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class RepositoryServiceTest {

    @Mock
    RepositoryRepository repositoryMock;
    @InjectMocks
    RepositoryService repositoryServiceMock;
    RepositoryService repositoryService=new RepositoryService();

    @Test
    void testConvertEntityTODTOWhenDataIsPresent()
    {
        List<RepositoryEntity> repositoryList = new ArrayList<>();
        RepositoryEntity repositoryOne = new RepositoryEntity();
        repositoryOne.setRepositoryId(1234);
        repositoryOne.setRepositoryName("TestRepository");
        repositoryList.add(repositoryOne);

        RepositoryEntity repositoryTwo = new RepositoryEntity();
        repositoryTwo.setRepositoryId(4321);
        repositoryTwo.setRepositoryName("DevRepository");
        repositoryList.add(repositoryTwo);

        List<RepositoriesAddedDTO> actual = repositoryService.convertEntityToDTO(repositoryList);
        assertNotNull(actual);
        assertEquals(repositoryList.size(), actual.size());
        assertEquals(repositoryOne.getRepositoryId(), actual.get(0).getId());
        assertEquals(repositoryTwo.getRepositoryId(), actual.get(1).getId());

    }

    @Test
    void testConvertEntityTODTOWhenDataListIsEmpty()
    {
        List<RepositoryEntity> repositoryList = new ArrayList<>();
        List<RepositoriesAddedDTO> actual = repositoryService.convertEntityToDTO(repositoryList);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testConvertEntityTODTOWhenDataIsNull()
    {
        List<RepositoryEntity> repositoryList = null;
        List<RepositoriesAddedDTO> actual = repositoryService.convertEntityToDTO(repositoryList);
        assertNull(actual);

    }

    @Test
    void testConvertDTOTOEntityWhenDataIsPresent()
    {
        List<RepositoriesAddedDTO> RepositoriesAddedDTOList = new ArrayList<>();
        RepositoriesAddedDTO repositoriesAddedDTOOne = new RepositoriesAddedDTO();
        int userId= 32;
        repositoriesAddedDTOOne.setId(1234);
        repositoriesAddedDTOOne.setName("TestRepository");
        RepositoriesAddedDTOList.add(repositoriesAddedDTOOne);

        RepositoriesAddedDTO repositoriesAddedDTOTwo = new RepositoriesAddedDTO();
        repositoriesAddedDTOTwo.setId(1234);
        repositoriesAddedDTOTwo.setName("TestRepository");
        RepositoriesAddedDTOList.add(repositoriesAddedDTOTwo);

        List<RepositoryEntity> actual = repositoryService.convertDTOToEntityForInstallationEvent(RepositoriesAddedDTOList, userId);
        assertNotNull(actual);
        assertEquals(RepositoriesAddedDTOList.size(), actual.size());
        assertEquals(repositoriesAddedDTOOne.getId(), actual.get(0).getRepositoryId());
        assertEquals(repositoriesAddedDTOTwo.getName(), actual.get(1).getRepositoryName());

    }

    @Test
    void testConvertDTOTOEntityWhenDataIsNull()
    {
        List<RepositoriesAddedDTO> RepositoriesAddedDTOList = null;
        int userId= 32;
        List<RepositoryEntity> actual = repositoryService.convertDTOToEntityForInstallationEvent(RepositoriesAddedDTOList, userId);
        assertNull(actual);
    }

    @Test
    void testConvertDTOTOEntityWhenUserIdIsNull()
    {
        List<RepositoriesAddedDTO> RepositoriesAddedDTOList = new ArrayList<>();
        RepositoriesAddedDTO repositoriesAddedDTOOne = new RepositoriesAddedDTO();
        int userId= 0;
        repositoriesAddedDTOOne.setId(1234);
        repositoriesAddedDTOOne.setName("TestRepository");
        RepositoriesAddedDTOList.add(repositoriesAddedDTOOne);

        List<RepositoryEntity> actual = repositoryService.convertDTOToEntityForInstallationEvent(RepositoriesAddedDTOList, userId);
        assertNull(actual);

    }

    @Test
    void testConvertDTOTOEntityWhenDataIsEmpty()
    {
        List<RepositoriesAddedDTO> RepositoriesAddedDTOList = new ArrayList<>();
        int userId= 32;

        List<RepositoryEntity> actual = repositoryService.convertDTOToEntityForInstallationEvent(RepositoriesAddedDTOList, userId);
        assertNotNull(actual);
    }

    @Test
    void testGetRepositoryFromUserId()
    {

        List<RepositoryEntity> repositoryEntities =new ArrayList<>();

        Long userId = 32L;
        RepositoryEntity repositoryOne = new RepositoryEntity();
        repositoryOne.setRepositoryId(1234);
        repositoryOne.setRepositoryName("TestRepository");
        repositoryEntities.add(repositoryOne);

        RepositoryEntity repositoryTwo = new RepositoryEntity();
        repositoryTwo.setRepositoryId(4567);
        repositoryTwo.setRepositoryName("DevRepository");
        repositoryEntities.add(repositoryTwo);

        when(repositoryMock.findByUserId(userId)).thenReturn(repositoryEntities);
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryFromUserId(userId);

        List<RepositoriesAddedDTO> expected = new ArrayList<>();
        RepositoriesAddedDTO repositoriesAddedDTOOne = new RepositoriesAddedDTO();
        repositoriesAddedDTOOne.setId(1234);
        repositoriesAddedDTOOne.setName("TestRepository");
        expected.add(repositoriesAddedDTOOne);

        RepositoriesAddedDTO repositoriesAddedDTOTwo = new RepositoriesAddedDTO();
        repositoriesAddedDTOTwo.setId(4567);
        repositoriesAddedDTOTwo.setName("DevRepository");
        expected.add(repositoriesAddedDTOTwo);

        assertNotNull(actual);
        assertThat(actual, containsInAnyOrder(expected.toArray()));

    }

    @Test
    void testGetRepositoryFromUserIdWhenRepositoryIsEmpty()
    {
        Long userId = 32L;
        List<RepositoryEntity> repositoryEntities =new ArrayList<>();
        when(repositoryMock.findByUserId(userId)).thenReturn(repositoryEntities);

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryFromUserId(userId);

        assertNotNull(actual);
        assertEquals(repositoryEntities.size(),actual.size());
    }

    @Test
    void testGetRepositoryFromUserIdWhenUserIdIsInvalid()
    {
        Long userId = 32L;
        when(repositoryMock.findByUserId(userId)).thenReturn(null);
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryFromUserId(userId);
        assertNull(actual);
    }

    @Test
    void testDeleteRepositoryById()
    {
        int repositoryId = 32;
        Mockito.doNothing().when(repositoryMock).deleteById(repositoryId);
        boolean isDeleted = repositoryServiceMock.deleteRepositoryById(repositoryId);
        assertTrue(isDeleted);
        verify(repositoryMock,Mockito.times(1)).deleteById(repositoryId);
    }

    @Test
    void testDeleteRepositoryByIDWhenNullOrZero()
    {
        int repositoryId = 0;
        boolean isDeleted = repositoryServiceMock.deleteRepositoryById(repositoryId);
        assertFalse(isDeleted);
        verify(repositoryMock,Mockito.times(0)).deleteById(repositoryId);
    }

    @Test
    void testGetRepositoryByIdWhenIdsAreEmpty()
    {
        List<Integer> repositoryIds = new ArrayList<>();
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetRepositoryByIdWhenIdsArePresent()
    {
        List<Integer> repositoryIds = Arrays.asList(1234,4321);

        RepositoryEntity repositoryOne = new RepositoryEntity();
        repositoryOne.setRepositoryId(1234);
        repositoryOne.setRepositoryName("TestRepository");

        RepositoryEntity repositoryTwo = new RepositoryEntity();
        repositoryTwo.setRepositoryId(4321);
        repositoryTwo.setRepositoryName("DevRepository");
        List<RepositoryEntity> expected = Arrays.asList(repositoryOne, repositoryTwo);

        when(repositoryMock.findById(1234)).thenReturn(Optional.of(repositoryOne));
        when(repositoryMock.findById(4321)).thenReturn(Optional.of(repositoryTwo));

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(expected.get(0).getRepositoryName(), actual.get(0).getName());
        assertEquals(expected.get(1).getRepositoryName(), actual.get(1).getName());
    }

    @Test
    void testGetRepositoryByIdWhenIdsAreInvalid()
    {
        List<Integer> repositoryIds = Arrays.asList(1234, 4321);
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetRepositoryByIdWhenIdsAreValidAndInValid()
    {
        List<Integer> repositoryIds = Arrays.asList(1234,4321);

        RepositoryEntity repositoryOne = new RepositoryEntity();
        repositoryOne.setRepositoryId(1234);
        repositoryOne.setRepositoryName("TestRepository");

        List<RepositoryEntity> expected = Arrays.asList(repositoryOne);

        when(repositoryMock.findById(1234)).thenReturn(Optional.of(repositoryOne));
        when(repositoryMock.findById(4321)).thenReturn(Optional.empty());

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected.get(0).getRepositoryName(), actual.get(0).getName());
    }



    @Test
    void testAddRepositoryWithEmptyList()
    {
        List<RepositoriesAddedDTO> repositoriesAddedDTOS = new ArrayList<>();
        int userId = 32;
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.addRepository(repositoriesAddedDTOS, userId);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());

    }


    @Test
    void testAddRepositoryWithNullValues()
    {
        List<RepositoriesAddedDTO> repositoriesAddedDTOS = null;
        int userId = 0;
        List<RepositoriesAddedDTO> actual = repositoryService.addRepository(repositoriesAddedDTOS, userId);
        assertNull(actual);

    }


    @Test
    void testAddRepositoryWithCorrectValues()
    {
        List<RepositoriesAddedDTO> repositoriesAddedDTOList = new ArrayList<>();
        int userId = 32;
        RepositoriesAddedDTO repositoryOne = new RepositoriesAddedDTO();;
        repositoryOne.setId(1234);
        repositoryOne.setName("TestRepository");
        repositoriesAddedDTOList.add(repositoryOne);
        RepositoriesAddedDTO repositoryTwo = new RepositoriesAddedDTO();;
        repositoryTwo.setId(4321);
        repositoryTwo.setName("DevRepository");
        repositoriesAddedDTOList.add(repositoryTwo);

        List<RepositoryEntity> expected = new ArrayList<>();
        RepositoryEntity repositoryEntityOne = new RepositoryEntity();;
        repositoryEntityOne.setRepositoryId(1234);
        repositoryEntityOne.setRepositoryName("TestRepository");
        expected.add(repositoryEntityOne);
        RepositoryEntity repositoryEntityTwo = new RepositoryEntity();;
        repositoryEntityTwo.setRepositoryId(4321);
        repositoryEntityTwo.setRepositoryName("DevRepository");
        expected.add(repositoryEntityTwo);

        Mockito.when(repositoryMock.saveAll(Mockito.anyList())).thenReturn(expected);

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.addRepository(repositoriesAddedDTOList,userId);
        assertEquals(2, actual.size());
        assertEquals(expected.get(0).getRepositoryId(), actual.get(0).getId());
        assertEquals(expected.get(1).getRepositoryId(), actual.get(1).getId());

    }

}