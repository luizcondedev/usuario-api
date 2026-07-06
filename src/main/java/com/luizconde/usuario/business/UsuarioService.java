package com.luizconde.usuario.business;

import com.luizconde.usuario.business.converter.UsuarioConverter;
import com.luizconde.usuario.business.dto.UsuarioDTO;
import com.luizconde.usuario.infrastructure.entity.Usuario;
import com.luizconde.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
