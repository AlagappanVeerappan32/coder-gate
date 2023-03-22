package com.github.codergate.services;
import com.github.codergate.dto.installation.AccountDTO;
import com.github.codergate.entities.UserEntity;
import com.github.codergate.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    /***
     * adds the value into the table using entity and returns the values in dto class during installation event
     * @param userToAdd dto class
     * @return dto class with user values
     */
    public AccountDTO addUser(AccountDTO userToAdd) {
        AccountDTO account=null;
        UserEntity userEntity = convertAccountDtoToEntity(userToAdd);
        if (userEntity != null) {
            UserEntity savedEntity = userRepository.save(userEntity);
            LOGGER.info("addUser : The user information is added {}", savedEntity);
            account = convertEntityToAccountDto(savedEntity);
        }
        return account;
    }

    /***
     * adds user to the table during push event
     * @param  userId, login, userEmail
     * @param userEmail email of User in String format
     * @return SenderDTO Object
     */
    public UserEntity addUser(Integer userId,String login, String userEmail)
    {
        UserEntity userEntity = convertSenderDtoToEntity(userId,login, userEmail);
        if(userEntity!=null) {
            UserEntity savedEntity = userRepository.save(userEntity);
            LOGGER.info("addUser : The user information email is added {}", savedEntity);
        }
        return userEntity;
    }


    /***
     * Gets the user information from the table using id
     * @param userId user id
     * @return dto class with user information
     */

    public AccountDTO getUserById(Long userId)
    {
        AccountDTO account = null;
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isPresent()){
            account = convertEntityToAccountDto(userEntity.get());
            LOGGER.info("getUserById : Successfully retrieved account with ID: {}", userId);
        }else {
            LOGGER.warn("getAccountById: Account with ID: {} not found", userId);
        }
        return account;
    }

    /***
     * updates the user information using userId
     * @param userId userId
     * @return dto class
     */
    public AccountDTO updateUserById(Long userId) {
        AccountDTO accountDto = null;
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            LOGGER.info("updateUserById : Updating the user information");
            UserEntity savedEntity = userRepository.save(userEntity);
            accountDto = convertEntityToAccountDto(savedEntity);
        }
        return accountDto;
    }


    /***
     *  deletes user row by id
     * @param userId user_id
     * @return true or false
     */
    public boolean deleteUserByID(Long userId) {
        boolean isDeleted = false;
        if(userId != null)
        {
            userRepository.deleteById(userId);
            isDeleted=true;
            LOGGER.info("deleteUserByID : Deleting the user information {}", userId);
        }
        return isDeleted;
    }


    /***
     * converts AccountDTO to User Entity
     * @param accountDTO dto, id and name check and set.
     * @return entity
     */
    private UserEntity convertAccountDtoToEntity(AccountDTO accountDTO)
    {
        UserEntity userEntity = null;
        if(accountDTO != null)
        {
            userEntity = new UserEntity();
            if(accountDTO.getId() != null)
            {
                userEntity.setUserId(accountDTO.getId());
            }
            if(accountDTO.getLogin()!= null)
            {
                userEntity.setUserName(accountDTO.getLogin());
            }
            LOGGER.info("convertAccountDtoToEntity : Converted AccountDTO to Entity {}", userEntity);
        }else {
            LOGGER.warn("convertAccountDtoToEntity : AccountDTO value is null");
        }
        return userEntity;
    }


    /***
     * converts SenderDTO to User Entity during push event
     * @param userEmail email of User in String format
     * @return User Entity
     */
    private UserEntity convertSenderDtoToEntity(Integer id, String login, String userEmail)
    {
        UserEntity userEntity = null;
        if(id!=null && login!=null && userEmail!=null) {
            userEntity = new UserEntity();
            userEntity.setUserId(id);
            userEntity.setUserName(login);
            userEntity.setEmail(userEmail);
            LOGGER.info("convertSenderDtoToEntity : Converted SenderDTO to Entity {}", userEntity);
        }

        return userEntity;
    }

    /***
     * converts User entity to AccountDTO
     * @param userEntity user entity
     * @return AccountDTO
     */
    private AccountDTO convertEntityToAccountDto(UserEntity userEntity)
    {
        AccountDTO accountDTO = null;
        if(userEntity != null)
        {
            accountDTO = new AccountDTO();
            if(userEntity.getUserId() != 0L)
            {
                accountDTO.setId((int) userEntity.getUserId());
            }
            if(userEntity.getUserName() != null)
            {
                accountDTO.setLogin(userEntity.getUserName());
            }
            LOGGER.info("convertEntityToAccountDto : Entity has been converted to AccountDTO {}", accountDTO );
        }else {
            LOGGER.warn("convertEntityToAccountDto : User entity value is null");
        }
        return accountDTO;
    }

    /***
     * converts User Entity to SenderDTO
     * @param userEntity User Entity
     * @return SenderDTO
     */

}

