package com.github.codergate.services;
import com.github.codergate.dto.installation.RepositoriesAddedDTO;
import com.github.codergate.entities.RepositoryEntity;
import com.github.codergate.entities.UserEntity;
import com.github.codergate.repositories.RepositoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class RepositoryServiceTest {

    @Mock
    RepositoryRepository repositoryMock;
    @InjectMocks
    RepositoryService repositoryServiceMock;

    RepositoryService repositoryService = new RepositoryService();


    @Test
    void testGetRepositoryFromUserId() {

        List<RepositoryEntity> repositoryEntities = new ArrayList<>();

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
    void testGetRepositoryFromUserIdWhenRepositoryIsEmpty() {
        Long userId = 32L;
        List<RepositoryEntity> repositoryEntities = new ArrayList<>();
        when(repositoryMock.findByUserId(userId)).thenReturn(repositoryEntities);

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryFromUserId(userId);

        assertNotNull(actual);
        assertEquals(repositoryEntities.size(), actual.size());
    }

    @Test
    void testGetRepositoryFromUserIdWhenUserIdIsInvalid() {
        Long userId = 32L;
        when(repositoryMock.findByUserId(userId)).thenReturn(null);
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryFromUserId(userId);
        assertNull(actual);
    }

    @Test
    void testDeleteRepositoryById() {
        int repositoryId = 32;
        Mockito.doNothing().when(repositoryMock).deleteById(repositoryId);
        boolean isDeleted = repositoryServiceMock.deleteRepositoryById(repositoryId);
        assertTrue(isDeleted);
        verify(repositoryMock, Mockito.times(1)).deleteById(repositoryId);
    }

    @Test
    void testDeleteRepositoryByIDWhenNullOrZero() {
        int repositoryId = 0;
        boolean isDeleted = repositoryServiceMock.deleteRepositoryById(repositoryId);
        assertFalse(isDeleted);
        verify(repositoryMock, Mockito.times(0)).deleteById(repositoryId);
    }

    @Test
    void testGetRepositoryByIdWhenIdsAreEmpty() {
        List<Integer> repositoryIds = new ArrayList<>();
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetRepositoryByIdWhenIdsArePresent() {
        List<Integer> repositoryIds = Arrays.asList(1234, 4321);

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
    void testGetRepositoryByIdWhenIdsAreInvalid() {
        List<Integer> repositoryIds = Arrays.asList(1234, 4321);
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.getRepositoryById(repositoryIds);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetRepositoryByIdWhenIdsAreValidAndInValid() {
        List<Integer> repositoryIds = Arrays.asList(1234, 4321);

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
    void testAddRepositoryWithEmptyList() {
        List<RepositoriesAddedDTO> repositoriesAddedDTOS = new ArrayList<>();
        int userId = 32;
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.addRepository(repositoriesAddedDTOS, userId);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());

    }


    @Test
    void testAddRepositoryWithNullValues() {
        List<RepositoriesAddedDTO> repositoriesAddedDTOS = null;
        int userId = 0;
        List<RepositoriesAddedDTO> actual = repositoryService.addRepository(repositoriesAddedDTOS, userId);
        assertNull(actual);

    }


    @Test
    void testAddRepositoryWithCorrectValues() {
        List<RepositoriesAddedDTO> repositoriesAddedDTOList = new ArrayList<>();
        int userId = 32;
        RepositoriesAddedDTO repositoryOne = new RepositoriesAddedDTO();
        ;
        repositoryOne.setId(1234);
        repositoryOne.setName("TestRepository");
        repositoriesAddedDTOList.add(repositoryOne);
        RepositoriesAddedDTO repositoryTwo = new RepositoriesAddedDTO();
        ;
        repositoryTwo.setId(4321);
        repositoryTwo.setName("DevRepository");
        repositoriesAddedDTOList.add(repositoryTwo);

        List<RepositoryEntity> expected = new ArrayList<>();
        RepositoryEntity repositoryEntityOne = new RepositoryEntity();
        ;
        repositoryEntityOne.setRepositoryId(1234);
        repositoryEntityOne.setRepositoryName("TestRepository");
        expected.add(repositoryEntityOne);
        RepositoryEntity repositoryEntityTwo = new RepositoryEntity();
        ;
        repositoryEntityTwo.setRepositoryId(4321);
        repositoryEntityTwo.setRepositoryName("DevRepository");
        expected.add(repositoryEntityTwo);

        Mockito.when(repositoryMock.saveAll(Mockito.anyList())).thenReturn(expected);

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.addRepository(repositoriesAddedDTOList, userId);
        assertEquals(2, actual.size());
        assertEquals(expected.get(0).getRepositoryId(), actual.get(0).getId());
        assertEquals(expected.get(1).getRepositoryId(), actual.get(1).getId());

    }

    @Test
    void testAddRepositoryWithRepositoryDTOIsNUllOrUserIdIsNull() {
        List<RepositoriesAddedDTO> repositoriesAddedDTOList = new ArrayList<>();
        int userId = 0;
        RepositoriesAddedDTO repositoryOne = new RepositoriesAddedDTO();
        repositoryOne.setId(0);
        repositoryOne.setName(null);
        repositoriesAddedDTOList.add(repositoryOne);

        List<RepositoriesAddedDTO> actual = repositoryServiceMock.addRepository(repositoriesAddedDTOList, userId);
        assertNull(actual);
        verify(repositoryMock, never()).saveAll(anyList());

    }

    @Test
    void testUpdateRepository() {
        RepositoryEntity repositoryEntity = new RepositoryEntity();
        repositoryEntity.setRepositoryId(32);
        repositoryEntity.setRepositoryName("TestRepository");

        List<RepositoryEntity> savedEntities = new ArrayList<>();
        savedEntities.add(repositoryEntity);

        List<RepositoriesAddedDTO> expectedDto = new ArrayList<>();
        RepositoriesAddedDTO repositoryDto = new RepositoriesAddedDTO();
        repositoryDto.setId(32);
        repositoryDto.setName("TestRepository");
        expectedDto.add(repositoryDto);

        Mockito.when(repositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(repositoryEntity));
        Mockito.when(repositoryMock.save(Mockito.any(RepositoryEntity.class))).thenReturn(repositoryEntity);

        List<RepositoriesAddedDTO> actualDto = repositoryServiceMock.updateRepository(32);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testUpdateRepositoryWhenNull() {

        int repositoryId =32;
        when(repositoryMock.findById(repositoryId)).thenReturn(Optional.empty());
        List<RepositoriesAddedDTO> actual = repositoryServiceMock.updateRepository(repositoryId);
        assertNull(actual);
    }

    @Test
    void testAddRepositoryInformationForPush() {

        Integer repositoryId = 1234;
        String repositoryName = "TestRepository";
        boolean fork = true;
        int userId = 32;
        RepositoryEntity repositoryEntityMock = new RepositoryEntity();
        repositoryEntityMock.setRepositoryId(repositoryId);
        repositoryEntityMock.setRepositoryName(repositoryName);
        repositoryEntityMock.setFork(fork);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        repositoryEntityMock.setUserEntity(userEntity);

        when(repositoryMock.save(any(RepositoryEntity.class))).thenReturn(repositoryEntityMock);

        RepositoryEntity actual = repositoryServiceMock.addRepository(repositoryId, repositoryName, fork, userId);

        assertNotNull(actual);
        assertEquals(actual.getRepositoryId(), repositoryId);
        assertEquals(actual.getRepositoryName(), repositoryName);
        assertEquals(actual.isFork(), fork);
        assertEquals(actual.getUserEntity().getUserId(), userId);
        verify(repositoryMock, times(1)).save(any(RepositoryEntity.class));
    }

    @Test
    void testAddRepositoryInformationForPushIfNull() {

        Integer repositoryId = null;
        String repositoryName = "TestRepository";
        boolean fork = false;
        int userId = 32;

        RepositoryEntity actual = repositoryServiceMock.addRepository(repositoryId, repositoryName, fork, userId);

        assertNull(actual);
    }

    @Test
    public void testAddRepositoryInformationForPushIfNullName() {

        Integer repositoryId = 1234;
        Boolean fork = false;
        int userId = 32;
        RepositoryEntity repositoryEntity = repositoryServiceMock.addRepository(repositoryId, null, fork, userId);

        assertNull(repositoryEntity);
    }


}



