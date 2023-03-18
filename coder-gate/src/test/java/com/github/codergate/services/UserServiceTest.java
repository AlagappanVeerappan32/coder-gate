package com.github.codergate.services;

import com.github.codergate.dto.installation.AccountDTO;
import com.github.codergate.entities.UserEntity;
import com.github.codergate.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;
    @InjectMocks
    UserService userServiceMock;


    @Test
    void testDeleteUserByID() {
        Long userId = 32L;
        Mockito.doNothing().when(userRepositoryMock).deleteById(userId);
        boolean isDeleted = userServiceMock.deleteUserByID(userId);
        assertTrue(isDeleted);
        verify(userRepositoryMock, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserByIDWhenNull() {
        Long userId = null;
        boolean isDeleted = userServiceMock.deleteUserByID(userId);
        assertFalse(isDeleted);
        verify(userRepositoryMock, Mockito.times(0)).deleteById(userId);
    }

    @Test
    void testGetUserByID() {
        Long userId = 32L;
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("TestBot");
        userEntity.setUserId(userId);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        AccountDTO expected = new AccountDTO();

        expected.setId(32);
        expected.setLogin("TestBot");

        AccountDTO actual = userServiceMock.getUserById(userId);
        assertEquals(expected, actual);
    }

    @Test
    void testGetUserByIDWhenIdIsNull() {
        Long userId = null;

        AccountDTO actual = userServiceMock.getUserById(userId);
        assertNull(actual);
    }

    @Test
    void testAddUser() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(32);
        accountDTO.setLogin("TestBot");

        UserEntity expected = new UserEntity();
        expected.setUserId(32);
        expected.setUserName("TestBot");

        Mockito.when(userRepositoryMock.save(Mockito.any(UserEntity.class))).thenReturn(expected);

        AccountDTO actual = userServiceMock.addUser(accountDTO);

        assertEquals(expected.getUserId(), actual.getId().intValue());
        assertEquals(expected.getUserName(), actual.getLogin());

        verify(userRepositoryMock, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void testAddUserWithNullValues() {
        AccountDTO accountDTO = null;

        AccountDTO actual = userServiceMock.addUser(accountDTO);

        assertNull(actual);

    }

    @Test
    void testAddUserWithAccountDTOIsNUllOrUserIdIsNull() {

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(null);
        accountDTO.setLogin(null);


        AccountDTO actual = userServiceMock.addUser(accountDTO);
        assertNull(actual);
        verify(userRepositoryMock, never()).saveAll(anyList());

    }

    @Test
    void testAddUserWithUserEntityIsNUllOrUserIdIsNull() {


        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin(null);

        UserEntity expected = new UserEntity();
        expected.setUserId(0);
        expected.setUserName(null);

        Mockito.when(userRepositoryMock.save(Mockito.any(UserEntity.class))).thenReturn(expected);

        AccountDTO actual = userServiceMock.addUser(accountDTO);

        assertNotNull(actual);
        verify(userRepositoryMock, never()).saveAll(anyList());

    }

    @Test
    void testAddUserHandlesException() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(32);
        accountDTO.setLogin("TestBot");
        Mockito.doThrow(new RuntimeException()).when(userRepositoryMock).save(Mockito.any(UserEntity.class));
        assertThrows(RuntimeException.class, () -> userServiceMock.addUser(accountDTO));
        verify(userRepositoryMock, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }


    @Test
    void testUpdateUser() {
        Long userId = 32L;
        String login = "TestBot";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(32);
        accountDTO.setLogin(login);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(login);
        userEntity.setUserId(userId);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepositoryMock.save(userEntity)).thenReturn(userEntity);

        AccountDTO actual = userServiceMock.updateUserById(userId);

        verify(userRepositoryMock, times(1)).findById(userId);
        verify(userRepositoryMock, times(1)).save(userEntity);
        assertEquals(accountDTO, actual);
    }

    @Test
    void testUpdateUserIfNull() {
        Long userId = 32L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());
        AccountDTO accountDTO = userServiceMock.updateUserById(userId);
        assertNull(accountDTO);
    }

    @Test
    void testAddUserForPush() {
        int userId = 32;
        String login = "TestBot";
        String email = "test@abc.com";

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(login);
        userEntity.setEmail(email);
        userEntity.setUserId(userId);

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);
        UserEntity actual = userServiceMock.addUser(userId, login, email);
        assertNotNull(actual);
        assertEquals(actual.getUserId(), userId);
        assertEquals(actual.getUserName(), login);
        assertEquals(actual.getEmail(), email);
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }


    @Test
    void testAddUserWhenUserEntityForPushIsNull() {
        Integer userId = null;
        String login = null;
        String userEmail = null;

        UserEntity actual = userServiceMock.addUser(userId, login, userEmail);
        assertNull(actual);

    }

    @Test
    void testAddUserInformationForPushIfNullName() {

        Integer userId = 32;
        String login = null;
        String email = "test@abc.com";

        UserEntity actual = userServiceMock.addUser(userId, login, email);

        assertNull(actual);
        verify(userRepositoryMock, never()).save(any(UserEntity.class));

    }

    @Test
    void testAddUserInformationForPushIfNullEmail() {

        Integer userId = 32;
        String login = "TestBot";
        ;
        String email = null;

        UserEntity actual = userServiceMock.addUser(userId, login, email);

        assertNull(actual);
        verify(userRepositoryMock, never()).save(any(UserEntity.class));

    }

}
















