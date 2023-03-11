package com.github.codergate.controllers;

import com.github.codergate.dto.installation.RepositoriesAdded;
import com.github.codergate.services.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController

public class RepositoryController {

    @Autowired
    RepositoryService repositoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryController.class);

        @GetMapping("/getRepositories")
        public ResponseEntity<String> fetchRepositories(@RequestBody String userId) {
        LOGGER.debug("fetchRepositories : Entering the method");
        if (userId != null && !userId.isEmpty()) {
            List<RepositoriesAdded> repositoryFromUserId = repositoryService.getRepositoryFromUserId(Long.parseLong(userId));
        }
        LOGGER.debug("fetchRepositories : Exiting the method");
        return ResponseEntity.ok(null);
    }
}
