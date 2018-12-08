package com.cart4j.auth.controller;

import com.cart4j.auth.dto.RedirectUriDto;
import com.cart4j.auth.service.RedirectUriService;
import com.cart4j.common.dto.PageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth/redirect-uri")
public class RedirectUriController {
    @GetMapping
    PageDto<RedirectUriDto> getRedirectUris(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<RedirectUriDto> resourcesPage = redirectUriService.getRedirectUris(pageable, searchKey);
        return PageDto.<RedirectUriDto>builder().totalRecords(resourcesPage.getTotalElements()).totalPage(resourcesPage.getTotalPages())
                .offset(resourcesPage.getPageable().getOffset()).list(resourcesPage.getContent()).limit(resourcesPage.getSize()).build();
    }

    @PostMapping("/client/{clientId}")
    RedirectUriDto addRedirectUri(Principal principal,@PathVariable Long clientId, @RequestBody RedirectUriDto redirectUri) {
        RedirectUriDto newRedirectUri = redirectUriService.addRedirectUri(clientId, redirectUri);
        LOGGER.info("{} added a redirectUri[{}]", principal.getName(), newRedirectUri.getId());
        return newRedirectUri;
    }

    @PutMapping("/{id}")
    RedirectUriDto editRedirectUri(Principal principal, @PathVariable Long id, @RequestBody RedirectUriDto redirectUri) {
        RedirectUriDto updated = redirectUriService.editRedirectUri(id, redirectUri);
        LOGGER.info("{} modified the redirectUri[{}]", principal.getName(), updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    void deleteRediretUri(Principal principal, @PathVariable Long id) {
        redirectUriService.deleteRedirectUri(id);
        LOGGER.info("{} deleted the redirectUri[{}]", principal.getName(), id);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RedirectUriController.class);

    @Autowired
    private RedirectUriService redirectUriService;
}
