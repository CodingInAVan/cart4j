package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.RedirectUri;
import com.cart4j.auth.repository.ClientRepository;
import com.cart4j.auth.repository.RedirectUriRepository;
import com.cart4j.auth.service.RedirectUriService;
import com.cart4j.model.security.dto.v1.RedirectUriDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class RedirectUriServiceImpl implements RedirectUriService {
    @Autowired
    public RedirectUriServiceImpl(ClientRepository clientRepository, RedirectUriRepository redirectUriRepository) {
        this.clientRepository = clientRepository;
        this.redirectUriRepository = redirectUriRepository;
    }

    @Override
    public Page<RedirectUriDto> getRedirectUris(Pageable pageable, String searchKey) {
        Specification<RedirectUri> spec = null;

        if(!StringUtils.isEmpty(searchKey)) {
            spec = RedirectUriSpec.search(searchKey);
        }
        return redirectUriRepository.findAll(spec, pageable).map(RedirectUri::toDto);
    }

    @Override
    public RedirectUriDto getRedirectUri(Long id) {
        if(!redirectUriRepository.existsById(id)) {
            return null;
        }
        return redirectUriRepository.getOne(id).toDto();
    }

    @Override
    public RedirectUriDto addRedirectUri(Long clientId, RedirectUriDto redirectUri) {
        Client client = clientRepository.getOne(clientId);
        RedirectUri newRedirectUri = RedirectUri.builder().client(client)
                .redirectUri(redirectUri.getRedirectUri())
                .build();

        return redirectUriRepository.save(newRedirectUri).toDto();
    }

    @Override
    public RedirectUriDto editRedirectUri(Long id, RedirectUriDto redirectUri) {
        RedirectUri updatingRedirectUri = redirectUriRepository.getOne(id);
        updatingRedirectUri.setRedirectUri(redirectUri.getRedirectUri());
        return redirectUriRepository.save(updatingRedirectUri).toDto();
    }

    @Override
    public void deleteRedirectUri(Long id) {
        redirectUriRepository.deleteById(id);
    }

    private static class RedirectUriSpec {
        private static Specification<RedirectUri> search(String searchKey) {
            return (root, query, builder) -> {
                String likeSearch = "%" + searchKey + "%";
                return builder.like(root.get("redirectUri"), likeSearch);
            };
        }
    }

    private final ClientRepository clientRepository;

    private final RedirectUriRepository redirectUriRepository;
}
