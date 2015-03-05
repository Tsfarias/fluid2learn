package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

/*Importa biblioteca que contem hashmap*/
import java.util.*;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;

	public Enquirer()
	{
	}


	@Override
	public void connect(IResponder responder)
	{
        /*Implementação de um "dicionario" que armazena as perguntas já feitas*/
        Map<String, String> jaPerguntada = new HashMap<String, String>();
        int animal = 0;
        /*Booleanos para verificar se já encontrou o animal*/
        boolean animalEsperado = true, achou = false;
        /*Strings que armazenam a pergunta lida do db, a resposta lida do db e
         a resposta lida do usuario*/
        String pergunta, respostaEsperada, resposta;

        IBaseConhecimento bc = new BaseConhecimento();
        /*Armazena em um array de string a lista de animais*/
        String listaAnimais[] = bc.listaNomes();
        /*Armazena as linhas do db*/
        IDeclaracao decl;

        while(!achou){
            /*Faz do objeto o animal da listaAnimal na posicao animal.
              Ex. 0 = aranha, 1 = camarao, (...)*/
            obj = bc.recuperaObjeto(listaAnimais[animal]);
            decl = obj.primeira();

            while(decl != null && animalEsperado) {
                pergunta = decl.getPropriedade();
                respostaEsperada = decl.getValor();

                if(jaPerguntada.get(pergunta) == null){
                    /*Pega a pergunta e a resposta já dentro do bd*/
                    resposta = responder.ask(pergunta);
                    /*Insere a pergunta e a resposta no map*/
                    jaPerguntada.put(pergunta,respostaEsperada);
                }else{
                    resposta = jaPerguntada.get(pergunta);
                }


                if (resposta.equalsIgnoreCase(respostaEsperada))
                    /*Armazena no "dicionario" pergunta e resposta*/
                    decl = obj.proxima();
                else
                    animalEsperado = false;
            }
            /*Caso saiu do loop acima sem ser o animal esperado, ele incrementa
            o contador do animal e faz novamente o loop*/
            if(!animalEsperado){
                animal++;
                animalEsperado = true;
            }else{
                achou = true;
            }
        }

        boolean acertei = responder.finalAnswer(listaAnimais[animal]);

		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
