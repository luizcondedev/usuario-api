package com.luizconde.usuario.business;

import com.luizconde.usuario.business.converter.UsuarioConverter;
import com.luizconde.usuario.business.dto.UsuarioDTO;
import com.luizconde.usuario.infrastructure.entity.Usuario;
import com.luizconde.usuario.infrastructure.exceptions.ConflictException;
import com.luizconde.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.luizconde.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario nao encontrado: " + email)
        );
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email ja cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}
