class usuario {
  nickname = ""
  nombre = ""
  apellido = ""
  email = ""
  nacimiento = ""
constructor(nickname, nombre, apellido, email, nacimiento){
  this.nickname = nickname;
  this.nombre = nombre
  this.apellido = apellido
  this.email = email
  this.nacimiento = nacimiento
}
  }

function obtenerUsuarios() {
  const url = ''; 
  
  fetch(url)
    .then(response => {
      if (response.status !== 200) {
        console.error('Error al obtener datos de usuarios. Código de estado: ' + response.status);
        return;
      }
      return response.json();
    })
    .then(data => {
      new usuario(data);
    })
    .catch(error => {
      console.error('Error en la solicitud AJAX: ' + error);
    });
}

obtenerUsuarios();

function crearUsuario(nick, nom, ape, em, nac){
  let user = new usuario;
  user.setNickname(nick)
  user.setNombre(nom)
  user.setApellido(ape)
  user.setEmail(em)
  user.setNacimiento(nac)
}
