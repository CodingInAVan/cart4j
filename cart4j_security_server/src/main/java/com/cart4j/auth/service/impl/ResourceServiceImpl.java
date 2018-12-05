package com.cart4j.auth.service.impl;

import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.RedirectUri;
import com.cart4j.auth.entity.Resource;
import com.cart4j.auth.exception.ResourceAlreadyExistingException;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.repository.ClientRepository;
import com.cart4j.auth.repository.ResourceRepository;
import com.cart4j.auth.service.ResourceService;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Override
    public Page<ResourceDto> getResources(Pageable pageable, String searchKey) {
        Specification<Resource> spec = null;
        if(!StringUtils.isEmpty(searchKey)) {
            spec = ResourceSpec.search(searchKey);
        }
        return resourceRepository.findAll(spec, pageable).map(ResourceDto::from);
    }

    @Override
    public ResourceDto addResource(ResourceDto resource) throws ResourceAlreadyExistingException {
        if(resourceRepository.existsByResourceUniqueId(resource.getResourceUniqueId())) {
            throw new ResourceAlreadyExistingException("Resource[" + resource.getResourceUniqueId() + "] is already in use.");
        }
        Resource newResource = Resource.builder()
                .resourceUniqueId(resource.getResourceUniqueId())
                .description(resource.getDescription())
                .build();
        return ResourceDto.from(resourceRepository.save(newResource));
    }

    @Override
    public ResourceDto editResource(Long resourceId, ResourceDto resource) throws ResourceAlreadyExistingException {
        if(resourceRepository.existsByResourceUniqueIdNotId(resource.getResourceUniqueId(), resourceId)) {
            throw new ResourceAlreadyExistingException("Resource[" + resource.getResourceUniqueId() + "] is already in use.");
        }
        Resource updatingResource = resourceRepository.getOne(resourceId);
        updatingResource.setDescription(resource.getDescription());
        updatingResource.setResourceUniqueId(resource.getResourceUniqueId());
        return ResourceDto.from(resourceRepository.save(updatingResource));
    }

    @Override
    public void deleteResource(Long resourceId) {
        resourceRepository.deleteById(resourceId);
    }

    static class ResourceSpec {
        public static Specification<Resource> search(String searchKey) {
            return (root, query, builder) -> {
                String likeSearch = "%" + searchKey + "%";
                return builder.or(builder.like(root.get("resource"), likeSearch), builder.like(root.get("description"), likeSearch));
            };
        }
    }

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ResourceRepository resourceRepository;
}
