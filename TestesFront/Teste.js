let usuarioIdLogado = null;


const btn = document.getElementById("cadastrar");
const form = document.getElementById("form");

//Erros CORS: GERALMENTE É QUANDO O NAVEGADOR NÃO PERMITE QUE UM ENDEREÇO POSSA RECEBER DADOS, MUITO PROVAVELMENTE O ENDEREÇO DA URL DA API PODE ESTAR DIFERENTE E ELE NÃO ACEITA RECEBER OS DADOS.
const url = "http://localhost:8082/usuarios/save";

form.addEventListener("submit", async function(event) {
    
event.preventDefault();
const formData = new FormData(form) //Criamos um objeto a partir dos dados do form
console.log(formData);

const response = await fetch(url, {
    method:"POST",
    headers: {
        "Content-Type": "application/json"
    },
    body:JSON.stringify(Object.fromEntries(formData)) //Todos os dados são convertidos em objetos.
});

if(response.ok){
    const usuario = await response.json();
    window.location.href ="get.html";


    //usuario.id||usuario.usuarioId;

const nomeDoNovoUsuario = usuario.nomeUsuario
const usuarioId = usuario.usuarioId;

//O true da primeira linha é como se fosse uma flag, ela diz para o navegador guardar os novos dados : 
localStorage.setItem('usuarioLogado',true);
localStorage.setItem('emailUsuario',usuario.emailUsuario);
localStorage.setItem('nomedoUsuario',nomeDoNovoUsuario);
localStorage.setItem('usuarioId',usuarioId);



alert(`Cadastro Realizado com Sucesso! Bem vindo <b>${limparInput(nomeDoNovoUsuario)}!</b>Seu acesso a plataforma foi configurado com sucesso !`);

}else{
    alert("Houve um erro inesperado no Sistema!");
}

function limparInput(texto){
    const div = document.createElement('div');
    div.textContent = texto;
    return div.innerHTML;
}   


})
