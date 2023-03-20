package com.github.codergate.services;

import com.github.codergate.dto.push.RepositoryDTO;
import com.github.codergate.entities.TagEntity;
import com.github.codergate.entities.TagId;
import com.github.codergate.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

class TagServiceTest {
    @Mock
    TagRepository tagRepositoryMock;
    @InjectMocks
    TagService tagServiceMock;




    @Test
    void testAddTag() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(123);
        repositoryDTO.setTagsUrl("https://getTag");

        TagEntity tagEntity = new TagEntity();
        TagId tagId =new TagId();
        tagId.setRepositoryId(123);
        tagId.setTagUrl("https://getTag");
        tagEntity.setTagId(tagId);

        when(tagRepositoryMock.save(Mockito.any(TagEntity.class))).thenReturn(tagEntity);

        RepositoryDTO actual = tagServiceMock.addTag(repositoryDTO);

        assertEquals(actual.getId(), repositoryDTO.getId());
        assertEquals(actual.getTagsUrl(), repositoryDTO.getTagsUrl());

    }

    @Test
    void testAddTagValueIsNullValues()
    {
        RepositoryDTO repositoryDTO=null;
        RepositoryDTO actual =tagServiceMock.addTag(repositoryDTO);
        assertNull(actual);
    }

    @Test
    void testAddTagWithAccountDTOIsNUllOrUserIdIsNull() {

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(null);
        repositoryDTO.setTagsUrl(null);


        RepositoryDTO actual = tagServiceMock.addTag(repositoryDTO);
        assertNull(actual);
        verify(tagRepositoryMock, never()).saveAll(anyList());

    }
    @Test
    void testAddTagWithNullTagsUrlAndId() {
        // Arrange
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(0);
        repositoryDTO.setTagsUrl("https://getTag");



        RepositoryDTO actual = tagServiceMock.addTag(repositoryDTO);

        assertNull(actual);
        verify(tagRepositoryMock, never()).saveAll(anyList());

    }














}