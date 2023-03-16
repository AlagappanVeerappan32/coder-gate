package com.github.codergate.services;

import com.github.codergate.dto.installation.InstallationPayloadDTO;

import com.github.codergate.entities.EventEntity;
import com.github.codergate.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepositoryMock;
    @InjectMocks
    EventService eventServiceMock;
    EventService eventService=new EventService();


    @Test
    void testConvertEntityToDTOWhenDataIsNull()
    {
        List<EventEntity> expected = null;
        InstallationPayloadDTO actual = eventService.convertEntityToDTO(expected);
        assertNull(actual);
    }

    @Test
    void testConvertEntityToDTOWhenDataIsPresent()
    {
        List<EventEntity> expected = new ArrayList<>();
        EventEntity eventEntityOne = new EventEntity();
        eventEntityOne.setEventId(1L);
        eventEntityOne.setEventName("install application");
        expected.add(eventEntityOne);

        InstallationPayloadDTO actual = eventService.convertEntityToDTO(expected);

        assertNotNull(actual);
        assertEquals(eventEntityOne.getEventName(), actual.getAction());
    }

    @Test
    void testConvertEntityToDTOWhenSomeDataIsNull()
    {
        List<EventEntity> expected = new ArrayList<>();
        EventEntity eventEntityOne = new EventEntity();
        eventEntityOne.setEventId(1L);
        eventEntityOne.setEventName(null);
        expected.add(eventEntityOne);

        InstallationPayloadDTO actual = eventService.convertEntityToDTO(expected);

        assertNotNull(actual);
        assertNull(actual.getAction());
    }

    @Test
    void testConvertDTOToEntityWhenSomeDataIsPresent()
    {
        String eventTypeName = "commit";
        int userId = 32;
        List<Integer> repositoryIdList = Arrays.asList(1, 2, 3);

        List<EventEntity> actual = eventService.convertDTOToEntity(eventTypeName, userId, repositoryIdList);

        assertEquals(repositoryIdList.size(), actual.size());
        assertEquals(eventTypeName, actual.get(0).getEventName());
        assertEquals(userId, actual.get(0).getUserIdInEvent().getUserId());
        assertTrue(repositoryIdList.contains(actual.get(0).getRepositoryIdInEvent().getRepositoryId()));
        assertEquals(eventTypeName, actual.get(1).getEventName());
        assertEquals(userId, actual.get(1).getUserIdInEvent().getUserId());
        assertTrue(repositoryIdList.contains(actual.get(1).getRepositoryIdInEvent().getRepositoryId()));
        assertEquals(eventTypeName, actual.get(2).getEventName());
        assertEquals(userId, actual.get(2).getUserIdInEvent().getUserId());
        assertTrue(repositoryIdList.contains(actual.get(2).getRepositoryIdInEvent().getRepositoryId()));
    }

    @Test
    void testConvertDTOToEntityWhenSomeDataIsNull()
    {
        String eventTypeName = null;
        int userId = 32;
        List<Integer> repositoryIdList = Arrays.asList(1, 2, 3);
        List<EventEntity> actual = eventService.convertDTOToEntity(eventTypeName, userId, repositoryIdList);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testConvertDTOToEntityWhenIdIsNull()
    {
        String eventTypeName = "commit";
        int userId = 0;
        List<Integer> repositoryIdList = Arrays.asList(1, 2, 3);
        List<EventEntity> actual = eventService.convertDTOToEntity(eventTypeName, userId, repositoryIdList);
        assertTrue(actual.isEmpty());
    }
    @Test
    void testConvertDTOToEntityWhenRepositoryIdIsOnlyOne()
    {
        String eventTypeName = "commit";
        int userId = 32;
        List<Integer> repositoryIdList =  Collections.singletonList(1);

        List<EventEntity> actual = eventService.convertDTOToEntity(eventTypeName, userId, repositoryIdList);

        assertEquals(repositoryIdList.size(), actual.size());
        assertEquals(eventTypeName, actual.get(0).getEventName());
        assertEquals(userId, actual.get(0).getUserIdInEvent().getUserId());
        assertEquals(1, actual.get(0).getRepositoryIdInEvent().getRepositoryId());
    }

    @Test
    void testAddEventWhenAllDataIsPresent() {
        String eventType = "create";
        int userId = 123;
        List<Integer> repositoryIdList = Arrays.asList(1, 2, 3);

        List<EventEntity> eventEntityList = new ArrayList<>();
        EventEntity eventEntityOne = new EventEntity();
        eventEntityOne.setEventId(1);
        eventEntityOne.setEventName("add");
        eventEntityList.add(eventEntityOne);

        Mockito.when(eventRepositoryMock.saveAll(Mockito.anyList())).thenReturn(eventEntityList);

        InstallationPayloadDTO actual = eventServiceMock.addEvent(eventType, userId, repositoryIdList);

        assertNotNull(actual);
        assertEquals(eventEntityList.get(0).getEventName(),actual.getAction());

    }

    @Test
    void testAddEventWithNullInputs() {

        String eventType = null;
        Integer userId = null;
        List<Integer> repositoryIdList = null;

        assertThrows(NullPointerException.class, () -> {
            eventServiceMock.addEvent(eventType, userId, repositoryIdList);
        });
    }






}