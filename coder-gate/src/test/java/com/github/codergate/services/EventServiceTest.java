package com.github.codergate.services;

import com.github.codergate.dto.installation.InstallationPayloadDTO;

import com.github.codergate.dto.push.HeadCommitDTO;
import com.github.codergate.entities.EventEntity;
import com.github.codergate.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepositoryMock;
    @InjectMocks
    EventService eventServiceMock;



    @Test
    void testAddEventWhenAllDataIsPresent() {
        String eventType = "create";
        int userId = 32;
        List<Integer> repositoryIdList = Arrays.asList(1, 2, 3);

        List<EventEntity> eventEntityList = new ArrayList<>();
        EventEntity eventEntityOne = new EventEntity();
        eventEntityOne.setEventId(1);
        eventEntityOne.setEventName("add");
        eventEntityList.add(eventEntityOne);

        when(eventRepositoryMock.saveAll(Mockito.anyList())).thenReturn(eventEntityList);

        InstallationPayloadDTO actual = eventServiceMock.addEvent(eventType, userId, repositoryIdList);

        assertNotNull(actual);
        assertEquals(eventEntityList.get(0).getEventName(),actual.getAction());

    }

    @Test
    void testAddEventWithNullInputs() {

        String eventType = null;
        Integer userId = 0;
        List<Integer> repositoryIdList = null;

        assertThrows(IllegalArgumentException.class, () -> {
            eventServiceMock.addEvent(eventType, userId, repositoryIdList);
        });
    }
    @Test
    void testAddEventWhenEventTypeIsNull() {
        String eventType = null;
        int userId = 32;
        List<Integer> repositoryIdList = new ArrayList<>();
        repositoryIdList.add(1);

        assertThrows(IllegalArgumentException.class, () -> {
            eventServiceMock.addEvent(eventType, userId, repositoryIdList);
        });

    }

    @Test
    void testAddEventWhenRepositoryIdListIsNull() {
        String eventType = "create";
        int userId = 32;
        List<Integer> repositoryIdList = null;

        assertThrows(IllegalArgumentException.class, () -> {
            eventServiceMock.addEvent(eventType, userId, repositoryIdList);
        });
    }

    @Test
    void testDeleteEvent()
    {
        int eventId = 11;
        Mockito.doNothing().when(eventRepositoryMock).deleteById(eventId);

        boolean actual = eventServiceMock.deleteEventById(eventId);

        assertTrue(actual);
        verify(eventRepositoryMock, Mockito.times(1)).deleteById(eventId);
    }

    @Test
    void deleteEventByIdShouldNotDeleteEventWhenIdIsZero() {
        int eventId = 0;
        boolean actual = eventServiceMock.deleteEventById(eventId);
        assertFalse(actual);
        verify(eventRepositoryMock, Mockito.times(0)).deleteById(eventId);
    }

    @Test
    void testEventByIdWithValidId() {
        Long eventId = 111L;
        EventEntity expected = new EventEntity();
        when(eventRepositoryMock.findById(eventId.intValue())).thenReturn(Optional.of(expected));

        EventEntity actual = eventServiceMock.getEventById(eventId);

        assertEquals(expected, actual);
        verify(eventRepositoryMock).findById(eventId.intValue());
        verifyNoMoreInteractions(eventRepositoryMock);

    }

    @Test
    void testEventByIdWithInvalidId() {
        Long eventId = 121L;
        when(eventRepositoryMock.findById(eventId.intValue())).thenReturn(Optional.empty());

        EventEntity actual = eventServiceMock.getEventById(eventId);

        assertNull(actual);
        verify(eventRepositoryMock).findById(eventId.intValue());
        verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
     void testUpdateEntity() {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(111L);
        eventEntity.setEventName("Testing");

        Mockito.when(eventRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(eventEntity));
        Mockito.when(eventRepositoryMock.save(Mockito.any(EventEntity.class))).thenReturn(eventEntity);

        EventEntity actual = eventServiceMock.updateEntity(1L);

        assertNotNull(actual);

        assertEquals(actual.getEventId(), eventEntity.getEventId());
        assertEquals(actual.getEventName(), eventEntity.getEventName());

        Mockito.verify(eventRepositoryMock, Mockito.times(1)).save(Mockito.any(EventEntity.class));
    }

    @Test
     void testUpdateEntityWhenEventNotFound() {
        int eventId = 1211;
        when(eventRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        EventEntity actual = eventServiceMock.updateEntity((long) eventId);
        assertNull(actual);
        verify(eventRepositoryMock, times(1)).findById(eventId);
        verifyNoMoreInteractions(eventRepositoryMock);
    }

    @Test
    void testAddEventFromPushEvent()
    {
        EventEntity savedEventEntity = new EventEntity();
        savedEventEntity.setEventId(1L);
        when(eventRepositoryMock.save(any(EventEntity.class))).thenReturn(savedEventEntity);

        HeadCommitDTO headCommitDTO = new HeadCommitDTO();
        headCommitDTO.setId("commit_id");
        headCommitDTO.setMessage("commit_message");


        HeadCommitDTO actual = eventServiceMock.addEvent(headCommitDTO, 1, 2);

        assertNotNull(actual);
        assertEquals(savedEventEntity.getCommitId(), actual.getId());
        assertEquals(savedEventEntity.getCommitMessage(), actual.getMessage());
    }

    @Test
    public void testAddPushEventWithNullCommit(){
        HeadCommitDTO headCommitDTO = null;
        int userID = 1;
        int repositoryID = 23;
        HeadCommitDTO result = eventServiceMock.addEvent(headCommitDTO, userID, repositoryID);
        assertNull(result);
    }

    @Test
    public void testAddPushEventWithUserRepositoryIdIsNull(){
        HeadCommitDTO headCommitDTO = new HeadCommitDTO();
        int userID = 123;
        int repositoryID = 0;
        HeadCommitDTO result = eventServiceMock.addEvent(headCommitDTO, userID, repositoryID);
        assertNull(result);
    }

    @Test
    public void testAddPushEventWithUserRepositoryIdIsNull1(){
        HeadCommitDTO headCommitDTO = null;
        int userID = 0;
        int repositoryID = 0;
        HeadCommitDTO result = eventServiceMock.addEvent(headCommitDTO, userID, repositoryID);
        assertNull(result);
    }







}