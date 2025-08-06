package com.blumbit.supermercado.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.supermercado.dto.request.PermisoRequest;
import com.blumbit.supermercado.dto.request.RolRequest;
import com.blumbit.supermercado.dto.response.PermisoResponse;
import com.blumbit.supermercado.dto.response.RolResponse;
import com.blumbit.supermercado.entity.Permiso;
import com.blumbit.supermercado.entity.PermisoRol;
import com.blumbit.supermercado.entity.Rol;
import com.blumbit.supermercado.repository.PermisoRepository;
import com.blumbit.supermercado.repository.PermisoRolRepository;
import com.blumbit.supermercado.repository.RolRepository;

@Service
public class RolService implements IRolService {

    private final RolRepository rolRepository;

    private final PermisoRolRepository permisoRolRepository;

    private final PermisoRepository permisoRepository;

    public RolService(RolRepository rolRepository, PermisoRolRepository permisoRolRepository,
            PermisoRepository permisoRepository) {
        this.rolRepository = rolRepository;
        this.permisoRolRepository = permisoRolRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public List<RolResponse> findAllRoles() {
        return rolRepository.findAllByEstado((short) 1).stream().map(RolResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RolResponse findRolById(Short id) {
        try {
            RolResponse rolResponse = RolResponse.fromEntity(rolRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("NO se encuentra el rol con el id proporcionado")));
            rolResponse.setPermisosIds(permisoRolRepository.findByRol_Id(id).stream()
                    .map(permisoRol -> permisoRol.getPermiso().getId()).collect(Collectors.toList()));
            return rolResponse;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    @Transactional
    public RolResponse createRol(RolRequest rolRequest) {
        try {
            Rol rolToCreate = RolRequest.toEntity(rolRequest);
            Rol rolCreated = rolRepository.save(rolToCreate);
            for (Integer idPermiso : rolRequest.getPermisosIds()) {
                permisoRolRepository.save(PermisoRol.builder()
                        .permiso(permisoRepository.findById(idPermiso).orElse(null))
                        .rol(rolCreated)
                        .build());
            }
            return RolResponse.fromEntity(rolCreated);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el rol");
        }
    }

    @Override
    @Transactional
    public RolResponse updateRol(Short id, RolRequest rolRequest) {
        try {
            Rol rolRetrieved = rolRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error al recuperar el rol"));
            rolRetrieved.setDescripcion(rolRequest.getDescripcion());
            rolRetrieved.setNombre(rolRequest.getNombre());
            Rol rolUpdated = rolRepository.save(rolRetrieved);
            // Get current permissions for this role
            List<PermisoRol> currentPermissions = permisoRolRepository.findByRol_Id(id);
            Set<Integer> currentPermissionIds = currentPermissions.stream()
                    .map(pr -> pr.getPermiso().getId())
                    .collect(Collectors.toSet());

            // Get requested permission IDs
            Set<Integer> requestedPermissionIds = new HashSet<>(rolRequest.getPermisosIds());

            // Find permissions to add (exist in request but not in current)
            Set<Integer> permissionsToAdd = new HashSet<>(requestedPermissionIds);
            permissionsToAdd.removeAll(currentPermissionIds);

            // Find permissions to remove (exist in current but not in request)
            Set<Integer> permissionsToRemove = new HashSet<>(currentPermissionIds);
            permissionsToRemove.removeAll(requestedPermissionIds);

            // Add new permissions
            for (Integer permisoId : permissionsToAdd) {
                Permiso permiso = permisoRepository.findById(permisoId)
                        .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permisoId));

                PermisoRol permisoRol = PermisoRol.builder()
                        .permiso(permiso)
                        .rol(rolUpdated) // Use rolUpdated instead of rolCreated
                        .build();

                permisoRolRepository.save(permisoRol);
            }

            // Remove permissions that are no longer needed
            if (!permissionsToRemove.isEmpty()) {
                List<PermisoRol> toDelete = currentPermissions.stream()
                        .filter(pr -> permissionsToRemove.contains(pr.getPermiso().getId()))
                        .collect(Collectors.toList());
                permisoRolRepository.deleteAll(toDelete);

            }

            return RolResponse.fromEntity(rolUpdated);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el rol");
        }
    }

    @Override
    public void deleteRol(Short id) {
        try {
            // BORRADO LOGICO
            Rol rolRetrieved = rolRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encuentra el rol con el identificador solicitado"));
            rolRetrieved.setEstado((short) 0);
            rolRepository.save(rolRetrieved);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PermisoResponse createPermiso(PermisoRequest permisoRequest) {
        return PermisoResponse.fromEntity(permisoRepository.save(PermisoRequest.toEntity(permisoRequest)));
    }

    @Override
    public List<PermisoResponse> findAllPermisos() {
        return permisoRepository.findAll().stream().map(PermisoResponse::fromEntity).collect(Collectors.toList());
    }

}
