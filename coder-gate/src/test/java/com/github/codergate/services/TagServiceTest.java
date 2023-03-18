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
    TagService tagService=new TagService();


    @Test
    void testTagConvertEntityToDTO()
    {
        TagEntity tagEntity = new TagEntity();
        TagId tagId = new TagId();
        tagId.setRepositoryId(1234);
        tagId.setTagUrl("https://getTag");
        tagEntity.setTagId(tagId);
        RepositoryDTO actual = tagService.convertEntityToDTO(tagEntity);
        assertNotNull(actual);
        assertEquals(tagId.getRepositoryId(), actual.getId());
        assertEquals(tagId.getTagUrl(), actual.getTagsUrl());

    }


    @Test
    void testTagConvertEntityToDTOWhenNullRepositoryDTO() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagId(new TagId());
        RepositoryDTO actual = tagService.convertEntityToDTO(tagEntity);
        assertNotNull(actual);
    }

    @Test
    void testTagConvertEntityToDTONullValue() {
        TagEntity tagEntity=null;
        RepositoryDTO actual = tagService.convertEntityToDTO(tagEntity);
        assertNull(actual);
    }

    @Test
    void testTagConvertDTOToEntity()
    {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(32);
        repositoryDTO.setTagsUrl("https://getTag");

        TagEntity actual = tagService.convertDTOToEntity(repositoryDTO);

        assertNotNull(actual);
        assertNotNull(actual.getTagId());
        assertEquals(repositoryDTO.getId(), actual.getTagId().getRepositoryId());
        assertEquals(repositoryDTO.getTagsUrl(), actual.getTagId().getTagUrl());
    }

    @Test
    void testTagConvertDTOToEntityWithNullValue() {
        RepositoryDTO repositoryDTO =null;
        TagEntity actual = tagService.convertDTOToEntity(repositoryDTO);
        assertNull(actual);
    }

    @Test
    void testTagConvertDTOToEntityNullTagURL() {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setId(123);
        repositoryDTO.setTagsUrl(null);

        TagEntity tagEntity = tagService.convertDTOToEntity(repositoryDTO);

        assertNull(tagEntity.getTagId());
    }

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








}