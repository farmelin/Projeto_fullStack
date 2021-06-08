package br.com.fullstack.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.fullstack.model.usuario;

public interface UsuarioDAO extends CrudRepository<usuario, Integer>{
	
	// DAO (é um  Patter - CRUD em uma classe)
	// Gravar - Excluir - Consultar - Alterar
	
	// save (objeto) : void => server tanto para inclusao de um novo dado quato para alteração de um dado já criado com base na PK informada
	// findById(int) : objeto
	// findAll() : iterable
	// deleteById() : void
	// deleteAll() : 
	//public List<usuario> findByNome(String nome); // Por estar dentro do framework, não é necessário declarar o "Public" antes do List, assim, o comando fica:
	List<usuario> findByNome(String nome);
	List<usuario> findByNomeLike(String nome);
	usuario findByEmailAndSenha(String email, String senha);
	

}
