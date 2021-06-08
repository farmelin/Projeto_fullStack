package br.com.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fullstack.dao.UsuarioDAO;
import br.com.fullstack.model.usuario;

@RestController // para que a classe responda as requisições HTTP
@CrossOrigin("*")  // habilita para que a controller receba requisições externas (fora do servidor)
public class UsuarioController {
	
	@Autowired // transfere para o SpringBoot a responsabilidade sobre este objeto
	private UsuarioDAO dao;
	
	/*
	 * Get => método para ações simples, retorno para grande conjunto de dados;
	 * Get SEMPRE os ados são passados pela "rot", por meio de variáries
	 * @PathVariable
	 */
	
	
	@PostMapping("/pornome")
	public ResponseEntity<List<usuario>> getUserName (@RequestBody usuario usuario){
		/*
		List<usuario> lista = dao.findByNome(usuario.getNome());
		*/
		
		/*
		// responsta utilizando a lógica e não o framework
		List<usuario> lista = (List<usuario>) dao.findAll();
		List<usuario> resposta = new ArrayList<usuario>();
			for (usuario u : lista) {
				if (u.getNome().contains(usuario.getNome())) {
					resposta.add(u);
				}
			}
		if (lista.size()==0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
		 */
		
		List<usuario> lista = dao.findByNomeLike("%" + usuario.getNome() + "%");
		if (lista.size()==0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
		
		
	}
	
	
	
	@GetMapping("/loginget/{e}/{s}")
	public ResponseEntity<usuario> logar(@PathVariable String e, @PathVariable String s){
		usuario resposta = dao.findByEmailAndSenha(e, s);
		if (resposta==null) {
			return ResponseEntity.status(404).build();
		}
		resposta.setSenha("");
		return ResponseEntity.ok(resposta);
	}
	
	/*
	 * Post => método para ações onde os dados são enviados dentro do pacote (envelope)
	 * Post SEMPRE terá no Body (corpo) o conjuto de dados (JSON)
	 * @RequestBody => que recupera o objeto.
	 */
	
	@PostMapping("/login")
	public ResponseEntity<usuario> logar(@RequestBody usuario usuario){
		usuario resposta = dao.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if (resposta==null) {
			return ResponseEntity.status(404).build();
		}
		resposta.setSenha("");
		return ResponseEntity.ok(resposta);
	}	
	
	@PostMapping("/novousuario")
	public ResponseEntity<usuario> add(@RequestBody usuario usuario){
		try {
			dao.save(usuario);
			return ResponseEntity.ok(usuario);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}
	
	// o nome do parametro deve ser o mesmo informado no GetMapping
	@GetMapping("/usuario/{var}")
	public ResponseEntity<usuario> getUser(@PathVariable int var){  
		usuario resposta = dao.findById(var).orElse(null);
		if (resposta==null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(resposta);
	}
	
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<usuario>> getAll(){
		List<usuario> lista = (List<usuario>) dao.findAll();
		if (lista.size()==0) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.ok(lista);
	}

	
	
}
